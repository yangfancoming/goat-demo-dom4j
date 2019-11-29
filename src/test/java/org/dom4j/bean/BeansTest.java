

package org.dom4j.bean;

import org.dom4j.AbstractTestCase;
import org.dom4j.Document;
import org.dom4j.io.SAXReader;

// org.dom4j.DocumentException: E:\Code\dom4j\GitHub3\goat-demo-dom4j\xml\bean\gui.xml (系统找不到指定的路径。)
// org.dom4j.DocumentException: E:\Code\dom4j\GitHub3\goat-demo-dom4j\xml\bean\gui.xml (系统找不到指定的路径。)
public class BeansTest extends AbstractTestCase {

    public void testReadXML() throws Exception {
        SAXReader reader = new SAXReader(BeanDocumentFactory.getInstance());
        Document document = getDocument("xml/bean/gui.xml", reader);
        System.out.println("tesng-------"+document);
    }
}

