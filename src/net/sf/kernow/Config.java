package net.sf.kernow;

import java.io.PrintStream;
import net.sf.kernow.schema.SchemaVersion;
import net.sf.kernow.util.PropertyManager;
import net.sf.kernow.xquery.NamespaceBindings;
import net.sf.saxon.lib.FeatureKeys;
import net.sf.saxon.s9api.Processor;

/**
 *
 * @author AWelch
 */
public class Config {
    
    private String kernowVersion = "Kernow 1.7.1";

    private static boolean loadedProps = false;
    private boolean useCachingURIResolver = true;
    private boolean useCachingEntityResolver = true;
    private boolean useCachingCollectionEntityResolver = true;
    private boolean suppressURIandEntityMessages = false;
    private String localCacheDir = "";
    private String catalogPath = "";
    
    private boolean cleanOutputFiles = false;
    
    private boolean schemaAwareEnabled = false;
    private boolean useSchemaAware = false;
    
    // Schema Aware validation options
    private boolean skipValidation = true;
    private boolean laxValidation = false;
    private boolean strictValidation = false;
    private boolean treatValidationErrorsAsWarnings = true;
    
    // Directory validation options
    private boolean validationSaxon = false;
    private boolean validationXerces = true;
    
    private boolean useSpecificSuffix = false;
    private String specificSuffix = ".xml";  //default to .xml
    private String allowedInputSuffixes = ".xml, .xhtml, .htm, .html, .svg";  //default to these
    
    private boolean schemaRecurseSubdirectories = true;
    private boolean schemaReportFailuresOnly = false;
        
    private SchemaVersion schemaVersion = SchemaVersion.ONE_DOT_ZERO;
    
    private boolean directoryRecurseSubdirectories = true;
    private boolean directoryStopOnAnError = true;
    
    private boolean useStandardTree = false;
    private boolean useTinyTree = true;
    private boolean useInitialMode = false;
    private String initialMode = "";
    private boolean useInitialTemplate = false;
    private String initialTemplate = "";
    private boolean novw = false;
    private boolean retainLineNumbers = false;
    private boolean stripAllWhitespace = false;
    private boolean stripIgnorableWhitespace = false;
    private boolean stripNoWhitespace = true;
    private boolean showTiming = false;
    private boolean traceCalls = false;
    private boolean explain = false;
    private boolean recoverablesAreSilent = false;
    private boolean recoverablesAreReported = true;
    private boolean recoverablesAreFatal = false;
    private boolean allowXML11 = false;

    private NamespaceBindings namespaceBindings = new NamespaceBindings();

    /** The level of information that should be output, from SILENT to DEBUG */
    private MessageLevel messageLevel = MessageLevel.INFO;
    
    /** The size of the thread pool to use */
    private int threadPoolSize = 2;
    
    /** Whether to repeatedly run a task to get an average time */
    private boolean runRepeatedly = false;
    /** The number of times to repeat an operation to get an average time */
    private int numberOfTimesToRepeatTask = 5;
    /** The number of initial runs to ignore to get a better average */
    private int numberOfTasksToIgnore = 2;
    
    /** Booleans holding which tabs are enabled */
    private boolean singleFileTabEnabled = true;
    private boolean standaloneTabEnabled = true;
    private boolean directoryTabEnabled = true;
    private boolean xsltSandboxTabEnabled = true;
    private boolean xsdSandboxTabEnabled = true;
    private boolean xquerySandboxTabEnabled = true;
    private boolean validationTabEnabled = true;
    private boolean schematronTabEnabled = true;
    private boolean batchTabEnabled = true;        

    private int ops = 100;
    private Status status = Status.LOCKED;
    private boolean useTagSoup = false;

    private boolean useLexEv = false;
    private boolean lexevCdata = true;
    private String lexevCdataNamespace = "";    
    private boolean lexevEntityRef = true;
    private boolean lexevDoctype = true;
    private boolean lexevComments = true;    
    private boolean lexevDTDEntities = true;

    private Processor processor;

    private PrintStream outputStream = System.out;  // default to system.out, the GUI will the set output window over the top

    public enum MessageLevel { 
        SILENT, INFO, DEBUG; 
        public boolean includes(MessageLevel other) {
            return this.compareTo(other) >= 0;
        }
    };
    
    private static Config config = new Config();
        
    /** Private - Singleton */
    private Config() {
        if (isLicenseFileFound()) {
            setSchemaAwareEnabled(true);
        } else {
            setSchemaAwareEnabled(false);
        }
    }

