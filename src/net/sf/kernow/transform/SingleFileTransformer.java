/*
 * SingleFileTransformer.java
 *
 * Created on 31 December 2005, 10:59
 *
 */

package net.sf.kernow.transform;

import java.io.File;
import javax.xml.transform.ErrorListener;
import javax.xml.transform.Source;
import javax.xml.transform.SourceLocator;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.sax.SAXSource;
import net.sf.kernow.internal.CancelObserver;
import net.sf.kernow.internal.Cancellable;
import net.sf.kernow.Config;
import net.sf.kernow.Message;
import net.sf.kernow.util.Timer;
import net.sf.saxon.Controller;
import net.sf.saxon.s9api.MessageListener;
import net.sf.saxon.s9api.Processor;
import net.sf.saxon.s9api.QName;
import net.sf.saxon.s9api.Serializer;
import net.sf.saxon.s9api.XdmNode;
import net.sf.saxon.s9api.XsltCompiler;
import net.sf.saxon.s9api.XsltExecutable;
import net.sf.saxon.s9api.XsltTransformer;
import org.xml.sax.XMLReader;

/**
 *
 * @author ajwelch
 */
public class SingleFileTransformer extends TransformTask implements CancelObserver {
    
    private Processor processor;
    private Config config;    
        
    /** Creates a new instance of SingleFileTransformer */
    public SingleFileTransformer() {    
        this.config = Config.getConfig();
        processor = config.getProcessor();
    }

    public boolean transform(Source input, Source stylesheet, Serializer serializer, String initialTemplate) {

        if (stylesheet == null) {
            Message.error("Stylesheet is null");
            return false;
        }
        if (serializer == null) {
            Message.error("Output is null");
            return false;
        }
        if (input == null && initialTemplate == null) {
            Message.error("Both input and inital template are null");
            return false;
        }

        boolean successful = false;
        try {                   
            
            XsltCompiler xsltCompiler = processor.newXsltCompiler();
            xsltCompiler.setErrorListener(CustomErrorListener.getInstance());
            XsltExecutable xsltExecutable = xsltCompiler.compile(stylesheet);

            XsltTransformer xsltTransformer = xsltExecutable.load();

            Controller controller = xsltTransformer.getUnderlyingController();
            
            /* Sort out where result-document should be output, either relative to the given
               output path (if one is specified), or relative to the stylesheet (if one isn't) */
            if (serializer.getOutputDestination() instanceof File) {
                xsltTransformer.setBaseOutputURI(((File)serializer.getOutputDestination()).toURI().toString());
            }

            // Set the switches, parameters and im and it
            SwitchSetter.setSwitches(controller.getConfiguration());
            setParameters(xsltTransformer, Params.getParamsForStylesheet(stylesheet));
            setControllerSwitches(controller, config);
            
            // Override the intial template if needed
            if (initialTemplate != null && !initialTemplate.trim().equals("")) {
                xsltTransformer.setInitialTemplate(new QName(initialTemplate));
            }

            if (input != null) {
                SAXSource saxSource = new SAXSource(SAXSource.sourceToInputSource(input));
                if (config.isUseCachingEntityResolver()) {
                    XMLReader xmlReader = processor.getUnderlyingConfiguration().getSourceParser();
                    xmlReader.setEntityResolver(CustomEntityResolver.getInstance());
                    saxSource.setXMLReader(xmlReader);
                }
                xsltTransformer.setSource(saxSource);
            }

            if (serializer.getOutputDestination() instanceof File) {
                xsltTransformer.setBaseOutputURI(((File)serializer.getOutputDestination()).toURI().toString());
            }
            
            xsltTransformer.setDestination(serializer);            

            xsltTransformer.setMessageListener(new MessageListener() {
                @Override
                public void message(XdmNode xn, boolean bln, SourceLocator sl) {
                    System.out.println(xn.getStringValue());
                }
            } );

            xsltTransformer.setErrorListener(CustomErrorListener.getInstance());


            long startTime = System.currentTimeMillis();

            xsltTransformer.transform();
            
            long endTime = System.currentTimeMillis();

            setTimeTakeInMS(endTime - startTime);
            setTimeTakenInWords("Done in " + Timer.getTimeTaken(startTime, endTime));                        
            
            successful = true;
            
        }  catch (TransformerFactoryConfigurationError ex) {
            Message.error(ex.toString());
        } catch (Exception ex) {
            Message.exception(ex, false);
        } finally {
            stylesheet = null;
            processor = null;
        }
        
        notifyTimeObservers();

        return successful;
    }

    @Override
    public void updateCancelStatus(Cancellable c) {
        // TODO: Make single file transforms cancellable
    }
}
