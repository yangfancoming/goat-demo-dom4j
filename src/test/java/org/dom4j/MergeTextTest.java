

package org.dom4j;

import org.dom4j.io.SAXReader;

import java.util.Iterator;

/**
 * A test harness for SAXReader option setMergeAdjacentText(true)
 * 
 * @author <a href="mailto:slehmann@novell.com">Steen Lehmann </a>
 * @version $Revision: 1.4 $
 */
public class MergeTextTest extends AbstractTestCase {
    /** Input XML file to read */
    private static final String INPUT_XML_FILE = "/xml/test/mergetext.xml";


    public void testNoAdjacentText() throws Exception {
        // After reading using SAXReader with mergeAdjacentText true,
        // no two Text objects should be adjacent to each other in the
        // document.
        SAXReader reader = new SAXReader();
        reader.setMergeAdjacentText(true);

        Document document = getDocument(INPUT_XML_FILE, reader);

        checkNoAdjacent(document.getRootElement());
        log("No adjacent Text nodes in " + document.asXML());
    }

    // Implementation methods
    // -------------------------------------------------------------------------
    private void checkNoAdjacent(Element parent) {
        // Check that no two Text nodes are adjacent in the parent's content
        Node prev = null;
        Iterator iter = parent.nodeIterator();

        while (iter.hasNext()) {
            Node n = (Node) iter.next();

            if (n instanceof Text && ((prev != null) && prev instanceof Text)) {
                fail("Node: " + n + " is text and so is its "
                        + "preceding sibling: " + prev);
            } else if (n instanceof Element) {
                checkNoAdjacent((Element) n);
            }

            prev = n;
        }
    }
}

