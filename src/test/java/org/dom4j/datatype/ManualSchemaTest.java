

package org.dom4j.datatype;

import org.dom4j.Document;
import org.dom4j.DocumentFactory;

/**
 * Test harness for the XML Schema Data Type integration. These tests manually
 * load the schemas
 * 
 * @author <a href="mailto:jstrachan@apache.org">James Strachan </a>
 * @version $Revision: 1.4 $
 */
public class ManualSchemaTest extends AutoSchemaTest {
    // Implementation methods
    // -------------------------------------------------------------------------
    protected String getDocumentURI() {
        return "/xml/test/schema/personal.xml";
    }

    protected DocumentFactory loadDocumentFactory() throws Exception {
        DatatypeDocumentFactory factory = new DatatypeDocumentFactory();

        Document schemaDocument = getDocument("/xml/test/schema/personal.xsd");
        factory.loadSchema(schemaDocument);

        return factory;
    }
}

