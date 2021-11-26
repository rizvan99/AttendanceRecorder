/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package attendance_recorder.dal.dao;

import attendance_recorder.be.Date;
import attendance_recorder.be.Student;
import attendance_recorder.dal.dbaccess.DBSettings;
import attendance_recorder.dal.facades.IDateDalFacade;
import com.microsoft.sqlserver.jdbc.SQLServerException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author math2
 */
public class DateDBDAO implements IDateDalFacade{

    private DBSettings dbs;

    public DateDBDAO() {
        try {
            dbs = new DBSettings();

        } catch (IOException e) {

        }

    }

    @Override
    public List<Date> getStudentDays(Student s) {
        List<Date> days = new ArrayList();
        try ( Connection con = dbs.getConnection()) {
            String sql = "SELECT * FROM Date WHERE StudentID = ?;";
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, s.getId());
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                String date = rs.getString("date");

                int presenceCheck = rs.getInt("presence");
                boolean presence = true;
                if (presenceCheck == 1) {
                    presence = true;
                } else if (presenceCheck == 0) {
                    presence = false;
                }

                int studentId = rs.getInt("studentId");
                String absenceNote = rs.getString("AbsenceNote");

                Date day = new Date(date, studentId, presence);
                day.setAbsenceNote(absenceNote);
                days.add(day);
            }

        } catch (SQLServerException ex) {
            Logger.getLogger(CourseDBDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(CourseDBDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return days;
    }

    @Override
    public boolean updatePresence(Date d) {
        int presence = 0;
        try ( Connection con = dbs.getConnection()) {
            String sql = "UPDATE Date Set presence = ? WHERE StudentId = ? AND Date = ?";

            PreparedStatement pstmt = con.prepareStatement(sql);

            if (d.isIsPresent()) {
                presence = 1;
            } else if (!d.isIsPresent()) {
                presence = 0;
            }

            pstmt.setInt(1, presence);
            pstmt.setInt(2, d.getStudentId());
            pstmt.setString(3, d.getDate());

            int updatedRows = pstmt.executeUpdate();

            if (updatedRows > 0) {
                return true;
            }

        } catch (SQLServerException ex) {
            Logger.getLogger(CourseDBDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(CourseDBDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    @Override
    public boolean updateAbsenceNote(Date d) {
        try ( Connection con = dbs.getConnection()) {
            String sql = "UPDATE Date SET AbsenceNote = ? WHERE StudentId = ? AND Date = ?";
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setString(1, d.getAbsenceNote());
            pstmt.setInt(2, d.getStudentId());
            pstmt.setString(3, d.getDate());

            int updatedRows = pstmt.executeUpdate();
            if (updatedRows > 0) {
                return true;
            }

        } catch (SQLServerException ex) {
            Logger.getLogger(CourseDBDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(CourseDBDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    @Override
    public List<Date> getAllDates() {
        try ( Connection con = dbs.getConnection()) {

            List<Date> dates = new ArrayList<>();

            String sql = "SELECT * FROM Date";
            PreparedStatement pstm = con.prepareStatement(sql);

            ResultSet rs = pstm.executeQuery();
            while (rs.next()) {
                String date = rs.getString("date");

                int presenceCheck = rs.getInt("presence");
                boolean presence = true;
                if (presenceCheck == 1) {
                    presence = true;
                } else if (presenceCheck == 0) {
                    presence = false;
                }
                int studentId = rs.getInt("studentId");
                Date d = new Date(date, studentId, presence, null);
                dates.add(d);
            }

            return dates;
        } catch (SQLServerException ex) {
            Logger.getLogger(DateDBDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(DateDBDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }

    @Override
    public boolean addNewDates(List<Date> dates) {
        try ( Connection con = dbs.getConnection()) {
            int saveMePlease = 1;
            for (Date d1 : dates) {

                String sql = "INSERT INTO Date "
                        + "VALUES (?,?,0,'');";
                PreparedStatement pstm = con.prepareStatement(sql);

                pstm.setString(1, d1.getDate());
                pstm.setInt(2, saveMePlease);

                if (saveMePlease < 4) {
                    saveMePlease++;
                }
                pstm.executeUpdate();
            }
        } catch (SQLServerException ex) {
            System.out.println("Dates where already added");
        } catch (SQLException ex) {
            Logger.getLogger(DateDBDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
}
