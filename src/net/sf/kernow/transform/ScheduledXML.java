
package net.sf.kernow.transform;

import java.io.StringReader;
import java.util.TimerTask;
import net.sf.kernow.ui.XMLSandboxTab;
import org.xml.sax.ErrorHandler;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

/**
 * A helper class that allows an XML to be checked after a scheduled amount of time. 
 *
 * @author Andrew Welch
 */
public class ScheduledXML extends TimerTask {
    
    private XMLSandboxTab xmlSandboxTab;
    private XMLReader xmlReader;    
    
    public ScheduledXML(final XMLSandboxTab xmlSandboxTab) {
        try {
            this.xmlSandboxTab = xmlSandboxTab;
            xmlReader = XMLReaderFactory.createXMLReader();            
            xmlReader.setErrorHandler(new ErrorHandler() {
                public void warning(SAXParseException exception) {
                    xmlSandboxTab.processXMLError(exception);
                }

                public void error(SAXParseException exception) {
                    xmlSandboxTab.processXMLError(exception);
                }

                public void fatalError(SAXParseException exception) {
                    xmlSandboxTab.processXMLError(exception);
                }                
            });
        } catch (SAXException ex) {
            ex.printStackTrace();
        }
    }
    
    public void run() {        
        try {            
            xmlReader.parse(new InputSource(new StringReader(xmlSandboxTab.getXML())));
        } catch (SAXParseException se) {
            xmlSandboxTab.processXMLError(se);
        } catch (Exception e) {
            //do nothing
        }                      
    }
    
    
}
