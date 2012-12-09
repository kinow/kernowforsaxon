/*
 * PropertyManager.java
 *
 * Created on 06 March 2007, 13:14
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package net.sf.kernow.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;
import net.sf.kernow.Config;
import net.sf.kernow.Message;
import net.sf.kernow.Status;
import net.sf.kernow.xquery.NamespaceBindings;

/**
 * Stores the various configuration properties to an XML file.
 *  *Saxon8-dom.jar* is required on the classpath for this.
 * @author welcha
 */
public class PropertyManager {
    
    private static Properties properties = new Properties();    
    
    private static final String KERNOW_CONFIG = "kernow.config";
    
    private static final String SINGLE_FILE_TAB_ENABLED = "Single_File_Tab_Enabled";
    private static final String STANDALONE_TAB_ENABLED = "Standalone_Tab_Enabled";
    private static final String DIRECTORY_TAB_ENABLED = "Directory_Tab_Enabled";
    private static final String XSLT_SANDBOX_TAB_ENABLED = "XSLT_Sandbox_Tab_Enabled";
    private static final String XSD_SANDBOX_TAB_ENABLED = "XSL_Sanbox_Tab_Enabled";
    private static final String XQUERY_SANDBOX_TAB_ENABLED = "XQuery_Sandbox_Tab_Enabled";
    private static final String VALIDATION_TAB_ENABLED = "Validation_Tab_Enabled";
    private static final String SCHEMATRON_TAB_ENABLED = "Schematron_Tab_Enabled";
    private static final String BATCH_TAB_ENABLED = "Batch_Tab_Enabled";
    
    private static final String CLEAN_OUTPUT_FILES = "Clean_Output_Files";
    private static final String USE_THIS_SUFFIX_CHECKBOX = "Use_This_Suffix_Checkbox";
    private static final String USE_THIS_SUFFIX_TEXT_FIELD = "Use_This_Suffix_Text_Field";
    private static final String ALLOWED_INPUT_SUFFIXES = "Allowed_Input_Suffixes";

    private static final String OPS = "ops";
    private static final String STATUS = "sts";
    private static final String USE_CACHING_ENTITY_RESOLVER = "Use_Caching_Entity_Resolver";
    private static final String USE_CACHING_URI_RESOLVER = "Use_Caching_URI_Resolver";    
    private static final String LOCAL_CACHE_DIR = "Local_Cache_Dir";
    private static final String SUPPRESS_CACHE_MESSAGES = "Suppress_Cache_Messages";
    
    private static final String TREAT_VALIDATION_ERRORS_AS_WARNINGS = "Treat_Validation_Errors_As_Warnings";
    private static final String USE_STRICT_VALIDATION = "Use_Strict_Validation";
    private static final String USE_LAX_VALIDATION = "Use_Lax_Validation";
    private static final String USE_SKIP_VALIDATION = "Use_Skip_Validation";
           
    public static final String SCHEMA_RECURSE_SUBDIRECTORIES = "Schema_Recurse_Subdirectories";
    public static final String SCHEMA_REPORT_FAILURES_ONLY = "Schema_Report_Failures_Only";
    
    public static final String DIRECTORY_RECURSE_SUBDIRECTORIES = "Directory_Recurse_Subdirectories";
    public static final String DIRECTORY_STOP_ON_ERROR = "Directory_Stop_On_An_Error";
    
    private static final String VALIDATION_USE_SAXON = "Validation_Use_Saxon";
    private static final String VALIDATION_USE_XERCES = "Validation_Use_Xerces";
    
    private static final String USE_STANDARD_TREE = "Use_Standard_Tree";
    private static final String USE_TINY_TREE = "Use_Tiny_Tree";
    
    private static final String USE_INITIAL_MODE_CHECKBOX = "Use_Initial_Mode_Checkbox";
    private static final String USE_INITIAL_MODE_TEXT_FIELD = "Use_Initial_Mode_Text_Field";
    