    /**
     *  Gets Kernow's configuration object.  This is a singleton. 
     *  @return Returns the singleton instance
     */
    public static Config getConfig() {

        if (!loadedProps) {
            try {
                loadedProps = true;
                PropertyManager.loadProperties(config);
            } catch (Exception e) {
                Message.error(e.getMessage());
            }
        }

        return config;
    }

    /**
     * @return The namespace bindings registered within this configuration.
     */
    public NamespaceBindings getNamespaceBindings() {
        return namespaceBindings;
    }

    /**
     * @return Whether schema-aware Saxon has been detected.
     */
    public boolean isSchemaAwareEnabled() {
        return schemaAwareEnabled;
    }

    /**
     *  @return <code>true</code> if Kernow is running in Schema Aware mode, otherwise false
     */
    public boolean isUseSchemaAware() {
        return useSchemaAware;
    }
    
    public Processor getProcessor() {
        if (this.processor == null) {
            if (isSchemaAwareEnabled() && isUseSchemaAware()) {
                this.processor = new Processor(true);
            } else {
                this.processor = new Processor(false);
            }
        }

        if (isUseLexEv()) {
            processor.setConfigurationProperty(FeatureKeys.SOURCE_PARSER_CLASS, "net.sf.kernow.lexev.LexEvXMLReader");
        } else {
            processor.setConfigurationProperty(FeatureKeys.SOURCE_PARSER_CLASS, "org.apache.xerces.parsers.SAXParser");
        }

        return this.processor;
    }
    
    /**
     *  Sets whether to run in Schema Aware mode
     *  @param useSchemaAware <code>true</code> if Kernow should run in Schema Aware mode, otherwise false
     */
    public final void setUseSchemaAware(boolean useSchemaAware) {
        this.useSchemaAware = useSchemaAware;
    }
    
    /**
     *  For internal use.
     */
    public final void setSchemaAwareEnabled(boolean schemaAwareEnabled) {
        this.schemaAwareEnabled = schemaAwareEnabled;
        this.setValidationSaxon(schemaAwareEnabled);
    }
       
    /* ==================================== */
    
    /**
     *  Gets the version of Kernow
     *  @return The version number
     */
    public String getKernowVersion() {
        return kernowVersion;
    }
    
    /**
     *  Gets the version of Saxon Kernow is using
     *  @return The version as a string 
     */
    public String getSaxonVersion() {
        return net.sf.saxon.Version.getProductName() + " " + net.sf.saxon.Version.getProductVariantAndVersion(getProcessor().getUnderlyingConfiguration());
    }
    
    /**
     *  Gets whether a caching URI resolver will be used.
     *  @return <code>true</code> if a caching URI resolver will be used, <code>false</code> if not.
     */
    public boolean isUseCachingURIResolver() {
        return useCachingURIResolver;
    }

    /**
     *  Sets whether a caching URI resolver should be used.
     *  @param useCachingURIResolver <code>true</code> if a caching URI resolver should be used, <code>false</code> if not.
     */    
    public void setUseCachingURIResolver(boolean useCachingURIResolver) {
        this.useCachingURIResolver = useCachingURIResolver;
    }

    /**
     *  Gets whether a caching entity resolver will be used.
     *  @return <code>true</code> if a caching entity resolver will be used, <code>false</code> if not.
     */
    public boolean isUseCachingEntityResolver() {
        return useCachingEntityResolver;
    }

    /**
     *  Sets whether a caching entity resolver should be used.
     *  @param useCachingEntityResolver <code>true</code> if a caching entity resolver should be used, <code>false</code> if not.
     */       
    public void setUseCachingEntityResolver(boolean useCachingEntityResolver) {
        this.useCachingEntityResolver = useCachingEntityResolver;
    }

    /**
     *  Gets whether a caching entity resolver will be used for the collection() function.
     *  @return <code>true</code> if a caching entity resolver will be used for the collection() function, <code>false</code> if not.
     */    
    public boolean isUseCachingCollectionEntityResolver() {
        return useCachingCollectionEntityResolver;
    }

    /**
     *  Sets whether a caching entity resolver should be used for the collection() function.
     *  @param useCachingCollectionEntityResolver <code>true</code> if a caching entity resolver should be used for the collection() function, <code>false</code> if not.
     */       
    public void setUseCachingCollectionEntityResolver(boolean useCachingCollectionEntityResolver) {
        this.useCachingCollectionEntityResolver = useCachingCollectionEntityResolver;
    }

