/*
 * SchemaValidatorTest.java
 * JUnit based test
 *
 * Created on 30 July 2007, 12:10
 */

package net.sf.kernow.schema;

import junit.framework.*;
import java.io.File;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;

/**
 *
 * @author welcha
 */
public class SchemaValidatorTest extends TestCase {
    
    public SchemaValidatorTest(String testName) {
        super(testName);
    }

    /**
     * Test validateSchema() with a local schema.
     *
     * <dl>
     *   <dt>schema</dt>
     *   <dd>test/xsd/videos.xsd</dd>
     * </dl>
     */
    public void testValidateSchemaLocal() {
        System.out.println("testValidateSchemaLocal");
        
        String schemaPath = "test/xsd/videos.xsd";
        Source schema = new StreamSource(new File(schemaPath));

        assertTrue(new SchemaValidator().validateSchema(schema));
    }

    /**
     * Test validateSchema() with a local schema with spaces in its name.
     *
     * <dl>
     *   <dt>schema</dt>
     *   <dd>test/xsd/videos with space.xsd</dd>
     * </dl>
     */
    public void testValidateSchemaLocalWithSpace() {
        System.out.println("testValidateSchemaLocalWithSpace");
        
        String schemaPath = "test/xsd/videos with space.xsd";
        Source schema = new StreamSource(new File(schemaPath));

        assertTrue(new SchemaValidator().validateSchema(schema));
    }    
    
    /**
     * Test validateSchema() with a remote schema.
     *
     * <dl>
     *   <dt>schema</dt>
     *   <dd>http://www.w3.org/2002/08/xhtml/xhtml1-transitional.xsd</dd>
     * </dl>
     */
    public void testValidateSchemaRemote() {
        System.out.println("testValidateSchemaRemote");
        
        String schemaPath = "http://www.w3.org/2002/08/xhtml/xhtml1-transitional.xsd";
        Source schema     = new StreamSource(schemaPath);

        assertTrue(new SchemaValidator().validateSchema(schema));
    }

    /**
     * Test validateSchema() with an invalid schema.
     *
     * <dl>
     *   <dt>schema</dt>
     *   <dd>test/xsd/invalid-schema.xsd</dd>
     * </dl>
     */
    public void testValidateSchemaInvalid() {
        System.out.println("testValidateSchemaInvalid");
        
        String schemaPath = "test/xsd/invalid-schema.xsd";
        Source schema     = new StreamSource(new File(schemaPath));

        assertFalse(new SchemaValidator().validateSchema(schema));
    }

    /**
     * Test validateSchema() with some XML instance instead of the schema itself.
     *
     * <dl>
     *   <dt>schema</dt>
     *   <dd>test/xml/videos.xml</dd>
     * </dl>
     */
    public void testValidateSchemaWithInstance() {
        System.out.println("testValidateSchemaWithInstance");
        
        String schemaPath = "test/xml/videos.xml"; // not a schema
        Source schema     = new StreamSource(new File(schemaPath));

        assertFalse(new SchemaValidator().validateSchema(schema));
    }

    /**
     * Test validateSchema() with an illformed schema.
     *
     * <dl>
     *   <dt>schema</dt>
     *   <dd>test/xsd/illformed-schema.xsd</dd>
     * </dl>
     */
    public void testValidateSchemaIllformed() {
        System.out.println("testValidateSchemaIllformed");
        
        String schemaPath = "test/xsd/illformed-schema.xsd";
        Source schema     = new StreamSource(new File(schemaPath));

        assertFalse(new SchemaValidator().validateSchema(schema));
    }

    /**
     * Test validateSchema() with a non existing schema.
     *
     * <dl>
     *   <dt>schema</dt>
     *   <dd>test/xsd/does-not-exist-schema.xsd</dd>
     * </dl>
     */
    public void testValidateSchemaDoesNotExist() {
        System.out.println("testValidateSchemaDoesNotExist");
        
        String schemaPath = "test/xsd/does-not-exist-schema.xsd";
        Source schema     = new StreamSource(new File(schemaPath));

        assertFalse(new SchemaValidator().validateSchema(schema));
    }

    /**
     * Test validate() with a valid document and valid schema.
     *
     * <dl>
     *   <dt>schema</dt>
     *   <dd>test/xsd/videos.xsd</dd>
     *   <dt>input</dt>
     *   <dd>test/xml/videos.xml</dd>
     * </dl>
     */
    public void testValidateSuccess() {
        System.out.println("testValidateSuccess");

        String xmlFilePath = "test/xml/videos.xml";
        String schemaPath  = "test/xsd/videos.xsd";
        Source input  = new StreamSource(new File(xmlFilePath));
        Source schema = new StreamSource(new File(schemaPath));

        assertTrue(new SchemaValidator().validate(input, schema));
    }
    
