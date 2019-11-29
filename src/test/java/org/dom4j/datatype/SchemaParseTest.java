

package org.dom4j.datatype;

import org.dom4j.AbstractTestCase;
import org.dom4j.Document;
import org.dom4j.io.SAXReader;

/**
 * Tests the SchemaParser based on a test case provided by Luis Peña Sánchez
 * 
 * @author <a href="mailto:jstrachan@apache.org">James Strachan </a>
 * @version $Revision: 1.4 $
 */
public class SchemaParseTest extends AbstractTestCase {

    public void testParseSchema() throws Exception {
        DatatypeDocumentFactory factory = new DatatypeDocumentFactory();

        SAXReader reader = new SAXReader();
        reader.setDocumentFactory(factory);

        Document schema = getDocument("/xml/test/LuisSchema.xsd", reader);
        factory.loadSchema(schema);

        log("Loaded the schema");

        // now load an instance document
    }
}

