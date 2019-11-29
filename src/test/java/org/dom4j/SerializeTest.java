

package org.dom4j;

import org.dom4j.io.SAXReader;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * Tests that a dom4j document is Serializable
 * 
 * @author <a href="mailto:jstrachan@apache.org">James Strachan </a>
 * @version $Revision: 1.4 $
 */
public class SerializeTest extends AbstractTestCase {

    public void testSerializePeriodicTable() throws Exception {
        testSerialize("/xml/periodic_table.xml");
    }

    public void testSerializeMuchAdo() throws Exception {
        testSerialize("/xml/much_ado.xml");
    }

    public void testSerializeTestSchema() throws Exception {
        testSerialize("/xml/test/schema/personal.xsd");
    }

    public void testSerializeXPath() throws Exception {
        Map uris = new HashMap();
        uris.put("SOAP-ENV", "http://schemas.xmlsoap.org/soap/envelope/");
        uris.put("m", "urn:xmethodsBabelFish");

        DocumentFactory factory = new DocumentFactory();
        factory.setXPathNamespaceURIs(uris);

        // now parse a document using my factory
        SAXReader reader = new SAXReader();
        reader.setDocumentFactory(factory);

        Document doc = getDocument("/xml/soap.xml", reader);

        // now lets use the prefixes
        String expr = "/SOAP-ENV:Envelope/SOAP-ENV:Body/m:BabelFish";
        Node element = doc.selectSingleNode(expr);
        assertTrue("Found valid element", element != null);

        XPath xpath = factory
                .createXPath("/SOAP-ENV:Envelope/SOAP-ENV:Body/m:BabelFish");
        element = xpath.selectSingleNode(doc);
        assertTrue("Found valid element", element != null);

        // now serialize
        ByteArrayOutputStream bytesOut = new ByteArrayOutputStream();
        ObjectOutputStream out = new ObjectOutputStream(bytesOut);
        out.writeObject(xpath);
        out.close();

        byte[] data = bytesOut.toByteArray();

        ObjectInputStream in = new ObjectInputStream(new ByteArrayInputStream(
                data));
        XPath xpath2 = (XPath) in.readObject();
        in.close();

        element = xpath2.selectSingleNode(doc);
        assertTrue("Found valid element", element != null);
    }

    // Implementation methods
    // -------------------------------------------------------------------------
    protected void testSerialize(String xmlFile) throws Exception {
        Document document = getDocument(xmlFile);
        String text = document.asXML();

        ByteArrayOutputStream bytesOut = new ByteArrayOutputStream();
        ObjectOutputStream out = new ObjectOutputStream(bytesOut);
        out.writeObject(document);
        out.close();

        byte[] data = bytesOut.toByteArray();

        ObjectInputStream in = new ObjectInputStream(new ByteArrayInputStream(
                data));
        Document doc2 = (Document) in.readObject();
        in.close();

        String text2 = doc2.asXML();

        assertEquals("Documents text are equal", text, text2);

        assertTrue("Read back document after serialization", (doc2 != null)
                && doc2 instanceof Document);

        assertDocumentsEqual(document, (Document) doc2);

        // now lets try add something to the document...
        doc2.getRootElement().addElement("new");
    }
}

