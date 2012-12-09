
package net.sf.kernow.transform;

import java.io.File;
import java.io.InputStream;
import java.io.Reader;
import java.util.ArrayList;
import javax.xml.transform.Source;
import net.sf.kernow.Config;
import net.sf.kernow.Message;
import net.sf.kernow.internal.TransformController;
import net.sf.kernow.ant.AntRunner;
import net.sf.kernow.schema.SchemaValidator;
import net.sf.kernow.ui.TabbedView;
import net.sf.kernow.util.AverageTime;
import net.sf.kernow.xquery.StandaloneXQuery;
import net.sf.saxon.s9api.Destination;
import net.sf.saxon.s9api.Serializer;

/**
 *
 * @author AWelch
 */
public class TransformControllerImpl implements TransformController {
    
    final TabbedView view;
    private boolean schemaAwareEnabled = false;

    private Config config;    
    
    /**
     * Creates a new instance of TransformControllerImpl
     */
    public TransformControllerImpl(final TabbedView view) {        
        
        this.view = view;
                        
        config = Config.getConfig();
               
        view.init(this);
        
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                view.setVisible(true);
            }
        });
    }
    
    /**
     * Evaluate a standalone XQuery
     * @param query The query to evaluate
     * @param output Where to send the result of the evaluation
     */
    @Override
    public boolean runStandaloneXQuery(String query, Serializer serializer, String baseUri) {
        StandaloneXQuery standaloneXQuery = new StandaloneXQuery();        
        standaloneXQuery.addTimeObserver(view);
        return standaloneXQuery.runQuery(query, serializer, baseUri);
    }

    /**
     * Evaluate a standalone XQuery
     * @param query The query to evaluate
     * @param output Where to send the result of the evaluation
     */
    @Override
    public boolean runStandaloneXQuery(Reader query, Serializer serializer, String baseUri) {
        StandaloneXQuery standaloneXQuery = new StandaloneXQuery();        
        standaloneXQuery.addTimeObserver(view);
        return standaloneXQuery.runQuery(query, serializer, baseUri);
    }

    /**
     * Evaluate a standalone XQuery
     * @param query The query to evaluate
     * @param output Where to send the result of the evaluation
     */
    @Override
    public boolean runStandaloneXQuery(InputStream query, Serializer serializer, String baseUri) {
        StandaloneXQuery standaloneXQuery = new StandaloneXQuery();        
        standaloneXQuery.addTimeObserver(view);
        return standaloneXQuery.runQuery(query, serializer, baseUri);
    }
    
    /**
     * Transforms a dummy XML file using a normal uncompiled stylesheet
     * @param stylesheet   The stylesheet to be used
     * @param output       The destination of the transform
     */
    @Override
    public boolean runStandaloneTransform(Source stylesheet, Serializer serializer, String initialTemplate) {
        return repeatedSingleFileTransform(null, stylesheet, serializer, initialTemplate);
    }

    /**
     * Transforms a single XML file using a normal uncompiled stylesheet
     * @param input        The XML document to transform
     * @param stylesheet   The stylesheet to be used
     * @param output       The destination of the transform
     */
    @Override
    public boolean runSingleFileTransform(Source input, Source stylesheet, Serializer serializer) {
        return repeatedSingleFileTransform(input, stylesheet, serializer, null);
    }

    /* Repeats the SingleFileTransform a number of times */
    private boolean repeatedSingleFileTransform(Source input, Source stylesheet, Serializer serializer, String initialTemplate) {
        int repeat = 1;
        
        /* Get the number of times to repeat the task.  If repeat is disabled
         * then the values stays at 1 */
        if (config.isRunRepeatedly()) {
            System.out.println("Performance testing mode...");
            repeat = config.getNumberOfTimesToRepeatTask();
        }
        
        boolean success = true;
        
        // holds the times for each run
        ArrayList<Long> times = new ArrayList<Long>();
        
        // repeatedly run the transform and and store the time
        for (int i = 1; i<= repeat & success; i++) {
            SingleFileTransformer singleFileTransform = new SingleFileTransformer();            
            singleFileTransform.addTimeObserver(view);
            success = singleFileTransform.transform(input, stylesheet, serializer, initialTemplate);
            times.add(singleFileTransform.getTimeTakeInMS());
        }
        
        if (success && config.isRunRepeatedly()) {
            Message.info(AverageTime.getAverageTimeInWords(times));
        }
        
        return success;        
    }

    /**
     * Creates a DirectoryTransformer to compile the stylesheet and then transform
     * each file in the input directory with the compiled stylesheet  
     * @param inputDir the source directory contains the XML files
     * @param stylesheet the path to the stylesheet
     * @param outputDir the target output directory for the transformations
     */
    @Override
    public boolean runDirectoryTransform(File inputDir, Source stylesheet, File outputDir) {
        DirectoryTransformer directoryTransformer = new DirectoryTransformer();
        directoryTransformer.addProgressObserver(view);
        view.addCancelObserver(directoryTransformer);
        return directoryTransformer.runDirectoryTransform(inputDir, stylesheet, outputDir);
    }               
    
    @Override
    public boolean runSchemaCheck(Source schema) {
        return runSchemaCheck(new Source[] { schema });
    }

    @Override
    public boolean runSchemaCheck(Source[] schemas) {
        SchemaValidator schemaValidator = new SchemaValidator();
        schemaValidator.addProgressObserver(view);
        view.addCancelObserver(schemaValidator);
        view.clearOutputWindow();
        return schemaValidator.validateSchema(schemas);
    }

    @Override
    public boolean runValidate(Source doc, Source schema) {
        return runValidate(doc, new Source[] { schema });
    }

    @Override
    public boolean runValidate(Source doc, Source[] schemas) {
        SchemaValidator schemaValidator = new SchemaValidator();
        schemaValidator.addProgressObserver(view);
        view.addCancelObserver(schemaValidator);
        view.clearOutputWindow();
        return schemaValidator.validate(doc, schemas);
    }

    @Override
    public boolean runDirectoryValidate(File dir, Source schema) {        
        return runDirectoryValidate(dir, new Source[] {schema});
    }

    @Override
    public boolean runDirectoryValidate(File dir, Source[] schemas) {
        SchemaValidator schemaValidator = new SchemaValidator();
        schemaValidator.addProgressObserver(view);
        view.addCancelObserver(schemaValidator);
        view.clearOutputWindow();
        return schemaValidator.validateDirectory(dir, schemas);
    }

    @Override
    public boolean runAnt(String buildFile, String target, int messageLevel) {
        AntRunner antRunner = new AntRunner();
        antRunner.init(buildFile, messageLevel);
        antRunner.addTimeObserver(view);
        return antRunner.runTarget(target);
    }
    
    public boolean isSchemaAwareEnabled() {
        return schemaAwareEnabled;
    }    
}
