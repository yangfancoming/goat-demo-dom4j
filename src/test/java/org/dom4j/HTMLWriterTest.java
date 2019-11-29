

package org.dom4j;

import org.dom4j.io.HTMLWriter;
import org.dom4j.io.OutputFormat;

import java.io.StringWriter;

/**
 * Test harness for the HTMLWriter
 * 
 * @author <a href="mailto:jstrachan@apache.org">James Strachan </a>
 * @version $Revision: 1.4 $
 */
public class HTMLWriterTest extends AbstractTestCase {

    public void testWriter() throws Exception {
        String xml = "<html> <body><![CDATA[First&nbsp;test]]></body> </html>";
        Document document = DocumentHelper.parseText(xml);
        StringWriter buffer = new StringWriter();
        HTMLWriter writer = new HTMLWriter(buffer);
        writer.write(document);

        String output = buffer.toString();

        String expects = "\n<html>\n  <body>First&nbsp;test</body>\n</html>\n";

        System.out.println("expects: " + expects);
        System.out.println("output: " + output);

        assertEquals("Output is correct", expects, output);
    }

    public void testBug923882() throws Exception {
        Document doc = DocumentFactory.getInstance().createDocument();
        Element root = doc.addElement("root");
        root.addText("this is ");
        root.addText(" sim");
        root.addText("ple text ");
        root.addElement("child");
        root.addText(" contai");
        root.addText("ning spaces and");
        root.addText(" multiple textnodes");

        OutputFormat format = new OutputFormat();
        format.setEncoding("UTF-8");
        format.setIndentSize(4);
        format.setNewlines(true);
        format.setTrimText(true);
        format.setExpandEmptyElements(true);

        StringWriter buffer = new StringWriter();
        HTMLWriter writer = new HTMLWriter(buffer, format);
        writer.write(doc);

        String xml = buffer.toString();
        log(xml);

        int start = xml.indexOf("<root");
        int end = xml.indexOf("/root>") + 6;
        String eol = "\n"; // System.getProperty("line.separator");
        String expected = "<root>this is simple text" + eol
                + "    <child></child>containing spaces and multiple textnodes"
                + eol + "</root>";
        System.out.println("Expected:");
        System.out.println(expected);
        System.out.println("Obtained:");
        System.out.println(xml.substring(start, end));
        assertEquals(expected, xml.substring(start, end));
    }

    public void testBug923882asWriter() throws Exception {
        // use an the HTMLWriter sax-methods.
        //
        StringWriter buffer = new StringWriter();
        HTMLWriter writer = new HTMLWriter(buffer, OutputFormat
                .createPrettyPrint());
        writer.characters("wor".toCharArray(), 0, 3);
        writer.characters("d-being-cut".toCharArray(), 0, 11);

        String expected = "word-being-cut";
        assertEquals(expected, buffer.toString());

        buffer = new StringWriter();
        writer = new HTMLWriter(buffer, OutputFormat.createPrettyPrint());
        writer.characters("    wor".toCharArray(), 0, 7);
        writer.characters("d being    ".toCharArray(), 0, 11);
        writer.characters("  cut".toCharArray(), 0, 5);

        expected = "word being cut";
        assertEquals(expected, buffer.toString());
    }

    public void testBug923882asWriterWithEmptyCharArray() throws Exception {
        // use an the HTMLWriter sax-methods.
        //
        StringWriter buffer = new StringWriter();
        HTMLWriter writer = new HTMLWriter(buffer, OutputFormat
                .createPrettyPrint());
        writer.characters("wor".toCharArray(), 0, 3);
        writer.characters(new char[0], 0, 0);
        writer.characters("d-being-cut".toCharArray(), 0, 11);

        String expected = "word-being-cut";
        assertEquals(expected, buffer.toString());
    }

    public void testBug619415() throws Exception {
        Document doc = getDocument("/xml/test/dosLineFeeds.xml");

        StringWriter wr = new StringWriter();
        HTMLWriter writer = new HTMLWriter(wr, new OutputFormat("", false));
        writer.write(doc);

        String result = wr.toString();
        System.out.println(result);

        assertTrue(result.indexOf("Mary had a little lamb.") > -1);
        assertTrue(result.indexOf("Hello, this is a test.") > -1);
    }
}

