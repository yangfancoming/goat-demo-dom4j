

package org.dom4j.samples;

import org.dom4j.Document;
import org.dom4j.io.SAXReader;

/**
 * A simple test program to demonstrate using SAX to create a DOM4J tree
 * 
 * @author <a href="mailto:james.strachan@metastuff.com">James Strachan </a>
 * @version $Revision: 1.4 $
 */
public class SAXDemo extends AbstractDemo {

    public static void main(String[] args) {
        run(new SAXDemo(), args);
    }

    public SAXDemo() {
    }

    protected Document parse(String xmlFile) throws Exception {
        SAXReader reader = new SAXReader();
        return reader.read(xmlFile);
    }

}

