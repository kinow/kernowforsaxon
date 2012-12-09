/*
 * ParameterExtractorTest.java
 * JUnit based test
 *
 * Created on 28 June 2007, 11:11
 */

package net.sf.kernow.util;

import java.io.File;
import java.net.URI;
import junit.framework.*;
import java.util.HashMap;

/**
 *
 * @author welcha
 */
public class ParameterExtractorTest extends TestCase {
    
    public ParameterExtractorTest(String testName) {
        super(testName);
    }

    /**
     * Test of getParametersForStylesheet method, of class net.sf.kernow.util.ParameterExtractor.
     */
    public void testGetParametersForStylesheet() {       
        
        URI stylesheetURI = new File("test/xslt/transform with space in the name.xslt").toURI();
        
        HashMap<String, Object> result = ParameterExtractor.getParametersForStylesheet(stylesheetURI);
        assertTrue(result.size() == 1);
    }
    
}
