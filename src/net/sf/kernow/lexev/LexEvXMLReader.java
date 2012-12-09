
package net.sf.kernow.lexev;

import java.io.IOException;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;
import net.sf.kernow.Config;
import net.sf.kernow.transform.CustomEntityResolver;
import org.xml.sax.Attributes;
import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.SAXNotRecognizedException;
import org.xml.sax.SAXNotSupportedException;
import org.xml.sax.XMLReader;
import org.xml.sax.ext.DeclHandler;
import org.xml.sax.ext.LexicalHandler;
import org.xml.sax.helpers.AttributesImpl;
import org.xml.sax.helpers.XMLFilterImpl;
import org.xml.sax.helpers.XMLReaderFactory;


public class LexEvXMLReader extends XMLFilterImpl implements LexicalHandler, DeclHandler {

    private boolean isProcessingDTD;
    private boolean isInCDATASection;
    private boolean isParsingCDATA;   
    private boolean reportDTDEntities = true;
    private boolean reportDoctype = true;
    private boolean markupEntities = true;
    private boolean markupCDATASections = true;
    private boolean markupComments = true;

    private String namespace = "http://andrewjwelch.com/lexev/";
    private String prefix = "lexev";
    
    private XMLReader xmlReader;    

    private Map<String, String> internalEntityMap = new HashMap<String, String>();

    private LexicalHandler saxonsLexicalHandler = new LexicalHandler() {
        /* included to allow debugging without being setup from Saxon */
        @Override
        public void startDTD(String name, String publicId, String systemId) throws SAXException { }
        @Override
        public void endDTD() throws SAXException { }
        @Override
        public void startEntity(String name) throws SAXException { }
        @Override
        public void endEntity(String name) throws SAXException { }
        @Override
        public void startCDATA() throws SAXException { }
        @Override
        public void endCDATA() throws SAXException { }
        @Override
        public void comment(char[] ch, int start, int length) throws SAXException { }
    };

    private DeclHandler saxonsDeclHandler = new DeclHandler() {
        @Override
        public void elementDecl(String name, String model) throws SAXException { }
        @Override
        public void attributeDecl(String eName, String aName, String type, String mode, String value) throws SAXException { }
        @Override
        public void internalEntityDecl(String name, String value) throws SAXException { }
        @Override
        public void externalEntityDecl(String name, String publicId, String systemId) throws SAXException { }
    };

    public LexEvXMLReader() {
        super();
        
        xmlReader = getXMLReader();
                      
//        setMarkupCDATASections(Boolean.parseBoolean(System.getProperty("com.andrewjwelch.lexev.cdata", "true")));
//        setMarkupComments(Boolean.parseBoolean(System.getProperty("com.andrewjwelch.lexev.comments", "true")));
//        setMarkupInlineEntities(Boolean.parseBoolean(System.getProperty("com.andrewjwelch.lexev.inline-entities", "true")));
//        setReportDTDEntities(Boolean.parseBoolean(System.getProperty("com.andrewjwelch.lexev.dtd-entities", "true")));
//        setReportDoctype(Boolean.parseBoolean(System.getProperty("com.andrewjwelch.lexev.doctype", "true")));
//
//        setCdataSectionNamespace(System.getProperty("com.andrewjwelch.doctype.cdataNamespace", ""));

        Config config = Config.getConfig();
        setMarkupCDATASections(config.isLexevCdata());
        setMarkupInlineEntities(config.isLexevEntityRef());
        setReportDoctype(config.isLexevDoctype());
        setReportDTDEntities(config.isLexevDTDEntities());
        setMarkupComments(config.isLexevComments());

        super.setParent(xmlReader);        
    }   
    
    private XMLReader getXMLReader() {
        
        XMLReader reader;
        
        String readerStr = System.getProperty("org.xml.sax.driver");
        
        try {

            if (readerStr == null || readerStr.isEmpty()) {
                reader = XMLReaderFactory.createXMLReader("org.apache.xerces.parsers.SAXParser");
            } else {
                reader = XMLReaderFactory.createXMLReader(readerStr);
            }

            reader.setProperty("http://xml.org/sax/properties/lexical-handler", this);
            reader.setProperty("http://xml.org/sax/properties/declaration-handler", this);

            reader.setFeature("http://apache.org/xml/features/scanner/notify-char-refs", true);
            reader.setFeature("http://apache.org/xml/features/scanner/notify-builtin-refs", true);

            if (Config.getConfig().isUseCachingEntityResolver()) {
                super.setEntityResolver(CustomEntityResolver.getInstance());
            }
            
        } catch (SAXException se) {
            throw new RuntimeException(se);         
        }        
        
        return reader;
    }

    @Override
    public void setProperty(String name, Object value) throws SAXNotRecognizedException, SAXNotSupportedException {        
        if ("http://xml.org/sax/properties/lexical-handler".equals(name)) {
            saxonsLexicalHandler = (LexicalHandler)value;
        } else if ("http://xml.org/sax/properties/lexical-handler".equals(name)) {
            saxonsDeclHandler = (DeclHandler)value;
        } else {
            super.setProperty(name, value);
        }
    }
    
    @Override
    public void parse(InputSource input) throws SAXException, IOException {        
        super.parse(input);        
    }

    @Override
    public void parse(String systemId) throws SAXException, IOException {
        super.parse(systemId);        
    }
    
