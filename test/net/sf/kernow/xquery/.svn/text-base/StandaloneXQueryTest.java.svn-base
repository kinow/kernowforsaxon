package net.sf.kernow.xquery;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;


import junit.framework.*;
import net.sf.saxon.s9api.Destination;
import net.sf.saxon.s9api.Serializer;

import net.sf.saxon.trans.XPathException;

/**
 * Unit tests for {@link StandaloneXQuery}.
 *
 * <p>TODO: Test the evaluation of SA queries.</p>
 *
 * @author Florent Georges
 */
public class StandaloneXQueryTest extends TestCase {

    private Destination myIgnoreResult;

    public StandaloneXQueryTest(String name) {
        super(name);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();

        Serializer serializer = new Serializer();
        serializer.setOutputStream(System.out);
        myIgnoreResult= serializer;
    }


    // StandaloneXQuery.runQuery(String)
    // ---------------------------------

    /**
     * Test {@link StandaloneXQuery#runQuery(String)} with a correct expression.
     *
     * <p>I don't like the idea that the result is automatically seralised on System.out.
     * TODO: Test that the right result has been serialised on System.out?</p>
     *
     * <p><dl>
     *   <dt><b>Query:</b></dt>
     *   <dd>1 + 1</dd>
     * </dl></p>
     */
    public void testRunQueryStringCorrect() {
        System.out.println("testRunQueryStringCorrect");

        String query = "1 + 1";

        assertTrue("The query must succeed", new StandaloneXQuery().runQuery(query));
    }

    /**
     * Test {@link StandaloneXQuery#runQuery(String)} with a syntactically incorrect expression.
     *
     * <p><dl>
     *   <dt><b>Query:</b></dt>
     *   <dd>+</dd>
     * </dl></p>
     */
    public void testRunQueryStringIncorrect() {
        System.out.println("testRunQueryStringIncorrect");

        String query = "+";

        assertFalse("The query must fail", new StandaloneXQuery().runQuery(query));
    }

    /**
     * Test {@link StandaloneXQuery#runQuery(String)} with an empty expression.
     *
     * <p><dl>
     *   <dt><b>Query:</b></dt>
     *   <dd>&lt;empty></dd>
     * </dl></p>
     */
    public void testRunQueryStringEmpty() {
        System.out.println("testRunQueryStringEmpty");

        String query = "";

        assertFalse("The query must fail", new StandaloneXQuery().runQuery(query));
    }

    /**
     * Test {@link StandaloneXQuery#runQuery(String)} with a null expression.
     *
     * <p><dl>
     *   <dt><b>Query:</b></dt>
     *   <dd>&lt;null></dd>
     * </dl></p>
     */
    public void testRunQueryStringNull() {
        System.out.println("testRunQueryStringNull");

        String query = null;

        assertFalse("The query must fail", new StandaloneXQuery().runQuery(query));
    }

    // StandaloneXQuery.runQuery(String,Result)
    // ----------------------------------------

    /**
     * Test {@link StandaloneXQuery#runQuery(String,Result)} with a correct expression.
     *
     * Check the result.
     *
     * <p><dl>
     *   <dt><b>Query:</b></dt>
     *   <dd>1 + 1</dd>
     * </dl></p>
     */
    public void testRunQueryStringResultCorrect() throws XPathException {
        System.out.println("testRunQueryStringResultCorrect");

        String query = "1 + 1";
        OutputStream stream = new ByteArrayOutputStream();
        Serializer s = new Serializer();
        s.setOutputStream(stream);
        s.setOutputProperty(Serializer.Property.OMIT_XML_DECLARATION, "yes");
        assertTrue("The query must succeed", new StandaloneXQuery().runQuery(query, s));
        assertEquals("The query result must be correct", "2", stream.toString());
    }

    /**
     * Test {@link StandaloneXQuery#runQuery(String,Result)} with a syntactically incorrect expression.
     *
     * <p><dl>
     *   <dt><b>Query:</b></dt>
     *   <dd>+</dd>
     * </dl></p>
     */
    public void testRunQueryStringResultIncorrect() {
        System.out.println("testRunQueryStringResultIncorrect");

        String query = "+";

        boolean success = new StandaloneXQuery().runQuery(query, myIgnoreResult);
            
        if (success) {
            fail("The query should fail");
        }
    }

    /**
     * Test {@link StandaloneXQuery#runQuery(String,Result)} with an empty expression.
     *
     * <p><dl>
     *   <dt><b>Query:</b></dt>
     *   <dd>&lt;empty></dd>
     * </dl></p>
     */
    public void testRunQueryStringResultEmpty() throws XPathException {
        System.out.println("testRunQueryStringResultEmpty");

        String query = "";

        boolean success = new StandaloneXQuery().runQuery(query, myIgnoreResult);
        if (success) {
            fail("The query should fail");
        }
    }

    /**
     * Test {@link StandaloneXQuery#runQuery(String,Result)} with a null expression.
     *
     * <p><dl>
     *   <dt><b>Query:</b></dt>
     *   <dd>&lt;null></dd>
     * </dl></p>
     */
    public void testRunQueryStringResultNull() throws XPathException {
        System.out.println("testRunQueryStringResultNull");

        String query = null;

        assertFalse("The query must fail", new StandaloneXQuery().runQuery(query, myIgnoreResult));
    }

