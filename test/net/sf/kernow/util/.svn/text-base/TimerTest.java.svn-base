/*
 * TimerTest.java
 * JUnit based test
 *
 * Created on 16 November 2006, 15:07
 */

package net.sf.kernow.util;

import junit.framework.*;

/**
 *
 * @author welcha
 */
public class TimerTest extends TestCase {
    
    public TimerTest(String testName) {
        super(testName);
    }

    /**
     * Test of getTimeTaken method, of class net.sf.kernow.util.Timer.
     */
    
    public void testGetDurationFor0Millis() {             
        long duration = 0L;        
        String expResult = "0 ms";
        String result = Timer.getDurationInWords(duration);
        System.out.println("Expected: \"" + expResult + "\" actual: \"" + result + "\"");
        assertEquals(expResult, result);       
    }
    
    public void testGetDurationFor1Millis() {             
        long duration = 1L;        
        String expResult = "1 ms";
        String result = Timer.getDurationInWords(duration);
        System.out.println("Expected: \"" + expResult + "\" actual: \"" + result + "\"");
        assertEquals(expResult, result);       
    }    
    
    public void testGetDurationFor1sec50Millis() {    
        long duration = 1050L;        
        String expResult = "1 second 50 ms";
        String result = Timer.getDurationInWords(duration);
        System.out.println("Expected: \"" + expResult + "\" actual: \"" + result + "\"");        
        assertEquals(expResult, result);       
    }   
    
    public void testGetDurationFor2secs150Millis() {    
        long duration = 2150L;        
        String expResult = "2 seconds 150 ms";
        String result = Timer.getDurationInWords(duration);
        System.out.println("Expected: \"" + expResult + "\" actual: \"" + result + "\"");        
        assertEquals(expResult, result);       
    }       
    
    public void testGetDurationFor10secs() {                
        long duration = 10000L;        
        String expResult = "10 seconds";
        String result = Timer.getDurationInWords(duration);
        System.out.println("Expected: \"" + expResult + "\" actual: \"" + result + "\"");
        assertEquals(expResult, result);       
    }        
    
    public void testGetDurationFor1min() {                
        long duration = 60000L;        
        String expResult = "1 minute exactly";
        String result = Timer.getDurationInWords(duration);
        System.out.println("Expected: \"" + expResult + "\" actual: \"" + result + "\"");
        assertEquals(expResult, result);       
    }    
    
    public void testGetDurationFor2mins() {                
        long duration = 120000L;        
        String expResult = "2 minutes exactly";
        String result = Timer.getDurationInWords(duration);
        System.out.println("Expected: \"" + expResult + "\" actual: \"" + result + "\"");
        assertEquals(expResult, result);       
    }
    
    public void testGetDurationFor2mins1sec() {                
        long duration = 121000L;        
        String expResult = "2 minutes 1 second";
        String result = Timer.getDurationInWords(duration);
        System.out.println("Expected: \"" + expResult + "\" actual: \"" + result + "\"");
        assertEquals(expResult, result);       
    }
    
    public void testGetDurationFor2mins2secs() {                
        long duration = 122000L;        
        String expResult = "2 minutes 2 seconds";
        String result = Timer.getDurationInWords(duration);
        System.out.println("Expected: \"" + expResult + "\" actual: \"" + result + "\"");
        assertEquals(expResult, result);       
    }  
    
    public void testGetDurationFor3mins3secs3ms() {                
        long duration = 183003L;        
        String expResult = "3 minutes 3 seconds";
        String result = Timer.getDurationInWords(duration);
        System.out.println("Expected: \"" + expResult + "\" actual: \"" + result + "\"");
        assertEquals(expResult, result);       
    }  

    public void testGetDurationFor29secs3ms() {                
        long duration = 29003L;        
        String expResult = "29 seconds 3 ms";
        String result = Timer.getDurationInWords(duration);
        System.out.println("Expected: \"" + expResult + "\" actual: \"" + result + "\"");
        assertEquals(expResult, result);       
    }  
    
    public void testGetDurationFor30secs3msDoesntShowMS() {                
        long duration = 30003L;        
        String expResult = "30 seconds";
        String result = Timer.getDurationInWords(duration);
        System.out.println("Expected: \"" + expResult + "\" actual: \"" + result + "\"");
        assertEquals(expResult, result);       
    } 
    
    public void testGetDurationFor1hour() {                
        long duration = 3600000L;        
        String expResult = "1 hour exactly";
        String result = Timer.getDurationInWords(duration);
        System.out.println("Expected: \"" + expResult + "\" actual: \"" + result + "\"");
        assertEquals(expResult, result);       
    } 
    
    public void testGetDurationFor1hour2msDoesntShowMS() {                
        long duration = 3600002L;        
        String expResult = "1 hour exactly";
        String result = Timer.getDurationInWords(duration);
        System.out.println("Expected: \"" + expResult + "\" actual: \"" + result + "\"");
        assertEquals(expResult, result);       
    } 
    
    public void testGetDurationFor1hour2mins2secs() {                
        long duration = 3722000L;        
        String expResult = "1 hour 2 minutes 2 seconds";
        String result = Timer.getDurationInWords(duration);
        System.out.println("Expected: \"" + expResult + "\" actual: \"" + result + "\"");
        assertEquals(expResult, result);       
    } 
    
    public void testGetDurationFor2hours() {                
        long duration = 7200000;        
        String expResult = "2 hours exactly";
        String result = Timer.getDurationInWords(duration);
        System.out.println("Expected: \"" + expResult + "\" actual: \"" + result + "\"");
        assertEquals(expResult, result);       
    }   
    
    public void testGetDurationFor2hours2mins2secs() {                
        long duration = 7322000L;        
        String expResult = "2 hours 2 minutes 2 seconds";
        String result = Timer.getDurationInWords(duration);
        System.out.println("Expected: \"" + expResult + "\" actual: \"" + result + "\"");
        assertEquals(expResult, result);       
    }         
}
