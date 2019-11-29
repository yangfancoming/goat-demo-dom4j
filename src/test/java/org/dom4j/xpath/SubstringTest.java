

package org.dom4j.xpath;

import org.dom4j.AbstractTestCase;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.testng.annotations.BeforeTest;

import java.io.File;
import java.util.List;

/**
 * Test harness for the substring function
 * 
 * @author <a href="mailto:james.strachan@metastuff.com">James Strachan </a>
 * @version $Revision: 1.3 $
 */
public class SubstringTest extends AbstractTestCase {

    public void testSubstring() throws Exception {
        String[] results1 = {"1100", "1101"};

        testSubstring("//field[substring(@id,1,2)='11']", results1);

        String[] results2 = {"2111", "3111"};
        testSubstring("//field[substring(@id,3)='11']", results2);
    }

    // Implementation methods
    // -------------------------------------------------------------------------
    protected void testSubstring(String path, String[] results)
            throws Exception {
        log("Using XPath: " + path);

        List list = document.selectNodes(path);

        log("Found: " + list);

        // Object object = list.get(0);
        // log( "(0) = " + object + " type: " + object.getClass() );
        int size = results.length;
        assertTrue("List should contain " + size + " results: " + list, list
                .size() == size);

        for (int i = 0; i < size; i++) {
            Element element = (Element) list.get(i);
            assertEquals(element.attributeValue("id"), results[i]);
        }
    }

    @BeforeTest
    public void setUp() throws Exception {
        super.setUp();
        document = new SAXReader().read(new File("xml/test/fields.xml"));
    }
}

