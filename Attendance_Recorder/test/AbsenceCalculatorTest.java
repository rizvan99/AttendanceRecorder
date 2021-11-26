/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import attendance_recorder.be.Date;
import attendance_recorder.bll.utility.AbsenceCalculator;
import attendance_recorder.be.Student;
import attendance_recorder.dal.mockdata.MockDateManagerDAO;
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
public class AbsenceCalculatorTest {
    
    public AbsenceCalculatorTest() {
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

    @Test
    public void calculateTotalAbsence1(){
        Student testStudent = new Student(1, null, null, null, null, null);
        MockDateManagerDAO dateManager = new MockDateManagerDAO();
        
        AbsenceCalculator ac = new AbsenceCalculator(dateManager.getStudentDays(testStudent));
        
        double absence = ac.calculateAbsencePercentage(dateManager.getStudentDays(testStudent));
        
        assertEquals(0, absence, 1E-3);
    }
    
    @Test
    public void calculateTotalAbsence2(){
        Student testStudent = new Student(1, null, null, null, null, null);
        MockDateManagerDAO dateManager = new MockDateManagerDAO();
        
        dateManager.updatePresence(new Date("2020-02-13", 1, false));
        
        AbsenceCalculator ac = new AbsenceCalculator(dateManager.getStudentDays(testStudent));
        
        double absence = ac.getTotalAbsence(dateManager.getStudentDays(testStudent));
        
        assertEquals(33, absence, 1E-3);
    }
}
