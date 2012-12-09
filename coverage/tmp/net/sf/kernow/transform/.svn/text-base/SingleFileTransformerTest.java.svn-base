package net.sf.kernow.transform;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.net.URISyntaxException;
import javax.xml.transform.stream.StreamResult;
import junit.framework.*;
import java.io.File;
import java.util.HashMap;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import net.sf.kernow.util.FileUtil;
import net.sf.saxon.s9api.Serializer;

/**
 * Test {@link SingleFileTransformer}.
 *
 * @author Florent Georges
 */
public class SingleFileTransformerTest extends TestCase {

    /** Serializer object used when we want to ignore the result. */
    private Serializer myIgnoreOutput = new Serializer();

    public SingleFileTransformerTest(String testName) {
        super(testName);
        myIgnoreOutput.setOutputStream(System.out);
    }

    // transform(Source,Source,Result,String)
    // --------------------------------------

    /**
     * Test {@link SingleFileTransformer#transform(Source,Source,Result,String)} with correct invocation.
     *
     * <p><dl>
     *   <dt><b>Input:</b></dt>
     *   <dd>test/xml/videos.xml</dd>
     *   <dt><b>Stylesheet:</b></dt>
     *   <dd>test/xslt/simple.xslt</dd>
     *   <dt><b>Output:</b></dt>
     *   <dd>to a buffer, checked</dd>
     *   <dt><b>Initial template:</b></dt>
     *   <dd>&lt;null></dd>
     * </dl></p>
     */
    public void testTransformSucceed() {
        System.out.println("testTransformSucceed");
        
        Source       input           = new StreamSource(new File("test/xml/videos.xml"));
        Source       stylesheet      = new StreamSource(new File("test/xslt/simple.xslt"));
        OutputStream stream          = new ByteArrayOutputStream();
        Serializer serializer = new Serializer();
        serializer.setOutputStream(stream);
        String       initialTemplate = null;
        
        assertTrue(
                "The transform must succeed",
                new SingleFileTransformer().transform(input, stylesheet, serializer, initialTemplate));
        assertEquals("The result of the transform must be correct", "Ok", stream.toString());
    }

    /**
     * Test {@link SingleFileTransformer#transform(Source,Source,Result,String)} with inexistent stylesheet.
     *
     * <p><dl>
     *   <dt><b>Input:</b></dt>
     *   <dd>test/xml/videos.xml</dd>
     *   <dt><b>Stylesheet:</b></dt>
     *   <dd>test/xslt/does-not-exist.xslt</dd>
     *   <dt><b>Output:</b></dt>
     *   <dd>-</dd>
     *   <dt><b>Initial template:</b></dt>
     *   <dd>&lt;null></dd>
     * </dl></p>
     */
    public void testTransformStylesheetNotExist() {
        System.out.println("testTransformStylesheetNotExist");

        Source input           = new StreamSource(new File("test/xml/videos.xml"));
        Source stylesheet      = new StreamSource(new File("test/xslt/does-not-exist.xslt"));
        String initialTemplate = null;
        
        assertFalse(
                "The transform must fail, the stylesheet does not exist",
                new SingleFileTransformer().transform(input, stylesheet, myIgnoreOutput, initialTemplate));
    }

    /**
     * Test {@link SingleFileTransformer#transform(Source,Source,Result,String)} with null stylesheet.
     *
     * <p><dl>
     *   <dt><b>Input:</b></dt>
     *   <dd>test/xml/videos.xml</dd>
     *   <dt><b>Stylesheet:</b></dt>
     *   <dd>&lt;null></dd>
     *   <dt><b>Output:</b></dt>
     *   <dd>-</dd>
     *   <dt><b>Initial template:</b></dt>
     *   <dd>&lt;null></dd>
     * </dl></p>
     */
    public void testTransformNullStylesheet() {
        System.out.println("testTransformNullStylesheet");

        Source input           = new StreamSource(new File("test/xml/videos.xml"));
        Source stylesheet      = null;
        String initialTemplate = null;
        
        assertFalse(
                "The transform must fail, the stylesheet is null",
                new SingleFileTransformer().transform(input, stylesheet, myIgnoreOutput, initialTemplate));
    }

    /**
     * Test {@link SingleFileTransformer#transform(Source,Source,Result,String)} with null output.
     *
     * <p><dl>
     *   <dt><b>Input:</b></dt>
     *   <dd>test/xml/videos.xml</dd>
     *   <dt><b>Stylesheet:</b></dt>
     *   <dd>test/xslt/simple.xslt</dd>
     *   <dt><b>Output:</b></dt>
     *   <dd>&lt;null></dd>
     *   <dt><b>Initial template:</b></dt>
     *   <dd>&lt;null></dd>
     * </dl></p>
     */
    public void testTransformNullOutput() {
        System.out.println("testTransformNullOutput");

        Source input           = new StreamSource(new File("test/xml/videos.xml"));
        Source stylesheet      = new StreamSource(new File("test/xslt/simple.xslt"));
        Serializer serializer = null;
        String initialTemplate = null;
        
        assertFalse(
                "The transform must fail, the output is null",
                new SingleFileTransformer().transform(input, stylesheet, serializer, initialTemplate));
    }

