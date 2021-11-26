/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package attendance_recorder.be;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author fauxtistic
 */
public class Course {

    private String name;
    int teacherId;
    int studentId;

    List<Student> students;
    List<Teacher> teachers;
    
    public Course(String name) {
        this.name = name;
        students = new ArrayList<>();
    }    

    public Course(String name, int teacherId, int studentId) {
        this.name = name;
        this.teacherId = teacherId;
        this.studentId = studentId;
    }
    
    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    
    public int getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(int id) {
        this.teacherId = id;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }
    
    @Override
    public String toString() {
        return name;
    }
    
    
    
    
        
}
