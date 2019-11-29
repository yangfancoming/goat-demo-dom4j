

package org.dom4j.datatype;

import org.dom4j.Document;
import org.dom4j.DocumentFactory;

/**
 * Test harness for the XML Schema Data Type integration. These tests manually
 * load the schemas using prefixes in the XSD file.
 * 
 * @author <a href="mailto:jstrachan@apache.org">James Strachan </a>
 * @version $Revision: 1.4 $
 */
public class ManualSchemaPrefixTest extends AutoSchemaTest {
    // Implementation methods
    // -------------------------------------------------------------------------
    protected DocumentFactory loadDocumentFactory() throws Exception {
        DatatypeDocumentFactory factory = new DatatypeDocumentFactory();

        Document schemaDocument = 
                getDocument("/xml/test/schema/personal-prefix.xsd");
        factory.loadSchema(schemaDocument);

        return factory;
    }
}

