
package net.sf.kernow;

import java.io.File;
import java.net.URISyntaxException;
import java.util.HashMap;
import javax.xml.transform.Source;
import net.sf.kernow.transform.DirectoryTransformer;
import net.sf.kernow.transform.Params;
import net.sf.kernow.util.CommandLineParamParser;
import net.sf.kernow.util.FileUtil;
import net.sf.kernow.util.PropertyManager;

/**
 *
 * @author welcha
 */
public class DirectoryTransform {
    
    /** Creates a new instance of DirectoryTransform */
    public DirectoryTransform() {
    }
    
    public static void main(String[] args) throws Exception {
        
        if (args.length < 3) {
            System.err.println("USAGE: DirectoryTransform inputDir stylesheet outputDir [paramName=paramValue]*");
        }
                
        if (args.length > 3) {
            HashMap<String, Object> params = CommandLineParamParser.parseParams(args, 3);
            if (params != null) {
                Params.setParamsForStylesheet(FileUtil.createURI(args[1]), params);    
            } else {
                throw new AssertionError("Tranformation Failed: params null");
            }            
        }
        
        try {
            DirectoryTransformer dirTransformer = new DirectoryTransformer();
            File inputDir = new File(args[0]);
            Source stylesheet = FileUtil.createSource(args[1]);
            File outputDir = new File(args[2]);
            boolean success = dirTransformer.runDirectoryTransform(inputDir, stylesheet, outputDir);
        
            if (!success) {
                throw new AssertionError("Transformation Failed.");
            }
        } catch (URISyntaxException ex) {
            Message.exception(ex, false);
        }
        
    }
}
