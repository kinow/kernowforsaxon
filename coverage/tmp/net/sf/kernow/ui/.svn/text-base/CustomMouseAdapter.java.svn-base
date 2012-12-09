
package net.sf.kernow.ui;

import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.AbstractAction;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JPopupMenu;
import javax.swing.SwingUtilities;
import javax.swing.text.JTextComponent;

/**
 *
 * @author welcha
 */
public class CustomMouseAdapter extends MouseAdapter {
    
    private final JComponent component;
    private final JPopupMenu menu;
    
    /** Creates a new instance of CustomMouseAdapter */
    public CustomMouseAdapter(final JComboBox comboBox) {
        this.component = comboBox;
        
        menu = new JPopupMenu();
        
        final JTextComponent textComp = ((JTextComponent)comboBox.getEditor().getEditorComponent());
        
        menu.add(new AbstractAction(java.util.ResourceBundle.getBundle("net/sf/kernow/i18n/RightClickMenu").getString("Cut")) {
            @Override
            public void actionPerformed(ActionEvent e) {
                textComp.cut();
            }
        });
        
        menu.add(new AbstractAction(java.util.ResourceBundle.getBundle("net/sf/kernow/i18n/RightClickMenu").getString("Copy")) {
            @Override
            public void actionPerformed(ActionEvent e) {
                textComp.copy();
            }
        });
        
        menu.add(new AbstractAction(java.util.ResourceBundle.getBundle("net/sf/kernow/i18n/RightClickMenu").getString("Paste")) {
            @Override
            public void actionPerformed(ActionEvent e) {
                textComp.paste();
            }
        });
        
        menu.addSeparator();
        
        menu.add(new AbstractAction(java.util.ResourceBundle.getBundle("net/sf/kernow/i18n/RightClickMenu").getString("Select_All")) {
            @Override
            public void actionPerformed(ActionEvent e) {
                textComp.selectAll();
            }
        });
        
        menu.addSeparator();
        
        menu.add(new AbstractAction(java.util.ResourceBundle.getBundle("net/sf/kernow/i18n/RightClickMenu").getString("Remove")) {
            @Override
            public void actionPerformed(ActionEvent e) {
                comboBox.removeItemAt(comboBox.getSelectedIndex());
            }
        });        

        menu.addSeparator();
        
        menu.add(new AbstractAction(java.util.ResourceBundle.getBundle("net/sf/kernow/i18n/RightClickMenu").getString("Remove_All")) {
            @Override
            public void actionPerformed(ActionEvent e) {
                comboBox.removeAllItems();
            }
        });           
        
    }
    
    public JPopupMenu getPopupMenu() {
        return menu;
    }
    
    public CustomMouseAdapter(final JTextComponent textComp) {
        
        this.component = textComp;
        
        menu = new JPopupMenu();
               
        menu.add(new AbstractAction(java.util.ResourceBundle.getBundle("net/sf/kernow/i18n/RightClickMenu").getString("Cut")) {
            @Override
            public void actionPerformed(ActionEvent e) {
                textComp.cut();
            }
        });
        
        menu.add(new AbstractAction(java.util.ResourceBundle.getBundle("net/sf/kernow/i18n/RightClickMenu").getString("Copy")) {
            @Override
            public void actionPerformed(ActionEvent e) {
                textComp.copy();
            }
        });
        
        menu.add(new AbstractAction(java.util.ResourceBundle.getBundle("net/sf/kernow/i18n/RightClickMenu").getString("Paste")) {
            @Override
            public void actionPerformed(ActionEvent e) {
                textComp.paste();
            }
        });
        
        menu.addSeparator();
        
        menu.add(new AbstractAction(java.util.ResourceBundle.getBundle("net/sf/kernow/i18n/RightClickMenu").getString("Select_All")) {
            @Override
            public void actionPerformed(ActionEvent e) {
                textComp.selectAll();
            }
        });             
        
        menu.addSeparator();
        
        menu.add(new AbstractAction(java.util.ResourceBundle.getBundle("net/sf/kernow/i18n/RightClickMenu").getString("Clear")) {
            @Override
            public void actionPerformed(ActionEvent e) {
                textComp.setText("");                
            }
        });          
    }
    
    /* TODO: Excerpt from Javadoc of MouseEvent.isPopupTrigger():
     *
     *     Returns whether or not this mouse event is the popup menu trigger event
     *     for the platform.
     *
     *     Note: Popup menus are triggered differently on different systems.
     *     Therefore, isPopupTrigger should be checked in both mousePressed and
     *     mouseReleased for proper cross-platform functionality.
     */
    @Override
    public void mousePressed(MouseEvent evt) {
        if(SwingUtilities.isRightMouseButton(evt)) {       
            // Display the menu
            Point pt = SwingUtilities.convertPoint(evt.getComponent(), evt.getPoint(), component);
            menu.show(component, pt.x, pt.y);
        } else {
            
            // Hide the menu - calling setVisible(true) is a bit of a hack
            // to ensure the popup is always hidden even if the user clicks
            // on a different combobox
            menu.setVisible(true);
            menu.setVisible(false);

            // I experimented strange behaviour with the above code.  In some
            // cases this is impossible to edit the combo box text field.  I
            // think this is because, while the text field is getting focus, the
            // menu gets it (because of setVisible true), then loose it (because
            // of setVisible false), but doesn't give it back to the text field.
            // Anyway, the following instruction solves the problem.  -fg
            component.requestFocus();
        }
    }

}
