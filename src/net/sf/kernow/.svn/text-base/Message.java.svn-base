package net.sf.kernow;

import java.io.PrintStream;

/**
 * A class which handles message output to the user.  It prints everything to 
 * Standard out and err at the moment - a future enhancement is to send the output
 * to a user supplied PrintWriter
 *
 * @author Andrew Welch
 */
public class Message {
    
    private static Config config = Config.getConfig();
    private static PrintStream outputStream = Config.getConfig().getOutputStream();

    public static void debug(String message) {
        if (config.getMessageLevel().includes(Config.MessageLevel.DEBUG)) {
            outputStream.println(message);
        }
    }
    
    public static void info(String message) {
        if (config.getMessageLevel().includes(Config.MessageLevel.INFO)) {
            outputStream.println(message);
        }
    }
    
    public static void error(String message) {
        if (config.getMessageLevel().includes(Config.MessageLevel.INFO)) {
            outputStream.println(message);
        }
    }

    public static void exception(Throwable t, boolean printStackTrace) {
        if (config.getMessageLevel().includes(Config.MessageLevel.INFO)) {
            if (printStackTrace) {
                t.printStackTrace(outputStream);
            } else {
                outputStream.println(t.getMessage());
            }
        }
    }

}
