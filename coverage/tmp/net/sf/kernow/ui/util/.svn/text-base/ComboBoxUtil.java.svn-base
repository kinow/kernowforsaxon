package net.sf.kernow.ui.util;

import java.io.File;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.text.JTextComponent;

/**
 *
 * @author Florent Georges
 */
public class ComboBoxUtil {

    /**
     * Returns the currently selected combobox value.  If there
     * is no value, it return the empty string.
     */
    public static String getComboBoxValue(JComboBox comboBox) {
        if (comboBox.getSelectedItem() != null) {
            return comboBox.getSelectedItem().toString();
        } else {
            return "";
        }
    }

    /**
     * When the text is longer than the text box it gets centered, this
     * method ensures they all show the end of the text, eg the filename.
     */
    public static void setCaretPosition(JComboBox comboBox) {
        Object item = comboBox.getSelectedItem();
        if (item != null) {
            String val = item.toString();
            JTextComponent c = (JTextComponent) comboBox.getEditor().getEditorComponent();
            c.setCaretPosition(val.length());
        }
    }

    /**
     * Opens a file chooser dialog
     * @param chooseAFolder true if the dialog should return a folder, false for a file
     */
    public static String chooseFile(JComboBox comboBox, boolean chooseAFolder) {
        JFileChooser chooser = new JFileChooser();
        String path = "";
        String currentDir = "";

        if (comboBox.getSelectedItem() != null) {
            currentDir = comboBox.getSelectedItem().toString();
        }

        // Set whether we want to select a folder instead of a file
        if (chooseAFolder) {
            chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        }

        chooser.setCurrentDirectory(new File(currentDir));

        // Show open dialog; this method does not return until the dialog is closed
        if (chooser.showOpenDialog(comboBox) == JFileChooser.APPROVE_OPTION) {
            path = chooser.getSelectedFile().getAbsolutePath();
        }

        if (path == null || path.equals("")) {
            return currentDir;
        }
        return path;
    }
}
