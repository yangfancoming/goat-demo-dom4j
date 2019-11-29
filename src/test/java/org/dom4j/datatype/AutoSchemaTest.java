

package org.dom4j.datatype;

import org.dom4j.DocumentFactory;
import org.dom4j.io.SAXReader;
import org.testng.annotations.BeforeClass;

import java.math.BigInteger;
import java.util.Calendar;

/**
 * Test harness for the XML Schema Data Type integration. These tests use
 * auto-loading of the XML Schema document
 * 
 * @author <a href="mailto:jstrachan@apache.org">James Strachan </a>
 * @version $Revision: 1.4 $
 */
public class AutoSchemaTest extends AbstractDataTypeTestCase {

    public void testIntAttribute() throws Exception {
        testNodes("//person/@x", Integer.class);
    }

    public void testIntElement() throws Exception {
        testNodes("//person/salary", Integer.class);
    }

    public void testString() throws Exception {
        testNodes("//person/note", String.class);
    }

    public void testDate() throws Exception {
        testNodes("//person/@d", Calendar.class);
    }

    public void testDateTime() throws Exception {
        testNodes("//person/@dt", Calendar.class);
    }

    public void testInteger() throws Exception {
        testNodes("//person/@age", BigInteger.class);
    }

    // Implementation methods
    // -------------------------------------------------------------------------
    @BeforeClass
    public void setUp() throws Exception {
        super.setUp();

        DocumentFactory factory = loadDocumentFactory();
        SAXReader reader = new SAXReader(factory);
        document = getDocument(getDocumentURI(), reader);
    }

    protected String getDocumentURI() {
        return "/xml/test/schema/personal-schema.xml";
    }

    protected DocumentFactory loadDocumentFactory() throws Exception {
        return DatatypeDocumentFactory.getInstance();
    }
}

