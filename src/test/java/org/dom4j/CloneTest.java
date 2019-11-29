

package org.dom4j;

import org.dom4j.dom.DOMDocument;
import org.dom4j.dom.DOMDocumentFactory;
import org.dom4j.util.NodeComparator;

import java.util.Comparator;

/**
 * A test harness to test the clone() methods on Nodes
 * 
 * @author <a href="mailto:jstrachan@apache.org">James Strachan </a>
 * @version $Revision: 1.6 $
 */
public class CloneTest extends AbstractTestCase {
    private Comparator comparator = new NodeComparator();


    public void testBug1148333() {
        DOMDocumentFactory factory = (DOMDocumentFactory) DOMDocumentFactory
                .getInstance();
        DOMDocument doc = (DOMDocument) factory.createDocument();
        Element el = doc.addElement("root");
        el.addNamespace("pref2", "uri2");

        DOMDocument clone = (DOMDocument) doc.cloneNode(true);
        
        assertNotSame(doc, clone);
        assertNodesEqual(doc, clone);
    }

    public void testElementWithNamespaceClone() {
        Element element = DocumentFactory.getInstance()
                .createElement("element");
        element.addNamespace("prefix", "uri");
        Element clone = (Element) element.clone();

        assertNotSame(element, clone);
        assertNodesEqual(element, clone);
    }

    public void testDocumentClone() throws Exception {
        document.setName("doc1");

        Document doc2 = (Document) document.clone();

        assertNotSame(document, doc2);
        assertNodesEqual(document, doc2);
    }

    public void testAddCloneToOtherElement() {
        DocumentFactory factory = DocumentFactory.getInstance();
        Document doc = factory.createDocument();
        Element root = doc.addElement("root");
        Element parent1 = root.addElement("parent");
        Element child1 = parent1.addElement("child");

        Element parent2 = (Element) parent1.clone();
        root.add(parent2);

        assertSame("parent not correct", root, parent2.getParent());
        assertSame("document not correct", doc, parent2.getDocument());

        Element child2 = parent2.element("child");

        assertNotSame("child not cloned", child1, child2);
        assertSame("parent not correct", parent2, child2.getParent());
        assertSame("document not correct", doc, child2.getDocument());
    }

    public void testRootElementClone() throws Exception {
        testElementClone(document.getRootElement());
    }

    public void testAuthorElementClone() throws Exception {
        testElementClone((Element) document.selectSingleNode("//author"));
    }

    public void testRootCompare1() throws Exception {
        Document doc2 = (Document) document.clone();
        Element author = doc2.getRootElement();
        author.addAttribute("foo", "bar");

        assertTrue("Documents are not equal", comparator
                .compare(document, doc2) != 0);
    }

    public void testRootCompare2() throws Exception {
        Document doc2 = (Document) document.clone();
        Element author = doc2.getRootElement();

        author.addText("foo");

        assertTrue("Documents are not equal", comparator
                .compare(document, doc2) != 0);
    }

    public void testAuthorCompare1() throws Exception {
        Document doc2 = (Document) document.clone();
        Element author = (Element) doc2.selectSingleNode("//author");
        author.addAttribute("name", "James Strachan");

        assertTrue("Documents are not equal", comparator
                .compare(document, doc2) != 0);
    }

    public void testAuthorCompare2() throws Exception {
        Document doc2 = (Document) document.clone();
        Element author = (Element) doc2.selectSingleNode("//author");

        author.addText("foo");

        assertTrue("Documents are not equal", comparator
                .compare(document, doc2) != 0);
    }

    protected void testElementClone(Element element) throws Exception {
        Element element2 = (Element) element.clone();

        assertTrue("Returned a new Element", element2 != element);
        assertNull("New element has no parent", element2.getParent());
        assertNull("New element has no Document", element2.getDocument());

        assertTrue("Element fragments are equal", comparator.compare(element,
                element2) == 0);
    }
}

