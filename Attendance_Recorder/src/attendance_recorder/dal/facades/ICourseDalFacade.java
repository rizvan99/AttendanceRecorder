/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package attendance_recorder.dal.facades;

import attendance_recorder.be.Course;
import attendance_recorder.be.Student;
import attendance_recorder.be.Teacher;
import java.util.List;

/**
 *
 * @author math2
 */
public interface ICourseDalFacade {
    
    public List<Course> getTeacherCourses(Teacher teacher);
    public List<Student> getCourseStudents(Course course);
    
}
