

package org.dom4j;

import org.dom4j.io.SAXContentHandler;
import org.testng.annotations.BeforeClass;
import org.xml.sax.XMLReader;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.util.List;

public class SAXContentHandlerTest extends AbstractTestCase {
    private XMLReader xmlReader;

    protected String[] testDocuments = {"/xml/test/test_schema.xml",
            "/xml/test/encode.xml", "/xml/fibo.xml",
            "/xml/test/schema/personal-prefix.xsd", "/xml/test/soap2.xml"};

    @BeforeClass
    public void setUp() throws Exception {
        super.setUp();

        SAXParserFactory spf = SAXParserFactory.newInstance();
        spf.setNamespaceAware(true);

        SAXParser parser = spf.newSAXParser();
        xmlReader = parser.getXMLReader();
    }

    public void testSAXContentHandler() throws Exception {
        SAXContentHandler contentHandler = new SAXContentHandler();
        xmlReader.setContentHandler(contentHandler);
        xmlReader.setDTDHandler(contentHandler);
        xmlReader.setProperty("http://xml.org/sax/properties/lexical-handler",
                contentHandler);

        for (int i = 0, size = testDocuments.length; i < size; i++) {
            Document docFromSAXReader = getDocument(testDocuments[i]);

            xmlReader.parse(getFile(testDocuments[i]).toString());

            Document docFromSAXContentHandler = contentHandler.getDocument();

            docFromSAXContentHandler.setName(docFromSAXReader.getName());

            assertDocumentsEqual(docFromSAXReader, docFromSAXContentHandler);
            assertEquals(docFromSAXReader.asXML(), docFromSAXContentHandler
                    .asXML());
        }
    }

    public void testBug926713() throws Exception {
        Document doc = getDocument("/xml/test/cdata.xml");
        Element foo = doc.getRootElement();
        Element bar = foo.element("bar");
        List content = bar.content();
        assertEquals(3, content.size());
        assertEquals(Node.TEXT_NODE, ((Node) content.get(0)).getNodeType());
        assertEquals(Node.CDATA_SECTION_NODE, ((Node) content.get(1))
                .getNodeType());
        assertEquals(Node.TEXT_NODE, ((Node) content.get(2)).getNodeType());
    }
}

