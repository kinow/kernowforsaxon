/*
 * CustomURIResolver.java
 *
 * Created on 01 December 2005, 17:21
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package net.sf.kernow.transform;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.concurrent.ConcurrentHashMap;
import javax.xml.transform.Source;
import javax.xml.transform.URIResolver;
import javax.xml.transform.sax.SAXSource;
import net.sf.kernow.Config;
import net.sf.kernow.Message;
import net.sf.kernow.util.FileUtil;
import net.sf.kernow.util.IOUtils;
import net.sf.saxon.om.NodeInfo;
import net.sf.saxon.s9api.DocumentBuilder;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

/**
 *
 * @author AWelch
 */
public class CustomURIResolver implements URIResolver {
    
    private ConcurrentHashMap<String, NodeInfo> referencedDocs;
    private Config config;
    private DocumentBuilder documentBuilder;
    
    public CustomURIResolver() {
        referencedDocs = new ConcurrentHashMap<String, NodeInfo>();
        config = Config.getConfig();
        documentBuilder = config.getProcessor().newDocumentBuilder();
    }
    
    @Override
    public Source resolve(String href, String base) {
        
        try {
        
            if (href.endsWith(".xslt") || href.endsWith(".xsl")) {
                return null;
            }
            
            URI targetURI = URI.create(FileUtil.escapeSpaces(base)).resolve(FileUtil.escapeSpaces(href));
            
            if (referencedDocs.get(targetURI.toString()) != null) {

                // This doc has already been stored, so return it
                return getFromCache(targetURI.toString());

            } else {
                
                File targetFile = new File(targetURI);                                

                NodeInfo nodeInfo;

                File f = new File(config.getLocalCacheDir(), targetFile.getName());

                if (!config.getLocalCacheDir().trim().equals("") && f.exists()) {

                    targetFile = f;

                    if (!config.isSuppressURIandEntityMessages()) {
                        Message.info("Using local copy of: " + f.getName());
                    }
                } 
                    
                nodeInfo = documentBuilder.build(getSAXSource(targetFile.toURI())).getUnderlyingNode();
                
                referencedDocs.put(targetURI.toString(), nodeInfo);

                if (!config.isSuppressURIandEntityMessages()) {
                    Message.info("The URI resolver has cached " + targetFile.getName() + "\n");
                }

                return getFromCache(targetURI.toString());

            }
        
        } catch (Exception ex) {
            Message.error(ex.toString());
        }
        // fallback to not caching
        return null;
    }
    
    private SAXSource getSAXSource(URI uri) throws SAXException {
        XMLReader xmlReader = XMLReaderFactory.createXMLReader();
        xmlReader.setEntityResolver(CustomEntityResolver.getInstance());
        SAXSource ss = new SAXSource(xmlReader, new InputSource(uri.toString()));                
        return ss;        
    }
    
    private Source getFromCache(String key) throws SAXException { 
//        XMLReader xmlReader = XMLReaderFactory.createXMLReader();
//        xmlReader.setEntityResolver(CustomEntityResolver.getInstance());
//        SAXSource ss = new SAXSource(xmlReader, new InputSource(new ByteArrayInputStream(referencedDocs.get(key))));        
//        ss.setSystemId(key);
//        return ss;
        return referencedDocs.get(key);
    }
    
    public static byte[] getBytesFromFile(File file) throws IOException {
        InputStream is = null;
        byte[] bytes;
        
        try {
            is = new FileInputStream(file);
    
            // Get the size of the file
            long length = file.length();

            if (length > Integer.MAX_VALUE) {
                // File is too large
                throw new RuntimeException("File is too large: " + file.getName());
            }

            // Create the byte array to hold the data
            bytes = new byte[(int)length];

            // Read in the bytes
            int offset = 0;
            int numRead = 0;
            while (offset < bytes.length
                   && (numRead=is.read(bytes, offset, bytes.length-offset)) >= 0) {
                offset += numRead;
            }

            // Ensure all the bytes have been read in
            if (offset < bytes.length) {
                throw new IOException("Could not completely read file "+file.getName());
            }
        } finally {
            // Close the input stream and return bytes
            IOUtils.closeStream(is);
        }

        return bytes;
    }    
}
