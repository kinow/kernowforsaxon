package net.sf.kernow.transform;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import junit.framework.*;
import java.io.File;
import net.sf.kernow.Config;
import net.sf.kernow.util.FileUtil;

/**
 * Test {@link DirectoryTransformer}.
 *
 * @author Florent Georges
 */
public class DirectoryTransformerTest extends TestCase {
    
    public DirectoryTransformerTest(String testName) {
        super(testName);
    }
    
    /**
     * Test {@link DirectoryTransformer#runDirectoryTransform(String,String,String)} with correct invocation.
     *
     * <p><dl>
     *   <dt><b>Source dir:</b></dt>
     *   <dd>test/xml/valid/</dd>
     *   <dt><b>Stylesheet:</b></dt>
     *   <dd>test/xslt/identity.xslt</dd>
     *   <dt><b>Output dir:</b></dt>
     *   <dd>test/output/tmp</dd>
     * </dl></p>
     */
    public void testRunDirectoryTransformSucceed() throws MalformedURLException, URISyntaxException {
        System.out.println("testRunDirectoryTransformSucceed");
        
        Config.getConfig().setSchemaRecurseSubdirectories(false);

        String source_dir = "test/xml/valid/";
        String stylesheet = "test/xslt/identity.xslt";
        String output_dir = "test/output/tmp/";
        DirectoryTransformer sut = new DirectoryTransformer();
        
        assertTrue("Transform must succeed",
                sut.runDirectoryTransform(new File(source_dir), FileUtil.createSource(stylesheet), new File(output_dir)));
    }
    
    /**
     * Test {@link DirectoryTransformer#runDirectoryTransform(String,String,String)} with one ill-formed file.
     *
     * <p><dl>
     *   <dt><b>Source dir:</b></dt>
     *   <dd>test/xml/ (contains illformed.xml)</dd>
     *   <dt><b>Stylesheet:</b></dt>
     *   <dd>test/xslt/identity.xslt</dd>
     *   <dt><b>Output dir:</b></dt>
     *   <dd>test/output/tmp</dd>
     * </dl></p>
     */
    public void testRunDirectoryTransformOneIllformedInputFile() throws MalformedURLException, URISyntaxException {
        System.out.println("testRunDirectoryTransformOneIllformedInputFile");
        
        Config.getConfig().setSchemaRecurseSubdirectories(false);

        String source_dir = "test/xml/";
        String stylesheet = "test/xslt/identity.xslt";
        String output_dir = "test/output/tmp/";
        DirectoryTransformer sut = new DirectoryTransformer();

        assertFalse("Transform must fail, one file is ill-formed",
                sut.runDirectoryTransform(new File(source_dir), FileUtil.createSource(stylesheet), new File(output_dir)));
    }

    /**
     * Test {@link DirectoryTransformer#runDirectoryTransform(String,String,String)} with inexistent directory.
     *
     * <p><dl>
     *   <dt><b>Source dir:</b></dt>
     *   <dd>does-not-exist/</dd>
     *   <dt><b>Stylesheet:</b></dt>
     *   <dd>test/xslt/identity.xslt</dd>
     *   <dt><b>Output dir:</b></dt>
     *   <dd>test/output/tmp</dd>
     * </dl></p>
     */
    public void testRunDirectoryTransformInputNotExist() throws MalformedURLException, URISyntaxException {
        System.out.println("testRunDirectoryTransformInputNotExist");
        
        String source_dir = "does-not-exist/";
        String stylesheet = "test/xslt/identity.xslt";
        String output_dir = "test/output/tmp/";
        DirectoryTransformer sut = new DirectoryTransformer();

        assertFalse("Transform must fail, input directory does not exist",
                sut.runDirectoryTransform(new File(source_dir), FileUtil.createSource(stylesheet), new File(output_dir)));
    }

