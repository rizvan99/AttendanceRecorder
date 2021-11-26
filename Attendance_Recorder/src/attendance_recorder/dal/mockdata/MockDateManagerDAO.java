/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package attendance_recorder.dal.mockdata;

import attendance_recorder.be.Date;
import attendance_recorder.be.Student;
import attendance_recorder.dal.facades.IDateDalFacade;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 *
 * @author math2
 */
public class MockDateManagerDAO implements IDateDalFacade{

    List<Date> dates;
    
    public MockDateManagerDAO() {
        
        initialize();
    }
    
    private void initialize(){
        
        dates = new ArrayList<>();
        
        Date d1 = new Date("2020-02-12", 1, true);
        Date d2 = new Date("2020-02-13", 1, true);
        Date d3 = new Date("2020-02-14", 1, true);
        Date d4 = new Date("2020-02-12", 2, true);
        Date d5 = new Date("2020-02-13", 2, true);
        Date d6 = new Date("2020-02-14", 2, true);
        
        dates.add(d1);
        dates.add(d2);
        dates.add(d3);
        dates.add(d4);
        dates.add(d5);
        dates.add(d6);
    }
    
    
    @Override
    public List<Date> getStudentDays(Student s) {
    
        List<Date> studentDates = new ArrayList<>();
        
        for (Date d : dates) {
            if (d.getStudentId() == s.getId()) {
                studentDates.add(d);
            }
        }
        return studentDates;
    }

    @Override
    public List<Date> getAllDates() {
        return dates;
    }

    @Override
    public boolean addNewDates(List<Date> dates) {
        return dates.addAll(dates);
    }

    @Override
    public boolean updatePresence(Date d) {
        
        for (Date date : dates) {
            if(d.getDate().equals(date.getDate())){
                date = d;
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean updateAbsenceNote(Date d) {
        return true;
    }

}
    
    
    

