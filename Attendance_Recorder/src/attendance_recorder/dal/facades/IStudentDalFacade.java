/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package attendance_recorder.dal.facades;

import attendance_recorder.be.Student;
import java.util.List;

/**
 *
 * @author fauxtistic
 */
public interface IStudentDalFacade {
    
    public List<Student> getAllStudents();
    
}