    private static final String USE_INITIAL_TEMPLATE_CHECKBOX = "Use_Initial_Template_Checkbox";
    private static final String USE_INITIAL_TEMPLATE_TEXT_FIELD = "Use_Initial_Template_Text_Field";
    
    private static final String LINE_NUMBERS = "Line_Numbers";
    private static final String NO_VERSION_WARNING = "No_Version_Warning";
    
    private static final String WHITESPACE_STRIP_ALL = "Whitespace_Strip_All";
    private static final String WHITESPACE_STRIP_IGNORABLE = "Whitespace_Strip_Ignorable";
    private static final String WHITESPACE_STRIP_NONE = "Whitespace_Strip_None";
    
    private static final String SHOW_TIMING_INFO = "Show_Timing_Info";
    private static final String EXPLAIN = "Explain";
    private static final String TRACE_JAVA = "Trace_Java";
    
    private static final String RECOVER_SILENTLY = "Recover_Silently";
    private static final String RECOVER_AND_CONTINUE = "Recover_And_Continue";
    private static final String RECOVERABLES_ARE_FATAL = "Recoverable_Are_Fatal";
    
    private static final String ALLOW_XML_1point1 = "Allow_XML_1point1";       
    
    private static final String THREAD_POOL_SIZE = "Thread_Pool_Size";
    
    private static final String PERFORM_AVG_TIMING = "Perform_Avg_Timing";
    private static final String REPEAT_TIMES = "Repeat_Times";
    private static final String IGNORE_TIMES = "Ignore_Times";
    
    private static final String NAMESPACE_BINDINGS = "Namespace_Bindings";

    private static final String USE_LEXEV = "LexEv_Enabled";      
    private static final String LEXEV_REPORT_DOCTYPE = "LexEv_report_doctype";
    private static final String LEXEV_REPORT_DTD_ENTITIES = "LexEv_report_dtd_entities";
    private static final String LEXEV_MARKUP_ENTITY_REFS = "LexEv_markup_entity_refs";
    private static final String LEXEV_MARKUP_CDATA_SECTIONS = "LexEv_markup_CDATA_sections";
    private static final String LEXEV_MARKUP_COMMENTS = "LexEv_markup_comments";

    /** Creates a new instance of PropertyManager */
    private PropertyManager() {
    }
    
    public static Properties getProperties() {
        return properties;
    }
    
    private static File getPropsFile() {
        
        String userHome = System.getProperty("user.home");
        String kernowConfigFilename = "kernow.config";
        
        if (userHome != null && !userHome.equals("")) {
            File f = new File(userHome + "/.kernow/" + kernowConfigFilename);
            if (!f.getParentFile().exists()) {
                boolean done = f.getParentFile().mkdirs();
                if (!done) {
                    Message.error("Unable to create \".kernow\" directory in home dir");
                }
            }

            return f;
        } 
            
        return null;
    }
    