    /**
     * Test validate() with a valid document and valid schema (both with spaces in the name).
     *
     * <dl>
     *   <dt>schema</dt>
     *   <dd>test/xsd/videos with space.xsd</dd>
     *   <dt>input</dt>
     *   <dd>test/xml/videos with space.xml</dd>
     * </dl>
     */
    public void testValidateSuccessWithSpace() {
        System.out.println("testValidateSuccessWithSpace");

        String xmlFilePath = "test/xml/videos with space.xml";
        String schemaPath  = "test/xsd/videos with space.xsd";
        Source input  = new StreamSource(new File(xmlFilePath));
        Source schema = new StreamSource(new File(schemaPath));

        assertTrue(new SchemaValidator().validate(input, schema));
    }

    /**
     * Test validate() with an invalid document and valid schema.
     *
     * <dl>
     *   <dt>schema</dt>
     *   <dd>test/xsd/videos.xsd</dd>
     *   <dt>input</dt>
     *   <dd>test/xml/videos-invalid.xml</dd>
     * </dl>
     */
    public void testValidateDocInvalid() {
        System.out.println("testValidateDocInvalid");

        String xmlFilePath = "test/xml/videos-invalid.xml";
        String schemaPath  = "test/xsd/videos.xsd";
        Source input  = new StreamSource(new File(xmlFilePath));
        Source schema = new StreamSource(new File(schemaPath));

        assertFalse(new SchemaValidator().validate(input, schema));
    }

    /**
     * Test validate() with an invalid document and valid schema.
     *
     * <dl>
     *   <dt>schema</dt>
     *   <dd>test/xsd/invalid-schema.xsd</dd>
     *   <dt>input</dt>
     *   <dd>test/xml/videos.xml</dd>
     * </dl>
     */
    public void testValidateWithSchemaInvalid() {
        System.out.println("testValidateDocInvalid");

        String xmlFilePath = "test/xml/videos.xml";
        String schemaPath  = "test/xsd/invalid-schema.xsd";
        Source input  = new StreamSource(new File(xmlFilePath));
        Source schema = new StreamSource(new File(schemaPath));

        assertFalse(new SchemaValidator().validate(input, schema));
    }

    /**
     * Test validate() with a non existing document and valid schema.
     *
     * <dl>
     *   <dt>schema</dt>
     *   <dd>test/xsd/videos.xsd</dd>
     *   <dt>input</dt>
     *   <dd>test/xml/does-not-exist.xml</dd>
     * </dl>
     */
    public void testValidateDocDoesNotExist() {
        System.out.println("testValidateDocDoesNotExist");

        String xmlFilePath = "test/xml/does-not-exist.xml";
        String schemaPath  = "test/xsd/videos.xsd";
        Source input  = new StreamSource(new File(xmlFilePath));
        Source schema = new StreamSource(new File(schemaPath));

        assertFalse(new SchemaValidator().validate(input, schema));
    }

    /**
     * Test validate() with a valid document and non existing schema.
     *
     * <dl>
     *   <dt>schema</dt>
     *   <dd>test/xsd/does-not-exist.xsd</dd>
     *   <dt>input</dt>
     *   <dd>test/xml/videos.xml</dd>
     * </dl>
     */
    public void testValidateWhenSchemaDoesNotExist() {
        System.out.println("testValidateWhenSchemaDoesNotExist");

        String xmlFilePath = "test/xml/videos.xml";
        String schemaPath  = "test/xsd/does-not-exist.xsd";
        Source input  = new StreamSource(new File(xmlFilePath));
        Source schema = new StreamSource(new File(schemaPath));

        assertFalse(new SchemaValidator().validate(input, schema));
    }

    /**
     * Test validate() with an illformed document and valid schema.
     *
     * <dl>
     *   <dt>schema</dt>
     *   <dd>test/xsd/videos.xsd</dd>
     *   <dt>input</dt>
     *   <dd>test/xml/illformed.xml</dd>
     * </dl>
     */
    public void testValidateDocIllformed() {
        System.out.println("testValidateDocIllformed");

        String xmlFilePath = "test/xml/illformed.xml";
        String schemaPath  = "test/xsd/videos.xsd";
        Source input  = new StreamSource(new File(xmlFilePath));
        Source schema = new StreamSource(new File(schemaPath));

        assertFalse(new SchemaValidator().validate(input, schema));
    }

