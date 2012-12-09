/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.sf.kernow.ui;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.JSeparator;
import javax.swing.text.JTextComponent;
import net.sf.kernow.util.XMLFormatter;

/**
 * This is no longer used since switch to RSyntaxTextArea
 * @author awelch
 */
public class EditorMouseAdapter extends CustomMouseAdapter {
    
    public EditorMouseAdapter(final JTextComponent textComp) {        
        
        super(textComp);
        
        super.getPopupMenu().insert(new AbstractAction(java.util.ResourceBundle.getBundle("net/sf/kernow/i18n/RightClickMenu").getString("Format_XML")) {
            @Override
            public void actionPerformed(ActionEvent e) {
                String formatted = XMLFormatter.formatXML(textComp.getText());
                
                if (formatted != null) {
                    textComp.setText(formatted);
                }
            }
        }, 0);
        
        super.getPopupMenu().insert(new JSeparator(), 1);
                
    }   
}
