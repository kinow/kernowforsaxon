/*
 * UniqueValuesComboControllerTest.java
 * JUnit based test
 *
 * Created on October 4, 2007, 8:43 PM
 */

package net.sf.kernow.ui.util;

import java.util.Properties;
import javax.swing.JComboBox;
import junit.framework.TestCase;

/**
 * Unit tests for {@link UniqueValuesComboController}.
 *
 * @author Florent Georges
 */
public class UniqueValuesComboControllerTest extends TestCase {
    
    public UniqueValuesComboControllerTest(String testName) {
        super(testName);
    }

    public void testCtor_valid() {
        // fixture
        JComboBox combo = new JComboBox();
        combo.setEditable(true);
        UniqueValuesComboController sut;
        // exercise the SUT
        sut = new UniqueValuesComboController(combo);
        // check
    }

    public void testCtor_nonEditable() {
        // fixture
        JComboBox combo = new JComboBox();
        UniqueValuesComboController sut;
        // exercise the SUT
        try {
            sut = new UniqueValuesComboController(combo);
        }
        catch (IllegalArgumentException ex) {
            return;
        }
        // check
        fail("Constructor should have thrown an illegal arg exception");
    }

    public void testController_editOneValue() {
        // fixture
        JComboBox combo = new JComboBox();
        combo.setEditable(true);
        UniqueValuesComboController sut = new UniqueValuesComboController(combo);
        // exercise the SUT
        simulateEditingValue(combo, "one");
        // check
        assertEquals("the value must be added", 1, combo.getItemCount());
    }

    public void testController_editTwoValues() {
        // fixture
        JComboBox combo = new JComboBox();
        combo.setEditable(true);
        UniqueValuesComboController sut = new UniqueValuesComboController(combo);
        // exercise the SUT
        simulateEditingValue(combo, "one");
        simulateEditingValue(combo, "two");
        // check
        assertEquals("both values must be added", 2, combo.getItemCount());
    }

    public void testController_editSimilarValue() {
        // fixture
        JComboBox combo = new JComboBox();
        combo.setEditable(true);
        UniqueValuesComboController sut = new UniqueValuesComboController(combo);
        simulateEditingValue(combo, "single");
        assertEquals("initial state", 1, combo.getItemCount());
        // exercise the SUT
        simulateEditingValue(combo, "single");
        // check
        assertEquals("value must NOT be added", 1, combo.getItemCount());
    }

    public void testController_editSimilarValueAfter() {
        // fixture
        JComboBox combo = new JComboBox();
        combo.setEditable(true);
        UniqueValuesComboController sut = new UniqueValuesComboController(combo);
        simulateEditingValue(combo, "one");
        simulateEditingValue(combo, "two");
        assertEquals("initial state", 2, combo.getItemCount());
        // exercise the SUT
        simulateEditingValue(combo, "one");
        // check
        assertEquals("value must NOT be added", 2, combo.getItemCount());
    }

    public void testGetSelectedString_onNewCtrl() {
        // fixture
        JComboBox combo = new JComboBox();
        combo.setEditable(true);
        UniqueValuesComboController sut = new UniqueValuesComboController(combo);
        // exercise the SUT
        String value = sut.getSelectedString();
        // check
        assertEquals("selected string", "", value);
    }

    public void testGetSelectedString_onAddedString() {
        // fixture
        String ITEM = "an item";
        JComboBox combo = new JComboBox();
        combo.setEditable(true);
        UniqueValuesComboController sut = new UniqueValuesComboController(combo);
        combo.setSelectedItem(ITEM);
        // exercise the SUT
        String value = sut.getSelectedString();
        // check
        assertEquals("selected string", ITEM, value);
    }

    public void testLoadValues() {
        // fixture
        JComboBox combo = new JComboBox();
        combo.setEditable(true);
        UniqueValuesComboController sut = new UniqueValuesComboController(combo);
        Properties props = new Properties();
        props.setProperty("combo_item0", "one");
        props.setProperty("combo_item1", "two");
        props.setProperty("combo_item2", "three");
        props.setProperty("combo_selectedIndex", "1");
        assertEquals("initial state", 0, combo.getItemCount());
        // exercise the SUT
        sut.loadValues(props, "combo");
        // check
        assertEquals("value count", 3, combo.getItemCount());
        assertEquals("selected index", 1, combo.getSelectedIndex());
        assertEquals("first item", "one", (String) combo.getItemAt(0));
        assertEquals("second item", "two", (String) combo.getItemAt(1));
        assertEquals("third item", "three", (String) combo.getItemAt(2));
    }

    public void testSaveValues() {
        // fixture
        JComboBox combo = new JComboBox();
        combo.setEditable(true);
        UniqueValuesComboController sut = new UniqueValuesComboController(combo);
        // values are added in reverse order
        simulateEditingValue(combo, "three");
        simulateEditingValue(combo, "two");
        simulateEditingValue(combo, "one");
        combo.setSelectedItem("two");
        assertEquals("initial state", 3, combo.getItemCount());
        // exercise the SUT
        Properties props = new Properties();
        sut.saveValues(props, "combo");
        // check
        assertEquals("property count", 4, props.size()); // 3 values + index
        assertEquals("first item", "one", (String) props.get("combo_item0"));
        assertEquals("second item", "two", (String) props.get("combo_item1"));
        assertEquals("third item", "three", (String) props.get("combo_item2"));
        assertEquals("selected index", "1", (String) props.get("combo_selectedIndex"));
    }

    /**
     * Simulate editing the combo box text field.
     * 
     * <p>The purpose is to fire the right events between the components (combo
     * box, its editor, etcetera) to make as if we were, in a running GUI,
     * editing the text field of the cobo box.</p>
     */
    private void simulateEditingValue(JComboBox combo, String value) {
        combo.getEditor().setItem(value);
        // Use null as the JComboBox.actionPerformed() doesn't actually use the
        // event.  But this is maybe too implementation-dependent.  Change it if
        // there is any NPE running these tests.
        combo.actionPerformed(null);
    }
}
