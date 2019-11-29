

package org.dom4j.samples.bean;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.bean.BeanDocumentFactory;
import org.dom4j.io.SAXReader;
import org.dom4j.samples.SAXDemo;

import java.awt.*;
import java.util.Iterator;
import java.util.List;

/**
 * A simple test program to demonstrate using simple binding of JavaBeans to
 * inside a DOM4J tree
 * 
 * @author <a href="mailto:james.strachan@metastuff.com">James Strachan </a>
 * @version $Revision: 1.4 $
 */
public class BeanDemo extends SAXDemo {

    public static void main(String[] args) {
        run(new BeanDemo(), args);
    }

    public BeanDemo() {
    }

    public void run(String[] args) throws Exception {
        if (args.length < 1) {
            printUsage("<XML document URL>");
            return;
        }

        Document document = parse(args[0]);
        process(document);
    }

    protected Document parse(String url) throws Exception {
        SAXReader reader = new SAXReader(BeanDocumentFactory.getInstance());
        return reader.read(url);
    }

    protected void process(Document document) {
        // find all of the windows
        List windows = document.selectNodes("//window");
        for (Iterator iter = windows.iterator(); iter.hasNext();) {
            Element element = (Element) iter.next();
            Object window = element.getData();
            if (window instanceof Component) {
                Component component = (Component) window;
                component.setVisible(true);
            }

            println("found element: " + element);
            println("found window: " + window);
        }

        println("");
        println("Now lets find all the fonts...");

        List fonts = document.selectNodes("//@font");
        for (Iterator iter = fonts.iterator(); iter.hasNext();) {
            Attribute font = (Attribute) iter.next();
            println("found font: " + font.getData());
        }

    }

}
