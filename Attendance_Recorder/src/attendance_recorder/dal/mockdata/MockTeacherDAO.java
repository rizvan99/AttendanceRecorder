/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package attendance_recorder.dal.mockdata;

import attendance_recorder.be.Course;
import attendance_recorder.be.Student;
import attendance_recorder.be.Teacher;
import java.util.ArrayList;
import java.util.List;
import attendance_recorder.dal.facades.ITeacherDalFacade;

/**
 *
 * @author fauxtistic
 */
public class MockTeacherDAO implements ITeacherDalFacade {

    public MockTeacherDAO() {
    }

    @Override
    public List<Teacher> getTeachers() {
        List<Student> students = new ArrayList<Student>();        
        List<Teacher> teachers = new ArrayList<Teacher>();        
        Student s1 = new Student(0, "Mathias", "Birins", "math231k", "test", null);
        Student s2 = new Student(1, "Mock", "Student", "student", "password", null);
        Student s3 = new Student(2, "Peter", "Nielsen", "student2", "test", null);
        Student s4 = new Student(3, "Hans", "Sørensen", "student3", "test", null);
        Student s5 = new Student(4, "Kasper", "Davidsen", "student4", "test", null);
        
        s1.setImageFilePath("/attendance_recorder/images/thiasPic.jpg");
        s1.setAbsence(2);             
        s2.setAbsence(14);             
        s3.setAbsence(7);             
        s4.setAbsence(10);             
        s5.setAbsence(8);             
        
        students.add(s1);
        students.add(s4);
        students.add(s3);
        students.add(s2);
        students.add(s5);
        
        Teacher t1 = new Teacher(0, "Jeppe", "Moritz Led", "jle", "test");
        Teacher t2 = new Teacher(1, "Søren", "Spangsberg Jørgensen", "smsj", "test");
        Teacher t3 = new Teacher(2, "Mock", "Teacher", "teacher", "password");
        
        List<Student> c1Students = new ArrayList<Student>();
        List<Student> c2Students = new ArrayList<Student>();
        
        c1Students.add(s2);
        c1Students.add(s1);
        c1Students.add(s3);
        c2Students.add(s2);
        c2Students.add(s4);
        c2Students.add(s5);
        
        List<Course> courses = new ArrayList<Course>();
        Course c1 = new Course("CS2019");
        Course c2 = new Course ("Mock class");
        courses.add(c1);
        courses.add(c2);
        c1.setStudents(c1Students);
        c2.setStudents(c2Students);
        t1.setCourses(courses);
        t2.setCourses(courses);
        t3.setCourses(courses);
        teachers.add(t1);
        teachers.add(t2);
        teachers.add(t3);
        
        return teachers;
    }
    
    
}