    // StandaloneXQuery.runQuery(Reader,Result)
    // ----------------------------------------

    /**
     * Test {@link StandaloneXQuery#runQuery(Reader,Result)} with a correct expression.
     *
     * Check the result.
     *
     * <p><dl>
     *   <dt><b>Query:</b></dt>
     *   <dd>1 + 1</dd>
     * </dl></p>
     */
    public void testRunQueryReaderCorrect() throws XPathException, IOException {
        System.out.println("testRunQueryReaderCorrect");

        String query  = "1 + 1";
        OutputStream stream = new ByteArrayOutputStream();
        Serializer s = new Serializer();
        s.setOutputStream(stream);
        s.setOutputProperty(Serializer.Property.OMIT_XML_DECLARATION, "yes");

        assertTrue("The query must succeed", new StandaloneXQuery().runQuery(query, s));
        assertEquals("The query result must be correct", "2", stream.toString());
    }

    /**
     * Test {@link StandaloneXQuery#runQuery(Reader,Result)} with a syntactically incorrect expression.
     *
     * <p><dl>
     *   <dt><b>Query:</b></dt>
     *   <dd>+</dd>
     * </dl></p>
     */
    public void testRunQueryReaderIncorrect() throws IOException {
        System.out.println("testRunQueryReaderIncorrect");

        String query = "+";

        boolean success = new StandaloneXQuery().runQuery(query, myIgnoreResult);
        if (success) {
            fail("The query should fail");
        }
    }

    /**
     * Test {@link StandaloneXQuery#runQuery(Reader,Result)} with an empty expression.
     *
     * <p><dl>
     *   <dt><b>Query:</b></dt>
     *   <dd>&lt;empty></dd>
     * </dl></p>
     */
    public void testRunQueryEmpty() throws XPathException, IOException {
        System.out.println("testRunQueryEmpty");

        boolean success = new StandaloneXQuery().runQuery("", myIgnoreResult);
        if (success) {
            fail("The query should fail");
        }
    }

    /**
     * Test {@link StandaloneXQuery#runQuery(Reader,Result)} with a null expression.
     *
     * <p><dl>
     *   <dt><b>Query:</b></dt>
     *   <dd>&lt;null></dd>
     * </dl></p>
     */
    public void testRunQueryReaderNull() throws XPathException, IOException {
        System.out.println("testRunQueryReaderNull");

        String query = null;

        assertFalse("The query must fail", new StandaloneXQuery().runQuery(query, myIgnoreResult));
    }

    // StandaloneXQuery.runQuery(InputStream,Result)
    // ---------------------------------------------

    /**
     * Test {@link StandaloneXQuery#runQuery(InputStream,Result)} with a correct expression.
     *
     * Check result.
     *
     * <p><dl>
     *   <dt><b>Query:</b></dt>
     *   <dd>1 + 1</dd>
     * </dl></p>
     */
    public void testRunQueryStreamCorrect() throws XPathException, IOException {
        System.out.println("testRunQueryStreamCorrect");

        InputStream  query  = new ByteArrayInputStream("1 + 1".getBytes());
        OutputStream stream = new ByteArrayOutputStream();
        Serializer serializer = new Serializer();
        serializer.setOutputStream(stream);
        serializer.setOutputProperty(Serializer.Property.OMIT_XML_DECLARATION, "yes");
        assertTrue("The query must succeed", new StandaloneXQuery().runQuery(query, serializer));
        assertEquals("The query result must be correct", "2", stream.toString());
    }

    /**
     * Test {@link StandaloneXQuery#runQuery(InputStream,Result)} with a syntactically incorrect expression.
     *
     * <p><dl>
     *   <dt><b>Query:</b></dt>
     *   <dd>+</dd>
     * </dl></p>
     */
    public void testRunQueryStreamIncorrect() throws IOException {
        System.out.println("testRunQueryStreamIncorrect");

        InputStream query = new ByteArrayInputStream("+".getBytes());

        boolean success = new StandaloneXQuery().runQuery(query, myIgnoreResult);
        if (success) {
            fail("The query should fail");
        }
    }

    /**
     * Test {@link StandaloneXQuery#runQuery(InputStream,Result)} with an empty expression.
     *
     * <p><dl>
     *   <dt><b>Query:</b></dt>
     *   <dd>&lt;empty></dd>
     * </dl></p>
     */
    public void testRunQueryStreamEmpty() throws XPathException, IOException {
        System.out.println("testRunQueryStreamEmpty");

        InputStream query = new ByteArrayInputStream("".getBytes());

        boolean success = new StandaloneXQuery().runQuery(query, myIgnoreResult);
        if (success) {
            fail("The query should fail");
        }   // success
        
    }

    /**
     * Test {@link StandaloneXQuery#runQuery(InputStream,Result)} with a null expression.
     *
     * <p><dl>
     *   <dt><b>Query:</b></dt>
     *   <dd>&lt;null></dd>
     * </dl></p>
     */
    public void testRunQueryStreamNull() throws XPathException, IOException {
        System.out.println("testRunQueryStreamNull");

        InputStream query = null;

        assertFalse("The query must fail", new StandaloneXQuery().runQuery(query, myIgnoreResult));
    }

}
