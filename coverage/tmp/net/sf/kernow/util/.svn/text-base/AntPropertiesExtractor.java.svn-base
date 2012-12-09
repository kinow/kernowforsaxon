/*
 * NamedTemplateExtractor.java
 *
 * Created on 28 November 2005, 19:33
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package net.sf.kernow.util;

import java.io.File;
import java.net.URI;
import java.util.HashMap;
import java.util.Hashtable;
import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.ProjectHelper;

/**
 * Extract the targets from an ant buildfile
 * @author ajwelch
 */
public class AntPropertiesExtractor {
    
    public static HashMap<String, Object> getAntProperties(URI antBuildfile) {
        
        HashMap<String, Object> props = new HashMap<String, Object>();
        
        Project project = new Project();

        try { 
            project.init(); 
            ProjectHelper.getProjectHelper().parse(project, new File(antBuildfile)); 
            
            Hashtable propsTable = project.getProperties();
            for (Object name : propsTable.keySet()) {            
                props.put((String)name, propsTable.get(name));
            }
            
        } catch (BuildException e) {
            System.err.println(e.toString());
        }
        
        return props;
    }
  
}
