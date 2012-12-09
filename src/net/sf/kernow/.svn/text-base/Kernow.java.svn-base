package net.sf.kernow;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import net.sf.kernow.schema.SchemaValidator;
import net.sf.kernow.transform.DirectoryTransformer;
import net.sf.kernow.transform.Params;
import net.sf.kernow.transform.SingleFileTransformer;
import net.sf.kernow.util.FileUtil;
import net.sf.kernow.xquery.StandaloneXQuery;
import net.sf.saxon.s9api.Destination;
import net.sf.saxon.s9api.Serializer;

/**
 * A high level API for running transforms, XQueries and XML Schema validation.
 *
 * <p>To use Kernow's GUI use <code>net.sf.kernow.GUI</code>.
 *
 * @author Andrew Welch
 */
public class Kernow {
    
    /**
     *  Creates a new instance of Kernow
     */
    public Kernow() {
        
    }
    
    /**
     *  Returns Kernow's configuration object.  This is a singleton and should be used
     *  to set any options or switches prior to the transform.
     *  @return Kernow's config object
     */
    public Config getConfig() {
        return Config.getConfig();
    }

    /**
     *  Runs a standard transformation with parameters
     *  @param inputXML The input XML file
     *  @param stylesheet The XSLT to use
     *  @param result The <code>Result</code> of the transform
     *  @param params A HashMap of name value pairs to use as parameters
     *  @return Returns true if the transform was a success
     */
    public boolean singleFileTransform(Source inputXML, Source stylesheet, Serializer serializer, HashMap<String, Object> params) {
        Params.setParamsForStylesheet(URI.create(stylesheet.getSystemId()), params);
        return singleFileTransform(inputXML, stylesheet, serializer);
    }
    
    /**
     *  Runs a standard transformation without parameters
     *  @param inputXML The XML input to transform
     *  @param stylesheet The XSLT to use
     *  @param result The <code>Result</code> of the transform
     *  @return Returns true if the transform was a success
     */    
    public boolean singleFileTransform(Source inputXML, Source stylesheet, Serializer serializer) {
        SingleFileTransformer sfTransformer = new SingleFileTransformer();
        return sfTransformer.transform(inputXML, stylesheet, serializer, null);
    }

    /**
     *  Runs a directory transformation with parameters
     *  @param inputDir The input directory
     *  @param stylesheet The XSLT to use
     *  @param outputDir The output directory
     *  @param params A HashMap of name value pairs to use as parameters     
     *  @return Returns true if all transforms are successful, false if any fail.
     */       
    public boolean directoryTransform(String inputDir, String stylesheet, String outputDir, HashMap<String, Object> params) throws URISyntaxException {
        Params.setParamsForStylesheet(FileUtil.createURI(stylesheet), params);
        return directoryTransform(inputDir, stylesheet, outputDir);
    }

    /**
     *  Runs a directory transformation.
     *  @param inputDir The input directory
     *  @param stylesheet The XSLT to use
     *  @param outputDir The output directory
     *  @return Returns true if all transforms are successful, false if any fail. 
     */    
    public boolean directoryTransform(String inputDir, String stylesheet, String outputDir) throws URISyntaxException {
        DirectoryTransformer dirTransformer = new DirectoryTransformer();
        return dirTransformer.runDirectoryTransform(new File(inputDir), FileUtil.createSource(stylesheet), new File(outputDir));
    }

    /**
     *  Runs a standalone transformation with parameters.  Standalone transforms don't have an input XML, instead
     *  processing starts at the named template specified by the "initial template" parameter.
     *  @param stylesheet The XSLT to use
     *  @param initialTemplate The name of the template where processing should start
     *  @param result The <code>Result</code> of the transform
     *  @param params A HashMap of name value pairs to use as parameters     
     *  @return Returns true if the transform was a success
     */    
    public boolean standaloneTransform(Source stylesheet, String initialTemplate, Serializer serializer, HashMap<String, Object> params) {
        Params.setParamsForStylesheet(URI.create(stylesheet.getSystemId()), params);
        return standaloneTransform(stylesheet, initialTemplate, serializer);
    }

    /**
     *  Runs a standalone transformation.  Standalone transforms don't have an input XML, instead
     *  processing starts at the named template specified by the "initial template" parameter.
     *  @param stylesheet The XSLT to use
     *  @param initialTemplate The name of the template where processing should start
     *  The <code>Result</code> of the transform
     *  @return Returns true if the transform was a success
     */    
    public boolean standaloneTransform(Source stylesheet, String initialTemplate, Serializer serializer) {
        SingleFileTransformer sfTransformer = new SingleFileTransformer();        
        return sfTransformer.transform(null, stylesheet, serializer, initialTemplate);
    }    
      
    /**
     * Runs a standalone XQuery (one with no input) and sends the result to the Result.
     * @param xquery The XQuery to execute
     * The <code>Result</code> of the XQuery
     */
    public boolean standaloneXQuery(String xquery, Result result) {
        StandaloneXQuery standaloneXQuery = new StandaloneXQuery();
        return standaloneXQuery.runQuery(xquery);
    }
    
