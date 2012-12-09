/*
 * StandaloneTransformTest.java
 * JUnit based test
 *
 * Created on 11 June 2007, 14:24
 */

package net.sf.kernow;

import junit.framework.*;

/**
 *
 * @author welcha
 */
public class StandaloneTransformTest extends TestCase {
    
    public StandaloneTransformTest(String testName) {
        super(testName);
    }

    /**
     * Test of main method, of class net.sf.kernow.StandaloneTransform.
     */
    public void testMain() {
        System.out.println("main");
        
        String[] args = {"test/xslt/HelloWorld.xslt", "main", "test/output/HelloWorld.txt"};
        
        try {            
            StandaloneTransform.main(args);            
            assertTrue(true);
        } catch (Exception ex) {
            ex.printStackTrace();
            fail();
        }
    }
    
}
