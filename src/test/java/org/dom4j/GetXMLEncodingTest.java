

package org.dom4j;

import org.dom4j.io.SAXReader;
import org.xml.sax.InputSource;

import java.io.ByteArrayInputStream;
import java.io.StringReader;

/**
 * DOCUMENT ME!
 * 
 * @author Maarten Coene
 * @version $Revision: 1.4 $
 */
public class GetXMLEncodingTest extends AbstractTestCase {

    public void testXMLEncodingFromString() throws Exception {
        String xmlEnc = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<root/>";

        SAXReader reader = new SAXReader();
        InputSource source = new InputSource(new ByteArrayInputStream(xmlEnc
                .getBytes("UTF-8")));
        Document doc = reader.read(source);
        assertEquals("UTF-8", doc.getXMLEncoding());

        doc = reader.read(new StringReader(xmlEnc));
        assertNull(doc.getXMLEncoding());
    }

    public void testXMLEncodingFromURL() throws Exception {
        Document doc = getDocument("/xml/test/encode.xml");
        assertEquals("UTF-8", doc.getXMLEncoding());

        doc = getDocument("/xml/russArticle.xml");
        assertEquals("koi8-r", doc.getXMLEncoding());
    }

    public void testXMLEncodingFromStringWithHelper() throws Exception {
        String xmlEnc = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<root/>";
        String xmlNoEnc = "<root/>";

        Document doc = DocumentHelper.parseText(xmlEnc);
        assertEquals("UTF-8", doc.getXMLEncoding());

        doc = DocumentHelper.parseText(xmlNoEnc);
        assertNull(doc.getXMLEncoding());
    }
}

