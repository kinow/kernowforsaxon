/*
 * Timer.java
 *
 * Created on 06 December 2005, 10:08
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package net.sf.kernow.util;

/**
 * Outputs seconds as a sentence - "x minutes x seconds"
 * @author AWelch
 */
public class Timer {
    
    public static String getTimeTaken(long startTimeInMillis, long endTimeInMillis) {
        long duration = endTimeInMillis - startTimeInMillis;
        return getDurationInWords(duration);
    }
    
    public static String getDurationInWords(long duration) {
        
        if (duration == 0L) {
            // duration is zero so just return
            return "0 ms";
        }
        
        long ms = duration % 1000;
        duration = duration / 1000;
        long s = duration % 60;
        duration = duration / 60;
        long m = duration % 60;
        duration = duration / 60;
        long h = duration % 60;        
        
        StringBuffer timeTaken = new StringBuffer(50);

        if (h > 1) {
            timeTaken.append(h);
            timeTaken.append(" hours ");
        } else if (h == 1) {
            timeTaken.append(h);
            timeTaken.append(" hour ");
        }

        if (m > 1) {
            timeTaken.append(m);
            timeTaken.append(" minutes ");
        } else if (m == 1) {
            timeTaken.append(m);
            timeTaken.append(" minute ");
        }

        if ((h != 0 | m != 0) && s == 0) {
            timeTaken.append("exactly");
        } else if (s == 1) {    
            timeTaken.append(s);
            timeTaken.append(" second ");
        } else if (s > 1) {
            timeTaken.append(s);
            timeTaken.append(" seconds ");
        }

        if (h == 0 && m == 0 && s < 30 & ms != 0) {
            timeTaken.append(ms);
            timeTaken.append(" ms");
        }            
        
        return timeTaken.toString().trim(); 
    }    
}
