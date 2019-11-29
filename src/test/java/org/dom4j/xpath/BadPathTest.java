

package org.dom4j.xpath;

import org.dom4j.AbstractTestCase;
import org.dom4j.XPath;

/**
 * Tests bad XPath expressions
 * 
 * @author <a href="mailto:jstrachan@apache.org">James Strachan </a>
 * @version $Revision: 1.3 $
 */
public class BadPathTest extends AbstractTestCase {
    private String[] paths = {"+", "/foo/bar/"};


    public void testBadPaths() throws Exception {
        for (int i = 0, size = paths.length; i < size; i++) {
            String path = paths[i];
            testBadPath(path);
        }
    }

    protected void testBadPath(String path) throws Exception {
        try {
            document.selectObject(path);

            fail("Should have thrown exception for: " + path);
        } catch (Exception e) {
            log("Successfully caught: " + e);
        }

        try {
            XPath xpath = document.createXPath(path);

            fail("Should have thrown exception for: " + path);
        } catch (Exception e) {
            log("Successfully caught: " + e);
        }
    }
}