    /**
     *  Gets whether target output directory/file will be deleted first.
     *  @return <code>true</code> if output directory/file will be deleted first, <code>false</code> if not.
     */    
    public boolean isCleanOutputFiles() {
        return cleanOutputFiles;
    }

    /**
     *  Sets whether target output directory/file should be deleted first.
     *  @param cleanOutputFiles <code>true</code> if output directory/file should be deleted first, <code>false</code> if not.
     */      
    public void setCleanOutputFiles(boolean cleanOutputFiles) {
        this.cleanOutputFiles = cleanOutputFiles;
    }

    /**
     *  Gets the SkipValidation switch
     *  @return <code>true</code> if set, <code>false</code> if not set.
     */       
    public boolean isSkipValidation() {
        return skipValidation;
    }

    /**
     *  Sets the SkipValidation switch
     *  @param skipValidation <code>true</code> if set, <code>false</code> if not set.
     */      
    public void setSkipValidation(boolean skipValidation) {
        this.skipValidation = skipValidation;
    }

    /**
     *  Gets the LaxValidation switch (-vlax)
     *  @return <code>true</code> if set, <code>false</code> if not set.
     */       
    public boolean isLaxValidation() {
        return laxValidation;
    }

    /**
     *  Sets the LaxValidation switch (-vlax)
     *  @param laxValidation <code>true</code> if set, <code>false</code> if not set.
     */ 
    public void setLaxValidation(boolean laxValidation) {
        this.laxValidation = laxValidation;
    }

    /**
     *  Gets the StrictValidation switch (-val)
     *  @return <code>true</code> if set, <code>false</code> if not set.     
     */       
    public boolean isStrictValidation() {
        return strictValidation;
    }

    /**
     *  Sets the StrictValidation switch (-val)
     *  @param strictValidation <code>true</code> if set, <code>false</code> if not set.
     */      
    public void setStrictValidation(boolean strictValidation) {
        this.strictValidation = strictValidation;
    }

    /**
     *  Gets the NOVW switch (-novw)
     */       
    public boolean isTreatValidationErrorsAsWarnings() {
        return treatValidationErrorsAsWarnings;
    }
   
    public void setTreatValidationErrorsAsWarnings(boolean treatValidationErrorsAsWarnings) {
        this.treatValidationErrorsAsWarnings = treatValidationErrorsAsWarnings;
    }

    public void setLocalCacheDir(String path) {
        localCacheDir = path;
    }
    
    public String getLocalCacheDir() {
        return localCacheDir;
    }
    
    public boolean isUseSpecificSuffix() {
        return useSpecificSuffix;
    }

    public void setUseSpecificSuffix(boolean useSpecificSuffix) {
        this.useSpecificSuffix = useSpecificSuffix;
    }

    public String getSpecificSuffix() {
        if (isUseSpecificSuffix()) {
            return specificSuffix;
        }
        return null;
    }

    public void setSpecificSuffix(String specificSuffix) {
        this.specificSuffix = specificSuffix;
    }
 
    public boolean isSchemaRecurseSubdirectories() {
        return schemaRecurseSubdirectories;
    }

    public void setSchemaRecurseSubdirectories(boolean recurse) {
        schemaRecurseSubdirectories = recurse;
    }
    
    /**
     *  Gets the VW switch (-vw)
     *  @return <code>true</code> if set, <code>false</code> if not set.
     */       
    public boolean isSchemaReportFailuresOnly() {
        return schemaReportFailuresOnly;
    }

    public void setSchemaReportFailuresOnly(boolean report) {
        schemaReportFailuresOnly = report;
    }
    
    public boolean isSuppressURIandEntityMessages() {
        return suppressURIandEntityMessages;
    }

    public void setSuppressURIandEntityMessages(boolean suppress) {
        suppressURIandEntityMessages = suppress;
    }
    
    public void setAllowedInputSuffixes(String suffixes) {
        if (suffixes != null) {
            allowedInputSuffixes = suffixes;
        }
    }
    
    public String getAllowedInputSuffixes() {
        return allowedInputSuffixes;
    }

    public boolean isValidationSaxon() {
        return validationSaxon;
    }

    public void setValidationSaxon(boolean validationSaxon) {
        this.validationSaxon = validationSaxon;
        this.validationXerces = !validationSaxon;
    }

    public boolean isValidationXerces() {
        return validationXerces;        
    }

