

package org.dom4j.xpath;

import org.dom4j.*;

import java.util.List;

/**
 * Test harness for the attribute axis
 * 
 * @author <a href="mailto:james.strachan@metastuff.com">James Strachan </a>
 * @version $Revision: 1.3 $
 */
public class AttributeTest extends AbstractTestCase {

    protected static String[] paths = {"attribute::*", "/root/author/attribute::*", "//attribute::*", "@name"};

    public void testXPaths() {
      for (String path : paths) {
        testXPath(path);
      }
    }

    // Implementation methods
    // -------------------------------------------------------------------------
    protected void testXPath(String xpathText) {
        XPath xpath = DocumentHelper.createXPath(xpathText);
        List<Node> list = xpath.selectNodes(document);

        log("Searched path: " + xpathText + " found: " + list.size() + " result(s)");

      for (Node node : list) {
        log("Found Result: " + node);
        assertTrue("Results should be Attribute objects", node instanceof Attribute);
        Attribute attribute = (Attribute) node;
        assertTrue("Results should support the parent relationship", attribute.supportsParent());
        assertTrue("Results should contain reference to the parent element", attribute.getParent() != null);
        assertTrue("Resulting document not correct", attribute.getDocument() != null);
      }
    }
}

