/*
 * StandaloneXQuery.java
 *
 * Created on 26 January 2006, 11:22
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package net.sf.kernow.xquery;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import javax.xml.transform.ErrorListener;
import javax.xml.transform.TransformerException;
import net.sf.kernow.Config;
import net.sf.kernow.Message;
import net.sf.kernow.util.Timer;
import net.sf.kernow.transform.TransformTask;
import net.sf.saxon.Configuration;
import net.sf.saxon.s9api.Destination;
import net.sf.saxon.s9api.Processor;
import net.sf.saxon.s9api.SaxonApiException;
import net.sf.saxon.s9api.Serializer;
import net.sf.saxon.s9api.XQueryCompiler;
import net.sf.saxon.s9api.XQueryEvaluator;
import net.sf.saxon.s9api.XQueryExecutable;

/**
 *
 * @author AWelch
 */
public class StandaloneXQuery extends TransformTask {
    
    private Processor processor;
    private Configuration configuration;

    private XQueryCompiler xqCompiler = null;
    private XQueryCompiler xqCompilerForSyntaxChecker = null;
    
    private Config kernowConfig;
    
    /** Creates a new instance of StandaloneXQuery */    
    public StandaloneXQuery() {
        super();

        kernowConfig = Config.getConfig();
        
        if (!kernowConfig.isUseSchemaAware()) {
            processor = new Processor(false);
        } else {
                
            processor = new Processor(true);
            configuration = processor.getUnderlyingConfiguration();

            if (kernowConfig.isSkipValidation()) {
                configuration.setSchemaValidationMode(net.sf.saxon.lib.Validation.SKIP);
            } else if (kernowConfig.isStrictValidation()) {
                configuration.setSchemaValidationMode(net.sf.saxon.lib.Validation.STRICT);
            } else if (kernowConfig.isLaxValidation()) {
                configuration.setSchemaValidationMode(net.sf.saxon.lib.Validation.LAX);
            }

            configuration.setValidationWarnings(kernowConfig.isTreatValidationErrorsAsWarnings());

        }
        
        // The normal xqueryCompiler
        xqCompiler = processor.newXQueryCompiler();
        
        // Now set up the xqueryCompiler used by the syntax checker
        // To prevent errors being written to System.err (they're written to the 
        // syntax errors box instead) a custom error listener is used
        xqCompilerForSyntaxChecker = processor.newXQueryCompiler();
        xqCompilerForSyntaxChecker.setErrorListener(new ErrorListener() {
            @Override
            public void fatalError(TransformerException te) {

            }
            @Override
            public void error(TransformerException te) {

            }
            @Override
            public void warning(TransformerException te) {

            }                
        });
       
    }
    
    public void checkQuery(String query) throws SaxonApiException {
        getXQueryExpression(query, "");
    }
    
    public boolean runQuery(String query) {
        Serializer serializer = new Serializer();
        serializer.setOutputStream(System.out);
        return runQuery(query, serializer);
    }
    
    public boolean runQuery(String query, String baseUri) {
        Serializer serializer = new Serializer();
        serializer.setOutputStream(System.out);
        return runQuery(query, serializer, baseUri);
    }
    
    public boolean runQuery(String query, Destination destination) {
        return runQuery(query, destination, null);
    }

    public boolean runQuery(String query, Destination destination, String baseUri) {
        try {
            return runQuery(getXQueryExpression(query, baseUri), destination);
        } catch (SaxonApiException ex ) {
            Message.exception(ex, false);
        }
        return false;
    }
    
    public boolean runQuery(InputStream query, Destination destination) {
        return runQuery(query, destination, null);
    }

    public boolean runQuery(InputStream query, Destination destination, String baseUri) {
        try {
            return runQuery(getXQueryExpression(query, baseUri), destination);
        } catch (SaxonApiException ex ) {
            Message.exception(ex, false);
        } catch (IOException ex ) {
            Message.exception(ex, false);
        }
        return false;
    }        

    public boolean runQuery(Reader query, Destination destination, String baseUri) {
        try {
            return runQuery(getXQueryExpression(query, baseUri), destination);
        } catch (IOException ex) {
            Message.exception(ex, true);
        } catch (SaxonApiException ex) {
            Message.exception(ex, false);
        }
        return false;
    }

    public boolean runQuery(final XQueryExecutable xqueryExecutable, Destination destination) {
        if (xqueryExecutable == null) {
            System.out.println("Query is null!");
            setTimeTakenInWords("error!");
            return false;
        }

        XQueryEvaluator xqueryEvaluator = xqueryExecutable.load();

        boolean success = false;
                  
        try {            
            
            long startTime = System.currentTimeMillis();
            
            xqueryEvaluator.run(destination);
            
            long endTime = System.currentTimeMillis();

            setTimeTakenInWords("Done in " + Timer.getTimeTaken(startTime, endTime));
            
            notifyTimeObservers();
            
            success = true;
        } catch (SaxonApiException ex) {
            //System.out.println(ex.toString());
            Message.exception(ex, false);
            setTimeTakenInWords("error!");
        }
        
        return success;
    }
    
    /**
     * Compile the query after having set the namespace bindings found in the config.
     */
    private XQueryExecutable getXQueryExpression(String query, String baseUri) throws SaxonApiException {
        if (query == null) {
            return null;
        }

        setNamespaceBindings(xqCompiler);
        XQueryExecutable xqe = xqCompiler.compile(query);

        //String origUri = sqc.getBaseURI();
        //sqc.setBaseURI(baseUri);
        //XQueryExpression expr = sqc.compileQuery(query);
        //sqc.setBaseURI(origUri);
        return xqe;
    }
    
    /**
     * Compile the query after having set the namespace bindings found in the config.
     */
    private XQueryExecutable getXQueryExpression(InputStream query, String baseUri) throws SaxonApiException, IOException {
        if (query == null) {
            return null;
        }

        setNamespaceBindings(xqCompiler);
        XQueryExecutable xqe = xqCompiler.compile(query);
        return xqe;
    }
    
    /**
     * Compile the query after having set the namespace bindings found in the config.
     */
    private XQueryExecutable getXQueryExpression(Reader query, String baseUri) throws SaxonApiException, IOException {
        if (query == null) {
            return null;
        }

        setNamespaceBindings(xqCompiler);
        XQueryExecutable xqe = xqCompiler.compile(query);
        return xqe;
    }
    
    /**
     * Set on the static query context the namespace bindings found in the config.
     */
    private void setNamespaceBindings(XQueryCompiler xqueryCompiler) {
        NamespaceBindings bindings = kernowConfig.getNamespaceBindings();
        for (String prefix : bindings.getPrefixes()) {
            xqueryCompiler.declareNamespace(prefix, bindings.getBinding(prefix));
        }
    }
 
}