    public void setValidationXerces(boolean validationXerces) {
        this.validationXerces = validationXerces;
        this.validationSaxon = !validationXerces;
    }

    public boolean isDirectoryRecurseSubdirectories() {
        return directoryRecurseSubdirectories;
    }

    /**
     * Recurse subdirectories of the input directory for Directory Transforms
     * @param recurse <code>true</code> to recursively find all XML files in the input directory, <code>false</code> to not process subdirectories
     */
    public void setDirectoryRecurseSubdirectories(boolean recurse) {
        directoryRecurseSubdirectories = recurse;
    }
    
    public boolean isUseStandardTree() {
        return useStandardTree;
    }

    public void setUseStandardTree(boolean useStandardTree) {
        this.useStandardTree = useStandardTree;
    }

    public boolean isUseTinyTree() {
        return useTinyTree;
    }

    public void setUseTinyTree(boolean useTinyTree) {
        this.useTinyTree = useTinyTree;
    }

    public String getInitialMode() {
        return initialMode;
    }

    /**
     *  Sets the name of the initial mode for the transform (the -im switch equivalent)
     *  <code>setUseInitialMode</code> must also be called to enable the feature
     *  @param initialMode The initial mode
     */
    public void setInitialMode(String initialMode) {
        if (initialMode != null) {
            this.initialMode = initialMode;
        } else {
            this.initialMode = "";
        }
    }

    public String getInitialTemplate() {
        return initialTemplate;
    }

    /**
     *  Set the name of the initial template for the trasform (the -it switch equivalent)
     *  <code>setUseInitialTemplate</code> must also be called to enable the feature
     *  @param initialTemplate The name of the initial template
     */    
    public void setInitialTemplate(String initialTemplate) {
        if (initialTemplate != null) {
            this.initialTemplate = initialTemplate;
        } else {
            this.initialTemplate = "";
        }
    }

    public boolean isNovw() {
        return novw;
    }

    /**
     *  Sets the -novw switch (No Version Warning).  Disabled by default.
     *  @param novw <code>true</code> to set the switch, <code>false</code> to unset it
     */
    public void setNovw(boolean novw) {
        this.novw = novw;
    }

    public boolean isRetainLineNumbers() {
        return retainLineNumbers;
    }

    public void setRetainLineNumbers(boolean retainLineNumbers) {
        this.retainLineNumbers = retainLineNumbers;
    }

    public boolean isStripAllWhitespace() {
        return stripAllWhitespace;
    }

    public void setStripAllWhitespace(boolean stripAllWhitespace) {
        this.stripAllWhitespace = stripAllWhitespace;
    }

    public boolean isStripIgnorableWhitespace() {
        return stripIgnorableWhitespace;
    }

    public void setStripIgnorableWhitespace(boolean stripIgnorableWhitespace) {
        this.stripIgnorableWhitespace = stripIgnorableWhitespace;
    }

    public boolean isStripNoWhitespace() {
        return stripNoWhitespace;
    }

    public void setStripNoWhitespace(boolean stripNoWhitespace) {
        this.stripNoWhitespace = stripNoWhitespace;
    }

    public boolean isShowTiming() {
        return showTiming;
    }

    /**
     *  Sets the -t switch (Saxon's show timing info).  Disabled by default.
     *  @param showTiming <code>true</code> to set the switch, <code>false</code> to unset it
     */
    public void setShowTiming(boolean showTiming) {
        this.showTiming = showTiming;
    }

    public boolean isExplain() {
        return explain;
    }

    public void setExplain(boolean explain) {
        this.explain = explain;
    }
    
    public boolean isTraceCalls() {
        return traceCalls;
    }

    public void setTraceCalls(boolean traceCalls) {
        this.traceCalls = traceCalls;
    }

    public boolean isRecoverablesAreSilent() {
        return recoverablesAreSilent;
    }

    public void setRecoverablesAreSilent(boolean recoverablesAreSilent) {
        this.recoverablesAreSilent = recoverablesAreSilent;
    }

    public boolean isRecoverablesAreReported() {
        return recoverablesAreReported;
    }

    public void setRecoverablesAreReported(boolean recoverablesAreReported) {
        this.recoverablesAreReported = recoverablesAreReported;
    }

    public boolean isRecoverablesAreFatal() {
        return recoverablesAreFatal;
    }

    public void setRecoverablesAreFatal(boolean recoverablesAreFatal) {
        this.recoverablesAreFatal = recoverablesAreFatal;
    }

