

package org.dom4j;

/**
 * A test harness to test the detach() method on root elements
 * 
 * @author <a href="mailto:james.strachan@metastuff.com">James Strachan </a>
 * @version $Revision: 1.3 $
 */
public class DetachTest extends AbstractTestCase {

    public void testRoot() throws Exception {
        document.setName("doc1");

        Element root = document.getRootElement();
        assertTrue("Has root element", root != null);
        assertTrue("Root has no parent", root.getParent() == null);

        root.detach();

        assertTrue("Detached root now has no document",
                root.getDocument() == null);
        assertTrue("Original doc now has no root element", document
                .getRootElement() == null);

        Document doc2 = DocumentHelper.createDocument();
        doc2.setName("doc2");

        assertTrue("Doc2 has no root element", doc2.getRootElement() == null);

        doc2.setRootElement(root);

        assertTrue("Doc2 has now has root element",
                doc2.getRootElement() == root);
        assertTrue("Root element now has document", root.getDocument() == doc2);

        Document doc3 = DocumentHelper.createDocument();
        doc3.setName("doc3");
        doc3.addElement("foo");

        assertTrue("Doc3 has root element", doc3.getRootElement() != null);

        root = doc2.getRootElement();
        root.detach();
        doc3.setRootElement(root);

        assertTrue("Doc3 now has root element", doc3.getRootElement() == root);
        assertSame("Root element now has a document", root.getDocument(), doc3);
        assertTrue("Doc2 has no root element", doc2.getRootElement() == null);
    }
}

