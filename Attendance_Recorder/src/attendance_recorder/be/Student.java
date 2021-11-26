/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package attendance_recorder.be;

/**
 *
 * @author math2
 */
public class Student extends User {
    private int absence;
    private String imageFilePath;
    private boolean present;
       
    public Student(int id, String firstName, String lastName, String profileName, String password, String imageFilePath) {
        super(id, firstName, lastName, profileName, password);
        this.imageFilePath = (imageFilePath != null) ? imageFilePath : "/attendance_recorder/images/defaultuserimage1.png";
        present = false;
    }
     
    public int getAbsence() {     
        return absence;       
    }

    public void setAbsence(int absence) {
        this.absence = absence;
    }

    public String getImageFilePath() {
        return imageFilePath;
    }

    public void setImageFilePath(String imageFilePath) {
        this.imageFilePath = imageFilePath;
    }

    public boolean isPresent() {
        return present;
    }

    public void setPresent(boolean present) {
        this.present = present;
    }
    
    
    

    
                   
    
}
