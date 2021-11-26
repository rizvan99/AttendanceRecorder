/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import attendance_recorder.be.Date;
import attendance_recorder.be.Student;
import attendance_recorder.dal.mockdata.MockDateManagerDAO;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author math2
 */
public class DateMockTest {
    
    public DateMockTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {

        
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}
    
    @Test
    public void testGetStudentDatesTrue(){
                
        boolean hasPassed = false;
        
        MockDateManagerDAO dateManager = new MockDateManagerDAO();
        Student dummy = new Student(1, null, null, null, null, null);
        
        List<Date> testDates = dateManager.getStudentDays(dummy);
        
        for (Date testDate : testDates) {
            if(testDate.getStudentId() == dummy.getId()){
                hasPassed = true;
            }
            else{
                hasPassed = false;
            }
        }
        
        assertEquals(hasPassed, true);
    }
    
    @Test
    public void testGetStudentDatesFalse(){
                
        boolean hasPassed = false;
        
        MockDateManagerDAO dateManager = new MockDateManagerDAO();
        Student dummy = new Student(2, null, null, null, null, null);
        
        List<Date> testDates = dateManager.getStudentDays(dummy);
        
        for (Date testDate : testDates) {
            if(testDate.getStudentId() == 1){
                hasPassed = true;
            }
            else{
                hasPassed = false;
            }
        }
        
        assertEquals(hasPassed, false);
    }
    
    @Test
    public void testUpdatePresenceSuccess(){
        
       MockDateManagerDAO dateManager = new MockDateManagerDAO(); 
       
       Date testDate = new Date("2020-02-13", 0, false);
       
        assertEquals(dateManager.updatePresence(testDate), true);
    }
    @Test
    public void testUpdatePresenceCorrectly(){
        
       MockDateManagerDAO dateManager = new MockDateManagerDAO(); 
       Student testStudent = new Student(1, null, null, null, null, null);
       
       Date testDate = new Date("2020-02-13", 1, false);
       
       dateManager.updatePresence(testDate);
       List<Date> dates = dateManager.getStudentDays(testStudent);
       
       assertEquals(testDate, dates.get(1));
    }
}
