/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package attendance_recorder.bll;

import attendance_recorder.be.Teacher;
import attendance_recorder.dal.dao.TeacherDBDAO;
import java.util.List;

/**
 *
 * @author math2
 */
public class TeacherBllManager {
    
    private final TeacherDBDAO tdb;

    public TeacherBllManager() {
        tdb = new TeacherDBDAO();
    }

    public List<Teacher> getTeachers() {
        return tdb.getTeachers();
    }
    
    
}
