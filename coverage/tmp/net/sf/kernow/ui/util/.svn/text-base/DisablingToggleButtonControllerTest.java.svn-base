/*
 * DisablingToggleButtonControllerTest.java
 * JUnit based test
 *
 * Created on October 4, 2007, 5:46 PM
 */

package net.sf.kernow.ui.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JToggleButton;
import junit.framework.TestCase;

/**
 * Unit tests for {@link DisablingToggleButtonController}.
 *
 * @author Florent Georges
 */
public class DisablingToggleButtonControllerTest extends TestCase {

    JToggleButton button;
    JComponent comp1;
    JComponent comp2;
    JComponent comp3;
    
    public DisablingToggleButtonControllerTest(String testName) {
        super(testName);
    }

    @Override
    public void setUp() throws Exception {
        super.setUp();
        // shared fixture: fresh button and components, all enabled, button
        // not selected
        button = new JCheckBox();
        comp1 = new JButton();
        comp2 = new JComboBox();
        comp3 = new JLabel();
    }

    public void testCtorSimple() {
        // fixture
        DisablingToggleButtonController sut;
        // exercise the SUT
        sut = new DisablingToggleButtonController(button);
        // check
        assertFalse("resulting state", sut.isSelected());
    }

    public void testCtorArray_ction() {
        // fixture
        JComponent[] comps = new JComponent[]{ comp1, comp2 };
        DisablingToggleButtonController sut;
        // exercise the SUT
        sut = new DisablingToggleButtonController(button, comps);
        // check
        assertFalse("resulting state", sut.isSelected());
    }

    public void testCtorArray_disabled() {
        // fixture
        JComponent[] comps = new JComponent[]{ comp1, comp2 };
        DisablingToggleButtonController sut;
        // exercise the SUT
        sut = new DisablingToggleButtonController(button, comps);
        // check
        assertFalse("resulting state", sut.isSelected());
        assertFalse("should have changed", comp1.isEnabled());
        assertFalse("should have changed", comp2.isEnabled());
        assertTrue("should NOT have changed", comp3.isEnabled());
    }

    public void testCtorIterable_ction() {       
        // fixture
        List<JComponent> list = new ArrayList<JComponent>();
        list.add(comp1);
        list.add(comp2);
        Iterable<JComponent> comps = list;
        DisablingToggleButtonController sut;
        // exercise the SUT
        sut = new DisablingToggleButtonController(button, comps);
        // check
        assertFalse("resulting state", sut.isSelected());
    }

    public void testCtorList_disabled() {
        // fixture
        List<JComponent> list = new ArrayList<JComponent>();
        list.add(comp1);
        list.add(comp2);
        Iterable<JComponent> comps = list;
        DisablingToggleButtonController sut;
        // exercise the SUT
        sut = new DisablingToggleButtonController(button, comps);
        // check
        assertFalse("resulting state", sut.isSelected());
        assertFalse("should have changed", comp1.isEnabled());
        assertFalse("should have changed", comp2.isEnabled());
        assertTrue("should NOT have changed", comp3.isEnabled());
    }

    public void testCtorCollection_ction() {
        // fixture
        Set<JComponent> set = new HashSet<JComponent>();
        set.add(comp1);
        set.add(comp2);
        Collection<JComponent> comps = set;
        DisablingToggleButtonController sut;
        // exercise the SUT
        sut = new DisablingToggleButtonController(button, comps);
        // check
        assertFalse("resulting state", sut.isSelected());
    }

    public void testCtorCollection_disabled() {
        // fixture
        Set<JComponent> set = new HashSet<JComponent>();
        set.add(comp1);
        set.add(comp2);
        Collection<JComponent> comps = set;
        DisablingToggleButtonController sut;
        // exercise the SUT
        sut = new DisablingToggleButtonController(button, comps);
        // check
        assertFalse("resulting state", sut.isSelected());
        assertFalse("should have changed", comp1.isEnabled());
        assertFalse("should have changed", comp2.isEnabled());
        assertTrue("should NOT have changed", comp3.isEnabled());
    }

    public void testDisableButton() {
        // fixture
        JComponent[] comps = new JComponent[]{ comp1, comp2 };
        DisablingToggleButtonController sut = new DisablingToggleButtonController(button, comps);
        // exercise the SUT
        button.setSelected(true);
        // check
        assertTrue("should have been reenabled", comp1.isEnabled());
        assertTrue("should have been reenabled", comp2.isEnabled());
        assertTrue("should NOT have changed", comp3.isEnabled());
    }

    public void testIsSelected_true() {
        // fixture
        DisablingToggleButtonController sut = new DisablingToggleButtonController(button);
        // exercise the SUT
        button.setSelected(true);
        // check
        assertTrue("state through the controller", sut.isSelected());
    }

    public void testIsSelected_false() {
        // fixture
        DisablingToggleButtonController sut = new DisablingToggleButtonController(button);
        // exercise the SUT
        button.setSelected(false);
        // check
        assertFalse("state through the controller", sut.isSelected());
    }

    public void testAdd_keepDisabled() {
        // fixture
        comp1.setEnabled(false);
        button.setSelected(false);
        DisablingToggleButtonController sut = new DisablingToggleButtonController(button);
        // exercise the SUT
        sut.add(comp1);
        // check
        assertFalse("should be kept disabled", comp1.isEnabled());
    }

    public void testAdd_keepEnabled() {
        // fixture
        comp1.setEnabled(true);
        button.setSelected(true);
        DisablingToggleButtonController sut = new DisablingToggleButtonController(button);
        // exercise the SUT
        sut.add(comp1);
        // check
        assertTrue("should be kept enabled", comp1.isEnabled());
    }

    public void testAdd_disable() {
        // fixture
        comp1.setEnabled(true);
        button.setSelected(false);
        DisablingToggleButtonController sut = new DisablingToggleButtonController(button);
        // exercise the SUT
        sut.add(comp1);
        // check
        assertFalse("should be disabled", comp1.isEnabled());
    }

    public void testAdd_enable() {
        // fixture
        comp1.setEnabled(false);
        button.setSelected(true);
        DisablingToggleButtonController sut = new DisablingToggleButtonController(button);
        // exercise the SUT
        sut.add(comp1);
        // check
        assertTrue("should be enabled", comp1.isEnabled());
    }
}
