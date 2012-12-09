/*
 * FileComboControllerTest.java
 * JUnit based test
 *
 * Created on October 7, 2007, 7:40 PM
 */

package net.sf.kernow.ui.util;

import java.awt.event.ActionEvent;
import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JToggleButton;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import junit.framework.TestCase;
import net.sf.saxon.s9api.Destination;

/**
 * Unit tests for {@link FileComboControllerTestDisabled}.
 *
 * @author Florent Georges
 */
//public class FileComboControllerTest extends TestCase {
public class FileComboControllerTestDisabled extends TestCase {
    
    public FileComboControllerTestDisabled(String testName) {
        super(testName);
    }

    public void testCtorBoolean() {
        // fixture
        JComboBox combo = new JComboBox();
        combo.setEditable(true);
        JButton button = new JButton();
        FileComboController sut;
        // exercise the SUT
        sut = new FileComboController(combo, button, false);
        // check
    }

    public void testCtorToggle() {
        // fixture
        JComboBox combo = new JComboBox();
        combo.setEditable(true);
        JButton button = new JButton();
        JToggleButton toggle = new JToggleButton();
        FileComboController sut;
        // exercise the SUT
        sut = new FileComboController(combo, button, toggle);
        // check
    }

    public void testController_launchChooser() {
        // fixture
        JComboBox combo = new JComboBox();
        combo.setEditable(true);
        PushableButton button = new PushableButton();
        ViewHelperCountingSpy helper = new ViewHelperCountingSpy();
        FileComboController sut = makeControllerWithHelper(combo, button, false, helper);
        assertEquals("initial state", 0, helper.getCallCount());
        // exercise the SUT
        button.push();
        // check
        assertEquals("call count", 1, helper.getCallCount());
        // exercise the SUT (twice in the same case, no need to have two separate cases)
        button.push();
        // check (again)
        assertEquals("call count", 2, helper.getCallCount());
    }

    public void testController_chooserUpdateSelected() {
        // fixture
        String ITEM = "value";
        JComboBox combo = new JComboBox();
        combo.setEditable(true);
        PushableButton button = new PushableButton();
        
        FileComboController sut = makeControllerWithHelper(combo, button, false, null);
        // exercise the SUT
        button.push();
        // check
        assertEquals("selected value", ITEM, sut.getSelectedString());
    }

    public void testController_staticOnlyFile() {
        // fixture
        boolean ALLOW_DIR = false;
        JComboBox combo = new JComboBox();
        combo.setEditable(true);
        PushableButton button = new PushableButton();
        ViewHelperDirAllowedSpy helper = new ViewHelperDirAllowedSpy();
        FileComboController sut = makeControllerWithHelper(combo, button, ALLOW_DIR, helper);
        // exercise the SUT
        button.push();
        // check
        assertEquals("allowed dir", ALLOW_DIR, helper.getDirAllowed());
    }

    public void testController_staticOnlyDir() {
        // fixture
        boolean ALLOW_DIR = true;
        JComboBox combo = new JComboBox();
        combo.setEditable(true);
        PushableButton button = new PushableButton();
        ViewHelperDirAllowedSpy helper = new ViewHelperDirAllowedSpy();
        FileComboController sut = makeControllerWithHelper(combo, button, ALLOW_DIR, helper);
        // exercise the SUT
        button.push();
        // check
        assertEquals("allowed dir", ALLOW_DIR, helper.getDirAllowed());
    }

    public void testController_toggleOnlyFile() {
        // fixture
        boolean ALLOW_DIR = false;
        JComboBox combo = new JComboBox();
        combo.setEditable(true);
        PushableButton button = new PushableButton();
        ViewHelperDirAllowedSpy helper = new ViewHelperDirAllowedSpy();
        JToggleButton toggleDir = new JToggleButton();
        FileComboController sut = makeControllerWithHelper(combo, button, toggleDir, helper);
        // exercise the SUT
        toggleDir.setSelected(ALLOW_DIR);
        button.push();
        // check
        assertEquals("allowed dir", ALLOW_DIR, helper.getDirAllowed());
    }

    public void testController_toggleOnlyDir() {
        // fixture
        boolean ALLOW_DIR = true;
        JComboBox combo = new JComboBox();
        combo.setEditable(true);
        PushableButton button = new PushableButton();
        ViewHelperDirAllowedSpy helper = new ViewHelperDirAllowedSpy();
        JToggleButton toggleDir = new JToggleButton();
        FileComboController sut = makeControllerWithHelper(combo, button, toggleDir, helper);
        // exercise the SUT
        toggleDir.setSelected(ALLOW_DIR);
        button.push();
        // check
        assertEquals("allowed dir", ALLOW_DIR, helper.getDirAllowed());
    }

    public void testGetSelectedFile_null() {
        // fixture
        JComboBox combo = new JComboBox();
        combo.setEditable(true);
        JButton button = new JButton();
        FileComboController sut = new FileComboController(combo, button, false);
        // exercise the SUT
        File file = sut.getSelectedFile();
        // check
        File expected = new File("");
        assertEquals("selected file", expected, file);
    }

