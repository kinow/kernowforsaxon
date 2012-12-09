/*
 * FileUtilTest.java
 * JUnit based test
 *
 * Created on 08 August 2007, 11:21
 */

package net.sf.kernow.util;

import java.io.File;
import junit.framework.*;
import java.net.URI;
import javax.xml.transform.Source;

/**
 *
 * @author welcha
 */
public class FileUtilTest extends TestCase {
    
    public FileUtilTest(String testName) {
        super(testName);
    }

    public void testEscapeSpaces() {
        System.out.println("escapeSpaces");
        
        String s = "foo bar";
        
        String expResult = "foo%20bar";
        String result = FileUtil.escapeSpaces(s);
        assertEquals(expResult, result);        
    }

     public void testCreateURLFromString() throws Exception {
        System.out.println("createURLFromString");
        
        String s = "C:\\foo\\bar";
        
        String expResult = "file:/C:/foo/bar";
        URI result = FileUtil.createURI(s);
        assertEquals(expResult, result.toString());
    }

    public void testCreateURLFromStringWithSpaces() throws Exception {
        System.out.println("createURLFromStringWithSpace");
        
        String s = "C:\\foo baz\\bar";
        
        String expResult = "file:/C:/foo%20baz/bar";
        URI result = FileUtil.createURI(s);
        assertEquals(expResult, result.toString());
    }
 
    public void testCreateSourceFromString() throws Exception {
        System.out.println("createSourceFromString");
        
        String s = "C:\\foo\\bar";
        
        Source result = FileUtil.createSource(s);
        
        String expectedSystemID = "file:/C:/foo/bar";
        
        assertEquals(expectedSystemID, result.getSystemId());
    }

    public void testCreateSourceFromStringWithSpaces() throws Exception {
        System.out.println("createSourceFromStringWithSpaces");
        
        String s = "C:\\foo baz\\bar";
        
        Source result = FileUtil.createSource(s);
        
        String expectedSystemID = "file:/C:/foo%20baz/bar";
                
        assertEquals(expectedSystemID, result.getSystemId());        
    }
    
    public void testCreateSourceFromHTTPString() throws Exception {
        System.out.println("createSourceFromHTTPString");
        
        String s = "http://www.foo.com";
        
        Source result = FileUtil.createSource(s);
        
        String expectedSystemID = "http://www.foo.com";
                
        assertEquals(expectedSystemID, result.getSystemId());        
    }
  
}
