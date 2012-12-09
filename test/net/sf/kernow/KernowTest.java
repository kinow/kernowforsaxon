/*
 * KernowTest.java
 * JUnit based test
 *
 * Created on 19 March 2007, 10:00
 */

package net.sf.kernow;

import java.io.File;
import java.io.StringWriter;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import junit.framework.*;
import net.sf.saxon.s9api.Serializer;

/**
 *
 * @author welcha
 */
public class KernowTest extends TestCase {
    
    public KernowTest(String testName) {
        super(testName);
    }
    
    /**
     * Test of getConfig method, of class net.sf.kernow.Kernow.
     */
    public void testGetConfig() {
        
        Kernow kernow = new Kernow();
        Config config = kernow.getConfig();
        
        assertNotNull(config);
    }
    
    /**
     * Test of runSingleFileTransform method, of class net.sf.kernow.Kernow.
     */
    public void testRunSingleFileTransform() {
        
        String inputXML = "test/xml/videos.xml";
        String stylesheet = "test/xslt/identity.xslt";
        String outputFile = "test/output/videos.xml";
        
        Kernow kernow = new Kernow();
        
        boolean result = kernow.runSingleFileTransform(inputXML, stylesheet, outputFile);
        assertTrue(result);
    }
    
    public void testRunSingleFileTransformWithSpaces() {
        System.out.println("testRunSingleFileTransformWithSpaces");
        
        String inputXML = "test/xml/videos.xml";
        String stylesheet = "test/xslt/Transform with space in the name.xslt";
        String outputFile = "test/output/videos.xml";
        
        Kernow kernow = new Kernow();
        
        boolean result = kernow.runSingleFileTransform(inputXML, stylesheet, outputFile);
        assertTrue(result);
    }
    
     public void testRunSingleFileTransformThatHasImportWithSpaces() {
        System.out.println("testRunSingleFileTransformThatHasImportWithSpaces");
        
        String inputXML = "test/xml/videos.xml";
        String stylesheet = "test/xslt/import/Specific.xslt";
        String outputFile = "test/output/videos.xml";
        
        Kernow kernow = new Kernow();
        
        boolean result = kernow.runSingleFileTransform(inputXML, stylesheet, outputFile);
        assertTrue(result);
    }
     
    /**
     * Test of runDirectoryTransform method, of class net.sf.kernow.Kernow.
     */
    public void testRunDirectoryTransform() throws MalformedURLException, URISyntaxException {
        System.out.println("runDirectoryTransform");
        
        String inputDir = "test/xml/valid";
        String stylesheet = "test/xslt/identity.xslt";
        String outputDir = "test/output";
        
        Kernow kernow = new Kernow();
        //Config.getConfig().setMessageLevel(Config.MessageLevel.SILENT);
        boolean expResult = true;
        boolean result = kernow.runDirectoryTransform(inputDir, stylesheet, outputDir);
        assertEquals(expResult, result);
    }
    
    /**
     * Test of runStandaloneTransform method, of class net.sf.kernow.Kernow.
     */
    public void testRunStandaloneTransform() {
        System.out.println("testRunStandaloneTransform");
        
        String stylesheet = "test/xslt/HelloWorld.xslt";
        String initialTemplate = "main";
        String outputFile = "test/output/HelloWorld.xml";
        
        Kernow kernow = new Kernow();
        
        boolean expResult = true;
        boolean result = kernow.runStandaloneTransform(stylesheet, initialTemplate, outputFile);
        assertEquals(expResult, result);
    }
    
    /**
     * Test of runStandaloneTransform method, of class net.sf.kernow.Kernow.
     */
    public void testRunExplicitBasicStandaloneTransform() {
        System.out.println("testRunExplicitBasicStandaloneTransform");
        
        String stylesheet = "test/xslt/HelloWorld.xslt";
        String initialTemplate = "main";
        String outputFile = "test/output/HelloWorld.xml";
        
        Kernow kernow = new Kernow();
        
        Config config = Config.getConfig();
        config.setUseSchemaAware(false);
        
        boolean expResult = true;
        boolean result = kernow.runStandaloneTransform(stylesheet, initialTemplate, outputFile);
        assertEquals(expResult, result);
    }
    
    /**
     * Test of runStandaloneTransform method, of class net.sf.kernow.Kernow.
     */
    public void testRunExplicitSchemaAwareStandaloneTransform() {
        System.out.println("testRunExplicitSchemaAwareStandaloneTransform");
        
        String stylesheet = "test/xslt/HelloWorld_SA.xslt";
        String initialTemplate = "main";
        String outputFile = "test/output/HelloWorld.xml";
        
        Kernow kernow = new Kernow();
        
        Config config = Config.getConfig();
        
        config.setUseSchemaAware(true);
        boolean result = kernow.runStandaloneTransform(stylesheet, initialTemplate, outputFile);
        
        if (config.isSchemaAwareEnabled()) {
            // schema aware transform should run
            assertEquals(result, true);
        } else {
            // transform should fail
            assertEquals(result, false);
        }
    }
   
    public void testStandaloneTransform() throws Exception {
        System.out.println("testStandaloneTransform");
        
        String stylesheet = "test/xslt/base-uri/base uri.xslt";
        String initialTemplate = "main";        
        
        Kernow kernow = new Kernow();

        StringWriter sw = new StringWriter();
        Serializer serializer = new Serializer();
        serializer.setOutputWriter(sw);

        String expResult = "base-uri/base%20uri.xslt";
        
        Source source = new StreamSource(new File(stylesheet));

        kernow.standaloneTransform(source, initialTemplate, serializer);
        
        assertTrue(sw.toString().endsWith(expResult));
    }    
}
