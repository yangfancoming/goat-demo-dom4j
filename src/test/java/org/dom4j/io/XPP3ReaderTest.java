

package org.dom4j.io;

import org.dom4j.AbstractTestCase;
import org.dom4j.Document;
import org.dom4j.Element;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.StringWriter;

/**
 * Test class for the XPP3Reader. This is based on the TestSaxReader class.
 * 
 * @author <a href="mailto:pelle@neubia.com">Pelle Braendgaard </a>
 * @author <a href="mailto:maartenc@sourceforge.net">Maarten Coene </a>
 */
public class XPP3ReaderTest extends AbstractTestCase {

    public void testRussian() throws Exception {
        File file = getFile("/xml/russArticle.xml");
        XPP3Reader xmlReader = new XPP3Reader();
        Document doc = xmlReader.read(file);
        Element el = doc.getRootElement();

        StringWriter writer = new StringWriter();
        XMLWriter xmlWriter = new XMLWriter(writer);
        OutputFormat format = OutputFormat.createPrettyPrint();
        format.setEncoding("koi8-r");
        xmlWriter.write(doc);
        log(writer.toString());
    }

    public void testRussian2() throws Exception {
        File file = getFile("/xml/russArticle.xml");
        XPP3Reader xmlReader = new XPP3Reader();
        Document doc = xmlReader.read(file);
        XMLWriter xmlWriter = new XMLWriter(new OutputFormat("", false,
                "koi8-r"));
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        xmlWriter.setOutputStream(out);
        xmlWriter.write(doc);
        xmlWriter.flush();
        xmlWriter.close();
        log(out.toString());
    }
}