    /**
     * Test {@link SingleFileTransformer#transform(Source,Source,Result,String)} with null input & initial template.
     *
     * <p><dl>
     *   <dt><b>Input:</b></dt>
     *   <dd>&lt;null></dd>
     *   <dt><b>Stylesheet:</b></dt>
     *   <dd>test/xslt/simple.xslt</dd>
     *   <dt><b>Output:</b></dt>
     *   <dd>-</dd>
     *   <dt><b>Initial template:</b></dt>
     *   <dd>&lt;null></dd>
     * </dl></p>
     */
    public void testTransformNullInputAndInitial() {
        System.out.println("testTransformNullInputAndInitial");
        
        Source input           = null;
        Source stylesheet      = new StreamSource(new File("test/xslt/simple.xslt"));
        String initialTemplate = null;
        
        assertFalse(
                "The transform must fail, both input and initial template are null",
                new SingleFileTransformer().transform(input, stylesheet, myIgnoreOutput, initialTemplate));
    }

    /**
     * Test {@link SingleFileTransformer#transform(Source,Source,Result,String)} with ill-formed stylesheet.
     *
     * <p><dl>
     *   <dt><b>Input:</b></dt>
     *   <dd>test/xml/videos.xml</dd>
     *   <dt><b>Stylesheet:</b></dt>
     *   <dd>test/xslt/illformed.xslt</dd>
     *   <dt><b>Output:</b></dt>
     *   <dd>-</dd>
     *   <dt><b>Initial template:</b></dt>
     *   <dd>&lt;null></dd>
     * </dl></p>
     */
    public void testTransformIllformedStylesheet() {
        System.out.println("testTransformIllformedStylesheet");
        
        Source input           = new StreamSource(new File("test/xml/videos.xml"));
        Source stylesheet      = new StreamSource(new File("test/xslt/illformed.xslt"));
        String initialTemplate = null;
        
        assertFalse(
                "The transform must fail, the stylesheet is ill-formed",
                new SingleFileTransformer().transform(input, stylesheet, myIgnoreOutput, initialTemplate));
    }

    /**
     * Test {@link SingleFileTransformer#transform(Source,Source,Result,String)} with inexistent input.
     *
     * <p><dl>
     *   <dt><b>Input:</b></dt>
     *   <dd>test/xml/does-not-exist.xml</dd>
     *   <dt><b>Stylesheet:</b></dt>
     *   <dd>test/xslt/simple.xslt</dd>
     *   <dt><b>Output:</b></dt>
     *   <dd>-</dd>
     *   <dt><b>Initial template:</b></dt>
     *   <dd>&lt;null></dd>
     * </dl></p>
     */
    public void testTransformInputNotExist() {
        System.out.println("testTransformInputNotExist");
        
        Source input           = new StreamSource(new File("test/xml/does-not-exist.xml"));
        Source stylesheet      = new StreamSource(new File("test/xslt/simple.xslt"));
        String initialTemplate = null;
        
        assertFalse(
                "The transform must fail, the input does not exist",
                new SingleFileTransformer().transform(input, stylesheet, myIgnoreOutput, initialTemplate));
    }

    /**
     * Test {@link SingleFileTransformer#transform(Source,Source,Result,String)} with ill-formed input.
     *
     * <p><dl>
     *   <dt><b>Input:</b></dt>
     *   <dd>test/xml/illformed.xml</dd>
     *   <dt><b>Stylesheet:</b></dt>
     *   <dd>test/xslt/simple.xslt</dd>
     *   <dt><b>Output:</b></dt>
     *   <dd>-</dd>
     *   <dt><b>Initial template:</b></dt>
     *   <dd>&lt;null></dd>
     * </dl></p>
     */
    public void testTransformIllformedInput() {
        System.out.println("testTransformIllformedInput");
        
        Source input           = new StreamSource(new File("test/xml/illformed.xml"));
        Source stylesheet      = new StreamSource(new File("test/xslt/simple.xslt"));
        String initialTemplate = null;
        
        assertFalse(
                "The transform must fail, the input is ill-formed",
                new SingleFileTransformer().transform(input, stylesheet, myIgnoreOutput, initialTemplate));
    }

    /**
     * Test {@link SingleFileTransformer#transform(Source,Source,Result,String)} with inexistent initial template.
     *
     * <p><dl>
     *   <dt><b>Input:</b></dt>
     *   <dd>test/xml/videos.xml</dd>
     *   <dt><b>Stylesheet:</b></dt>
     *   <dd>test/xslt/simple.xslt</dd>
     *   <dt><b>Output:</b></dt>
     *   <dd>-</dd>
     *   <dt><b>Initial template:</b></dt>
     *   <dd>does-not-exist</dd>
     * </dl></p>
     */
    public void testTransformInitialNotExist() {
        System.out.println("testTransformInitialNotExist");
        
        Source input           = new StreamSource(new File("test/xml/videos.xml"));
        Source stylesheet      = new StreamSource(new File("test/xslt/simple.xslt"));
        String initialTemplate = "does-not-exist";
        
        assertFalse(
                "The transform must fail, the initial does not exist",
                new SingleFileTransformer().transform(input, stylesheet, myIgnoreOutput, initialTemplate));
    }

