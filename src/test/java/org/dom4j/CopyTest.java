

package org.dom4j;

import java.util.List;

/**
 * A test harness to test the copy() methods on Element
 * 
 * @author <a href="mailto:james.strachan@metastuff.com">James Strachan </a>
 * @version $Revision: 1.3 $
 */
public class CopyTest extends AbstractTestCase {

    public void testRoot(){
        document.setName("doc1");
        Element root = document.getRootElement();
        List authors = root.elements("author");
        assertTrue(authors.size() == 2);

        Element author1 = (Element) authors.get(0);
        Element author2 = (Element) authors.get(1);

        testCopy(root);
        testCopy(author1);
        testCopy(author2);
    }

    protected void testCopy(Element element) {
        assertTrue("Not null", element != null);

        int attributeCount = element.attributeCount();
        int nodeCount = element.nodeCount();

        Element copy = element.createCopy();
        assertEquals("Node size not equal after copy", element.nodeCount(),  nodeCount);
        assertTrue("Same attribute size after copy",element.attributeCount() == attributeCount);
        assertTrue("Copy has same node size", copy.nodeCount() == nodeCount);
        assertTrue("Copy has same attribute size",copy.attributeCount() == attributeCount);


        for (int i = 0; i < attributeCount; i++) {
            Attribute attr1 = element.attribute(i);
            Attribute attr2 = copy.attribute(i);
            assertTrue("Attribute: " + i + " name is equal", attr1.getName().equals(attr2.getName()));
            assertTrue("Attribute: " + i + " value is equal", attr1.getValue().equals(attr2.getValue()));
        }

        for (int i = 0; i < nodeCount; i++) {
            Node node1 = element.node(i);
            Node node2 = copy.node(i);
            assertTrue("Node: " + i + " type is equal",node1.getNodeType() == node2.getNodeType());
            assertTrue("Node: " + i + " value is equal", node1.getText().equals(node2.getText()));
        }
    }
}

