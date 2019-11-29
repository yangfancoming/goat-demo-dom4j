

package org.dom4j.io;

import org.dom4j.*;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.List;

/**
 * A test harness to test the content API in DOM4J
 */
public class SAXReaderTest extends AbstractTestCase {

    /**
     * Test bug reported by Christian Oetterli http://tinyurl.com/6po8v
     * @throws Exception    DOCUMENT ME!
     */
    public void testReadFile() throws Exception {
        File file = getFile("/xml/#.xml");
        new SAXReader().read(file);
    }
    
    public void testEncoding() throws Exception {
        String xml = "<?xml version='1.0' encoding='ISO-8859-1'?><root/>";
        SAXReader reader = new SAXReader();
        reader.setEncoding("ISO-8859-1");
        Document doc = reader.read(new StringReader(xml));
        assertEquals("encoding incorrect", "ISO-8859-1", doc.getXMLEncoding());
    }

    public void testRussian() throws Exception {
        Document doc = getDocument("/xml/russArticle.xml");
        assertEquals("encoding not correct", "koi8-r", doc.getXMLEncoding());
        Element el = doc.getRootElement();
        StringWriter writer = new StringWriter();
        XMLWriter xmlWriter = new XMLWriter(writer);
        OutputFormat format = OutputFormat.createPrettyPrint();
        format.setEncoding("koi8-r");
        xmlWriter.write(doc);
        log(writer.toString());
    }

    public void testRussian2() throws Exception {
        Document doc = getDocument("/xml/russArticle.xml");
        XMLWriter xmlWriter = new XMLWriter(new OutputFormat("", false,"koi8-r"));
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        xmlWriter.setOutputStream(out);
        xmlWriter.write(doc);
        xmlWriter.flush();
        xmlWriter.close();
        log(out.toString());
    }

    public void testBug833765() throws Exception {
        SAXReader reader = new SAXReader();
        reader.setIncludeExternalDTDDeclarations(true);
        getDocument("/xml/dtd/external.xml", reader);
    }

    public void testBug527062() throws Exception {
        Document doc = getDocument("/xml/test/test.xml");
        List<Node> l = doc.selectNodes("//broked/junk");

        for (Node aL : l) {
            System.out.println("Found node: "  + aL.getStringValue());
        }
        assertEquals("hi there", l.get(0).getStringValue());
        assertEquals("hello world", l.get(1).getStringValue());
    }

    public void testEscapedComment() throws Exception {
        String txt = "<eg>&lt;!-- &lt;head> &amp; &lt;body> --&gt;</eg>";
        Document doc = DocumentHelper.parseText(txt);
        Element eg = doc.getRootElement();
        System.out.println(doc.asXML());
        assertEquals("<!-- <head> & <body> -->", eg.getText());
    }
}

