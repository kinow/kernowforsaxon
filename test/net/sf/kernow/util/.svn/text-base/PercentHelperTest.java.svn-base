/*
 * PercentHelperTest.java
 * JUnit based test
 *
 * Created on 09 March 2007, 13:16
 */

package net.sf.kernow.util;

import junit.framework.*;

/**
 *
 * @author welcha
 */
public class PercentHelperTest extends TestCase {
    
    public PercentHelperTest(String testName) {
        super(testName);
    }

    /**
     * Test of getPercentComplete method, of class net.sf.kernow.util.PercentHelper.
     */
    public void testAHalf() {       
        
        int doneSoFar = 50;
        int total = 100;
        
        int expResult = 50;
        
        int result = PercentHelper.getPercentComplete(doneSoFar, total);
        assertEquals(expResult, result);                
    }
    
    /**
     * Test of getPercentComplete method, of class net.sf.kernow.util.PercentHelper.
     */
    public void testAThird() {       
        
        int doneSoFar = 3;
        int total = 10;
        
        int expResult = 30;
        
        int result = PercentHelper.getPercentComplete(doneSoFar, total);
        assertEquals(expResult, result);                
    }    
    
    /**
     * Test of getPercentComplete method, of class net.sf.kernow.util.PercentHelper.
     */
    public void testNone() {       
        
        int doneSoFar = 0;
        int total = 1;
        
        int expResult = 0;
        
        int result = PercentHelper.getPercentComplete(doneSoFar, total);
        assertEquals(expResult, result);                
    }     
    
    /**
     * Test of getPercentComplete method, of class net.sf.kernow.util.PercentHelper.
     */
    public void testAll() {       
        
        int doneSoFar = 1;
        int total = 1;
        
        int expResult = 100;
        
        int result = PercentHelper.getPercentComplete(doneSoFar, total);
        assertEquals(expResult, result);    
    }
}
