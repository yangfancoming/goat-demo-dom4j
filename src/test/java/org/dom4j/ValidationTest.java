

package org.dom4j;

import org.dom4j.io.SAXReader;

/**
 * A test harness for validation when using SAXReader
 * 
 * @author <a href="mailto:jstrachan@apache.org">James Strachan </a>
 * @version $Revision: 1.3 $
 */
public class ValidationTest extends AbstractTestCase {

    public void testValidation() throws Exception {
        try {
            SAXReader reader = new SAXReader(true);
            reader.read("test");
            fail();
        } catch (DocumentException e) {
            // internal parser is non validating, so OK
        }
    }
}

