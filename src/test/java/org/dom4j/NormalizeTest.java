

package org.dom4j;

import org.testng.annotations.BeforeClass;

/**
 * A test harness for the normalize() method
 * 
 * @author <a href="mailto:jstrachan@apache.org">James Strachan </a>
 * @version $Revision: 1.3 $
 */
public class NormalizeTest extends AbstractTestCase {

    public void testNormalize() throws Exception {
        String text = document.asXML();

        document.normalize();

        String normalizedText = document.asXML();

        log("Initial: " + text);
        log("Normalized: " + normalizedText);

        String value = document.valueOf("/dummy/full");
        assertEquals("Should not trim text", " node ", value);
    }

    @BeforeClass
    public void setUp() throws Exception {
        super.setUp();

        String xml = "<dummy> <full> node </full> with text "
                + "<and>another node</and> </dummy>";
        document = DocumentHelper.parseText(xml);
    }
}

