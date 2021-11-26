/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package attendance_recorder.bll;

import attendance_recorder.be.Date;
import attendance_recorder.be.Student;
import attendance_recorder.dal.dao.DateDBDAO;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author math2
 */
public class DateBllManager {

    private final DateDBDAO dm;

    public DateBllManager() {
    
        dm = new DateDBDAO();
    }
    
    
    
    public List<Date> getDates(Student s) {
        
        List<Date> dates = new ArrayList<>();
        
        for (Date date : dm.getStudentDays(s)) {
            LocalDate ld = LocalDate.parse(date.getDate());
            if(!ld.getDayOfWeek().equals(DayOfWeek.SUNDAY) && !ld.getDayOfWeek().equals(ld.getDayOfWeek().SATURDAY)){
                dates.add(date);
            }
        }
        return dates;
    }

    public void updatePresence(Date updatedDate) {
         
        dm.updatePresence(updatedDate);
    
    }
    
    public boolean updateAbsenceNote(Date updatedDate) {
        return dm.updateAbsenceNote(updatedDate);
    }

    public boolean addCurrentDate(List<Date> addCurrentDate) {
        return dm.addNewDates(addCurrentDate);
    }
    
}
