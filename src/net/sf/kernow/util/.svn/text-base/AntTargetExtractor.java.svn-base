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
import java.util.Iterator;
import java.util.TreeSet;
import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.ProjectHelper;

/**
 * Extract the targets from an ant buildfile
 * @author ajwelch
 */
public class AntTargetExtractor {
    
    public static TreeSet<String> getAntTargets(String antBuildfile) {
        
        TreeSet<String> targets = new TreeSet<String>();
        
        Project project = new Project();

        try { 
            project.init(); 
            ProjectHelper.getProjectHelper().parse(project, new File(antBuildfile)); 
                        
            Iterator i = project.getTargets().values().iterator();
            while (i.hasNext()) {
                String s = i.next().toString();
                if (s != "") {
                    targets.add(s);
                }
            }
            
        } catch (BuildException e) {
            System.err.println(e.toString());
        }
        
        return targets;
    }
    
    public static String getDefaultTarget(String antBuildfile) {
                 
        Project project = new Project();
        
        try { 
            project.init(); 
            ProjectHelper.getProjectHelper().parse(project, new File(antBuildfile)); 
            
            return project.getDefaultTarget();          
            
        } catch (BuildException e) {
            System.err.println(e.toString());
        }
        
        return null;
    }    
}
