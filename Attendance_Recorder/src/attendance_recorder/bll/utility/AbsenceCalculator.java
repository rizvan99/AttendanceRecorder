/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package attendance_recorder.bll.utility;

import attendance_recorder.be.Date;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;

/**
 *
 * @author math2
 */
public class AbsenceCalculator {
    
    private int mondays;
    private int tuesdays;
    private int wednesdays;
    private int thursdays;
    private int fridays;
    
    private double  totalAbsence;

    public AbsenceCalculator(List<Date> dates) {
        dayCounter(dates);
    }
   
    
    
    
    
    public int getAbsentMondays(){
        
        return mondays;
    }
    
    public int getAbsentTuedays(){
        
        return tuesdays;
    }
    
    public int getAbsentWedesdays(){
       
        return wednesdays;
    }
    
    public int getAbsentThursdays(){
        
        return thursdays;
    }
    
    public int getAbsentFridays(){
        
        return fridays;
    }
    
    public void dayCounter(List<Date> d){
        
        for (Date localDate : d) {       
    
        if(LocalDate.parse(localDate.getDate()).getDayOfWeek().equals(DayOfWeek.MONDAY)){
            if(!localDate.isIsPresent())
                mondays++;
        }
        
        if(LocalDate.parse(localDate.getDate()).getDayOfWeek().equals(DayOfWeek.TUESDAY)){
            if(!localDate.isIsPresent())
                tuesdays++;
        }
        
        if(LocalDate.parse(localDate.getDate()).getDayOfWeek().equals(DayOfWeek.WEDNESDAY)){
            if(!localDate.isIsPresent())
                wednesdays++;
        }
        
        if(LocalDate.parse(localDate.getDate()).getDayOfWeek().equals(DayOfWeek.THURSDAY)){
            if(!localDate.isIsPresent())
                thursdays++;
        }
        
        if(LocalDate.parse(localDate.getDate()).getDayOfWeek().equals(DayOfWeek.FRIDAY)){
            if(!localDate.isIsPresent())
                fridays++;
        }

    }     
    }
  
    public double calculateAbsencePercentage(List<Date> dates){
        
        return Math.round(getTotalAbsence(dates)/dates.size()*100);        
    }
    
    public double getTotalAbsence(List<Date> dates){
        
        totalAbsence = 0;
        
        for (Date date : dates) {
            if (!date.isIsPresent()) {
                totalAbsence++;
            }
        }
        return totalAbsence;
    }
    
    
}
