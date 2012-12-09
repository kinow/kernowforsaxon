/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.sf.kernow.util;

import java.io.StringReader;
import java.io.StringWriter;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Result;
import javax.xml.transform.Transformer;
import javax.xml.transform.sax.SAXTransformerFactory;
import javax.xml.transform.sax.TransformerHandler;
import javax.xml.transform.stream.StreamResult;
import net.sf.saxon.Controller;
import net.sf.saxon.value.Whitespace;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

/**
 *
 * @author awelch
 */
public class XMLFormatter {

    public static String formatXML(String xml) {
        try {
            SAXTransformerFactory tFactory = (SAXTransformerFactory)new net.sf.saxon.TransformerFactoryImpl();
            TransformerHandler handler = tFactory.newTransformerHandler();
            Transformer transformer = handler.getTransformer();
            
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");

            ((Controller)transformer).getConfiguration().setStripsWhiteSpace(Whitespace.IGNORABLE);
            XMLReader xmlReader = XMLReaderFactory.createXMLReader();
            StringWriter sw = new StringWriter();
            Result result = new StreamResult(sw);
            handler.setResult(result);
            
            xmlReader.setContentHandler(handler);
            xmlReader.setProperty("http://xml.org/sax/properties/lexical-handler", handler);
            
            xmlReader.parse(new InputSource(new StringReader(xml)));
            
            return sw.toString();
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        
        return null;
    }
}
