

package org.dom4j;

/**
 * A test harness to test the parent relationship and use of the {@link
 * Node#asXPathResult} method.
 * 
 * @author <a href="mailto:james.strachan@metastuff.com">James Strachan </a>
 * @version $Revision: 1.3 $
 */
public class IsTextOnlyTest extends AbstractTestCase {

    public void testDocument() throws Exception {
        DocumentFactory factory = new DocumentFactory();
        Element root = factory.createElement("root");
        Element first = root.addElement("child");
        first.addText("This is some text");

        assertTrue("Root node is not text only: " + root, !root.isTextOnly());
        assertTrue("First child is text only: " + first, first.isTextOnly());
    }
}

