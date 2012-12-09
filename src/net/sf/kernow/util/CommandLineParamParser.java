/*
 * CommandLineParamParser.java
 *
 * Created on 09 March 2007, 11:19
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package net.sf.kernow.util;

import java.util.HashMap;

/**
 *
 * @author welcha
 */
public class CommandLineParamParser {
    
    /** Creates a new instance of CommandLineParamParser */
    public CommandLineParamParser() {
    }
 
    /**
     * Extracts name value pairs from the parameters section of the commandline
     * @param args The commmand line arguments array
     * @param startIndex The index at which the parameters start
     * @return A HashMap containing the name value pairs
     **/
    public static HashMap<String, Object> parseParams(String[] args, int startIndex) {
        
        HashMap<String, Object> params = new HashMap<String, Object>(args.length);
        
        for (int i = startIndex; i < args.length; i++) {
            String[] pair = args[i].split("=");
            if (pair.length != 2) {
                System.err.println("Bad param pair: " + args[i]);
                return null;
            }
            
            params.put(pair[0], pair[1]);
        }
        
        return params;
    }
}
