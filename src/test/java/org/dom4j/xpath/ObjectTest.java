

package org.dom4j.xpath;

import org.dom4j.AbstractTestCase;
import org.dom4j.Node;
import org.dom4j.XPath;

/**
 * Test harness for numeric XPath expressions
 * 
 * @author <a href="mailto:james.strachan@metastuff.com">James Strachan </a>
 * @version $Revision: 1.3 $
 */
public class ObjectTest extends AbstractTestCase {
    protected static String[] paths = {"name(/.)", "name()"};


    public void testXPaths() throws Exception {
        Node element = document.selectSingleNode("//author");
        int size = paths.length;

        for (int i = 0; i < size; i++) {
            testXPath(document, paths[i]);
            testXPath(element, paths[i]);
        }
    }

    // Implementation methods
    // -------------------------------------------------------------------------
    protected void testXPath(Node node, String xpathText) {
        XPath xpath = node.createXPath(xpathText);
        Object object = xpath.evaluate(node);
    }
}

