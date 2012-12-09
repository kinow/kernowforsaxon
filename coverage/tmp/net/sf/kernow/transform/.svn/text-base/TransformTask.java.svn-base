/*
 * TransformTask.java
 *
 * Created on 26 January 2006, 12:14
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package net.sf.kernow.transform;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import javax.xml.transform.Result;
import javax.xml.transform.stream.StreamResult;
import net.sf.kernow.Config;
import net.sf.kernow.Message;
import net.sf.kernow.internal.TimeObserver;
import net.sf.kernow.internal.TimedTransform;
import net.sf.kernow.util.Cleaner;
import net.sf.saxon.Controller;
import net.sf.saxon.s9api.QName;
import net.sf.saxon.s9api.XdmAtomicValue;
import net.sf.saxon.s9api.XsltTransformer;
import net.sf.saxon.trans.XPathException;

/**
 *
 * @author AWelch
 */
public abstract class TransformTask implements TimedTransform {
    
    private ArrayList<TimeObserver> timeObservers;
    private String timeTakenInWords;
    private long timeTakeInMS;
    
    /** Creates a new instance of TransformTask */
    public TransformTask() {
        timeObservers = new ArrayList<TimeObserver>();
        timeTakenInWords = "Error!";
    }
    
    public String getTimeTakenInWords() {
        return timeTakenInWords;
    }
    
    public void setTimeTakenInWords(String timeTaken) {
        this.timeTakenInWords = timeTaken;
    }
             
    void setParameters(XsltTransformer xsltTransformer, HashMap<String, Object> params) {
        if (params != null) {
            for (String name : params.keySet()) {
                String value = (String)params.get(name);
                //System.out.println("Specifying param \"" + name + "\" as " + "\"" + value + "\"");
                xsltTransformer.setParameter(new QName(name), new XdmAtomicValue(value));
            }
        }
    } 
    
    void setControllerSwitches(Controller controller, Config config) {
        if (config.isUseInitialTemplate()) {
            String itemplate = config.getInitialTemplate();
            if (itemplate != null && !itemplate.equals("")) {
                try {
                    controller.setInitialTemplate(itemplate);
                } catch (XPathException ex) {
                    Message.error(ex.getMessage());
                }
            } else {
                Message.error("\"Use Initial Template\" is set, but the value is empty");
            }
        }

        // Initial Mode
        if (config.isUseInitialMode()) {
            String imode = config.getInitialMode();
            if (imode != null && !imode.equals("")) {
                controller.setInitialMode(imode);
            } else {
                Message.error("\"Use Initial Mode\" is set, but the value is empty");
            }
        }

        // Explain 
        if (config.isExplain()) {
            controller.addTraceListener(new net.sf.saxon.trace.XSLTTraceListener());
        }
    }
    
    public void addTimeObserver(TimeObserver timeObserver) {
        timeObservers.add(timeObserver);
    }
    
    public void notifyTimeObservers() {
        for (TimeObserver t : timeObservers) {
            t.updateTimeTaken(this);
        }
    }    
    
    public void checkForCleaning(String path) {
        if (Config.getConfig().isCleanOutputFiles() && path != null) {
            System.out.print("Cleaning " + path + "...");
            Cleaner.clean(path);
            System.out.println("Done.");
        }        
    }

    public long getTimeTakeInMS() {
        return timeTakeInMS;
    }

    public void setTimeTakeInMS(long timeTakeInMS) {
        this.timeTakeInMS = timeTakeInMS;
    }
}
