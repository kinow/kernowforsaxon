/*
 * UniqueValuesComboController.java
 *
 * Created on Oct 2, 2007, 1:40:22 PM
 */

package net.sf.kernow.ui.util;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.util.Properties;
import javax.swing.JComboBox;
import net.sf.kernow.ui.CustomMouseAdapter;


/**
 * Controller for a Swing combo box, to handle it has unique values.
 *
 * <p>This controller in constructed with an <i>editable</i> combo box and
 * register on it the appropriated listener to handle the addition of new
 * values, only if the new value is not already in the list.  Values are added
 * at the top of the list (index 0).</p>
 * 
 * <p>It also registers a listener to add a popup menu to the editor field of
 * the combo box (when the user right-click on it), as well as it provides
 * convenient methods to load and save the values of the combo box from (to) a
 * property set.</p>
 * 
 * <p><b>TODO</b>: Some methods from {@link ComboBoxUtil} should be transfered
 * here, as they are only use here (loadComboBoxValues, saveComboBoxValues and
 * updateComboBoxNoDuplicate).</p>
 *
 * @author Florent Georges
 */
public class UniqueValuesComboController {

    /**
     * The combo box this controller controls.
     */
    protected JComboBox myCombo;
    
    /**
     * The controller is linked to the combo box {@code combo}.
     * 
     * <p>The controlled combo box, obviously, must be editable.</p>
     * 
     * @throws IllegalArgumentException If the combo box is not editable.
     */
    public UniqueValuesComboController(JComboBox combo) {
        if (!combo.isEditable()) {
            throw new IllegalArgumentException("The combo box must be editable!");
        }
        myCombo = combo;
        // Add a listener to add new values to the list (if they are not yet in
        // the list).
        ActionListener action = new ComboListener();
        myCombo.addActionListener(action);
        // Add the mouse listener.
        MouseListener mouse = new CustomMouseAdapter(myCombo);
        myCombo.getEditor().getEditorComponent().addMouseListener(mouse);
    }

    /**
     * Return the selected value from the combo box, as a String.
     * 
     * <p><b>TODO</b>: It should maybe be more appropriate to return {@code null}
     * in case there is no selected value.</p>
     * 
     * @return The selected value as a String if any, the empty string else.
     */
    public String getSelectedString() {
        return ComboBoxUtil.getComboBoxValue(myCombo);
    }

    /**
     * Load values from the properties into the combo box.
     * 
     * <p>The {@code name} is the name of the combo box within the properties.
     * The method will then look for a property with the same name, but with in
     * addition the suffix {@code _item0}.  If the property exists within
     * {@code properties}, it is then added to the combo box.  The method will
     * then look for a property with the suffix {@code _item1}, then {@code
     * _item2}, and so on.  The process stops as soon as the first property that
     * is not found.</p>
     * 
     * <p>The initial values of the combo box are not altered.</p>
     * 
     * <p>Last, the method will look for a property with the suffix {@code
     * _selectedIndex}.  The value of this property is parsed as an integer, and
     * this integer is used to set the selected index of the combo box.  If this
     * property is not found of if there is any related error, the selected
     * index is set to 0.</p>
     */
    public void loadValues(Properties properties, String name) {
        loadComboBoxValues(properties, myCombo, name);
    }

    /**
     * Save the values in the combo box into the {@code properties}.
     * 
     * <p>This is the counter part of {@link #loadValues(Properties,String)}.</p>
     * 
     * <p>It first cleans the properties by deleting the property with name
     * {@code <name>_item0}, then {@code <name>_item1} and so on.</p>
     *
     * <p>It takes then the first value in the combo box and add a property with
     * the name concatened from the {@code name} and {@code _item0}.  Then it
     * takes the second value and use now the suffix {@code _item1}, then
     * {@code _item2} and so on.</p>
     * 
     * <p>It last add the property {@code <name>_selectedIndex} with the
     * selected index of the combo box as value.</p>
     */
    public void saveValues(Properties properties, String name) {
        saveComboBoxValues(properties, myCombo, name);
    }


    public void updateComboBoxNoDuplicate(JComboBox comboBox) {
        updateComboBoxNoDuplicate(comboBox, ComboBoxUtil.getComboBoxValue(comboBox));
    }


    public void updateComboBoxNoDuplicate(JComboBox comboBox, String value) {
        if (value != null && !"".equals(value)) {
            for (int i = 0; i < comboBox.getItemCount(); ++i) {
                if (comboBox.getItemAt(i).toString().equals(value)) {
                    comboBox.setSelectedIndex(i);
                    return;
                }
            }
            comboBox.insertItemAt(value, 0);
            comboBox.setSelectedIndex(0);
        }
    }
    
    public void loadComboBoxValues(Properties properties, JComboBox comboBox, String name) {
        // Load all values until no more can be found
        for (int i = 0; properties.getProperty(name + "_item" + i) != null; ++i) {            
            comboBox.addItem(properties.getProperty(name + "_item" + i, ""));
        }

        if (comboBox.getItemCount() > 0) {
            try {
                int index = Integer.parseInt(properties.getProperty(name + "_selectedIndex"));
                if (comboBox.getItemCount() > index) {
                    comboBox.setSelectedIndex(index);
                } else {
                    comboBox.setSelectedIndex(0);
                }
            } catch (NumberFormatException nfe) {
                comboBox.setSelectedIndex(0);
            }
        }
    }

    public void saveComboBoxValues(Properties props, JComboBox comboBox, String name) {
        // first delete all existing values
        for (int i = 0; props.getProperty(name + "_item" + i) != null; ++i) {
            props.remove(name + "_item" + i);
        }
        // then save the new ones
        for (int j = 0; j < comboBox.getItemCount(); ++j) {
            props.setProperty(name + "_item" + j, comboBox.getItemAt(j).toString());
        }

        // then save the index position
        props.setProperty(name + "_selectedIndex", Integer.toString(comboBox.getSelectedIndex()));
    }
    
    /**
     * ActionListener that adds the selected value to the combo box if it doesn't contain it yet.
     */
    private class ComboListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent evt) {
            updateComboBoxNoDuplicate(myCombo);
        }
    }

    
}
