

package org.dom4j.xpath;

import org.dom4j.AbstractTestCase;
import org.dom4j.Node;
import org.dom4j.XPath;
import org.dom4j.io.SAXReader;
import org.testng.annotations.BeforeMethod;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * Tests the use of a Map for defining namespace URIs
 * 
 * @author <a href="mailto:jstrachan@apache.org">James Strachan </a>
 * @version $Revision: 1.3 $
 */
public class UriMapTest extends AbstractTestCase {

    public void testURIMap() throws Exception {
        Map<String, String> uris = new HashMap<String, String>();
        uris.put("SOAP-ENV", "http://schemas.xmlsoap.org/soap/envelope/");
        uris.put("m", "urn:xmethodsBabelFish");

        String path = "/SOAP-ENV:Envelope/SOAP-ENV:Body/m:BabelFish";
        XPath xpath = document.createXPath(path);
        xpath.setNamespaceURIs(uris);

        Node babelfish = xpath.selectSingleNode(document);

        // log( "Found: " + babelfish );
        assertNotNull("Found valid node", babelfish);
    }

    @BeforeMethod
    public void setUp() throws Exception {
        super.setUp();
        document = new SAXReader().read(new File("xml/soap.xml"));
    }
}

