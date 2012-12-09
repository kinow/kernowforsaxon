package net.sf.kernow;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import net.sf.kernow.transform.TransformControllerImpl;
import net.sf.kernow.ui.TabbedView;

/**
 * Starts the GUI.
 * @author welcha
 */
public class GUI {
    
    public static void main(String[] args) throws Exception {

        String osName = System.getProperty("os.name");

        try {
            if (osName.indexOf("Windows") == -1) {
                UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
            } else {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            }
        } catch (UnsupportedLookAndFeelException ulaf) {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        }
        
        new TransformControllerImpl(new TabbedView()); 
    }    
}