    @Override
    public void startDTD(String name, String publicId, String systemId) throws SAXException {

        if (isReportDoctype() && publicId != null && systemId != null) {
            super.processingInstruction("doctype-public", publicId);
            super.processingInstruction("doctype-system", systemId);
        }

        isProcessingDTD = true;
        saxonsLexicalHandler.startDTD(name, publicId, systemId);
    }

    @Override
    public void endDTD() throws SAXException {          
        isProcessingDTD = false;
        saxonsLexicalHandler.endDTD();

        if (isReportDoctype() && isReportDTDEntities()) {
            for (String key : internalEntityMap.keySet()) {
                processingInstruction(key, internalEntityMap.get(key));
            }
        }
    }

    @Override
    public void startEntity(String name) throws SAXException {        
        if (!isProcessingDTD) {
            if (isMarkupEntities()) {
                AttributesImpl atts = new AttributesImpl();
                atts.addAttribute("", "name", "name", "string", name);
                super.startElement(namespace, "entity", prefix + ":entity", atts);
            }
        } else if (isMarkupEntities()) {
            super.processingInstruction("entity", name);
        }
        
        saxonsLexicalHandler.startEntity(name);
    }

    @Override
    public void endEntity(String name) throws SAXException {
        if (!isProcessingDTD && isMarkupEntities()) {
            super.endElement(namespace, "entity", prefix + ":entity");
        }
        saxonsLexicalHandler.endEntity(name);
    }

    @Override
    public void startCDATA() throws SAXException {
        isInCDATASection = true;
        saxonsLexicalHandler.startCDATA();        
    }

    
    @Override
    public void endCDATA() throws SAXException { 
        isInCDATASection = false;
        saxonsLexicalHandler.endCDATA();
    }

    
    @Override
    public void comment(char[] ch, int start, int length) throws SAXException {              
        if (!isProcessingDTD && isMarkupComments()) {
            super.startElement(namespace, "comment", prefix + ":comment", new AttributesImpl());
            this.characters(ch, start, length);
            super.endElement(namespace, "comment", prefix + ":comment");
        } else {
            saxonsLexicalHandler.comment(ch, start, length);
        }
    }

    
    @Override
    public void startDocument() throws SAXException {       
        super.startDocument();
    }
    
    
    @Override
    public void endDocument() throws SAXException {
        if (!isInCDATASection) {
            super.endDocument();
        }
    }

    
    @Override
    public void startElement(String uri, String localName, String qName, Attributes atts) throws SAXException {
        super.startElement(uri, localName, qName, atts);        
    }
    
    
    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        if (isInCDATASection && !isParsingCDATA && isMarkupCDATASections()) {
            isParsingCDATA = true;
            try {
                XMLReader cdataXMLReader = getXMLReader();
                cdataXMLReader.setContentHandler(this);                
                String cdataStr = "<" +
                        prefix + ":cdata xmlns=\"" + namespace + "\" xmlns:" + prefix + "=\"" + namespace + "\">" +
                        String.valueOf(ch, start, length).toString() + 
                        "</" + prefix + ":cdata>";
                cdataXMLReader.parse(new InputSource(new StringReader(cdataStr)));
            } catch (IOException ex) {
                ex.printStackTrace();
            } finally {
                isParsingCDATA = false;
            }
        } else {
            super.characters(ch, start, length);            
        }
    }

    @Override
    public void processingInstruction(String target, String data) throws SAXException {
        super.processingInstruction(target, data);
    }


    @Override
    public void attributeDecl(String eName, String aName, String type, String mode, String value) throws SAXException {
        saxonsDeclHandler.attributeDecl(eName, aName, type, mode, value);
    }

    @Override
    public void elementDecl(String name, String model) throws SAXException {
        saxonsDeclHandler.elementDecl(name, model);
    }

    @Override
    public void externalEntityDecl(String name, String publicId, String systemId) throws SAXException {
        internalEntityMap.put("paramEnt_" + name.replaceAll("%", ""), "publicid" + " \"" + publicId + "\"" + " systemid" + " \"" + systemId + "\"");
        saxonsDeclHandler.externalEntityDecl(name, publicId, systemId);
    }

    @Override
    public void internalEntityDecl(String name, String value) throws SAXException {
        internalEntityMap.put("internalEnt_" + name.replaceAll("%", ""), " \"" + value + "\"");
        saxonsDeclHandler.internalEntityDecl(name, value);
    }

    public boolean isMarkupCDATASections() {
        return markupCDATASections;
    }

    public void setMarkupCDATASections(boolean markupCDATASections) {
        this.markupCDATASections = markupCDATASections;
    }

    public boolean isMarkupComments() {
        return markupComments;
    }

    public void setMarkupComments(boolean markupComments) {
        this.markupComments = markupComments;
    }

    public boolean isMarkupEntities() {
        return markupEntities;
    }

    public void setMarkupInlineEntities(boolean markupInlineEntities) {
        this.markupEntities = markupInlineEntities;
    }

    public boolean isReportDTDEntities() {
        return reportDTDEntities;
    }

    public void setReportDTDEntities(boolean reportDTDEntities) {
        this.reportDTDEntities = reportDTDEntities;
    }

    public boolean isReportDoctype() {
        return reportDoctype;
    }

    public void setReportDoctype(boolean reportDoctype) {
        this.reportDoctype = reportDoctype;
    }

    @Override
    public void setEntityResolver(EntityResolver resolver) {
        super.setEntityResolver(resolver);
    }


}
