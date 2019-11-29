

package org.dom4j.xpath;

import org.dom4j.AbstractTestCase;
import org.dom4j.Text;

import java.util.Iterator;
import java.util.List;

/**
 * Test harness for the text() function
 * 
 * @author <a href="mailto:james.strachan@metastuff.com">James Strachan </a>
 * @version $Revision: 1.3 $
 */
public class TextTest extends AbstractTestCase {
    protected static String[] paths = {"text()", "//author/text()"};


    public void testXPaths() throws Exception {
        int size = paths.length;

        for (int i = 0; i < size; i++) {
            testXPath(paths[i]);
        }
    }

    // Implementation methods
    // -------------------------------------------------------------------------
    protected void testXPath(String xpath) {
        List list = document.selectNodes(xpath);

        for (Iterator iter = list.iterator(); iter.hasNext();) {
            Object object = iter.next();

            log("Found Result: " + object);

            assertTrue("Results not Text objects", object instanceof Text);

            Text text = (Text) object;

            assertTrue("Results should support the parent relationship", text
                    .supportsParent());
            assertTrue(
                    "Results should contain reference to the parent element",
                    text.getParent() != null);
            assertTrue("Results should not reference to the owning document",
                    text.getDocument() != null);
        }
    }
}