    /**
     * Test {@link SingleFileTransformer#transform(Source,Source,Result,String)} with initial template (null input).
     *
     * <p><dl>
     *   <dt><b>Input:</b></dt>
     *   <dd>&lt;null></dd>
     *   <dt><b>Stylesheet:</b></dt>
     *   <dd>test/xslt/initialTemplateAndMode.xslt</dd>
     *   <dt><b>Output:</b></dt>
     *   <dd>to a buffer, checked</dd>
     *   <dt><b>Initial template:</b></dt>
     *   <dd>main</dd>
     * </dl></p>
     */
    public void testTransformInitial() {
        System.out.println("testTransformInitial");
        
        Source       input           = null;
        Source       stylesheet      = new StreamSource(new File("test/xslt/initialTemplateAndMode.xslt"));
        OutputStream stream          = new ByteArrayOutputStream();
        Serializer serializer = new Serializer();
        serializer.setOutputStream(stream);
        String       initialTemplate = "main";
        
        assertTrue(
                "The transform must succeed",
                new SingleFileTransformer().transform(input, stylesheet, serializer, initialTemplate));
        assertEquals(
                "The result of the transform must be correct",
                "Started named template with intial template \"main\"",
                stream.toString());
    }

    /**
     * Test {@link SingleFileTransformer#transform(Source,Source,Result,String)} with initial template and input.
     *
     * <p><dl>
     *   <dt><b>Input:</b></dt>
     *   <dd>test/xml/videos.xml</dd>
     *   <dt><b>Stylesheet:</b></dt>
     *   <dd>test/xslt/initialTemplateAndMode.xslt</dd>
     *   <dt><b>Output:</b></dt>
     *   <dd>to a buffer, checked</dd>
     *   <dt><b>Initial template:</b></dt>
     *   <dd>main</dd>
     * </dl></p>
     */
    public void testTransformInitialAndInputs() {
        System.out.println("testTransformInitialAndInputs");
        
        Source       input           = new StreamSource(new File("test/xml/videos.xml"));
        Source       stylesheet      = new StreamSource(new File("test/xslt/initialTemplateAndMode.xslt"));
        OutputStream stream          = new ByteArrayOutputStream();
        Serializer serializer = new Serializer();
        serializer.setOutputStream(stream);
        String       initialTemplate = "main";
        
        assertTrue(
                "The transform must succeed",
                new SingleFileTransformer().transform(input, stylesheet, serializer, initialTemplate));
        assertEquals(
                "The result of the transform must be correct",
                "Started named template with intial template \"main\"",
                stream.toString());
    }

    /**
     * Test {@link SingleFileTransformer#transform(Source,Source,Result,String)} correct gets parameters.
     *
     * <p><dl>
     *   <dt><b>Input:</b></dt>
     *   <dd>&lt;null></dd>
     *   <dt><b>Stylesheet:</b></dt>
     *   <dd>test/xslt/parameter.xslt</dd>
     *   <dt><b>Output:</b></dt>
     *   <dd>to a buffer, checked</dd>
     *   <dt><b>Initial template:</b></dt>
     *   <dd>main</dd>
     * </dl></p>
     */
    public void testSettingParameter() {
        System.out.println("testSettingParameter");
        
        Source input = null;
        String ss = "test/xslt/parameter.xslt";
        Source stylesheet = new StreamSource(new File(ss));
        OutputStream stream = new ByteArrayOutputStream();
        Serializer serializer = new Serializer();
        serializer.setOutputStream(stream);
        String initialTemplate = "main";
        
        HashMap<String, Object> hm = new HashMap<String, Object>();
        hm.put("param", "overridden");
        try {
            
            Params.setParamsForStylesheet(FileUtil.createURI(stylesheet.getSystemId()), hm);
            
            assertTrue(
                    "The transform must succeed",
                    new SingleFileTransformer().transform(input, stylesheet, serializer, initialTemplate));
            
            assertEquals(
                    "The result of the transform should show that the parameter has been set",
                    "Param: overridden",
                    stream.toString());
        } catch (URISyntaxException ex) {
            ex.printStackTrace();
            fail();
        }
    }
    
    // updateCancelStatus(Cancellable)
    // -------------------------------

    /**
     * Test {@link SingleFileTransformer#updateCancelStatus(Cancellable)} with correct invocation.
     */
    public void testUpdateCancelStatusSucceed() {
        System.out.println("updateCancelStatus");

        // TODO: To test when the method will be implemented.
        assertTrue("The tested method is not implemented yet", true);
    }
    
}
