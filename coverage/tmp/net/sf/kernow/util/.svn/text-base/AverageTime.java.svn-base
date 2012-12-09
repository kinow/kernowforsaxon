/*
 * AverageTime.java
 *
 * Created on 25 June 2007, 14:38
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package net.sf.kernow.util;

import java.util.ArrayList;
import net.sf.kernow.Config;

/**
 *
 * @author welcha
 */
public class AverageTime {
    
    public static String getAverageTimeInWords(ArrayList<Long> times) {
        Config config = Config.getConfig();
        StringBuffer buff = new StringBuffer();
        
        int ignoreFirst = config.getNumberOfTasksToIgnore();
        long total = getTotalTime(times, ignoreFirst);
                
        buff.append("\n\nRan " + times.size() + " times");

        for (int n = 0; n < times.size(); n++) {   
            buff.append("\nRun " + (n + 1) + ": " + Timer.getDurationInWords(times.get(n)));
        }
        if (ignoreFirst > 0) {
            buff.append("\nIgnoring first " + ignoreFirst + " times");
        }
        buff.append("\nTotal Time (last " + (times.size() - ignoreFirst) + " runs): " + Timer.getDurationInWords(total));
        buff.append("\nAverage Time (last " + (times.size() - ignoreFirst) + " runs): " + Timer.getDurationInWords(getAverageTime(times, ignoreFirst)));
        
        return buff.toString();
    }
    
    static long getTotalTime(ArrayList<Long> times, int ignoreFirst) {
        long total = 0;
        for (int n = ignoreFirst; n < times.size(); n++) {            
            total += times.get(n);                        
        }
        return total;
    }
    
    static long getAverageTime(ArrayList<Long> times, int ignoreFirst) {
        long total = getTotalTime(times, ignoreFirst);
        return total / (times.size() - ignoreFirst);
    }
}
