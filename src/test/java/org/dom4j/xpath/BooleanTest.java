

package org.dom4j.xpath;

import org.dom4j.AbstractTestCase;
import org.dom4j.DocumentHelper;
import org.dom4j.Node;
import org.dom4j.XPath;

import java.util.Iterator;
import java.util.List;

/**
 * Test harness for the boolean expressions
 * 
 * @author <a href="mailto:james.strachan@metastuff.com">James Strachan </a>
 * @version $Revision: 1.3 $
 */
public class BooleanTest extends AbstractTestCase {
    protected static String[] paths = {".[name()='author']",
            ".[.='James Strachan']", ".[name()='XXXX']", ".[.='XXXX']",
            "name()='author'", "name()='XXXX'", ".='James Strachan'",
            ".='XXXX'"};


    public void testXPaths() throws Exception {
        int size = paths.length;

        for (int i = 0; i < size; i++) {
            testXPath(paths[i]);
        }
    }

    // Implementation methods
    // -------------------------------------------------------------------------
    protected void testXPath(String xpathExpression) {
        XPath xpath = DocumentHelper.createXPath(xpathExpression);
        assertTrue("No xpath object was created", xpath != null);

        log("Evaluating xpath: " + xpath);

        List list = document.selectNodes("//author");

        for (Iterator iter = list.iterator(); iter.hasNext();) {
            Node node = (Node) iter.next();
            testXPath(node, xpath);
        }
    }

    protected void testXPath(Node node, XPath xpath) {
        List list = xpath.selectNodes(node);
    }
}

