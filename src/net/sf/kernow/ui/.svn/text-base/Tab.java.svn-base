/*
 * Tab.java
 *
 * Created on September 29, 2007, 12:07 AM
 */

package net.sf.kernow.ui;

import java.util.Properties;
import javax.swing.JTabbedPane;

/**
 *
 * @author Florent Georges
 */
public interface Tab {
    public void loadProperties(Properties props);
    public void saveProperties(Properties props);
    public void setRunButtonsEnabled(boolean enable);
    public void setAllCaretPositions();  
    public int getSplitPanePos();
    public void init(TabbedView view);
    public void insertInto(JTabbedPane pane, int pos);
}
