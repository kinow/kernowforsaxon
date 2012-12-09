/*
 * DisablingCheckboxController.java
 * 
 * Created on Oct 2, 2007, 5:47:50 PM
 */

package net.sf.kernow.ui.util;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import javax.swing.JComponent;
import javax.swing.JToggleButton;


/**
 * Makes a group of components enabled/disabled regarding the state of a toggle button.
 * 
 * <p>The group of {@link JComponent} registered in this object are automatically
 * enabled or disabled regarding the state of a {@link JToggleButton} (a check
 * box for instance).</p>
 *
 * @author Florent Georges
 */
public class DisablingToggleButtonController {

    /**
     * The controlling button.
     */
    private JToggleButton toggle;

    /**
     * Records the component group handled by this controller.
     */
    private Set<JComponent> myComponents = new HashSet<JComponent>();
    
    /**
     * Builds a new controller with the controling button.
     */
    public DisablingToggleButtonController(JToggleButton button) {
        toggle = button;
        toggle.addItemListener(new SelectionChangeListener());
    }

    /**
     * Builds a new controller with the controling button and an initial collection of components.
     */
    public DisablingToggleButtonController(JToggleButton button, Collection<JComponent> init) {
        this(button);
        for (JComponent c : init) {
            c.setEnabled(button.isSelected());
        }
        myComponents.addAll(init);
    }

    /**
     * Builds a new controller with the controling button and an initial array of components.
     */
    public DisablingToggleButtonController(JToggleButton button, JComponent[] init) {
        this(button);
        for (JComponent c : init) {
            c.setEnabled(button.isSelected());
            myComponents.add(c);
        }
    }

    /**
     * Builds a new controller with the controling button and an initial "iterable" of components.
     */
    public DisablingToggleButtonController(JToggleButton button, Iterable<JComponent> init) {
        this(button);
        for (JComponent c : init) {
            c.setEnabled(button.isSelected());
            myComponents.add(c);
        }
    }

    /**
     * Add a new component to the component group handled by this controller.
     *
     * If the component is already controlled by this object, it is not added
     * twice.
     */
    public void add(JComponent c) {
        if ( ! isContainedByIdentity(c) ) {
            c.setEnabled(toggle.isSelected());
            myComponents.add(c);
        }
    }

    /**
     * Return the selected state of the controlling button.
     */
    public boolean isSelected() {
        return toggle.isSelected();
    }

    /**
     * Check if the component is already in the group (by object identity).
     */
    private boolean isContainedByIdentity(JComponent c) {
        for ( JComponent i : myComponents ) {
            if ( c == i ) {
                return true;
            }
        }
        return false;
    }

    /**
     * Listener to be added to the button to switch the state of the components.
     */
    private class SelectionChangeListener implements ItemListener {
        @Override
        public void itemStateChanged(ItemEvent e) {
            boolean enable = isSelected();
            for (JComponent c : myComponents) {
                c.setEnabled(enable);
            }
        }
    }
}
