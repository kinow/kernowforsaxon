
package net.sf.kernow.transform;

import java.io.File;
import java.util.HashMap;
import javax.xml.transform.Source;
import javax.xml.transform.sax.SAXSource;
import net.sf.kernow.Config;
import net.sf.kernow.Message;
import net.sf.saxon.s9api.Processor;
import net.sf.saxon.s9api.SaxonApiException;
import net.sf.saxon.s9api.Serializer;
import net.sf.saxon.s9api.XsltExecutable;
import net.sf.saxon.s9api.XsltTransformer;
import org.xml.sax.XMLReader;

/**
 *
 * @author AWelch
 */
public class CachedStylesheetTransformer extends TransformTask {
    
    private final Processor processor;
    
    private Config config;
    
    /**
     * Creates a new instance of CachedStylesheetTransformer.  One instance is created
     * per transform run, therefore the options, switches and resolvers are all shared
     * for each of the transforms.
     */
    public CachedStylesheetTransformer(Processor processor) {
        super();
        
        this.config = Config.getConfig();
        this.processor = processor;                             

        if (config.isUseCachingURIResolver()) {
            processor.getUnderlyingConfiguration().setURIResolver(new CustomURIResolver());
        }

        SwitchSetter.setSwitches(processor.getUnderlyingConfiguration());
    }
    
    /**
     * Transforms a single XML file using a compiled stylesheet.  This should 
     * be used when running several transformations with the same stylesheet.
     * @param sourceXMLFile     The path to the XML file that is to be transformed
     * @param compiledStylesheet The compiled stylesheet     
     * @param stylesheetPath    The path to the stylesheet, used only for setting parameters
     * @param result            The destination for the result of the transform   
     * @param params            The parameters for the transform  
     */
    public boolean transform(Source sourceXML, XsltExecutable xsltExecutable, Serializer serializer, HashMap<String, Object> params) {
        
        boolean success = false;          
        
        try {
            XsltTransformer xsltTransformer = xsltExecutable.load();

            SAXSource saxSource = new SAXSource(SAXSource.sourceToInputSource(sourceXML));

            if (config.isUseCachingEntityResolver()) {
                XMLReader xmlReader = processor.getUnderlyingConfiguration().getSourceParser();
                xmlReader.setEntityResolver(CustomEntityResolver.getInstance());
                saxSource.setXMLReader(xmlReader);
            }

            xsltTransformer.setBaseOutputURI(((File)serializer.getOutputDestination()).toURI().toString());

            xsltTransformer.setSource(saxSource);
            
            xsltTransformer.setDestination(serializer);

            xsltTransformer.setErrorListener(CustomErrorListener.getInstance());
            xsltTransformer.setMessageListener(CustomMessageListener.getInstance());

            setParameters(xsltTransformer, params);
            setControllerSwitches(xsltTransformer.getUnderlyingController(), config);
            
            xsltTransformer.transform();
            
            success = true;

            Message.info("Transformed: " + sourceXML.getSystemId());

        } catch (SaxonApiException ex) {
            Message.exception(ex, false);
        }
        
        return success;
    }

}
