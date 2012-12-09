
package net.sf.kernow.transform;

import java.io.StringReader;
import java.util.TimerTask;
import javax.xml.transform.Source;
import javax.xml.transform.sax.SAXSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import net.sf.kernow.Config;
import net.sf.kernow.Message;
import net.sf.kernow.schema.SchemaValidator;
import net.sf.kernow.ui.TabXMLSchemaSandbox;
import net.sf.saxon.type.SchemaException;
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
public class ScheduledValidation extends TimerTask {
    
    private XMLReader xmlReader;
    private final TabXMLSchemaSandbox xsdSandbox;
    private final Config config;    
    private SchemaValidator schemaValidator;
    private final ErrorHandler xsdPaneErrorHandler;
    
    public ScheduledValidation(final TabXMLSchemaSandbox xsdSandbox) {
        this.xsdSandbox = xsdSandbox;
        this.config = Config.getConfig();         
           
        schemaValidator = new SchemaValidator();
        
        // Direct schema errors to the error box instead of standard out
        xsdPaneErrorHandler = new ErrorHandler() {
            public void warning(SAXParseException exception) {
                xsdSandbox.processXMLSchemaError(exception);
            }

            public void error(SAXParseException exception) throws SAXException {
                xsdSandbox.processXMLSchemaError(exception);
            }

            public void fatalError(SAXParseException exception) throws SAXException {
                xsdSandbox.processXMLSchemaError(exception);
            }            
        };
        
        try {
            xmlReader = XMLReaderFactory.createXMLReader();            
            xmlReader.setErrorHandler(new ErrorHandler() {
                public void warning(SAXParseException exception) {
                    xsdSandbox.processXMLError(exception);
                }

                public void error(SAXParseException exception) {
                    xsdSandbox.processXMLError(exception);
                }

                public void fatalError(SAXParseException exception) {
                    xsdSandbox.processXMLError(exception);
                }                
            });            
        } catch (SAXException ex) {
            ex.printStackTrace();
        }        
    }
    
    public boolean validate() {
        Source xml = new SAXSource(new InputSource(new StringReader(xsdSandbox.getXML())));
        Source xsd = new SAXSource(new InputSource(new StringReader(xsdSandbox.getXSD())));
        
        xml.setSystemId("XML");
        xsd.setSystemId("XSD");                                        
        
        boolean success = false;
        
        try {
            
            schemaValidator.validate(xml, xsd);
            
            success = true;
        } catch (Exception ex) {
            Message.error(ex.toString());
        }        
        
        return success;
    }
    
    public void run() {
        
        Source xsd = new SAXSource(new InputSource(new StringReader(xsdSandbox.getXSD())));
        xsd.setSystemId("XML Schema Sandbox");
        
        try {        
            
            // Parse it as xml first to get any wellformedness errors
            xmlReader.parse(new InputSource(new StringReader(xsdSandbox.getXSD())));
            
            // Now try and compile it
            SchemaFactory factory = schemaValidator.getSchemaFactory();
            factory.setErrorHandler(xsdPaneErrorHandler);
            factory.newSchema(xsd);
            
            //TODO probably don't need two separate steps here, but can't see
            //how to easily get parse errors from Saxon at the moment (probably
            // need to use a SAXTransformerFactory instead)
        } catch (SAXParseException se) {
            xsdSandbox.processXMLSchemaError(se);                   
        } catch (Exception e) {
            // have to process a SchemaException this way...
            if (e instanceof SchemaException) {
                xsdSandbox.processXMLSchemaError((SchemaException)e);                       
            } else {
                // do nothing...
                //System.out.println(e);
            }
        }
   
                      
    }
    
    
}