    public boolean isAllowXML11() {
        return allowXML11;
    }

    /**
     *  Sets the -1.1 switch (Allow XML 1.1 input).  Disabled by default.
     *  @param allowXML11 <code>true</code> to set the switch, <code>false</code> to unset it
     */    
    public void setAllowXML11(boolean allowXML11) {
        this.allowXML11 = allowXML11;
    }
        
    /**
     * Checks to see if saxon-license.lic is available on the classpath
     * @return <code>true</code> if it is, false if it can't be found
     */
    private static boolean isLicenseFileFound() {
        if (ClassLoader.getSystemResourceAsStream("saxon-license.lic") != null) {                
            return true;               
        } 
        return false;
    }    

    public boolean isUseInitialMode() {
        return useInitialMode;
    }

    /**
     *  Enables or disables the -im (Initial Mode) switch equivalent.  Disabled by default.
     *  <code>setInitialMode</code> should also be called with the value for the initial mode.
     *  @param useInitialMode <code>true</code> to set the switch, <code>false</code> to unset it
     */     
    public void setUseInitialMode(boolean useInitialMode) {
        this.useInitialMode = useInitialMode;
    }

    public boolean isUseInitialTemplate() {
        return useInitialTemplate;
    }

    /**
     *  Enables or disables the -it (Initial Template) switch equivalent.  Disabled by default.
     *  <code>setInitialTemplate</code> should also be called with the value for the initial template.
     *  @param useInitialTemplate <code>true</code> to set the switch, <code>false</code> to unset it
     */     
    public void setUseInitialTemplate(boolean useInitialTemplate) {
        this.useInitialTemplate = useInitialTemplate;
    }

    public static void setConfig(Config aConfig) {
        config = aConfig;
    }

    public boolean isDirectoryStopOnAnError() {
        return directoryStopOnAnError;
    }

    /**
     *  Sets whether Directory Transforms should stop if one of the transforms throws
     *  an error. Enabled by default.     
     *  @param directoryStopOnAnError <code>true</code> to stop on an error, <code>false</code> to continue
     */     
    public void setDirectoryStopOnAnError(boolean directoryStopOnAnError) {
        this.directoryStopOnAnError = directoryStopOnAnError;
    }

    /**
     * Gets Kernows message level
     * @return <code>true</code> if Kernow has been silenced
     */
    public MessageLevel getMessageLevel() {
        return messageLevel;
    }

    /**
     *  Sets Kernows message level
     *  For example: <code>MessageLevel.SILENT</code> will suppress all message output
     *  @param messageLevel 
     */
    public void setMessageLevel(MessageLevel messageLevel) {
        this.messageLevel = messageLevel;
    }

    public int getThreadPoolSize() {
        return threadPoolSize;
    }

    /**
     *  Sets the size of the thread pool to use for Directory Transforms.  
     *  By default this is set 1 to make Kernow single threaded.
     *  @param threadPoolSize The size of the thread pool to use
     */     
    public void setThreadPoolSize(int threadPoolSize) {
        this.threadPoolSize = threadPoolSize;
    }

    public boolean isRunRepeatedly() {
        return runRepeatedly;
    }

    /**
     * Enables or disables the Perfomance Testing feature.  Kernow will repeatedly
     * run Single File or Standalone Transforms and then provide a summary of the
     * transform times.
     * @param runRepeatedly <code>true</code> to enable the feature, <code>false</code> to disable it
     */
    public void setRunRepeatedly(boolean runRepeatedly) {
        this.runRepeatedly = runRepeatedly;
    }

    public int getNumberOfTimesToRepeatTask() {
        return numberOfTimesToRepeatTask;
    }

    /**
     * Performance Testing: Sets the number of times to repeat the transform
     * @param numberOfTimesToRepeatTask
     */
    public void setNumberOfTimesToRepeatTask(int numberOfTimesToRepeatTask) {
        this.numberOfTimesToRepeatTask = numberOfTimesToRepeatTask;
    }

    public int getNumberOfTasksToIgnore() {
        return numberOfTasksToIgnore;
    }

    /**
     * Performance Testing: Sets the number of initial transform times to ignore
     * when working out the average time for a sequence of repeatedly run transforms.
     * @param numberOfTasksToIgnore
     */    
    public void setNumberOfTasksToIgnore(int numberOfTasksToIgnore) {
        this.numberOfTasksToIgnore = numberOfTasksToIgnore;
    }

