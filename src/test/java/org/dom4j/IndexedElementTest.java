

package org.dom4j;

import org.dom4j.util.IndexedDocumentFactory;

import java.util.List;

/**
 * A test harness for the IndexedElement implementation
 * 
 * @author <a href="mailto:jstrachan@apache.org">James Strachan </a>
 * @version $Revision: 1.3 $
 */
public class IndexedElementTest extends AbstractTestCase {

    public void testXPaths() throws Exception {
        testXPath("//author");
    }

    // Implementation methods
    // -------------------------------------------------------------------------
    protected void testXPath(String xpath) {
        List list = document.selectNodes(xpath);

        log("Searched path: " + xpath);
        log("Found        : " + list.size() + " result(s)");

        log("Results");

        if (list == null) {
            log("null");
        } else {
            log("[");

            for (int i = 0, size = list.size(); i < size; i++) {
                Object object = list.get(i);
                String text = "null";

                if (object instanceof Node) {
                    Node node = (Node) object;
                    text = node.asXML();
                } else if (object != null) {
                    text = object.toString();
                }

                log("    " + text);
            }

            log("]");
        }

        log("...........................................");

        assertTrue("Found some results", list.size() > 0);
    }

    protected Document createDocument() {
        return IndexedDocumentFactory.getInstance().createDocument();
    }
}

