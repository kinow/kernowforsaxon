/*
 * Params.java
 *
 * Created on 09 November 2005, 15:55
 */

package net.sf.kernow.transform;

import java.net.URI;
import java.util.HashMap;
import javax.xml.transform.Source;

/*
 * Helper class for the params dialog to allow the storing of parameters for
 * different stylesheets
 * @author AWelch
 */
public class Params {
    
    private static HashMap<URI, HashMap<String, Object>> paramsMap = new HashMap<URI, HashMap<String, Object>>();
    
    /** Stores the parameters for a given stylesheet */
    public static void setParamsForStylesheet(URI stylesheet, HashMap<String, Object> params) {
        paramsMap.put(stylesheet, params);
    }
    
    public static HashMap<String, Object> getParamsForStylesheet(Source stylesheet) {
        URI uri = URI.create(stylesheet.getSystemId());
        return getParamsForStylesheet(uri);
    }
     
    /** Retrieves the parameters for a given stylesheetURI */
    public static HashMap<String, Object> getParamsForStylesheet(URI stylesheetURI) {
        if (stylesheetURI != null) {
            HashMap<String, Object> map = paramsMap.get(stylesheetURI);
            // Return the stored parameters
            if (map != null) {
                return map;
            }
        }

        // No parameters stored, so return empty hashmap
        return new HashMap<String, Object>();              
    }
    
}
