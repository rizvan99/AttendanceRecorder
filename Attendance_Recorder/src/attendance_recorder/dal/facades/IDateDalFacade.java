/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package attendance_recorder.dal.facades;

import attendance_recorder.be.Date;
import attendance_recorder.be.Student;
import java.util.List;

/**
 *
 * @author math2
 */
public interface IDateDalFacade {
    public List<Date> getStudentDays(Student s);
    public List<Date> getAllDates();
    public boolean addNewDates(List<Date> dates);
    public boolean updatePresence(Date d);
    public boolean updateAbsenceNote(Date d);
}
