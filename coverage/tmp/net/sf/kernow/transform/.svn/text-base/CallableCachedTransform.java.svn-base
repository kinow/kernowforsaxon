/*
 * CallableCachedTransform.java
 *
 * Created on 20 June 2007, 10:08
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package net.sf.kernow.transform;

import java.util.HashMap;
import java.util.concurrent.Callable;
import javax.xml.transform.Source;
import net.sf.saxon.s9api.Serializer;
import net.sf.saxon.s9api.XsltExecutable;

/**
 *
 * @author welcha
 */
public class CallableCachedTransform implements Callable<Boolean> {
    
    private final CachedStylesheetTransformer transformer;
    private final Source sourceXML;
    private final XsltExecutable xsltExecutable;
    private final Serializer serializer;
    private final HashMap<String, Object> params;
    
    /** Creates a new instance of CallableCachedTransform */
    public CallableCachedTransform(CachedStylesheetTransformer transformer, 
            Source sourceXML, XsltExecutable xsltExecutable,
            Serializer serializer, HashMap<String, Object> params) {
        this.transformer = transformer;
        this.sourceXML = sourceXML;
        this.xsltExecutable = xsltExecutable;
        this.serializer = serializer;
        this.params = params;        
    }

    @Override
    public Boolean call() throws Exception {       
        boolean success = transformer.transform(sourceXML, xsltExecutable, serializer, params);
        return Boolean.valueOf(success);
    }    
    
}
