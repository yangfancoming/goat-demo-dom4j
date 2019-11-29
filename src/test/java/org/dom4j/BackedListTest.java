

package org.dom4j;

import org.dom4j.io.XMLWriter;

import java.util.List;

/**
 * A test harness to test the backed list feature of DOM4J
 * 
 * @author <a href="mailto:james.strachan@metastuff.com">James Strachan </a>
 * @version $Revision: 1.3 $
 */
public class BackedListTest extends AbstractTestCase {

    public void testXPaths() throws Exception {
        Element element = (Element) document.selectSingleNode("/root");
        mutate(element);
        element = (Element) document.selectSingleNode("//author");
        mutate(element);
    }

    public void testAddRemove() throws Exception {
        Element parentElement = (Element) document.selectSingleNode("/root");
        List children = parentElement.elements();
        int lastPos = children.size() - 1;
        Element child = (Element) children.get(lastPos);

        try {
            // should throw an exception cause we cannot add same child twice
            children.add(0, child);
            fail();
        } catch (IllegalAddException e) {
        }
    }

    public void testAddWithIndex() throws Exception {
        DocumentFactory factory = DocumentFactory.getInstance();

        Element root = (Element) document.selectSingleNode("/root");
        List children = root.elements(); // return a list of 2 author
        // elements

        assertEquals(2, children.size());

        children.add(1, factory.createElement("dummy1"));
        children = root.elements();

        assertEquals(3, children.size());

        children = root.elements("author");

        assertEquals(2, children.size());

        children.add(1, factory.createElement("dummy2"));

        children = root.elements();

        assertEquals(4, children.size());
        assertEquals("dummy1", ((Node) children.get(1)).getName());
        assertEquals("dummy2", ((Node) children.get(2)).getName());

        /*
         * Some tests for issue reported at http://tinyurl.com/4jxrc
         */
        children.add(children.size(), factory.createElement("dummy3"));
        children = root.elements("author");
        children.add(children.size(), factory.createElement("dummy4"));
    }

    // Implementation methods
    // -------------------------------------------------------------------------
    protected void mutate(Element element) throws Exception {
        DocumentFactory factory = DocumentFactory.getInstance();

        List list = element.elements();
        list.add(factory.createElement("last"));
        list.add(0, factory.createElement("first"));

        List list2 = element.elements();

        assertTrue("Both lists should contain same number of elements", list
                .size() == list2.size());

        XMLWriter writer = new XMLWriter(System.out);

        log("Element content is now: " + element.content());
        writer.write(element);
    }
}

