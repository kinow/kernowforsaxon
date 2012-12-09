
package net.sf.kernow.ant;

import java.io.File;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import net.sf.kernow.Message;
import net.sf.kernow.transform.Params;
import net.sf.kernow.internal.ProgressObserver;
import net.sf.kernow.transform.TransformTask;
import net.sf.kernow.util.FileUtil;
import net.sf.kernow.util.Timer;
import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.DefaultLogger;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.ProjectHelper;

/**
 *
 * @author Andrew Welch
 */      

public class AntRunner extends TransformTask {
    
    private ArrayList<ProgressObserver> progressObservers;
     
    private boolean cancelClicked;
    private int percentComplete;
    private String progressText;
    
    private Project project;
    private String buildfile;
    
    public AntRunner() {
        progressObservers = new ArrayList<ProgressObserver>();
        cancelClicked = false;        
    }
    
    public void init(String buildfile, int messageLevel) {
        project = new Project();
        this.buildfile = buildfile;
        try { 
            project.init();
            
            DefaultLogger logger = new DefaultLogger();
            
            logger.setMessageOutputLevel(messageLevel);
            
            logger.setErrorPrintStream(System.out);
            logger.setOutputPrintStream(System.out);
            
            project.addBuildListener(logger);

            File f = new File(buildfile);
            project.setProperty("ant.file", f.getAbsolutePath());
            
            ProjectHelper.getProjectHelper().parse(project, f); 
        } catch (BuildException e) {
            Message.exception(e, false); 
        }
    }

    public void setProperties(HashMap<String, Object> properties) {
        for (Map.Entry<String, Object> entry : properties.entrySet()) {
            project.setUserProperty(entry.getKey(), (String)entry.getValue());
        }
    }

    public boolean runTarget(String target) {
        
        boolean success = false;
        
        try { 
            
            setProperties(Params.getParamsForStylesheet(FileUtil.createURI(buildfile)));
            
            long startTime = System.currentTimeMillis();
            
            project.executeTarget(target);  
            
            long endTime = System.currentTimeMillis();
            setTimeTakenInWords("Done in " + Timer.getTimeTaken(startTime, endTime));                        
            
            success = true;
        } catch (BuildException e) { 
            Message.exception(e, false); 
        } catch (URISyntaxException e) { 
            Message.exception(e, false); 
        }
        
        notifyTimeObservers();        
        
        return success;
    }
    
}
