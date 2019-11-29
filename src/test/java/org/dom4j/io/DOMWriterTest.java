

package org.dom4j.io;

import org.dom4j.AbstractTestCase;
import org.w3c.dom.NamedNodeMap;

import java.io.StringWriter;

/**
 * DOCUMENT ME!
 * 
 * @author Maarten
 */
public class DOMWriterTest extends AbstractTestCase {

    public void testNamespaceBug() throws Exception {
        org.dom4j.Document doc = getDocument("/xml/namespaces.xml");
        DOMWriter writer = new DOMWriter(org.dom4j.dom.DOMDocument.class);
        org.w3c.dom.Document result = writer.write(doc);

        NamedNodeMap atts = result.getDocumentElement().getAttributes();
        assertEquals(4, atts.getLength());

        XMLWriter wr = new XMLWriter();
        wr.setOutputStream(System.out);
        wr.write((org.dom4j.Document) result);
    }

    public void testBug905745() throws Exception {
        org.dom4j.Document doc = getDocument("/xml/namespaces.xml");
        DOMWriter writer = new DOMWriter();
        org.w3c.dom.Document result = writer.write(doc);

        NamedNodeMap atts = result.getDocumentElement().getAttributes();
        org.w3c.dom.Node versionAttr = atts.getNamedItem("version");
        assertNotNull(versionAttr);
        assertNotNull(versionAttr.getLocalName());
        assertEquals("version", versionAttr.getLocalName());
        assertEquals("version", versionAttr.getNodeName());
    }

    public void testBug926752() throws Exception {
        org.dom4j.Document doc = getDocument("/xml/test/defaultNamespace.xml");
        DOMWriter writer = new DOMWriter(org.dom4j.dom.DOMDocument.class);
        org.w3c.dom.Document result = writer.write(doc);

        NamedNodeMap atts = result.getDocumentElement().getAttributes();
        assertEquals(1, atts.getLength());

        OutputFormat format = OutputFormat.createCompactFormat();
        format.setSuppressDeclaration(true);

        XMLWriter wr = new XMLWriter(format);
        StringWriter strWriter = new StringWriter();
        wr.setWriter(strWriter);
        wr.write((org.dom4j.Document) result);
        assertEquals("<a xmlns=\"dummyNamespace\"><b><c>Hello</c></b></a>",
                strWriter.toString());
    }
}

