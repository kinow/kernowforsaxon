/*
 * Cleaner.java
 *
 * Created on 06 December 2005, 14:36
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package net.sf.kernow.util;

import java.io.File;
import net.sf.kernow.Message;

/**
 *
 * @author AWelch
 */
public class Cleaner {
    
    public static void clean(String path) {
        clean(new File(path));
    }

    public static void clean(File file) {
        // this needs sorting
        if (file.isDirectory()) {
            for (File child : file.listFiles()) {
                if (!child.delete()) {
                    Message.error("Unable to delete: " + child.getAbsolutePath());
                }
            }
        } else {
            boolean done = file.delete();
            if (!done) {
                Message.error("Unable to delete: " + file.getAbsolutePath());
            }
        }
    }    
}