    public static void loadProperties(Config config) {

        File props = getPropsFile(); 
        
        if (props == null || !props.exists()) {
            // kernow.config does not exist yet, it will be created after the first run
            return;
        }

        FileInputStream fis = null;

        try {        

            fis = new FileInputStream(props);

            properties.loadFromXML(fis);
            
            //tabs
            config.setSingleFileTabEnabled(Boolean.parseBoolean(properties.getProperty(SINGLE_FILE_TAB_ENABLED, "true")));
            config.setDirectoryTabEnabled(Boolean.parseBoolean(properties.getProperty(DIRECTORY_TAB_ENABLED, "true")));
            config.setStandaloneTabEnabled(Boolean.parseBoolean(properties.getProperty(STANDALONE_TAB_ENABLED, "true")));
            config.setXsltSandboxTabEnabled(Boolean.parseBoolean(properties.getProperty(XSLT_SANDBOX_TAB_ENABLED, "true")));
            config.setXsdSandboxTabEnabled(Boolean.parseBoolean(properties.getProperty(XSD_SANDBOX_TAB_ENABLED, "true")));
            config.setXquerySandboxTabEnabled(Boolean.parseBoolean(properties.getProperty(XQUERY_SANDBOX_TAB_ENABLED, "true")));
            config.setValidationTabEnabled(Boolean.parseBoolean(properties.getProperty(VALIDATION_TAB_ENABLED, "true")));
            config.setSchematronTabEnabled(Boolean.parseBoolean(properties.getProperty(SCHEMATRON_TAB_ENABLED, "true")));
            config.setBatchTabEnabled(Boolean.parseBoolean(properties.getProperty(BATCH_TAB_ENABLED, "true")));
            
            // options diag
            config.setCleanOutputFiles(Boolean.parseBoolean(properties.getProperty(CLEAN_OUTPUT_FILES, "false")));    
            config.setUseSpecificSuffix(Boolean.parseBoolean(properties.getProperty(USE_THIS_SUFFIX_CHECKBOX)));
            config.setSpecificSuffix(properties.getProperty(USE_THIS_SUFFIX_TEXT_FIELD));
            config.setAllowedInputSuffixes(properties.getProperty(ALLOWED_INPUT_SUFFIXES, ".xml, .xhtml, .htm, .html"));
            
            config.setUseCachingURIResolver(Boolean.parseBoolean(properties.getProperty(USE_CACHING_URI_RESOLVER)));
            config.setUseCachingEntityResolver(Boolean.parseBoolean(properties.getProperty(USE_CACHING_ENTITY_RESOLVER)));            
            config.setLocalCacheDir(properties.getProperty(LOCAL_CACHE_DIR));
            config.setSuppressURIandEntityMessages(Boolean.parseBoolean(properties.getProperty(SUPPRESS_CACHE_MESSAGES)));
            
            config.setTreatValidationErrorsAsWarnings(Boolean.parseBoolean(properties.getProperty(TREAT_VALIDATION_ERRORS_AS_WARNINGS)));
            config.setStrictValidation(Boolean.parseBoolean(properties.getProperty(USE_STRICT_VALIDATION)));
            config.setLaxValidation(Boolean.parseBoolean(properties.getProperty(USE_LAX_VALIDATION)));
            config.setSkipValidation(Boolean.parseBoolean(properties.getProperty(USE_SKIP_VALIDATION)));

            config.setSchemaRecurseSubdirectories(Boolean.parseBoolean(properties.getProperty(SCHEMA_RECURSE_SUBDIRECTORIES)));
            config.setSchemaReportFailuresOnly(Boolean.parseBoolean(properties.getProperty(SCHEMA_REPORT_FAILURES_ONLY)));
            
            config.setDirectoryRecurseSubdirectories(Boolean.parseBoolean(properties.getProperty(DIRECTORY_RECURSE_SUBDIRECTORIES))); 
            config.setDirectoryStopOnAnError(Boolean.parseBoolean(properties.getProperty(DIRECTORY_STOP_ON_ERROR))); 
            
            config.setValidationSaxon(Boolean.parseBoolean(properties.getProperty(VALIDATION_USE_SAXON)));
            config.setValidationXerces(Boolean.parseBoolean(properties.getProperty(VALIDATION_USE_XERCES)));
            
            config.setUseStandardTree(Boolean.parseBoolean(properties.getProperty(USE_STANDARD_TREE)));
            config.setUseTinyTree(Boolean.parseBoolean(properties.getProperty(USE_TINY_TREE)));
            
            config.setUseInitialMode(Boolean.parseBoolean(properties.getProperty(USE_INITIAL_MODE_CHECKBOX)));
            config.setInitialMode(properties.getProperty(USE_INITIAL_MODE_TEXT_FIELD));
            
            config.setUseInitialTemplate(Boolean.parseBoolean(properties.getProperty(USE_INITIAL_TEMPLATE_CHECKBOX)));
            config.setInitialTemplate(properties.getProperty(USE_INITIAL_TEMPLATE_TEXT_FIELD));
            
            config.setRetainLineNumbers(Boolean.parseBoolean(properties.getProperty(LINE_NUMBERS)));
            config.setNovw(Boolean.parseBoolean(properties.getProperty(NO_VERSION_WARNING)));
            config.setOps(Integer.parseInt(properties.getProperty(OPS, "100")));

            if (Boolean.parseBoolean(properties.getProperty(STATUS, "false"))) {
                config.setStatus(Status.UNLOCKED);
            }

            config.setStripAllWhitespace(Boolean.parseBoolean(properties.getProperty(WHITESPACE_STRIP_ALL)));
            config.setStripIgnorableWhitespace(Boolean.parseBoolean(properties.getProperty(WHITESPACE_STRIP_IGNORABLE)));
            config.setStripNoWhitespace(Boolean.parseBoolean(properties.getProperty(WHITESPACE_STRIP_NONE)));
            
            config.setShowTiming(Boolean.parseBoolean(properties.getProperty(SHOW_TIMING_INFO)));
            config.setTraceCalls(Boolean.parseBoolean(properties.getProperty(TRACE_JAVA)));
            config.setExplain(Boolean.parseBoolean(properties.getProperty(EXPLAIN)));
            
            config.setRecoverablesAreSilent(Boolean.parseBoolean(properties.getProperty(RECOVER_SILENTLY)));
            config.setRecoverablesAreReported(Boolean.parseBoolean(properties.getProperty(RECOVER_AND_CONTINUE)));
            config.setRecoverablesAreFatal(Boolean.parseBoolean(properties.getProperty(RECOVERABLES_ARE_FATAL)));
            
            config.setAllowXML11(Boolean.parseBoolean(properties.getProperty(ALLOW_XML_1point1)));
             
            config.setThreadPoolSize(Integer.parseInt(properties.getProperty(THREAD_POOL_SIZE, "1")));
            
            config.setRunRepeatedly(Boolean.parseBoolean(properties.getProperty(PERFORM_AVG_TIMING)));
            config.setNumberOfTimesToRepeatTask(Integer.parseInt(properties.getProperty(REPEAT_TIMES, "5")));
            config.setNumberOfTasksToIgnore(Integer.parseInt(properties.getProperty(IGNORE_TIMES, "2")));

            config.setUseLexEv(Boolean.parseBoolean(properties.getProperty(USE_LEXEV)));
            config.setLexevCdata(Boolean.parseBoolean(properties.getProperty(LEXEV_MARKUP_CDATA_SECTIONS)));
            config.setLexevComments(Boolean.parseBoolean(properties.getProperty(LEXEV_MARKUP_COMMENTS)));
            config.setLexevDoctype(Boolean.parseBoolean(properties.getProperty(LEXEV_REPORT_DOCTYPE)));
            config.setLexevDoctype(Boolean.parseBoolean(properties.getProperty(LEXEV_REPORT_DTD_ENTITIES)));
            config.setLexevEntityRef(Boolean.parseBoolean(properties.getProperty(LEXEV_MARKUP_ENTITY_REFS)));

            // Parse the namespace bindings: (prefix " " uri)? (" " prefix " " uri)*
            String[] bindings_str = properties.getProperty(NAMESPACE_BINDINGS, "").split(" ");
            NamespaceBindings bindings = config.getNamespaceBindings();
            for ( int i = 0; i < bindings_str.length - 1; i += 2 ) {
                bindings.addBinding(bindings_str[i], bindings_str[i + 1]);
            }
            
        } catch (Exception ex) {
            // delete the config file... this avoids the situation of a corrupt
            // or broken config file preventing kernow from starting
            props.delete();
            ex.printStackTrace();
        } finally {
            IOUtils.closeStream(fis);
        }
    }
    
