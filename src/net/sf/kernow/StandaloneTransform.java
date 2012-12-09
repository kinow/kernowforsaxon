/*
 * StandaloneTransform.java
 *
 * Created on 09 March 2007, 13:56
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package net.sf.kernow;

import java.io.File;
import java.util.HashMap;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import net.sf.kernow.transform.Params;
import net.sf.kernow.transform.SingleFileTransformer;
import net.sf.kernow.util.CommandLineParamParser;
import net.sf.kernow.util.FileUtil;
import net.sf.kernow.util.PropertyManager;
import net.sf.saxon.s9api.Serializer;

/**
 *
 * @author welcha
 */
public class StandaloneTransform {
    
    /** Creates a new instance of StandaloneTransform */
    public StandaloneTransform() {
    }
    
    public static void main(String[] args) throws Exception {
        
        if (args.length < 3) {
            System.err.println("USAGE: StandaloneTransform stylesheet initialTemplate outputFile [paramName=paramValue]*");
        }
                
        if (args.length > 3) {
            HashMap<String, Object> params = CommandLineParamParser.parseParams(args, 3);
            if (params != null) {
                Params.setParamsForStylesheet(FileUtil.createURI(args[0]), params);    
            } else {
                System.exit(1);
            }            
        }
        
        SingleFileTransformer sfTransformer = new SingleFileTransformer();
        Source style  = new StreamSource(new File(args[0]));

        Serializer serializer = new Serializer();
        serializer.setOutputFile(new File(args[2]));

        String initialTemplate = args[1];
        boolean success = sfTransformer.transform(null, style, serializer, initialTemplate);
        
        if (!success) {
            System.exit(1);
        }
        
    } 
}
