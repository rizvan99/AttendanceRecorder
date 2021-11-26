/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package attendance_recorder.dal.dao;

import attendance_recorder.be.Teacher;
import attendance_recorder.dal.dbaccess.DBSettings;
import attendance_recorder.dal.facades.ITeacherDalFacade;
import java.io.IOException;
import java.util.List;
import com.microsoft.sqlserver.jdbc.SQLServerException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author fauxtistic
 */
public class TeacherDBDAO implements ITeacherDalFacade {

    private DBSettings dbs;

    public TeacherDBDAO() {
        try {
            dbs = new DBSettings();            
        } catch (IOException e) {

        }
        
    }        
    
    @Override
    public List<Teacher> getTeachers() {
        List<Teacher> teachers = new ArrayList();
        try (Connection con = dbs.getConnection()) {            
            String sql = "SELECT * FROM Teacher;";
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                int id = rs.getInt("ID");
                String firstName = rs.getString("Name");
                String lastName = rs.getString("LastName");
                String profileName = rs.getString("Username");                             
                String password = rs.getString("Password");                             

                Teacher teacher = new Teacher(id, firstName, lastName, profileName, password);                
                
                teachers.add(teacher);
            }           
        } catch (SQLServerException ex) {
            Logger.getLogger(TeacherDBDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(TeacherDBDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return teachers;
    }
}
