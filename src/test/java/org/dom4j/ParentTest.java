

package org.dom4j;

import java.util.Iterator;
import java.util.List;

/**
 * A test harness to test the parent relationship and use of the {@link
 * Node#asXPathResult} method.
 * 
 * @author <a href="mailto:james.strachan@metastuff.com">James Strachan </a>
 * @version $Revision: 1.3 $
 */
public class ParentTest extends AbstractTestCase {

    public void testDocument() throws Exception {
        testParentRelationship(document.getRootElement());
    }

    public void testFragment() throws Exception {
        DocumentFactory factory = new DocumentFactory();
        Element root = factory.createElement("root");
        Element first = root.addElement("child");
        Element second = root.addElement("child");

        testXPathNode(root, first);
        testXPathNode(root, second);
    }

    // Implementation methods
    // -------------------------------------------------------------------------
    protected void testParentRelationship(Element parent, List content) {
        for (Iterator iter = content.iterator(); iter.hasNext();) {
            Object object = iter.next();

            if (object instanceof Element) {
                testParentRelationship((Element) object);
            }

            testXPathNode(parent, (Node) object);
        }
    }

    protected void testParentRelationship(Element element) {
        testParentRelationship(element, element.attributes());
        testParentRelationship(element, element.content());
    }

    protected void testXPathNode(Element parent, Node node) {
        if (node.supportsParent()) {
            log("Node: " + node);
            log("Parent: " + parent);
            log("getParent(): " + node.getParent());

            assertTrue("getParent() returns parent for: " + node, node
                    .getParent() == parent);
        } else {
            // lets create an XPath node
            Node xpathNode = node.asXPathResult(parent);
            assertTrue("XPath Node supports parent for: " + xpathNode,
                    xpathNode.supportsParent());
            assertTrue("getParent() returns parent for: " + xpathNode,
                    xpathNode.getParent() == parent);
        }
    }
}

