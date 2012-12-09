/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.sf.kernow.schema;

import java.io.File;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import junit.framework.TestCase;
import net.sf.kernow.Config;

/**
 *
 * @author andrew
 */
public class XSD11SchemaValidatorTest extends TestCase {
    
    public XSD11SchemaValidatorTest(String testName) {
        super(testName);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    public void testCompileXSD1dot1() {
        System.out.println("testCompileXSD1dot1");

        String schemaPath = "test/xsd/alternative.xsd";
        Source schema = new StreamSource(new File(schemaPath));

        Config.getConfig().setSchemaVersion(SchemaVersion.ONE_DOT_ONE);
        assertTrue(new SchemaValidator().validateSchema(schema));
    }

}