    public void testGetSelectedFile_valid() {
        // fixture
        String PATH = "some/file.txt";
        JComboBox combo = new JComboBox();
        combo.setEditable(true);
        combo.setSelectedItem(PATH);
        JButton button = new JButton();
        FileComboController sut = new FileComboController(combo, button, false);
        // exercise the SUT
        File file = sut.getSelectedFile();
        // check
        File expected = new File(PATH);
        assertEquals("selected file", expected, file);
    }

    public void testGetSelectedURI_null() throws URISyntaxException {
        // fixture
        JComboBox combo = new JComboBox();
        combo.setEditable(true);
        JButton button = new JButton();
        FileComboController sut = new FileComboController(combo, button, false);
        // exercise the SUT
        URI uri = sut.getSelectedURI();
        // check
        URI expected = new File("").toURI();
        assertEquals("selected uri", expected, uri);
    }

    public void testGetSelectedURI_valid() throws URISyntaxException {
        // fixture
        String PATH = "some/file.txt";
        JComboBox combo = new JComboBox();
        combo.setEditable(true);
        combo.setSelectedItem(PATH);
        JButton button = new JButton();
        FileComboController sut = new FileComboController(combo, button, false);
        // exercise the SUT
        URI uri = sut.getSelectedURI();
        // check
        URI expected = new File(PATH).toURI();
        assertEquals("selected uri", expected, uri);
    }

    // TODO: See the TODO comment on FileUtil.createURI().  getSelectedURI() is
    // expected to throw URI syntax, not an illegal argument.  Once this issue
    // is resolved, uncomment the following test and write similar tests for
    // getSelectedSource() and getSelectedResult().

//    public void testGetSelectedURI_invalid() {
//        // fixture
//        String PATH = "http://invalid uri !@#$%^&*()_+";
//        JComboBox combo = new JComboBox();
//        combo.setEditable(true);
//        combo.setSelectedItem(PATH);
//        JButton button = new JButton();
//        FileComboControllerTestDisabled sut = new FileComboControllerTestDisabled(combo, button, false);
//        // exercise the SUT
//        try {
//            sut.getSelectedURI();
//        }
//        catch (URISyntaxException ex) {
//            return;
//        }
//        // check
//        fail("must have thrown a URI syntax exception");
//    }

    public void testGetSelectedSource_null() throws URISyntaxException {
        // fixture
        JComboBox combo = new JComboBox();
        combo.setEditable(true);
        JButton button = new JButton();
        FileComboController sut = new FileComboController(combo, button, false);
        // exercise the SUT
        Source source = sut.getSelectedSource();
        // check
        URI expected = new File("").toURI();
        assertEquals("selected source", expected, new URI(source.getSystemId()));
    }

    public void testGetSelectedSource_valid() throws URISyntaxException {
        // fixture
        String PATH = "some/file.txt";
        JComboBox combo = new JComboBox();
        combo.setEditable(true);
        combo.setSelectedItem(PATH);
        JButton button = new JButton();
        FileComboController sut = new FileComboController(combo, button, false);
        // exercise the SUT
        Source source = sut.getSelectedSource();
        // check
        URI expected = new File(PATH).toURI();
        assertEquals("selected source", expected, new URI(source.getSystemId()));
    }

    /**
     * Builds a new controller with the specified view helper.
     */
    private FileComboController makeControllerWithHelper(JComboBox combo, JButton button, boolean canDir, ComboBoxUtil helper) {
        return new ControllerDouble(combo, button, canDir, helper);
    }

    /**
     * Builds a new controller with the specified view helper.
     */
    private FileComboController makeControllerWithHelper(JComboBox combo, JButton button, JToggleButton toggleDir, ComboBoxUtil helper) {
        return new ControllerDouble(combo, button, toggleDir, helper);
    }

    /**
     * A test double for the controller, that allows installing our own view helper.
     */
    private static class ControllerDouble extends FileComboController {
        public ControllerDouble(JComboBox combo, JButton button, boolean canDir, ComboBoxUtil helper) {
            super(combo, button, canDir);
            //myHelper = helper;
        }
        public ControllerDouble(JComboBox combo, JButton button, JToggleButton toggleDir, ComboBoxUtil helper) {
            super(combo, button, toggleDir);
            //myHelper = helper;
        }
    }

    /**
     * A test double for the view helper that counts chooseFile() calls.
     */
    private static class ViewHelperCountingSpy extends ComboBoxUtil {
        public int getCallCount() {
            return myCallCount;
        }
//        @Override
//        public String chooseFile(JComboBox combo, boolean dirAllowed) {
//            ++myCallCount;
//            return null;
//        }
        private int myCallCount = 0;
    }

    /**
     * A test double for the view helper that spy the <i>directory allowed</i> value.
     */
    private static class ViewHelperDirAllowedSpy extends ComboBoxUtil {
        public boolean getDirAllowed() {
            return myDirAllowed;
        }
//        @Override
//        public String chooseFile(JComboBox combo, boolean dirAllowed) {
//            myDirAllowed = dirAllowed;
//            return null;
//        }
        private boolean myDirAllowed;
    }

    /**
     * A test double button that allows to simulate pushing the button.
     */
    private static class PushableButton extends JButton {
        public void push() {
            ActionEvent evt = new ActionEvent(this, 0, "cmd");
            fireActionPerformed(evt);
        }
    }

}
