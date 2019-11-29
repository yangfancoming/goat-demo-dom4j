
package org.dom4j.samples;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

import java.io.FileWriter;
import java.util.Enumeration;
import java.util.Properties;

/**
 * A sample program to demonstrate creating some XML output using DOM4J. This
 * sample generates an XML document representing the state of the current JVM
 * displaying the current system properties.
 * 
 * @author <a href="mailto:jstrachan@apache.org">James Strachan </a>
 * @version $Revision: 1.4 $
 */
public class CreateXMLDemo extends AbstractDemo {

    public static void main(String[] args) {
        run(new CreateXMLDemo(), args);
    }

    public CreateXMLDemo() {
    }

    public void run(String[] args) throws Exception {
        Document document = createDocument();
        OutputFormat format = new OutputFormat("  ", true);
        if (args.length < 1) {
            XMLWriter writer = new XMLWriter(System.out, format);
            writer.write(document);
        } else {
            String fileName = args[0];
            println("Writing file: " + fileName);
            FileWriter out = new FileWriter(args[0]);
            XMLWriter writer = new XMLWriter(out, format);
            writer.write(document);
            out.close();
        }
    }

    protected Document createDocument() {
        Document document = DocumentHelper.createDocument();
        Element root = document.addElement("system");
        Properties properties = System.getProperties();
        for (Enumeration elements = properties.propertyNames(); elements.hasMoreElements();) {
            String name = (String) elements.nextElement();
            String value = properties.getProperty(name);
            Element element = root.addElement("property");
            element.addAttribute("name", name);
            element.addText(value);
        }
        return document;
    }
}
