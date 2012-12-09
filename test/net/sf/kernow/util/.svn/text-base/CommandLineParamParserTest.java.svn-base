/*
 * CommandLineParamParserTest.java
 * JUnit based test
 *
 * Created on 09 March 2007, 11:36
 */

package net.sf.kernow.util;

import junit.framework.*;
import java.util.HashMap;

/**
 *
 * @author welcha
 */
public class CommandLineParamParserTest extends TestCase {
    
    public CommandLineParamParserTest(String testName) {
        super(testName);
    }

    /**
     * Basic test
     */
    public void testBasicSingleParam() {                
        String[] args = {"foo=bar"};
        int startIndex = 0;
        
        HashMap<String, Object> expResult = new HashMap<String, Object>();
        expResult.put("foo", (Object)"bar");
        HashMap<String, Object> result = CommandLineParamParser.parseParams(args, startIndex);
        assertEquals(expResult, result);        
    }
    
    /**
     * Basic test, multiple params
     */
    public void testBasicMultipleParams() {                
        String[] args = {"foo=bar", "baz=bop"};
        int startIndex = 0;
        
        HashMap<String, Object> expResult = new HashMap<String, Object>();
        expResult.put("foo", (Object)"bar");
        expResult.put("baz", (Object)"bop");
        HashMap<String, Object> result = CommandLineParamParser.parseParams(args, startIndex);
        assertEquals(expResult, result);        
    }
    
    /**
     * Test of parseParams method, of class net.sf.kernow.util.CommandLineParamParser.
     */
    public void testStartIndex() {                
        String[] args = {"input.xml", "stylesheet.xslt", "output.xhtml", "foo=bar"};
        int startIndex = 3;
        
        HashMap<String, Object> expResult = new HashMap<String, Object>();
        expResult.put("foo", (Object)"bar");
        HashMap<String, Object> result = CommandLineParamParser.parseParams(args, startIndex);
        assertEquals(expResult, result);        
    }
    
    /**
     * Test of parseParams method, of class net.sf.kernow.util.CommandLineParamParser.
     */
    public void testMissingValue() {                
        String[] args = {"foo"};
        int startIndex = 0;
        
        HashMap<String, Object> result = CommandLineParamParser.parseParams(args, startIndex);
        assertNull(result);        
    }
    
    /**
     * Test of parseParams method, of class net.sf.kernow.util.CommandLineParamParser.
     */
    public void testEmpty() {                
        String[] args = new String[0];
        int startIndex = 0;
        
        HashMap<String, Object> expResult = new HashMap<String, Object>();
        HashMap<String, Object> result = CommandLineParamParser.parseParams(args, startIndex);
        assertEquals(expResult, result);        
    }    
    
}