    /**
     * Test validate() with a valid document and illformed schema.
     *
     * <dl>
     *   <dt>schema</dt>
     *   <dd>test/xsd/illformed-schema.xsd</dd>
     *   <dt>input</dt>
     *   <dd>test/xml/videos.xml</dd>
     * </dl>
     */
    public void testValidateWhenSchemaIllformed() {
        System.out.println("testValidateWhenSchemaIllformed");

        String xmlFilePath = "test/xml/videos.xml";
        String schemaPath  = "test/xsd/illformed-schema.xsd";
        Source input  = new StreamSource(new File(xmlFilePath));
        Source schema = new StreamSource(new File(schemaPath));

        assertFalse(new SchemaValidator().validate(input, schema));
    }

    /**
     * Test validateDirectory() with valid input and valid schema.
     *
     * <dl>
     *   <dt>schema</dt>
     *   <dd>test/xsd/videos.xsd</dd>
     *   <dt>input</dt>
     *   <dd>test/xml/valid/</dd>
     * </dl>
     */
    public void testValidateDirectoryValid() {
        System.out.println("testValidateDirectoryValid");
        
        String xmlDirPath = "test/xml/valid";
        String schemaPath = "test/xsd/videos.xsd";
        File   input  = new File(xmlDirPath);
        Source schema = new StreamSource(new File(schemaPath));
        
        assertTrue(new SchemaValidator().validateDirectory(input, schema));
    }

    /**
     * Test validateDirectory() with invalid input and valid schema.
     *
     * <dl>
     *   <dt>schema</dt>
     *   <dd>test/xsd/videos.xsd</dd>
     *   <dt>input</dt>
     *   <dd>test/xml/invalid/</dd>
     * </dl>
     */
    public void testValidateDirectoryInvalid() {
        System.out.println("testValidateDirectoryInvalid");
        
        String xmlDirPath = "test/xml/invalid";
        String schemaPath = "test/xsd/videos.xsd";
        File   input  = new File(xmlDirPath);
        Source schema = new StreamSource(new File(schemaPath));
        
        assertFalse(new SchemaValidator().validateDirectory(input, schema));
    }

    /**
     * Test validateDirectory() with partially valid input and valid schema.
     *
     * <dl>
     *   <dt>schema</dt>
     *   <dd>test/xsd/videos.xsd</dd>
     *   <dt>input</dt>
     *   <dd>test/xml/partially-valid/</dd>
     * </dl>
     */
    public void testValidateDirectoryPartiallyValid() {
        System.out.println("testValidateDirectoryPartiallyValid");
        
        String xmlDirPath = "test/xml/partially-valid";
        String schemaPath = "test/xsd/videos.xsd";
        File   input  = new File(xmlDirPath);
        Source schema = new StreamSource(new File(schemaPath));
        
        assertFalse(new SchemaValidator().validateDirectory(input, schema));
    }

    /**
     * Test validateDirectory() with valid input and non existing schema.
     *
     * <dl>
     *   <dt>schema</dt>
     *   <dd>test/xsd/does-not-exist-schema.xsd</dd>
     *   <dt>input</dt>
     *   <dd>test/xml/valid/</dd>
     * </dl>
     */
    public void testValidateDirectorySchemaDoesNotExist() {
        System.out.println("testValidateDirectorySchemaDoesNotExist");
        
        String xmlDirPath = "test/xml/valid";
        String schemaPath = "test/xsd/does-not-exist-schema.xsd";
        File   input  = new File(xmlDirPath);
        Source schema = new StreamSource(new File(schemaPath));
        
        assertFalse(new SchemaValidator().validateDirectory(input, schema));
    }

    /**
     * Test validateDirectory() with valid input and invalid schema.
     *
     * <dl>
     *   <dt>schema</dt>
     *   <dd>test/xsd/invalid-schema.xsd</dd>
     *   <dt>input</dt>
     *   <dd>test/xml/valid/</dd>
     * </dl>
     */
    public void testValidateDirectorySchemaInvalid() {
        System.out.println("testValidateDirectorySchemaIllformed");
        
        String xmlDirPath = "test/xml/valid";
        String schemaPath = "test/xsd/invalid-schema.xsd";
        File   input  = new File(xmlDirPath);
        Source schema = new StreamSource(new File(schemaPath));
        
        assertFalse(new SchemaValidator().validateDirectory(input, schema));
    }

    /**
     * Test validateDirectory() with valid input and illformed schema.
     *
     * <dl>
     *   <dt>schema</dt>
     *   <dd>test/xsd/illformed-schema.xsd</dd>
     *   <dt>input</dt>
     *   <dd>test/xml/valid/</dd>
     * </dl>
     */
    public void testValidateDirectorySchemaIllformed() {
        System.out.println("testValidateDirectorySchemaIllformed");
        
        String xmlDirPath = "test/xml/valid";
        String schemaPath = "test/xsd/illformed-schema.xsd";
        File   input  = new File(xmlDirPath);
        Source schema = new StreamSource(new File(schemaPath));
        
        assertFalse(new SchemaValidator().validateDirectory(input, schema));
    }
}
