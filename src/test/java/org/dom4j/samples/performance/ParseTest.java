

package org.dom4j.samples.performance;

import org.dom4j.Document;
import org.dom4j.io.SAXReader;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.StringReader;

/**
 * Tests the performance of parsing a Document
 * 
 * @author <a href="mailto:james.strachan@metastuff.com">James Strachan </a>
 * @version $Revision: 1.4 $
 */
public class ParseTest extends PerformanceSupport {

    private int bufferSize = 128 * 1024;

    private Document document;

    private SAXReader xmlReader;

    private String text;

    public static void main(String[] args) {
        run(new ParseTest(), args);
    }

    public ParseTest() {
    }

    protected void setUp() throws Exception {
        xmlReader = new SAXReader();

        StringBuffer buffer = new StringBuffer(64 * 1024);
        BufferedReader reader = new BufferedReader(new FileReader(xmlFile));
        while (true) {
            String text = reader.readLine();
            if (text == null) {
                break;
            }
            buffer.append(text);
            buffer.append("\n");
        }
        text = buffer.toString();
    }

    protected void tearDown()  {
        println("Created Document: " + document);
    }

    protected Task createTask()  {
        return ()->document = xmlReader.read(new StringReader(text));
    }
}

