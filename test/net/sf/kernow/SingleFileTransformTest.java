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
public class SingleFileTransformTest extends TestCase {
    
    private Config config;
    
    public SingleFileTransformTest(String testName) {
        super(testName);
    }

    /**
     * Test of main method, of class net.sf.kernow.DirectoryTransform.
     */
    public void testMain() {        
        String[] args = new String[3];
        args[0] = "test/xml/videos with space.xml";
        args[1] = "test/xslt/Transform with space in the name.xslt";
        args[2] = "test/output/video.xml";
        
        try {           
            SingleFileTransform.main(args);            
            assertTrue(true);
        } catch (Exception ex) {
            ex.printStackTrace();
            fail();
        }
    }            
}
