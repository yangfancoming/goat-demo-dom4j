

package org.dom4j;

import org.dom4j.dtd.ElementDecl;
import org.dom4j.io.SAXReader;

import java.util.List;

/**
 * Tests the DocType functionality
 * 
 * @author <a href="mailto:jstrachan@apache.org">James Strachan </a>
 * @version $Revision: 1.4 $
 */
public class DocTypeTest extends AbstractTestCase {
    /** Input XML file to read */
    protected static final String INPUT_XML_FILE = "/xml/dtd/internal.xml";


    public void testDocType() throws Exception {
        SAXReader reader = new SAXReader();
        reader.setIncludeInternalDTDDeclarations(true);

        Document document = getDocument(INPUT_XML_FILE, reader);

        DocumentType docType = document.getDocType();
        assertTrue("Has DOCTYPE", docType != null);

        List declarations = docType.getInternalDeclarations();
        assertTrue("DOCTYPE has declarations", (declarations != null)
                && !declarations.isEmpty());

        ElementDecl decl = (ElementDecl) declarations.get(0);

        assertEquals("name is correct", "greeting", decl.getName());
        assertEquals("model is correct", "(#PCDATA)", decl.getModel());

        String expected = "<!ELEMENT " + decl.getName() + " " + decl.getModel()
                + ">";
        assertEquals("toString() is correct", expected, decl.toString());
    }
}

