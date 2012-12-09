
package net.sf.kernow.transform;

import java.io.StringReader;
import java.util.TimerTask;
import javax.xml.transform.ErrorListener;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.sax.SAXSource;
import net.sf.kernow.Config;
import net.sf.kernow.Message;
import net.sf.kernow.ui.TabXSLTSandbox;
import net.sf.saxon.s9api.Processor;
import net.sf.saxon.s9api.SaxonApiException;
import net.sf.saxon.s9api.Serializer;
import net.sf.saxon.s9api.XsltCompiler;
import net.sf.saxon.s9api.XsltExecutable;
import net.sf.saxon.s9api.XsltTransformer;
import org.xml.sax.ErrorHandler;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

/**
 * A helper class that allows an XSLT to be compiled after a scheduled amount of time. 
 *
 * @author Andrew Welch
 */
public class ScheduledXSLT extends TimerTask {
        
    private final TabXSLTSandbox xsltSandbox;
    private final Config config;    
    private Processor processor;
    private final XMLParserErrorHandler xmlParserErrorHandler = new ScheduledXSLT.XMLParserErrorHandler();
    private final XSLTSandboxErrorListener xsltSandboxErrorListener = new ScheduledXSLT.XSLTSandboxErrorListener();
    
    public ScheduledXSLT(final TabXSLTSandbox xsltSandbox) {
        this.xsltSandbox = xsltSandbox;
        this.config = Config.getConfig(); 
                
        processor = config.getProcessor();
        processor.getUnderlyingConfiguration().setErrorListener(xsltSandboxErrorListener);
        
               
//        xmlReader = config.getXMLReader();
//
//        // direct xml parse errors to the error box instead of standard out
//        xmlReader.setErrorHandler(new ErrorHandler() {
//            public void warning(SAXParseException exception) {
//                xsltSandbox.processXSLTError(exception);
//            }
//
//            public void error(SAXParseException exception) {
//                xsltSandbox.processXSLTError(exception);
//            }
//
//            public void fatalError(SAXParseException exception) {
//                xsltSandbox.processXSLTError(exception);
//            }
//        });
    }
    
    public boolean transform() {
        
        boolean success = false;                
        
        try {
            XsltCompiler xsltCompiler = processor.newXsltCompiler();

            xsltCompiler.setErrorListener(xsltSandboxErrorListener);

            XsltExecutable xsltExecutable = xsltCompiler.compile(new SAXSource(new InputSource(new StringReader(xsltSandbox.getXSLT()))));
            
            Serializer serializer = new Serializer();
            serializer.setOutputStream(config.getOutputStream());
            
            XsltTransformer xsltTransformer = xsltExecutable.load();

            xsltTransformer.setErrorListener(xsltSandboxErrorListener);

            XMLReader xmlReader = processor.getUnderlyingConfiguration().getSourceParser();
            xmlReader.setEntityResolver(CustomEntityResolver.getInstance());

            SAXSource inputXML = new SAXSource(xmlReader, new InputSource(new StringReader(xsltSandbox.getXML())));

            xsltTransformer.setSource(inputXML);
            xsltTransformer.setDestination(serializer);

            SwitchSetter.setSwitches(xsltTransformer.getUnderlyingController().getConfiguration());
            
            xsltTransformer.transform();
            
            success = true;        
        } catch (TransformerFactoryConfigurationError ex) {
            Message.error(ex.toString());
        } catch (SaxonApiException ex) {
            Message.exception(ex, false);            
        } catch (Exception ex) {
            Message.exception(ex, true);            
        }        
        
        return success;
    }
    
    public void run() {

        try {        

            XMLReader xmlReader = XMLReaderFactory.createXMLReader();

            xmlReader.setErrorHandler(xmlParserErrorHandler);
            xmlReader.setEntityResolver(CustomEntityResolver.getInstance());
            
            // Parse it as xml first to get any wellformedness errors
            xmlReader.parse(new InputSource(new StringReader(xsltSandbox.getXSLT())));
           
            // Now try and compile it
            XsltCompiler xsltCompiler = processor.newXsltCompiler();
            xsltCompiler.setErrorListener(xsltSandboxErrorListener);

            xsltCompiler.compile(new SAXSource(new InputSource(new StringReader(xsltSandbox.getXSLT()))));
                        
        } catch (SAXParseException se) {
            xsltSandbox.processXSLTError(se);
        } catch (SaxonApiException sae) {
            //do nothing here as its done in the errorhandler...
        } catch (Exception e) {
            //do nothing
        }
   
                      
    }

    public class XMLParserErrorHandler implements ErrorHandler {
        @Override
        public void warning(SAXParseException exception) throws SAXException {
            xsltSandbox.processXSLTError(exception);
        }

        @Override
        public void error(SAXParseException exception) throws SAXException {
            xsltSandbox.processXSLTError(exception);
        }

        @Override
        public void fatalError(SAXParseException exception) throws SAXException {
            xsltSandbox.processXSLTError(exception);
        }
    }

    public class XSLTSandboxErrorListener implements ErrorListener {
                
        @Override
        public void warning(TransformerException exception) {
            xsltSandbox.processXSLTError(exception);
        }

        @Override
        public void error(TransformerException exception) {
            xsltSandbox.processXSLTError(exception);
        }

        @Override
        public void fatalError(TransformerException exception) {
            xsltSandbox.processXSLTError(exception);
        }
    }
}



