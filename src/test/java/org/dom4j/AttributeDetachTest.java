

package org.dom4j;

import java.util.Iterator;
import java.util.List;

/**
 * A test harness to test the detach() method on attributes
 * 
 * @author <a href="mailto:james.strachan@metastuff.com">James Strachan </a>
 * @version $Revision: 1.3 $
 */
public class AttributeDetachTest extends AbstractTestCase {

    public void testDetachAttribute() throws Exception {
        List attributes = document.selectNodes("//@name");

        assertTrue("Found more than one attribute: ", attributes.size() > 0);

        for (Iterator iter = attributes.iterator(); iter.hasNext();) {
            Attribute attribute = (Attribute) iter.next();
            Element element = attribute.getParent();

            assertTrue("Attribute: " + attribute + " has parent: " + element,
                    attribute.getParent() == element);

            QName qname = attribute.getQName();

            Attribute attribute2 = element.attribute(qname);

            String value = attribute.getValue();
            String value2 = element.attributeValue(qname);

            assertEquals("Attribute and Element have same attrbute value",
                    value, value2);

            attribute.detach();

            attribute2 = element.attribute(qname);
            value2 = element.attributeValue(qname);

            assertTrue("Element now has no value: " + value2, value2 == null);
            assertTrue("Element now has no attribute: " + attribute2,
                    attribute2 == null);
        }
    }
}

