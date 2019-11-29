

package org.dom4j;

/**
 * Tests the setContent method
 * 
 * @author <a href="mailto:jstrachan@apache.org">James Strachan </a>
 * @version $Revision: 1.3 $
 */
public class SetContentTest extends AbstractTestCase {

    public void testDocument() throws Exception {
        document.setName("doc1");

        Element oldRoot = document.getRootElement();

        assertTrue("Current root has document",
                oldRoot.getDocument() == document);

        Document doc2 = DocumentHelper.createDocument();
        doc2.setName("doc2");

        assertTrue("Doc2 has no root element", doc2.getRootElement() == null);

        doc2.setContent(document.content());

        Element newRoot = doc2.getRootElement();

        assertTrue("Current root has document",
                oldRoot.getDocument() == document);

        assertTrue("Doc2 has now has root element", newRoot != null);
        assertTrue("Doc2 has different root element", newRoot != oldRoot);
        assertTrue("Root element now has document",
                newRoot.getDocument() == doc2);

        testParent(doc2.getRootElement());
        testDocument(doc2, doc2);

        doc2.clearContent();

        assertTrue("New Doc has no root", doc2.getRootElement() == null);
        assertTrue("New root has no document", newRoot.getDocument() == null);
    }

    public void testRootElement() throws Exception {
        Document doc3 = DocumentHelper.createDocument();
        doc3.setName("doc3");

        Element root = doc3.addElement("root");
        Element oldElement = root.addElement("old");

        assertTrue("Doc3 has root element", root != null);

        root.setContent(document.getRootElement().content());

        assertTrue("Doc3's root now has content", root.nodeCount() > 0);
        assertTrue("Old element has no parent now",
                oldElement.getParent() == null);
        assertTrue("Old element has no document now",
                oldElement.getDocument() == null);

        testParent(root);
        testDocument(root, doc3);
    }

    /**
     * Tests all the children of the branch have the correct parent
     * 
     * @param parent
     *            DOCUMENT ME!
     */
    protected void testParent(Branch parent) {
        for (int i = 0, size = parent.nodeCount(); i < size; i++) {
            Node node = parent.node(i);
            assertTrue("Child node of root has parent of root", node
                    .getParent() == parent);
        }
    }

    /**
     * Tests all the children of the branch have the correct document
     * 
     * @param branch
     *            DOCUMENT ME!
     * @param doc
     *            DOCUMENT ME!
     */
    protected void testDocument(Branch branch, Document doc) {
        for (int i = 0, size = branch.nodeCount(); i < size; i++) {
            Node node = branch.node(i);
            assertTrue("Node has correct document", node.getDocument() == doc);
        }
    }
}

