
package net.sf.kernow.transform;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import net.sf.kernow.internal.CancelObserver;
import net.sf.kernow.internal.Cancellable;
import net.sf.kernow.Config;
import net.sf.kernow.Message;
import net.sf.kernow.internal.ObservableProgress;
import net.sf.kernow.internal.ProgressObserver;
import net.sf.kernow.util.Cleaner;
import net.sf.kernow.util.FileListBuilder;
import net.sf.kernow.util.PercentHelper;
import net.sf.kernow.util.Timer;
import net.sf.saxon.Controller;
import net.sf.saxon.s9api.Processor;
import net.sf.saxon.s9api.SaxonApiException;
import net.sf.saxon.s9api.Serializer;
import net.sf.saxon.s9api.XsltCompiler;
import net.sf.saxon.s9api.XsltExecutable;

/**
 *
 * @author AWelch
 */
public class DirectoryTransformer implements ObservableProgress, CancelObserver {
    
    /** Number of threads in the pool. */
    private int threadPoolSize = 1;

    /** The ExecutorService. */
    private ExecutorService threadPool;
    
    private ArrayList<ProgressObserver> progressObservers;
    
    private Config config;
    
    private String progressText;
    private int percentComplete;
    private int totalFiles; 

    private Processor processor;
    
    private boolean cancelled;
    
    /** Creates a new instance of DirectoryTransformer */
    public DirectoryTransformer() {    
        this.config = Config.getConfig();        
        this.threadPoolSize = config.getThreadPoolSize();
        this.threadPool = Executors.newFixedThreadPool(threadPoolSize);
        progressObservers = new ArrayList<ProgressObserver>();
        
        cancelled = false;
        
        this.processor = config.getProcessor();

        SwitchSetter.setSwitches(processor.getUnderlyingConfiguration());
    }

