/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package attendance_recorder.bll;

import attendance_recorder.be.Course;
import attendance_recorder.be.Student;
import attendance_recorder.be.Teacher;
import attendance_recorder.dal.dao.CourseDBDAO;
import java.util.List;

/**
 *
 * @author math2
 */
public class CourseBllManager {

    private final CourseDBDAO courseManager;

    public CourseBllManager() {

        courseManager = new CourseDBDAO();
    }

    public List<Course> getTeachersCourse(Teacher t) {

        return courseManager.getTeacherCourses(t);
    }

    public List<Student> getStudentsInCourse(Course course) {
        return courseManager.getCourseStudents(course);
    }

}
