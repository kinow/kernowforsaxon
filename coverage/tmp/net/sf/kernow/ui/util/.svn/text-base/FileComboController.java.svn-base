/*
 * FileComboController.java
 * 
 * Created on Oct 2, 2007, 2:44:01 PM
 */

package net.sf.kernow.ui.util;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JToggleButton;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import net.sf.kernow.util.FileUtil;
import net.sf.saxon.s9api.Serializer;


/**
 * Controls a pair <i>combo box + button</i> used to select files or directories.
 * 
 * <p>It extends {@link UniqueValuesComboController} (so the values of the combo
 * box are still unique strings) and adds a button to launch a file chooser
 * dialog box.  The file or directory selected is then automatically added to
 * the combo box (if not yet)</p>
 * 
 * <p>The fact that the file chooser will select a file or a directory is
 * controlled either by a boolean value at the construction, or by an additional
 * {@link JToggleButton} (a check box for instance).  If the toggle button is
 * selected, the file chooser will select a directory, if not it will select a
 * file.</p>
 * 
 * <p>The class provides in addition convenient methods to access the selected
 * value as a {@link File}, a {@link Source} or a {@link Result} object.<p>
 *
 * @author Florent Georges
 */
public class FileComboController extends UniqueValuesComboController {

    /**
     * Link the controller to the combo box {@code combo} and the button {@code button}.
     * 
     * @param canDir
     *         If {@code true}, the file chooser the {@code button} will launch
     *         will select a directory.  If {@code false}, it will select a file.
     */
    public FileComboController(JComboBox combo, JButton button, boolean canDir) {
        super(combo);
        myButton = button;
        myCanDir = canDir;
        ActionListener action = new ButtonListener();
        myButton.addActionListener(action);
    }

    /**
     * Link the controller to the combo box {@code combo} and the button {@code button}.
     * 
     * @param toggleDir
     *         When the {@code button} is pressed and the file chooser is
     *         launched, this toggle button is queried.  If it is selected,
     *         the file chooser will select a directory, if not it will select
     *         a file.
     */
    public FileComboController(JComboBox combo, JButton button, JToggleButton toggleDir) {
        this(combo, button, false);
        myToggleDir = toggleDir;
    }

    /**
     * Return the selected value as a {@link File}.
     */
    public File getSelectedFile()
    {
        String str = getSelectedString();
        return new File(str);
    }

    /**
     * Return the selected value as a {@link URI}.
     * 
     * @throws URISyntaxException If the selected value is not a proper URI.
     */
    public URI getSelectedURI() throws URISyntaxException
    {
        String str = getSelectedString();
        return FileUtil.createURI(str);
    }

    /**
     * Return the selected value as a {@link Source}.
     * 
     * @throws URISyntaxException If the selected value is not a proper URI.
     */
    public Source getSelectedSource() throws URISyntaxException
    {
        String str = getSelectedString();
        return FileUtil.createSource(str);
    }

    /**
     * Return the selected value as a {@link Result}.
     * 
     * @throws URISyntaxException If the selected value is not a proper URI.
     */
    public Serializer getSelectedDestination() throws URISyntaxException
    {
        String str = getSelectedString();
        return FileUtil.createDestination(str);
    }

    /**
     * Returns {@code true} if the file chooser must select a directory, {@code false} if not.
     * 
     * <p>It queries the toggle button if any, or use the boolean value if there
     * is no toggle button.</p>
     */
    private boolean isDirectoryAllowed() {
        if ( myToggleDir == null ) {
            return myCanDir;
        }
        else {
            return myToggleDir.isSelected();
        }
    }

    /**
     * Listener to attach to the button.
     * 
     * <p>It will launch the file chooser and update the combo box appropriately
     * with the file selected.  According on {@link #isDirectoryAllowed()}, the
     * file chooser will select a directory or a file.</p>
     */
    private class ButtonListener
        implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent evt) {
            String file = ComboBoxUtil.chooseFile(myCombo, isDirectoryAllowed());
            updateComboBoxNoDuplicate(myCombo, file);
            ComboBoxUtil.setCaretPosition(myCombo);
        }
    }

    /**
     * The button associated to the combo box to launch the file chooser.
     */
    private JButton myButton;

    /**
     * Tell if a directory must be selected.
     * 
     * <p>It is not used if a toggle button was registered (see #myToggleDir).</p>
     */
    private boolean myCanDir = false;

    /**
     * The toggle button to query to know if a directory must be selected.
     * 
     * <p>Can be null.  If it is, {@link #myCanDir} is used instead.</p>
     */
    private JToggleButton myToggleDir = null;
}