    /**
     * Takes an input folder, a stylesheet and an output folder and transforms
     * each input file with a compiled version of the stylesheet.  
     * @param inputDir      the source directory contains the XML files
     * @param stylesheet    the path to the stylesheet
     * @param outputDir     the target output directory for the transformations
     */
    public boolean runDirectoryTransform(File inputDir, Source stylesheet, File outputDir) {
        
        // Delete target if needed
        if (config.isCleanOutputFiles()) {
            Message.info("Cleaning output files...");
            Cleaner.clean(outputDir);
            Message.info("Done.");
        }
        
        percentComplete = 0;                
        
        boolean overallSuccess = false;
        boolean stoppedOnAnError = false;
        
        if (!inputDir.isDirectory()) {
            Message.error("Input XML directory is either not a directory or not there: " + inputDir.getAbsolutePath());
            progressText = "Error";            
        } else {
                        
            try {                                
                Message.info("Compiling stylesheet...");

                XsltCompiler xsltCompiler = processor.newXsltCompiler();
                xsltCompiler.setErrorListener(CustomErrorListener.getInstance());
                
                XsltExecutable xsltExecutable = xsltCompiler.compile(stylesheet);

                Message.info("Done.");
                
                if (threadPoolSize > 1) {
                    Message.info("Using " + threadPoolSize + " threads...");
                }
                
                // Get all the XML files in the directory
                ArrayList<File> xmlFiles = FileListBuilder.buildFileList(inputDir, 
                config.getAllowedInputSuffixes(), config.isDirectoryRecurseSubdirectories());
                
                // Get the output suffix to use
                String suffix = getSuffix(xsltExecutable);

                // Set the start time
                long startTime = System.currentTimeMillis();

                CachedStylesheetTransformer cachedTransformer = new CachedStylesheetTransformer(processor);
                
                ArrayList<FutureTask<Boolean>> taskList = new ArrayList<FutureTask<Boolean>>();
                
                progressText = "Submitting transform tasks to the thread pool...";
                notifyProgressObservers();

                totalFiles = xmlFiles.size();

                // Transform each xml file
                for (int i=0; i < xmlFiles.size() & !cancelled; i++) {
                    
                    // Get filename of file or directory
                    String filename = xmlFiles.get(i).getName();
                    String outputFile = getFilenameWithoutSuffix(filename);

                    File adjustedOutputFile = new File(outputDir, inputDir.toURI().relativize(xmlFiles.get(i).toURI()).toString());
                    File parentDir = adjustedOutputFile.getParentFile();

                    if (!parentDir.isDirectory()) {
                        boolean done = parentDir.mkdirs();
                        if (!done) {
                            Message.error("Unabled to create output directory: " + parentDir.getAbsolutePath());
                        }
                    }

                    File finalOutputFile = new File(parentDir, outputFile + suffix);

                    Serializer serializer = new Serializer();
                    serializer.setOutputFile(finalOutputFile);

                    HashMap<String, Object> params = Params.getParamsForStylesheet(stylesheet);

                    Source inputXML = new StreamSource(xmlFiles.get(i));
                    Callable<Boolean> c = new CallableCachedTransform(cachedTransformer, inputXML, xsltExecutable, serializer, params);
                    FutureTask<Boolean> task = new FutureTask<Boolean>(c);
                    taskList.add(task);
                    threadPool.submit(task);                    
                }

                progressText = "Checking for task completion...";
                notifyProgressObservers();
                    
                try {
                    for (int i = 0; i < taskList.size() & !cancelled; i++) {              
                        FutureTask<Boolean> task = taskList.get(i);
                        Boolean success = task.get();                        
                        if (!success.booleanValue()) {                           
                            if (config.isDirectoryStopOnAnError()) {
                                threadPool.shutdownNow();
                                stoppedOnAnError = true;
                                break;
                            }
                        }

                        percentComplete = PercentHelper.getPercentComplete(i + 1, totalFiles);
                        notifyProgressObservers();
                    }
                } catch (InterruptedException ex) {
                    threadPool.shutdownNow();
                } catch (ExecutionException ex) {
                    ex.printStackTrace();
                } 
                                            
                // Finished or cancelled, so tell the user
                if (!cancelled & !stoppedOnAnError) {
                    // Work out the time taken
                    long endTime = System.currentTimeMillis();
                    String time = Timer.getTimeTaken(startTime, endTime);
                    Message.info("\n" + xmlFiles.size() + " files processed in " + time + "\n\n");                    
                    progressText = "Done in " + time;
                    overallSuccess = true;                    
                } else if (cancelled) {
                    progressText = "Cancelled";
                    threadPool.shutdownNow();
                } else if (stoppedOnAnError) {
                    progressText = "Stopped on an error";
                }                             
                
            } catch (SaxonApiException ex) {
                progressText = "Error in stylesheet";
                Message.exception(ex, false);
            }
        }  
        
        notifyProgressObservers();
        
        return overallSuccess;
    }
    
    
    /* The output suffix is determined by the following:
     * - if it's specified in the config use that
     * - if the output method is specified in the stylesheet use the corresponding
     *   suffix (eg .html for HTML output, .txt for text)
     * - otherwise default to .xml  */
    private String getSuffix(XsltExecutable xsltExecutable) {
        String suffix = config.getSpecificSuffix();
        if (suffix == null || suffix.equals("")) {

            String outputMethod = xsltExecutable.getUnderlyingCompiledStylesheet().getOutputProperties().getProperty(OutputKeys.METHOD);

            if (outputMethod != null && !outputMethod.equals("")) {
                if (outputMethod.equals("xml")) {
                    suffix = ".xml";
                } else if (outputMethod.equals("html") || outputMethod.equals("xhtml")) {
                    suffix = ".html";                            
                } else if (outputMethod.equals("text")) {
                    suffix = ".txt";                            
                } else {
                    System.err.println("Unknown output method: \"" + outputMethod + "\", defaulting to .xml suffix");
                    suffix = ".xml";                            
                } 
            } else {
                //System.out.println("There is no output suffix specified in the Options, and no output method defined in the transform, defaulting output suffix to \".xml\"");
                suffix = ".xml";
            }
        }
        
        return suffix;
    }
    
    /*
     *  Gets the filename without the suffix - the filename can contain
     *  several . so it assumes the suffix comes after the last one
     */
    private String getFilenameWithoutSuffix(String filename) {
        return filename.substring(0, filename.lastIndexOf("."));
    }
    
    @Override
    public void updateCancelStatus(Cancellable c) {
        cancelled = c.isCancelButtonClicked();
    }
    
    void addProgressObserver(ProgressObserver progressObserver) {
        progressObservers.add(progressObserver);
    }
    
    void notifyProgressObservers() {
        for (ProgressObserver p : progressObservers) {
            p.updateProgress(this);           
        }
    }  
    
    @Override
    public String getProgressText() {
        return progressText;
    }

    @Override
    public int getPercentComplete() {
        return percentComplete;
    }
    
}
