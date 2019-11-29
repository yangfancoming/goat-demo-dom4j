

package org.dom4j.xpath;

import org.dom4j.AbstractTestCase;
import org.dom4j.DocumentHelper;
import org.dom4j.Node;
import org.dom4j.NodeFilter;

import java.util.Iterator;
import java.util.List;

/**
 * Test harness for XPath filters
 * 
 * @author <a href="mailto:james.strachan@metastuff.com">James Strachan </a>
 * @version $Revision: 1.3 $
 */
public class FilterTest extends AbstractTestCase {

    protected static String[] paths = {".[name()='author']", ".[name()='XXXX']", ".[.='James Strachan']", ".[.='XXXX']"};

    public void testXPaths() {
        int size = paths.length;
        for (int i = 0; i < size; i++) {
            testXPath(paths[i]);
        }
    }

    // Implementation methods
    // -------------------------------------------------------------------------
    protected void testXPath(String xpathExpression) {
        NodeFilter nodeFilter = DocumentHelper.createXPathFilter(xpathExpression);
        assertTrue("No NodeFilter object was created", nodeFilter != null);

        List list = document.selectNodes("//author");

        for (Iterator iter = list.iterator(); iter.hasNext();) {
            Node node = (Node) iter.next();

            if (nodeFilter.matches(node)) {
                log("Matches node: " + node.asXML());
            } else {
                log("No match for node: " + node.asXML());
            }
        }
    }
}

