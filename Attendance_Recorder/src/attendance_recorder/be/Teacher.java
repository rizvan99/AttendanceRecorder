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
public class Teacher extends User {
    
    List<Course> courses;

    public Teacher(int id, String firstName, String lastName, String profileName, String password) {
        super(id, firstName, lastName, profileName, password);
        courses = new ArrayList<>();
    }

    public List<Course> getCourses() {
        return courses;
    }

    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }
}
