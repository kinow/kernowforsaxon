/*
 * SchemaValidator.java
 *
 * Created on 02 March 2006, 20:45
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package net.sf.kernow.schema;


import java.io.File;
import java.io.IOException;

import java.util.ArrayList;
import javax.xml.XMLConstants;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import net.sf.kernow.Config;
import net.sf.kernow.Message;
import net.sf.kernow.internal.CancelObserver;
import net.sf.kernow.internal.Cancellable;
import net.sf.kernow.internal.ObservableProgress;
import net.sf.kernow.internal.ProgressObserver;
import net.sf.kernow.transform.TransformTask;
import net.sf.kernow.util.FileListBuilder;
import net.sf.kernow.util.PercentHelper;
import net.sf.kernow.util.Timer;
import net.sf.saxon.lib.FeatureKeys;
import org.xml.sax.SAXException;

/**
 *
 * @author ajwelch
 */
public class SchemaValidator extends TransformTask implements ObservableProgress, CancelObserver {
    
    private ArrayList<ProgressObserver> progressObservers;
         
    private Config config;
    private boolean cancelClicked;
    private int percentComplete;
    private String progressText;
    private boolean reportFailsOnly;
    private int totalFiles;
    private SchemaFactory factory;
    
    /** Creates a new instance of SchemaValidator */
    public SchemaValidator() {
        this.config = Config.getConfig();
        progressObservers = new ArrayList<ProgressObserver>();
        cancelClicked = false;
        reportFailsOnly = config.isSchemaReportFailuresOnly();
    }
    
    public SchemaFactory getSchemaFactory() {

        if (config.isValidationSaxon()) {
                try {
                    factory = (SchemaFactory)Class.forName("com.saxonica.jaxp.SchemaFactoryImpl").newInstance();                                        
                    
                    if (config.getSchemaVersion().equals(SchemaVersion.ONE_DOT_ONE)) {
                        try {
                            factory.setProperty(FeatureKeys.XSD_VERSION, "1.1");                            
                        } catch (Exception ex) {
                            Message.exception(ex, false);                                            
                        } 
                    }
                } catch (InstantiationException ex) {
                    Message.exception(ex, true);
                } catch (IllegalAccessException ex) {
                    Message.exception(ex, true);
                } catch (ClassNotFoundException ex) {
                    Message.error("Saxon is set as the Schema processor to use (in Options -> Validation), but Saxon-SA cannot be found.");
                    Message.exception(ex, true);
                }
        } else if (config.isValidationXerces()) {

            if (config.getSchemaVersion().equals(SchemaVersion.ONE_DOT_ZERO)) {
                factory = javax.xml.validation.SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI,
                    "com.sun.org.apache.xerces.internal.jaxp.validation.XMLSchemaFactory",
                    null);
            } else if (config.getSchemaVersion().equals(SchemaVersion.ONE_DOT_ONE)) {
                factory = SchemaFactory.newInstance("http://www.w3.org/XML/XMLSchema/v1.1");
            } else {
                throw new AssertionError("The schema version in in the config isn't recognised");
            }

        } else {
            throw new AssertionError("Unable to determine SchemaFactory to use");
        }       
        
        Message.debug("Using: " + factory);

        if (config.isSchemaAwareEnabled() && config.isValidationSaxon() && config.isTreatValidationErrorsAsWarnings()) {
            try {
                factory.setProperty(FeatureKeys.VALIDATION_WARNINGS, Boolean.TRUE);
            } catch (Exception exception) {
                Message.error("Unable to set NOVW on Saxon SchemaFactory");
            }
        }
        
