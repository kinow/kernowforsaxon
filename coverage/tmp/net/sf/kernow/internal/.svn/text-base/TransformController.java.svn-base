/*
 * TransformController.java
 *
 * Created on 10 January 2006, 14:05
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package net.sf.kernow.internal;

import java.io.File;
import java.io.InputStream;
import java.io.Reader;
import javax.xml.transform.Source;
import net.sf.saxon.s9api.Serializer;

/**
 *
 * @author AWelch
 */
public interface TransformController {

    // Transforms
    public boolean runStandaloneTransform(Source stylesheet, Serializer serializer, String initialTemplate);
    public boolean runSingleFileTransform(Source input, Source stylesheet, Serializer serializer);
    public boolean runDirectoryTransform(File inputDir, Source stylesheet, File outputDir);

    // Schemas
    public boolean runSchemaCheck(Source   schema);
    public boolean runSchemaCheck(Source[] schemas);
    public boolean runValidate(Source doc, Source   schema);
    public boolean runValidate(Source doc, Source[] schemas);
    public boolean runDirectoryValidate(File dir, Source   schema);
    public boolean runDirectoryValidate(File dir, Source[] schemas);

    // Ant
    public boolean runAnt(String buildFile, String target, int messageLevel);    

    // Queries
    public boolean runStandaloneXQuery(String query, Serializer serializer, String baseUri);
    public boolean runStandaloneXQuery(Reader query, Serializer serializer, String baseUri);
    public boolean runStandaloneXQuery(InputStream query, Serializer serializer, String baseUri);
}