    public static void saveProperties(Config config) {
        
        File props = getPropsFile(); 
        
        if (props == null) {
            Message.error("System property not set for kernow.config, no properties will be saved.");
            return;
        }        
        
        Message.info("Saving settings...");

        FileOutputStream fos = null;

        try {
            
            //tabs
            properties.setProperty(SINGLE_FILE_TAB_ENABLED, Boolean.toString(config.isSingleFileTabEnabled()));
            properties.setProperty(DIRECTORY_TAB_ENABLED, Boolean.toString(config.isDirectoryTabEnabled()));
            properties.setProperty(STANDALONE_TAB_ENABLED, Boolean.toString(config.isStandaloneTabEnabled()));
            properties.setProperty(XSLT_SANDBOX_TAB_ENABLED, Boolean.toString(config.isXsltSandboxTabEnabled()));
            properties.setProperty(XSD_SANDBOX_TAB_ENABLED, Boolean.toString(config.isXsdSandboxTabEnabled()));
            properties.setProperty(XQUERY_SANDBOX_TAB_ENABLED, Boolean.toString(config.isXquerySandboxTabEnabled()));
            properties.setProperty(VALIDATION_TAB_ENABLED, Boolean.toString(config.isValidationTabEnabled()));
            properties.setProperty(SCHEMATRON_TAB_ENABLED, Boolean.toString(config.isSchematronTabEnabled()));
            properties.setProperty(BATCH_TAB_ENABLED, Boolean.toString(config.isBatchTabEnabled()));
            properties.setProperty(OPS, Integer.toString(config.getOps()));
            properties.setProperty(STATUS, Boolean.toString(config.getStatus() == Status.UNLOCKED));

            // option diag
            properties.setProperty(CLEAN_OUTPUT_FILES, Boolean.valueOf(config.isCleanOutputFiles()).toString());           
            properties.setProperty(USE_THIS_SUFFIX_CHECKBOX, Boolean.valueOf(config.isUseSpecificSuffix()).toString());
            properties.setProperty(USE_THIS_SUFFIX_TEXT_FIELD, (config.getSpecificSuffix() != null)?config.getSpecificSuffix():"") ;
            properties.setProperty(ALLOWED_INPUT_SUFFIXES, (config.getAllowedInputSuffixes() != null)?config.getAllowedInputSuffixes():"") ;
            
            properties.setProperty(USE_CACHING_URI_RESOLVER, Boolean.toString(config.isUseCachingURIResolver()));
            properties.setProperty(USE_CACHING_ENTITY_RESOLVER, Boolean.toString(config.isUseCachingEntityResolver()));            
            properties.setProperty(LOCAL_CACHE_DIR, config.getLocalCacheDir());
            properties.setProperty(SUPPRESS_CACHE_MESSAGES, Boolean.toString(config.isSuppressURIandEntityMessages()));
            
            properties.setProperty(TREAT_VALIDATION_ERRORS_AS_WARNINGS, Boolean.toString(config.isTreatValidationErrorsAsWarnings()));
            properties.setProperty(USE_STRICT_VALIDATION, Boolean.toString(config.isStrictValidation()));
            properties.setProperty(USE_LAX_VALIDATION, Boolean.toString(config.isLaxValidation()));
            properties.setProperty(USE_SKIP_VALIDATION, Boolean.toString(config.isSkipValidation()));
            
            properties.setProperty(SCHEMA_RECURSE_SUBDIRECTORIES, Boolean.toString(config.isSchemaRecurseSubdirectories()));
            properties.setProperty(SCHEMA_REPORT_FAILURES_ONLY, Boolean.toString(config.isSchemaReportFailuresOnly()));

            properties.setProperty(DIRECTORY_RECURSE_SUBDIRECTORIES, Boolean.toString(config.isDirectoryRecurseSubdirectories()));
            properties.setProperty(DIRECTORY_STOP_ON_ERROR, Boolean.toString(config.isDirectoryStopOnAnError()));
            
            properties.setProperty(VALIDATION_USE_SAXON, Boolean.toString(config.isValidationSaxon()));
            properties.setProperty(VALIDATION_USE_XERCES, Boolean.toString(config.isValidationXerces()));

            properties.setProperty(USE_STANDARD_TREE, Boolean.toString(config.isUseStandardTree()));
            properties.setProperty(USE_TINY_TREE, Boolean.toString(config.isUseTinyTree()));

            properties.setProperty(USE_INITIAL_MODE_CHECKBOX, Boolean.toString(config.isUseInitialMode()));
            properties.setProperty(USE_INITIAL_MODE_TEXT_FIELD, config.getInitialMode());

            properties.setProperty(USE_INITIAL_TEMPLATE_CHECKBOX, Boolean.toString(config.isUseInitialTemplate()));
            properties.setProperty(USE_INITIAL_TEMPLATE_TEXT_FIELD, config.getInitialTemplate());

            properties.setProperty(LINE_NUMBERS, Boolean.toString(config.isRetainLineNumbers()));
            properties.setProperty(NO_VERSION_WARNING, Boolean.toString(config.isNovw()));

            properties.setProperty(WHITESPACE_STRIP_ALL, Boolean.toString(config.isStripAllWhitespace()));
            properties.setProperty(WHITESPACE_STRIP_IGNORABLE, Boolean.toString(config.isStripIgnorableWhitespace()));
            properties.setProperty(WHITESPACE_STRIP_NONE, Boolean.toString(config.isStripNoWhitespace()));

            properties.setProperty(SHOW_TIMING_INFO, Boolean.toString(config.isShowTiming()));
            properties.setProperty(TRACE_JAVA, Boolean.toString(config.isTraceCalls()));
            properties.setProperty(EXPLAIN, Boolean.toString(config.isExplain()));
            
            properties.setProperty(RECOVER_SILENTLY, Boolean.toString(config.isRecoverablesAreSilent()));
            properties.setProperty(RECOVER_AND_CONTINUE, Boolean.toString(config.isRecoverablesAreReported()));
            properties.setProperty(RECOVERABLES_ARE_FATAL, Boolean.toString(config.isRecoverablesAreFatal()));

            properties.setProperty(ALLOW_XML_1point1, Boolean.toString(config.isAllowXML11()));  
            
            properties.setProperty(THREAD_POOL_SIZE, Integer.toString(config.getThreadPoolSize()));
            
            properties.setProperty(PERFORM_AVG_TIMING, Boolean.toString(config.isRunRepeatedly()));
            properties.setProperty(REPEAT_TIMES, Integer.toString(config.getNumberOfTimesToRepeatTask()));
            properties.setProperty(IGNORE_TIMES, Integer.toString(config.getNumberOfTasksToIgnore()));

            properties.setProperty(USE_LEXEV, Boolean.toString(config.isUseLexEv()));            
            properties.setProperty(LEXEV_MARKUP_CDATA_SECTIONS, Boolean.toString(config.isLexevCdata()));
            properties.setProperty(LEXEV_MARKUP_ENTITY_REFS, Boolean.toString(config.isLexevEntityRef()));
            properties.setProperty(LEXEV_MARKUP_COMMENTS, Boolean.toString(config.isLexevComments()));
            properties.setProperty(LEXEV_REPORT_DOCTYPE, Boolean.toString(config.isLexevDoctype()));
            properties.setProperty(LEXEV_REPORT_DTD_ENTITIES, Boolean.toString(config.isLexevDTDEntities()));
           

            // Save the namespace bindings with the simple following format:
            // (prefix " " uri)? (" " prefix " " uri)*
            StringBuilder     bindings_str = new StringBuilder();
            NamespaceBindings bindings     = config.getNamespaceBindings();
            for ( String prefix : bindings.getPrefixes() ) {
                bindings_str.append(prefix);
                bindings_str.append(" ");
                bindings_str.append(bindings.getBinding(prefix));
                bindings_str.append(" ");
            }
            if ( bindings_str.length() > 0 ) {
                bindings_str.deleteCharAt(bindings_str.length() - 1);
            }
            properties.setProperty(NAMESPACE_BINDINGS, bindings_str.toString());

            fos = new FileOutputStream(props);
            
            properties.storeToXML(fos, null);
            
            Message.info("Done.");
            
        } catch (FileNotFoundException ex) {
            Message.error("Couldn't find kernow.config at: " + props.getAbsolutePath());
            ex.printStackTrace();
        } catch (IOException ex) {
            Message.exception(ex, false);
        } catch (Exception ex) {
            Message.exception(ex, true);
        } finally {
            IOUtils.closeStream(fos);
        }
    }
    
}
