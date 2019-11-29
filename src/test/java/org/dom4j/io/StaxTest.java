

package org.dom4j.io;

import org.dom4j.AbstractTestCase;
import org.dom4j.Document;

import javax.xml.stream.XMLInputFactory;
import java.io.File;
import java.io.FileReader;
import java.io.StringWriter;

/**
 * Tests STAX→DOM4J functionality.
 * 
 * @author <a href="mailto:maartenc@sourceforge.net">Maarten Coene </a>
 * @author Christian Niles
 */
public class StaxTest extends AbstractTestCase {

    /**
     * Tests that the encoding specified in the XML declaration is exposed in
     * the Document read via StAX, and also that it gets output when writing.
     */
    private void testEncoding() {
        //TODO probably bug in JDK?
        /*
         * only execute if a reference implementation is available
         */
        try {
            XMLInputFactory.newInstance();
        } catch (javax.xml.stream.FactoryConfigurationError e) {
            // no implementation found, stop the test.
            return;
        }

        try {
            File file = getFile("/xml/russArticle.xml");
            STAXEventReader xmlReader = new STAXEventReader();
            Document doc = xmlReader.readDocument(new FileReader(file));

            assertEquals("russArticle.xml encoding wasn't correct", "koi8-r",
                    doc.getXMLEncoding());

            StringWriter writer = new StringWriter();
            STAXEventWriter xmlWriter = new STAXEventWriter(writer);
            xmlWriter.writeDocument(doc);

            String output = writer.toString();
            String xmlDecl = output.substring(0, output.indexOf("?>") + 2);
            String expected = "<?xml version=\'1.0\' encoding=\'koi8-r\'?>";
            assertEquals("Unexpected xml declaration", expected, xmlDecl);
            System.out.println(output);
        } catch (Exception e) {
            e.printStackTrace();
            fail(e.getMessage());
        }
    }
}

