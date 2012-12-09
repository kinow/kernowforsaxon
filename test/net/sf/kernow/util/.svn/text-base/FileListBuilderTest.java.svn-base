/*
 * FileListBuilderTest.java
 * JUnit based test
 *
 * Created on 01 August 2007, 11:40
 */

package net.sf.kernow.util;

import junit.framework.*;
import java.io.File;
import java.util.ArrayList;

/**
 *
 * @author welcha
 */
public class FileListBuilderTest extends TestCase {
    
    public FileListBuilderTest(String testName) {
        super(testName);
    }

    /**
     * Test of buildFileList method, of class net.sf.kernow.util.FileListBuilder.
     */
    public void testBuildFileList() {
        System.out.println("buildFileList");
        
        File dir = new File("test/xml");
        String allowedInputSuffixes = ".xml";
        boolean recurseSubdirectories = true;

        ArrayList<File> result = FileListBuilder.buildFileList(dir, allowedInputSuffixes, recurseSubdirectories);
        assertTrue(result.size() > 0);
    }

    public void testBuildFileListRecurseFalse() {
        System.out.println("testBuildFileListRecurseFalse");
        
        File dir = new File("test");
        String allowedInputSuffixes = ".xml";
        boolean recurseSubdirectories = false;

        ArrayList<File> result = FileListBuilder.buildFileList(dir, allowedInputSuffixes, recurseSubdirectories);
        assertTrue(result.size() == 0);
    }    

    public void testBuildFileListRecurseTrue() {
        System.out.println("testBuildFileListRecurseTrue");
        
        File dir = new File("test");
        String allowedInputSuffixes = ".xml";
        boolean recurseSubdirectories = true;

        ArrayList<File> result = FileListBuilder.buildFileList(dir, allowedInputSuffixes, recurseSubdirectories);
        assertTrue(result.size() > 0);
    } 
    
    public void testGetAllowedInputSuffixes() {
        System.out.println("getAllowedInputSuffixes");
        
        String allowedInputSuffixes = ".xml, .html, .htm";
        
        ArrayList<String> expResult = new ArrayList<String>();
        expResult.add(".xml");
        expResult.add(".html");
        expResult.add(".htm");
        
        ArrayList<String> result = FileListBuilder.getAllowedInputSuffixes(allowedInputSuffixes);
        assertEquals(expResult, result);        
    }
    
    public void testGetAllowedInputSuffixesReturnsXMLWhenNoSuppliedSuffixes() {
        System.out.println("testGetAllowedInputSuffixesReturnsXMLWhenNoSuppliedSuffixes");
        
        String allowedInputSuffixes = "";
        
        ArrayList<String> expResult = new ArrayList<String>();
        expResult.add(".xml");
        
        ArrayList<String> result = FileListBuilder.getAllowedInputSuffixes(allowedInputSuffixes);
        assertEquals(expResult, result);        
    }    
    
}
