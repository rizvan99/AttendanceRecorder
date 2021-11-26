/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package attendance_recorder.bll;

import attendance_recorder.be.Student;
import attendance_recorder.dal.dao.StudentDBDAO;
import java.util.List;

/**
 *
 * @author math2
 */
public class StudentBllManager {

    private final StudentDBDAO studentManager;
    
    public StudentBllManager(){
        studentManager = new StudentDBDAO();      
    }
    
    public List<Student> getAllStudents() {
        return studentManager.getAllStudents();
    }
    
}