    public boolean isBatchTabEnabled() {
        return batchTabEnabled;
    }

    public void setBatchTabEnabled(boolean batchTabEnabled) {
        this.batchTabEnabled = batchTabEnabled;
    }

    public boolean isDirectoryTabEnabled() {
        return directoryTabEnabled;
    }

    public void setDirectoryTabEnabled(boolean directoryTabEnabled) {
        this.directoryTabEnabled = directoryTabEnabled;
    }

    public boolean isSchematronTabEnabled() {
        return schematronTabEnabled;
    }

    public void setSchematronTabEnabled(boolean schematronTabEnabled) {
        this.schematronTabEnabled = schematronTabEnabled;
    }

    public boolean isSingleFileTabEnabled() {
        return singleFileTabEnabled;
    }

    public void setSingleFileTabEnabled(boolean singleFileTabEnabled) {
        this.singleFileTabEnabled = singleFileTabEnabled;
    }

    public boolean isStandaloneTabEnabled() {
        return standaloneTabEnabled;
    }

    public void setStandaloneTabEnabled(boolean standaloneTabEnabled) {
        this.standaloneTabEnabled = standaloneTabEnabled;
    }

    public boolean isValidationTabEnabled() {
        return validationTabEnabled;
    }

    public void setValidationTabEnabled(boolean validationTabEnabled) {
        this.validationTabEnabled = validationTabEnabled;
    }

    public boolean isXquerySandboxTabEnabled() {
        return xquerySandboxTabEnabled;
    }

    public void setXquerySandboxTabEnabled(boolean xquerySandboxTabEnabled) {
        this.xquerySandboxTabEnabled = xquerySandboxTabEnabled;
    }

    public boolean isXsdSandboxTabEnabled() {
        return xsdSandboxTabEnabled;
    }

    public void setXsdSandboxTabEnabled(boolean xsdSandboxTabEnabled) {
        this.xsdSandboxTabEnabled = xsdSandboxTabEnabled;
    }

    public boolean isXsltSandboxTabEnabled() {
        return xsltSandboxTabEnabled;
    }

    public void setXsltSandboxTabEnabled(boolean xsltSandboxTabEnabled) {
        this.xsltSandboxTabEnabled = xsltSandboxTabEnabled;
    }

    public SchemaVersion getSchemaVersion() {
        return schemaVersion;
    }

    public void setSchemaVersion(SchemaVersion schemaVersion) {
        this.schemaVersion = schemaVersion;
    }

    public boolean isUseTagSoup() {
        return useTagSoup;
    }

    public void setUseTagSoup(boolean useTagSoup) {
        this.useTagSoup = useTagSoup;
    }
    
    public boolean isUseLexEv() {
        return useLexEv;
    }

    public void setUseLexEv(boolean useLexEv) {
        this.useLexEv = useLexEv;
    }

    public boolean isLexevCdata() {
        return lexevCdata;
    }

    public void setLexevCdata(boolean lexevCdata) {
        this.lexevCdata = lexevCdata;
    }

    public boolean isLexevComments() {
        return lexevComments;
    }

    public void setLexevComments(boolean lexevComments) {
        this.lexevComments = lexevComments;
    }

    public boolean isLexevDoctype() {
        return lexevDoctype;
    }

    public void setLexevDoctype(boolean lexevDoctype) {
        this.lexevDoctype = lexevDoctype;
    }

    public boolean isLexevEntityRef() {
        return lexevEntityRef;
    }

    public void setLexevEntityRef(boolean lexevEntityRef) {
        this.lexevEntityRef = lexevEntityRef;
    }

    public String getLexevCdataNamespace() {
        return lexevCdataNamespace;
    }

    public void setLexevCdataNamespace(String lexevCdataNamespace) {
        this.lexevCdataNamespace = lexevCdataNamespace;
    }

    public boolean isLexevDTDEntities() {
        return lexevDTDEntities;
    }

    public void setLexevDTDEntities(boolean lexevDTDEntities) {
        this.lexevDTDEntities = lexevDTDEntities;
    }

    public PrintStream getOutputStream() {
        return outputStream;
    }

    public void setOutputStream(PrintStream outputStream) {
        this.outputStream = outputStream;
    }

    public int getOps() {
        return ops;
    }

    public void setOps(int val) {
        ops = val;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getCatalogPath() {
        return catalogPath;
    }

    public void setCatalogPath(String catalogPath) {
        this.catalogPath = catalogPath;
    }
    
    
}