    /** 
     * Checks that schema compiles
     * @param schema The schema to compile
     * @return <code>true</code> if the schema compiles successfully
     */
    public boolean validateSchema(Source schema) {
        SchemaValidator schemaValidator = new SchemaValidator();
        return schemaValidator.validateSchema(schema);
    }

    /** 
     * Validates the XML with the schema
     * @param xml The XML to validate
     * @param schema The schema to validate the XML with
     * @return <code>true</code> if the XML is valid according to the schema
     */    
    public boolean validateFile(Source xml, Source schema) {
        SchemaValidator schemaValidator = new SchemaValidator();
        return schemaValidator.validate(xml, schema);
    }
    
    /**
     * Validate all files in a directory
     * @param directory The directory to validate
     * @param schema The schema to validate the directory with
     * @return <code>true</code> if all files in the directory are valid, <code>false</code> 
     * if any fail validation or there's an error
     */
    public boolean validateDirectory(File directory, Source schema) {
        SchemaValidator schemaValidator = new SchemaValidator();
        return schemaValidator.validateDirectory(directory, schema);
    }        
    
    /**
     *  Runs a standard transformation with parameters
     *  @param inputXML The input XML file
     *  @param stylesheet The XSLT to use
     *  @param outputFile The output file
     *  @param params A HashMap of name value pairs to use as parameters
     *  @return Returns true if the transform was a success
     */
    public boolean runSingleFileTransform(String inputXML, String stylesheet, String outputFile, HashMap<String, Object> params) throws URISyntaxException {
        Params.setParamsForStylesheet(FileUtil.createURI(stylesheet), params);
        return runSingleFileTransform(inputXML, stylesheet, outputFile);
    }
    
    /**
     *  Runs a standard transformation without parameters
     *  @param inputXML The input XML file
     *  @param stylesheet The XSLT to use
     *  @param outputFile The output file
     *  @return Returns true if the transform was a success
     */
    public boolean runSingleFileTransform(String inputXML, String stylesheet, String outputFile) {
        SingleFileTransformer sfTransformer = new SingleFileTransformer();
        Source input  = new StreamSource(new File(inputXML));
        Source style  = new StreamSource(new File(stylesheet));
        Serializer serializer = new Serializer();
        serializer.setOutputFile(new File(outputFile));
        return sfTransformer.transform(input, style, serializer, null);
    }

    /**
     *  Runs a directory transformation with parameters
     *  @param inputDir The input directory
     *  @param stylesheet The XSLT to use
     *  @param outputDir The output directory
     *  @param params A HashMap of name value pairs to use as parameters     
     *  @return Returns true if all transforms are successful, false if any fail.
     */
    public boolean runDirectoryTransform(String inputDir, String stylesheet, String outputDir, HashMap<String, Object> params) throws URISyntaxException {
        Params.setParamsForStylesheet(FileUtil.createURI(stylesheet), params);
        return runDirectoryTransform(inputDir, stylesheet, outputDir);
    }

    /**
     *  Runs a directory transformation.
     *  @param inputDir The input directory
     *  @param stylesheet The XSLT to use
     *  @param outputDir The output directory
     *  @return Returns true if all transforms are successful, false if any fail. 
     */
    public boolean runDirectoryTransform(String inputDir, String stylesheet, String outputDir) throws URISyntaxException {
        DirectoryTransformer dirTransformer = new DirectoryTransformer();
        return dirTransformer.runDirectoryTransform(new File(inputDir), FileUtil.createSource(stylesheet), new File(outputDir));
    }

    /**
     *  Runs a standalone transformation with parameters.  Standalone transforms don't have an input XML, instead
     *  processing starts at the named template specified by the "initial template" parameter.
     *  @param stylesheet The XSLT to use
     *  @param initialTemplate The name of the template where processing should start
     *  @param outputFile The primary output file
     *  @param params A HashMap of name value pairs to use as parameters     
     *  @return Returns true if the transform was a success
     */
    public boolean runStandaloneTransform(String stylesheet, String initialTemplate, String outputFile, HashMap<String, Object> params) throws URISyntaxException {
        Params.setParamsForStylesheet(FileUtil.createURI(stylesheet), params);
        return runStandaloneTransform(stylesheet, initialTemplate, outputFile);
    }

    /**
     *  Runs a standalone transformation.  Standalone transforms don't have an input XML, instead
     *  processing starts at the named template specified by the "initial template" parameter.
     *  @param stylesheet The XSLT to use
     *  @param initialTemplate The name of the template where processing should start
     *  @param outputFile The primary output file   
     *  @return Returns true if the transform was a success
     */ 
    public boolean runStandaloneTransform(String stylesheet, String initialTemplate, String outputFile) {
        SingleFileTransformer sfTransformer = new SingleFileTransformer();
        Source style  = new StreamSource(new File(stylesheet));
        Serializer serializer = new Serializer();
        serializer.setOutputFile(new File(outputFile));
        return sfTransformer.transform(null, style, serializer, initialTemplate);
    }
    
}
