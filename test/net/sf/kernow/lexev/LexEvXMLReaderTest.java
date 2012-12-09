/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.sf.kernow.lexev;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringWriter;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import junit.framework.TestCase;
import net.sf.kernow.Config;
import net.sf.kernow.transform.DirectoryTransformer;
import net.sf.kernow.transform.SingleFileTransformer;
import net.sf.saxon.s9api.Serializer;

/**
 *
 * @author andrew
 */
public class LexEvXMLReaderTest extends TestCase {
    
    public LexEvXMLReaderTest(String testName) {
        super(testName);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    /**
     * Test of setCdataSectionNamespace method, of class LexEvXMLReader.
     */
    public void testLexevSingleFile() {
        System.out.println("testLexev");

        String inputPath = "test/xml/lexev/xmlwithentityref.xml";
        String ss = "test/xslt/identity.xslt";

        Source input = new StreamSource(new File(inputPath));
        Source stylesheet = new StreamSource(new File(ss));

        StringWriter sw = new StringWriter();
        Serializer serializer = new Serializer();
        serializer.setOutputWriter(sw);

        Config config = Config.getConfig();
        config.setUseLexEv(true);
        config.setLexevCdata(true);
        config.setLexevComments(true);
        config.setLexevDoctype(true);
        config.setLexevEntityRef(true);

        new SingleFileTransformer().transform(input, stylesheet, serializer, null);

        String result = sw.toString();

        assertTrue(result.indexOf("<?internalEnt_nbsp") != -1);

        assertTrue(result.indexOf("lexev:comment") != -1);

        assertTrue(result.indexOf("lexev:entity") != -1);

        assertTrue(result.indexOf("lexev:cdata") != -1);
        
        System.out.println(result);
    }


    public void testLexevDirectory() {
        System.out.println("testLexevDirectory");

        String ss = "test/xslt/identity.xslt";

        File inputDir = new File("test/xml/lexev/");
        File outputDir = new File("test/output/lexev/");

        Source stylesheet = new StreamSource(new File(ss));

        Config config = Config.getConfig();
        config.setUseLexEv(true);
        config.setLexevCdata(true);
        config.setLexevComments(true);
        config.setLexevDoctype(true);
        config.setLexevEntityRef(true);

        new DirectoryTransformer().runDirectoryTransform(inputDir, stylesheet, outputDir);

        StringBuffer result = new StringBuffer();

        try {
            BufferedReader in = new BufferedReader(new FileReader(new File("test/output/lexev/xmlwithentityref.xml")));
            String str;
            while ((str = in.readLine()) != null) {
                result.append(str);
            }
            in.close();

            assertTrue(result.indexOf("<?internalEnt_nbsp") != -1);

            assertTrue(result.indexOf("lexev:comment") != -1);

            assertTrue(result.indexOf("lexev:entity") != -1);

            assertTrue(result.indexOf("lexev:cdata") != -1);
        } catch (IOException e) {
            fail(e.getMessage());
        }

        System.out.println(result);
    }
}
