

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
public class NumberTest extends AbstractTestCase {
    protected static String[] paths = {"2+2", "2 + 2", "2 + number(1) + 2",
            "number(1) * 2", "2 + count(//author) + 2", "2 + (2 * 5)",
            "count(//author) + count(//author/attribute::*)",
            "(12 + count(//author) + count(//author/attribute::*)) div 2",
            "count(//author)", "count(//author/attribute::*)",
            "2 + number(1) * 2", "count(descendant::author)",
            "count(ancestor::author)", "count(descendant::*)",
            "count(descendant::author)+1", "count(ancestor::*)",
            "10 + count(ancestor-or-self::author) + 5",
            "10 + count(descendant::author) * 5",
            "10 + (count(descendant::author) * 5)"};


    public void testXPaths() throws Exception {
        Node element = document.selectSingleNode("//author");
        int size = paths.length;

        for (int i = 0; i < size; i++) {
            testXPath(document, paths[i]);
            testXPath(element, paths[i]);
        }

        log("Finished successfully");
    }

    // Implementation methods
    // -------------------------------------------------------------------------
    protected void testXPath(Node node, String xpathText) throws Exception {
        try {
            XPath xpath = node.createXPath(xpathText);

            Number number = xpath.numberValueOf(node);

            log("Searched path: " + xpathText + " found: " + number);
        } catch (Throwable e) {
            log("Caught exception: " + e);
            e.printStackTrace();
            assertTrue("Failed to process:  " + xpathText
                    + " caught exception: " + e, false);
        }
    }
}

