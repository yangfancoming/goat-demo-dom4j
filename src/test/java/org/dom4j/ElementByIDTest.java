

package org.dom4j;

/**
 * Tests the elementByID() method
 * 
 * @author <a href="mailto:james.strachan@metastuff.com">James Strachan </a>
 * @version $Revision: 1.4 $
 */
public class ElementByIDTest extends AbstractTestCase {
    /** Input XML file to read */
    protected static final String INPUT_XML_FILE = "xml/test/elementByID.xml";


    public void testElementByID() throws Exception {
        String id = "message";
        Document document = getDocument(INPUT_XML_FILE);

        // test XPath
        Element element = (Element) document.selectSingleNode("//*[@ID='" + id  + "']");
        assertTrue("Found element by ID: " + id, element != null);
        assertEquals("ID is equal", id, element.attributeValue("ID"));

        // test with elementByID
        element = document.elementByID(id);

        assertTrue("Found element by ID: " + id, element != null);
        assertEquals("ID is equal", id, element.attributeValue("ID"));

        log("Found element: " + element.getText());

        element = document.elementByID("DoesNotExist");

        assertTrue("Found no element", element == null);
    }
}

