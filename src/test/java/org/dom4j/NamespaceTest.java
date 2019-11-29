

package org.dom4j;

import org.dom4j.io.SAXReader;
import org.testng.annotations.BeforeClass;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * A test harness to test the use of Namespaces.
 * 
 * @author <a href="mailto:james.strachan@metastuff.com">James Strachan </a>
 * @version $Revision: 1.4 $
 */
public class NamespaceTest extends AbstractTestCase {
    /** Input XML file to read */
    private static final String INPUT_XML_FILE = "/xml/namespaces.xml";

    /** Namespace to use in tests */
    private static final Namespace XSL_NAMESPACE = Namespace.get("xsl",
            "http://www.w3.org/1999/XSL/Transform");

    private static final QName XSL_TEMPLATE = QName.get("template",
            XSL_NAMESPACE);


    public void debugShowNamespaces() throws Exception {
        Element root = getRootElement();

        for (Iterator iter = root.elementIterator(); iter.hasNext();) {
            Element element = (Element) iter.next();

            log("Found element:    " + element);
            log("Namespace:        " + element.getNamespace());
            log("Namespace prefix: " + element.getNamespacePrefix());
            log("Namespace URI:    " + element.getNamespaceURI());
        }
    }

    public void testGetElement() throws Exception {
        Element root = getRootElement();

        Element firstTemplate = root.element(XSL_TEMPLATE);
        assertTrue(
                "Root element contains at least one <xsl:template/> element",
                firstTemplate != null);

        log("Found element: " + firstTemplate);
    }

    public void testGetElements() throws Exception {
        Element root = getRootElement();

        List list = root.elements(XSL_TEMPLATE);
        assertTrue(
                "Root element contains at least one <xsl:template/> element",
                list.size() > 0);

        log("Found elements: " + list);
    }

    public void testElementIterator() throws Exception {
        Element root = getRootElement();
        Iterator iter = root.elementIterator(XSL_TEMPLATE);
        assertTrue(
                "Root element contains at least one <xsl:template/> element",
                iter.hasNext());

        do {
            Element element = (Element) iter.next();
            log("Found element: " + element);
        } while (iter.hasNext());
    }

    /**
     * Tests the use of namespace URI Mapping associated with a DocumentFactory
     * 
     * @throws Exception
     *             DOCUMENT ME!
     */
    public void testNamespaceUriMap() throws Exception {
        // register namespace prefix->uri mappings with factory
        Map<String, String> uris = new HashMap<String, String>();
        uris.put("x", "fooNamespace");
        uris.put("y", "barNamespace");

        DocumentFactory factory = new DocumentFactory();
        factory.setXPathNamespaceURIs(uris);

        // parse or create a document
        SAXReader reader = new SAXReader();
        reader.setDocumentFactory(factory);

        Document doc = getDocument("/xml/test/nestedNamespaces.xml", reader);

        // evaluate XPath using registered namespace prefixes
        // which do not appear in the document (though the URIs do!)
        String value = doc.valueOf("/x:pizza/y:cheese/x:pepper");

        log("Found value: " + value);

        assertEquals("XPath used default namesapce URIS", "works", value);
    }

    // Implementation methods
    // -------------------------------------------------------------------------
    @BeforeClass
    public void setUp() throws Exception {
        super.setUp();
        document = getDocument(INPUT_XML_FILE);
    }

    /**
     * DOCUMENT ME!
     * 
     * @return the root element of the document
     */
    protected Element getRootElement() {
        Element root = document.getRootElement();
        assertNotNull("Document has root element", root);

        return root;
    }
}

