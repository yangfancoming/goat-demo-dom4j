

package org.dom4j.tree;

import org.dom4j.*;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

import java.io.ByteArrayOutputStream;

/**
 * Some tests on DefaultDocument.
 * @author <a href="mailto:maartenc@users.sourceforge.net">Maarten Coene </a>
 */
public class DefaultDocumentTest extends AbstractTestCase {

    public void testDoubleRootElement() {
        Document document = DocumentFactory.getInstance().createDocument();
        document.addElement("root");
        Element root = DocumentFactory.getInstance().createElement("anotherRoot");
        try {
            document.add(root);
        } catch (IllegalAddException e) {
            System.out.println(e.getMessage());
        }
    }

    public void testBug799656() throws Exception {
        Document document = DocumentFactory.getInstance().createDocument();
        Element el = document.addElement("root");
        el.setText("text with an \u00FC in it"); // u00FC is umlaut
        System.out.println(document.asXML());
        DocumentHelper.parseText(document.asXML());
    }

    public void testAsXMLWithEncoding() throws Exception {
        DefaultDocument document = new DefaultDocument();
        document.addElement("root");
        document.setXMLEncoding("ISO-8859-1");

        Document doc = DocumentHelper.parseText("<?xml version='1.0' "
                + "encoding='ISO-8859-1'?><root/>");

        String xml1 = document.asXML();
        String xml2 = doc.asXML();

        assertTrue(xml1.indexOf("ISO-8859-1") != -1);
        assertTrue(xml2.indexOf("ISO-8859-1") != -1);
    }

    public void testBug1156909() throws Exception {
        Document doc = DocumentHelper.parseText("<?xml version='1.0' encoding='ISO-8859-1'?><root/>");
        assertEquals("XMLEncoding not correct", "ISO-8859-1", doc.getXMLEncoding());
    }

    public void testAsXMLWithEncodingAndContent()  {
        DefaultDocument document = new DefaultDocument();
        document.setXMLEncoding("UTF-16");
        Element root = document.addElement("root");
        root.setText("text with an \u00FC in it"); // u00FC is umlaut

        String xml = document.asXML();
        assertTrue(xml.indexOf("UTF-16") != -1);
        assertTrue(xml.indexOf('\u00FC') != -1);
    }

    public void testEncoding() throws Exception {
        Document document = DocumentFactory.getInstance().createDocument(
                "koi8-r");
        Element el = document.addElement("root");
        el.setText("text with an \u00FC in it"); // u00FC is umlaut

        System.out.println(document.asXML());

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        OutputFormat of = OutputFormat.createPrettyPrint();
        of.setEncoding("koi8-r");

        XMLWriter writer = new XMLWriter(out, of);
        writer.write(document);

        String result = out.toString("koi8-r");
        System.out.println(result);

        Document doc2 = DocumentHelper.parseText(result);
        // System.out.println(doc2.asXML());

    }
}

