/*
 * AverageTimeTest.java
 * JUnit based test
 *
 * Created on 25 June 2007, 14:57
 */

package net.sf.kernow.util;

import junit.framework.*;
import java.util.ArrayList;

/**
 *
 * @author welcha
 */
public class AverageTimeTest extends TestCase {
    
    public AverageTimeTest(String testName) {
        super(testName);
    }

    /**
     * Test of getTotalTime method, of class net.sf.kernow.util.AverageTime.
     */
    public void testGetTotalTimeIgnoreFirst() {
        System.out.println("getTotalTimeIgnoreFirst");
        
        ArrayList<Long> times = new ArrayList<Long>();
        times.add(Long.valueOf(1));
        times.add(Long.valueOf(2));
        times.add(Long.valueOf(3));
        int ignoreFirst = 1;
        
        long expResult = 5L;
        long result = AverageTime.getTotalTime(times, ignoreFirst);
        assertEquals(expResult, result);
    }
    
    /**
     * Test of getTotalTime method, of class net.sf.kernow.util.AverageTime.
     */
    public void testGetTotalTimeIgnoreNone() {
        System.out.println("getTotalTimeIgnoreNone");
        
        ArrayList<Long> times = new ArrayList<Long>();
        times.add(new Long(1));
        times.add(new Long(2));
        times.add(new Long(3));
        int ignoreFirst = 0;
        
        long expResult = 6L;
        long result = AverageTime.getTotalTime(times, ignoreFirst);
        assertEquals(expResult, result);
    }    

    /**
     * Test of getAverageTime method, of class net.sf.kernow.util.AverageTime.
     */
    public void testGetAverageTimeIgnoreFirst() {
        System.out.println("getAverageTimeIgnoreFirst");
        
        ArrayList<Long> times = new ArrayList<Long>();
        times.add(new Long(1));
        times.add(new Long(2));
        times.add(new Long(3));
        int ignoreFirst = 1;
        
        long expResult = 2L;
        long result = AverageTime.getAverageTime(times, ignoreFirst);
        
        assertEquals(expResult, result);
    }
    
    public void testGetAverageTimeIgnoreNone() {
        System.out.println("getAverageTimeIgnoreNone");
        
        ArrayList<Long> times = new ArrayList<Long>();
        times.add(Long.valueOf(1));
        times.add(Long.valueOf(2));
        times.add(Long.valueOf(3));
        int ignoreFirst = 0;
        
        long expResult = 2L;
        long result = AverageTime.getAverageTime(times, ignoreFirst);
        assertEquals(expResult, result);
    }
        
    
}
