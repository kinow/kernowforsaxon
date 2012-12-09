/*
 * DirectoryTransformTest.java
 * JUnit based test
 *
 * Created on 09 March 2007, 15:29
 */

package net.sf.kernow;

import junit.framework.*;

/**
 *
 * @author welcha
 */
public class DirectoryTransformTest extends TestCase {
    
    public DirectoryTransformTest(String testName) {
        super(testName);
    }

    /**
     * Test of main method, of class net.sf.kernow.DirectoryTransform.
     */
    public void testMain() {        
        String[] args = new String[3];
        args[0] = "test/xml/valid";
        args[1] = "test/xslt/identity.xslt";
        args[2] = "test/output";
       
        try {
            
            DirectoryTransform.main(args);
            
            assertTrue(true);
        } catch (Exception ex) {
            ex.printStackTrace();
            fail();
        }
    }    
}
