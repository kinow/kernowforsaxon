/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.sf.kernow;

/**
 *
 * @author andrew
 */
public class OpManager {

    public static boolean hasOpsLeft() {
        Config config = Config.getConfig();
        if (config.getStatus() == Status.LOCKED) {
            if (config.getOps() > 0) {
                return true;
            } else {
                return false;
            }
        }

        return true;
    }

    public static void opPerformed() {
        int ops = Config.getConfig().getOps();
        Config.getConfig().setOps(--ops);
    }
}
