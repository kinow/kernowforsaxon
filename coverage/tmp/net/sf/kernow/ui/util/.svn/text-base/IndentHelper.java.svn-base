/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.sf.kernow.ui.util;

import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import javax.swing.AbstractAction;
import javax.swing.JEditorPane;
import javax.swing.KeyStroke;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.Element;

/**
 *
 * @author awelch
 */
public class IndentHelper {

    public void indent(final JEditorPane editorPane, boolean indent) {

        int selectionStart = editorPane.getSelectionStart();
        int selectionEnd = editorPane.getSelectionEnd();
        Document doc = editorPane.getDocument();
        Element root = doc.getDefaultRootElement();
        int start = root.getElementIndex(selectionStart);
        int end = root.getElementIndex(selectionEnd);

        for (int i = start; i <= end; i++) {
            Element line = root.getElement(i);
            int offset = line.getStartOffset();

            try {
                if (indent) {
                    doc.insertString(offset, "\t", null);

                    editorPane.setSelectionStart(selectionStart + 1);
                    editorPane.setSelectionEnd(selectionEnd + end - start + 1);
                } else if (doc.getText(offset, 1).contains("\t")) {
                    // unindent
                    doc.remove(offset, 1);

                    editorPane.setSelectionStart(selectionStart - 1);
                    editorPane.setSelectionEnd(selectionEnd - end + start - 1);
                }
            } catch (BadLocationException ble) {
            }
        }          
        
        editorPane.requestFocusInWindow();
    }

    public void addTabIndentSupport(final JEditorPane editorPane) {
        // Create an indent action and add it to the editor pane
        editorPane.getActionMap().put("indent",
            new AbstractAction("indent") {
                public void actionPerformed(ActionEvent evt) {
                    indent(editorPane, true);
                }
           });

        // Bind the ident action to tab
        editorPane.getInputMap().put(KeyStroke.getKeyStroke("TAB"), "indent");

        // Create an unindent action and add it to the editor pane
        editorPane.getActionMap().put("unindent",
            new AbstractAction("unindent") {
                public void actionPerformed(ActionEvent evt) {
                    indent(editorPane, false);
                }
            });

        // Bind the unindent action to shift-tab
        editorPane.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_TAB, InputEvent.SHIFT_DOWN_MASK), "unindent");        
    }
}
