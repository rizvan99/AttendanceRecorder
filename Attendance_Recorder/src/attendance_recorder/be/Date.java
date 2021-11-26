/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package attendance_recorder.be;

import java.time.LocalDate;
import java.util.Objects;

/**
 *
 * @author math2
 */
public class Date {
    private String date;
    private int studentId;
    private boolean isPresent;
    private String absenceNote;

    public Date(String date, int studentId, boolean isPresent) {
        this.date = date;
        this.studentId = studentId;
        this.isPresent = isPresent;
    }
    public Date(String date, int studentId, boolean isPresent, String note) {
        this.date = date;
        this.studentId = studentId;
        this.isPresent = isPresent;
        this.absenceNote = note;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public boolean isIsPresent() {
        return isPresent;
    }

    public void setIsPresent(boolean isPresent) {
        this.isPresent = isPresent;
    }
    
    public LocalDate convertToLocalDate(String dateToBeConverted){
       
        LocalDate ld = LocalDate.parse(dateToBeConverted);
        
        return ld;
    }

    public String getAbsenceNote() {
        return absenceNote;
    }

    public void setAbsenceNote(String absenceNote) {
        this.absenceNote = absenceNote;
    }    
    
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof Date)) {
        return false;
        }
        if (obj == this) {
            return true;
        }
        
        return this.date.equals(((Date) obj).getDate()) && this.studentId == (((Date) obj).getStudentId());
    }    

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 67 * hash + Objects.hashCode(this.date);
        hash = 67 * hash + this.studentId;
        return hash;
    }
    
    @Override
    public String toString() {
        return "Date{" + "date=" + date + ", studentId=" + studentId + ", isPresent=" + isPresent + '}';
    }
      
}