    /**
     * Test {@link DirectoryTransformer#runDirectoryTransform(String,String,String)} with input not a directory.
     *
     * <p><dl>
     *   <dt><b>Source dir:</b></dt>
     *   <dd>test/xml/videos.xml (regular file)</dd>
     *   <dt><b>Stylesheet:</b></dt>
     *   <dd>test/xslt/identity.xslt</dd>
     *   <dt><b>Output dir:</b></dt>
     *   <dd>test/output/tmp</dd>
     * </dl></p>
     */
    public void testRunDirectoryTransformInputRegularFile() throws MalformedURLException, URISyntaxException {
        System.out.println("testRunDirectoryTransformInputRegularFile");
        
        String source_dir = "test/xml/videos.xml";
        String stylesheet = "test/xslt/identity.xslt";
        String output_dir = "test/output/tmp/";
        DirectoryTransformer sut = new DirectoryTransformer();

        assertFalse("Transform must fail, input is not a directory (but a regular file)",
                sut.runDirectoryTransform(new File(source_dir), FileUtil.createSource(stylesheet), new File(output_dir)));
    }

    /**
     * Test {@link DirectoryTransformer#runDirectoryTransform(String,String,String)} with stylesheet with spaces in the name.
     *
     * <p><dl>
     *   <dt><b>Source dir:</b></dt>
     *   <dd>test/xml/valid/</dd>
     *   <dt><b>Stylesheet:</b></dt>
     *   <dd>test/xslt/Transform with space in the name.xslt</dd>
     *   <dt><b>Output dir:</b></dt>
     *   <dd>test/output/tmp</dd>
     * </dl></p>
     */
    public void testRunDirectoryTransformStylesheetWithSpaces() throws MalformedURLException, URISyntaxException {
        System.out.println("testRunDirectoryTransformStylesheetWithSpaces");
        
        Config.getConfig().setSchemaRecurseSubdirectories(false);

        String source_dir = "test/xml/valid/";
        String stylesheet = "test/xslt/Transform with space in the name.xslt";
        String output_dir = "test/output/tmp/";
        DirectoryTransformer sut = new DirectoryTransformer();
        
        assertTrue("Transform must succeed",
                sut.runDirectoryTransform(new File(source_dir), FileUtil.createSource(stylesheet), new File(output_dir)));
    }

    /**
     * Test {@link DirectoryTransformer#runDirectoryTransform(String,String,String)} with stylesheet that terminates.
     *
     * <p><dl>
     *   <dt><b>Source dir:</b></dt>
     *   <dd>test/xml/valid/</dd>
     *   <dt><b>Stylesheet:</b></dt>
     *   <dd>test/xslt/terminate.xslt (evaluate xsl:message with terminate="yes")</dd>
     *   <dt><b>Output dir:</b></dt>
     *   <dd>test/output/tmp</dd>
     * </dl></p>
     */
    public void testRunDirectoryTransformStylesheetTerminate() throws MalformedURLException, URISyntaxException {
        System.out.println("testRunDirectoryTransformStylesheetTerminate");
        
        Config.getConfig().setSchemaRecurseSubdirectories(false);

        String source_dir = "test/xml/valid/";
        String stylesheet = "test/xslt/terminate.xslt";
        String output_dir = "test/output/tmp/";
        DirectoryTransformer sut = new DirectoryTransformer();
        
        assertFalse("Transform must fail, the stylesheet evaluate xsl:message with terminate=\"yes\"",
                sut.runDirectoryTransform(new File(source_dir), FileUtil.createSource(stylesheet), new File(output_dir)));
    }

    /**
     * Test {@link DirectoryTransformer#runDirectoryTransform(String,String,String)} with ill-formed stylesheet.
     *
     * <p><dl>
     *   <dt><b>Source dir:</b></dt>
     *   <dd>test/xml/valid/</dd>
     *   <dt><b>Stylesheet:</b></dt>
     *   <dd>test/xslt/terminate.xslt (evaluate xsl:message with terminate="yes")</dd>
     *   <dt><b>Output dir:</b></dt>
     *   <dd>test/output/tmp</dd>
     * </dl></p>
     */
    public void testRunDirectoryTransformStylesheetIllformed() throws MalformedURLException, URISyntaxException {
        System.out.println("testRunDirectoryTransformStylesheetIllformed");
        
        Config.getConfig().setSchemaRecurseSubdirectories(false);

        String source_dir = "test/xml/valid/";
        String stylesheet = "test/xslt/illformed.xslt";
        String output_dir = "test/output/tmp/";
        DirectoryTransformer sut = new DirectoryTransformer();
        
        assertFalse("Transform must fail, the stylesheet is ill-formed",
                sut.runDirectoryTransform(new File(source_dir), FileUtil.createSource(stylesheet), new File(output_dir)));
    }
}
