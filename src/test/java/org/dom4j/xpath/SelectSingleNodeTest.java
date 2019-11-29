

package org.dom4j.xpath;

import org.dom4j.AbstractTestCase;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.Node;

/**
 * Tests the selectSingleNode method
 * 
 * @author <a href="mailto:jstrachan@apache.org">James Strachan </a>
 * @version $Revision: 1.4 $
 */
public class SelectSingleNodeTest extends AbstractTestCase {

    public void testSelectSingleNode() throws Exception {
        Document document = getDocument("/xml/test/jimBrain.xml");
        Node node = document.selectSingleNode("/properties/client/threadsafe");
        assertTrue("Found a valid node", node != null);

        Element server = (Element) document
                .selectSingleNode("/properties/server");
        assertTrue("Found a valid server", server != null);

        Element root = document.getRootElement();
        server = (Element) root.selectSingleNode("/properties/server");
        assertTrue("Found a valid server", server != null);

        // try finding it via a relative path
        server = (Element) document.selectSingleNode("properties/server");
        assertTrue("Found a valid server", server != null);

        // now lets use a relative path
        Element connection = (Element) server.selectSingleNode("db/connection");
        assertTrue("Found a valid connection", connection != null);
    }

    /**
     * Test out Steen's bug
     * 
     * @throws Exception
     *             DOCUMENT ME!
     */
    public void testSteensBug() throws Exception {
        Document document = getDocument("/xml/schema/personal.xsd");

        String xpath = "/xs:schema/xs:element[@name='person']";
        assertNotNull("element is null", document.selectSingleNode(xpath));

        Element root = document.getRootElement();

        assertNotNull("element is null", root.selectSingleNode(xpath));
    }
}

