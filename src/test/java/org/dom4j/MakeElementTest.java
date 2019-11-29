

package org.dom4j;

/**
 * A test harness to test the DocumentHelper.makeElement() methodt
 * 
 * @author <a href="mailto:jstrachan@apache.org">James Strachan </a>
 * @version $Revision: 1.3 $
 */
public class MakeElementTest extends AbstractTestCase {

    public void testMakeElement() throws Exception {
        Document doc = DocumentHelper.createDocument();

        Element c = DocumentHelper.makeElement(doc, "a/b/c");
        assertNotNull("Should return a valid element", c);

        Element c2 = DocumentHelper.makeElement(doc, "a/b/c");

        assertSame("Found same element again", c, c2);

        c.addAttribute("x", "123");

        Node found = doc.selectSingleNode("/a/b/c[@x='123']");

        assertEquals("Found same node via XPath", c, found);

        Element b = c.getParent();

        Element e = DocumentHelper.makeElement(b, "c/d/e");

        assertNotNull("Should return a valid element", e);

        Element e2 = DocumentHelper.makeElement(b, "c/d/e");

        assertSame("Found same element again", e, e2);

        e.addAttribute("y", "456");

        found = b.selectSingleNode("c/d/e[@y='456']");

        assertEquals("Found same node via XPath", e, found);
    }

    public void testMakeQualifiedElement() throws Exception {
        Document doc = DocumentHelper.createDocument();
        Element root = doc.addElement("root");
        root.addNamespace("", "defaultURI");
        root.addNamespace("foo", "fooURI");
        root.addNamespace("bar", "barURI");

        Element c = DocumentHelper.makeElement(doc, "root/foo:b/bar:c");
        assertNotNull("Should return a valid element", c);

        assertEquals("c has a valid namespace", "barURI", c.getNamespaceURI());

        Element b = c.getParent();

        assertEquals("b has a valid namespace", "fooURI", b.getNamespaceURI());

        log("Created: " + c);

        Element c2 = DocumentHelper.makeElement(doc, "root/foo:b/bar:c");
        assertSame("Found same element again", c, c2);
    }
}

