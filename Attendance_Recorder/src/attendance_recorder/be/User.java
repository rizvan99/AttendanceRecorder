/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package attendance_recorder.be;

/**
 *
 * @author fauxtistic
 */
public abstract class User {
    
    private String firstName;
    private String lastName;
    private String username; //should be unique id in database
    private String password;
    private int id;
    
    public User(int id, String firstName, String lastName, String profileName, String password) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = profileName;
        this.password = password;
    }

    public User(String firstName, String lastName, int id) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.id = id;
    }
    
    
    
    public int getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getProfileName() {
        return username;
    }

    public void setProfileName(String profileName) {
        this.username = profileName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return id + ", " + firstName + " " + lastName + ", " + username + ", " + password;
    }
    
    
    
}
