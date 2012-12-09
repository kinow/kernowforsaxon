/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.sf.kernow.transform;

import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.transform.sax.SAXSource;
import javax.xml.transform.stream.StreamSource;
import junit.framework.TestCase;
import net.sf.saxon.s9api.Processor;
import net.sf.saxon.s9api.SaxonApiException;
import net.sf.saxon.s9api.Serializer;
import net.sf.saxon.s9api.XsltCompiler;
import net.sf.saxon.s9api.XsltExecutable;
import net.sf.saxon.s9api.XsltTransformer;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

/**
 *
 * @author andrew
 */
public class CustomEntityResolverTest extends TestCase {
    
    public CustomEntityResolverTest(String testName) {
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

    public void testEntityResolver() {
        try {
            Processor processor = new Processor(false);
            XsltCompiler xsltCompiler = processor.newXsltCompiler();
            XsltExecutable xsltExecutable = xsltCompiler.compile(new StreamSource(new File("test\\xslt\\identity.xslt")));
            XsltTransformer xsltTransformer = xsltExecutable.load();
            
            Serializer serializer = new Serializer();
            serializer.setOutputStream(System.out);
            xsltTransformer.setDestination(serializer);

            XMLReader xmlReader = new org.apache.xerces.parsers.SAXParser();
            xmlReader.setEntityResolver(CustomEntityResolver.getInstance());
            SAXSource saxSource = new SAXSource(xmlReader, new InputSource("test\\xml\\xmlwithentityref.xml"));
            xsltTransformer.setSource(saxSource);

            xsltTransformer.transform();

        } catch (SaxonApiException ex) {
            Logger.getLogger(CustomEntityResolverTest.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
