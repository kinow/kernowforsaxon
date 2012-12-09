/*
 * CustomURIResolverTest.java
 * JUnit based test
 *
 * Created on 16 July 2007, 11:06
 */

package net.sf.kernow.transform;

import java.net.MalformedURLException;
import junit.framework.*;
import java.io.File;
import java.io.IOException;
import javax.xml.transform.Source;

/**
 *
 * @author welcha
 */
public class CustomURIResolverTest extends TestCase {
    
    public CustomURIResolverTest(String testName) {
        super(testName);
    }

    public void testGetBytesFromFile() {
        System.out.println("testGetBytesFromFile");
        
        String path = "test/xml/videos.xml";
        
        byte[] result = null;
        try {
            result = CustomURIResolver.getBytesFromFile(new File(path));
        } catch (IOException ex) {
            fail(ex.toString());
        }  
        
        assertNotNull(result);        
    }
    
    public void testGetBytesFromFileWithSpace() {
        System.out.println("testGetBytesFromFileWithSpace");
        
        String path = "test/xml/videos with space.xml";
        
        byte[] result = null;
        try {
            result = CustomURIResolver.getBytesFromFile(new File(path));
        } catch (IOException ex) {
            fail(ex.toString());
        }  
        
        assertNotNull(result);        
    }
    
    public void testResolve_BaseWithFile_HrefWithoutSpace() throws MalformedURLException {
        System.out.println("testResolve_BaseWithFile_HrefWithoutSpace");
        
        String href = "videos.xml";
        String base = getAbsoluteURL("test/xml/videos.xml");
        CustomURIResolver instance = new CustomURIResolver();

        Source result = instance.resolve(href, base);
  
        assertNotNull(result);
    }    

    public void testResolve_BaseWithFile_HrefWithSpace() throws MalformedURLException {
        System.out.println("testResolve_BaseWithFile_HrefWithSpace");
        
        String href = "videos with space.xml";
        String base = getAbsoluteURL("test/xml/videos.xml");
        CustomURIResolver instance = new CustomURIResolver();
                          
        Source result = instance.resolve(href, base);
  
        assertNotNull(result);
    }
    
    public void testResolve_BaseWithFileAndSpace_HrefWithSpace() throws MalformedURLException {
        System.out.println("testResolve_BaseWithFileAndSpace_HrefWithSpace");
        
        String href = "videos with space.xml";
        String base = getAbsoluteURL("test/xml/videos with space.xml");
        CustomURIResolver instance = new CustomURIResolver();
                          
        Source result = instance.resolve(href, base);
  
        assertNotNull(result);
    }   
    
     public void testResolve_BaseWithFile_HrefWithFileWithoutSpace() throws MalformedURLException {
        System.out.println("testResolve_BaseWithFile_HrefWithFileWithoutSpace");
        
        String href = getAbsoluteURL("test/xml/videos.xml");
        String base = getAbsoluteURL("test/xml/videos.xml");
        
        //String expected = "file:/c:/netbeansWorkspace/kernow/test/xml/videos.xml";
        CustomURIResolver instance = new CustomURIResolver();
                          
        Source result = instance.resolve(href, base);
        assertNotNull(result);
        // TODO: Why this is commented?  -fg
        //assertEquals(expected, result.getSystemId());
    }    

    public void testResolve_BaseWithFile_HrefWithFileAndWithSpace() throws MalformedURLException {
        System.out.println("testResolve_BaseWithFile_HrefWithFileAndWithSpace");
        
        String href = getAbsoluteURL("test/xml/videos with space.xml");
        String base = getAbsoluteURL("test/xml/videos.xml");
        CustomURIResolver instance = new CustomURIResolver();
                          
        Source result = instance.resolve(href, base);
  
        assertNotNull(result);
    }
    
    public void testResolve_BaseWithFileAndSpace_HrefWithFileAndWithSpace() throws MalformedURLException {
        System.out.println("testResolve_BaseWithFileAndSpace_HrefWithFileAndWithSpace");
        
        String href = getAbsoluteURL("test/xml/videos with space.xml");
        String base = getAbsoluteURL("test/xml/videos with space.xml");
        CustomURIResolver instance = new CustomURIResolver();

        Source result = instance.resolve(href, base);
  
        assertNotNull(result);
    }    
    
     public void testResolve_BaseWithFile_HrefWithFileTripleSlashWithoutSpace() throws MalformedURLException {
        System.out.println("testResolve_BaseWithFile_HrefWithFileTripleSlashWithoutSpace");
        
        String href = "file:///" + getAbsoluteURL("test/xml/videos.xml").substring(6);
        String base = getAbsoluteURL("test/xml/videos.xml");
        CustomURIResolver instance = new CustomURIResolver();

        Source result = instance.resolve(href, base);
  
        assertNotNull(result);
    }    

    public void testResolve_BaseWithFile_HrefWithFileTripleSlashAndWithSpace() throws MalformedURLException {
        System.out.println("testResolve_BaseWithFile_HrefWithFileTripleSlashAndWithSpace");
        
        String href = "file:///" + getAbsoluteURL("test/xml/videos with space.xml").substring(6);
        String base = getAbsoluteURL("test/xml/videos.xml");
        CustomURIResolver instance = new CustomURIResolver();
                          
        Source result = instance.resolve(href, base);
  
        assertNotNull(result);
    }
    
    public void testResolve_BaseWithFileAndSpace_HrefWithFileTripleSlashAndWithSpace() throws MalformedURLException {
        System.out.println("testResolve_BaseWithFileAndSpace_HrefWithFileTripleSlashAndWithSpace");
        
        String href = "file:///" + getAbsoluteURL("test/xml/videos with space.xml").substring(6);
        String base = getAbsoluteURL("test/xml/videos with space.xml");
        CustomURIResolver instance = new CustomURIResolver();
                          
        Source result = instance.resolve(href, base);
  
        assertNotNull(result);
    } 
    
    public void testResolve_BaseWithFileTripleSlashAndSpace_HrefWithFileTripleSlashAndWithSpace() throws MalformedURLException {
        System.out.println("testResolve_BaseWithFileTripleSlashAndSpace_HrefWithFileTripleSlashAndWithSpace");
        
        String href = "file:///" + getAbsoluteURL("test/xml/videos with space.xml").substring(6);
        String base = "file:///c:/should_be_ignored.xml";
        CustomURIResolver instance = new CustomURIResolver();
                          
        Source result = instance.resolve(href, base);
  
        assertNotNull(result);
    }     

    /**
     * Return a string representing an absolute URL for the path in parameter.
     * 
     * The path is a usual file path.  If it is relative, it is relative to the
     * project directory (so to access files under the test directory, use a
     * relative path beginning with "test/...").
     */
    private String getAbsoluteURL(String path) throws MalformedURLException {
        return new File(path).toURI().toURL().toString();
    }
}