        return factory;
    }
    
    public boolean validateSchema(Source schema) {
        return validateSchema(new Source[]{ schema });
    }

    public boolean validateSchema(Source[] schemas) {
        
        boolean success = false;
        boolean isRNG = false;
        percentComplete = 0;
        try {
            
            factory = getSchemaFactory();
            
            progressText = "Compiling schema...";
            notifyProgressObservers();
            
            long startTime = System.currentTimeMillis();
            
            // Compile the schema just to see if it is ok, don't use the result.
            factory.newSchema(schemas);
            
            long endTime = System.currentTimeMillis();
            
            String time = Timer.getTimeTaken(startTime, endTime);
            
            setTimeTakenInWords("Done in " + time);
            progressText = getTimeTakenInWords();          
            notifyProgressObservers();
            
            if (isRNG) {
                Message.info("Relax NG Schema is valid!");
            } else {
                Message.info("W3C XML Schema is valid!");
            }
            success = true;
        } catch(SAXException e) {
            progressText = "Error!";
            Message.exception(e, false);      
        } 
        
        notifyProgressObservers();
        notifyTimeObservers();
        return success;        
    }

    public boolean validate(Source doc, Source schema) {
        return validate(doc, new Source[]{ schema });
    }        
        
    public boolean validate(Source doc, Source[] schemas) {
        
        boolean success = false;
        
        try {                       
            factory = getSchemaFactory();               

            progressText = "Compiling schema...";
            notifyProgressObservers();                       
            
            Schema compiled_schema = factory.newSchema(schemas[0]);
            Validator validator = compiled_schema.newValidator();
            
            long startTime = System.currentTimeMillis();
            
            success = validateSource(doc, validator);

            long endTime = System.currentTimeMillis();
            String time = Timer.getTimeTaken(startTime, endTime);
            
            setTimeTakenInWords("Done in " + time);
            progressText = getTimeTakenInWords();
            
            notifyTimeObservers();
            notifyProgressObservers();
            
            return success;
            
        } catch(SAXException e) {
            progressText = "Error!";
            Message.exception(e, false);
        }
        
        notifyTimeObservers();
        notifyProgressObservers();
        
        return false;
    }

    public boolean validateDirectory(File dir, Source schema) {
        return validateDirectory(dir, new Source[]{ schema });
    }

    public boolean validateDirectory(File dir, Source[] schemas) {
        
        boolean success = false;
        
        try {                       
            factory = getSchemaFactory();               
            
            progressText = "Compiling schema...";
            notifyProgressObservers();                       
            
            Schema compiled_schema = factory.newSchema(schemas);
            Validator validator = compiled_schema.newValidator();
            
            int passed = 0;
            long startTime = System.currentTimeMillis();
            
            passed = validateDirectory(dir, validator);
            
            long endTime = System.currentTimeMillis();
            String time = Timer.getTimeTaken(startTime, endTime);
            
            setTimeTakenInWords("Done in " + time);
            progressText = getTimeTakenInWords();
            
            if (!cancelClicked) {                    
                Message.info("\n" + totalFiles + " files validated in " + time + "\n");
                Message.info(passed + " passed.\n\n");
                
                success = totalFiles == passed;                
            } else {                    
                progressText = "Cancelled.";      
            }
            
            notifyTimeObservers();
            notifyProgressObservers();
            
            return success;
            
        } catch(SAXException e) {
            progressText = "Error!";
            Message.exception(e, false);
        }
        
        notifyTimeObservers();
        notifyProgressObservers();
        
        return false;
    }

    private int validateDirectory(File dir, Validator validator) {
        int passed = 0;
                    
        ArrayList<File> fileList = FileListBuilder.buildFileList(dir, 
                config.getAllowedInputSuffixes(), config.isSchemaRecurseSubdirectories());
            
        totalFiles = fileList.size();
        
        if (totalFiles > 0) {
            for (int i = 0; i < totalFiles & !cancelClicked; i++) {
                File f = fileList.get(i);
                progressText = "Validating: " + f.getName();
                
                // validate the file and increment the passed counter
                if (validateSource(new StreamSource(f), validator)) {
                    passed++;
                }
                
                // Increment the progress bar
                percentComplete = PercentHelper.getPercentComplete(i + 1, totalFiles);
                
                // Update the progress text            
                notifyProgressObservers();
            }                
        } else {
            Message.error("Error - no files in directory");
        }
        
        return passed;
    }
    
   
    private boolean validateSource(Source doc, Validator validator) {
        try {
            validator.validate(doc);
            if (!reportFailsOnly) {
                Message.info(doc.getSystemId() + " is valid");
            }
            return true;
        } catch(SAXException e) {
            Message.info(doc.getSystemId() + " failed:");
            Message.exception(e, false);
        } catch (IOException io) {
            Message.exception(io, true);
        }
        
        return false;
    }

    @Override
    public String getProgressText() {
        return progressText;
    }

    @Override
    public int getPercentComplete() {
        return percentComplete;
    }      
    
    @Override
    public void updateCancelStatus(Cancellable c) {
        cancelClicked = c.isCancelButtonClicked();
    }
    
    public void addProgressObserver(ProgressObserver progressObserver) {
        progressObservers.add(progressObserver);
    }
    
    public void notifyProgressObservers() {
        for (ProgressObserver p : progressObservers) {
            p.updateProgress(this);           
        }
    } 
}
