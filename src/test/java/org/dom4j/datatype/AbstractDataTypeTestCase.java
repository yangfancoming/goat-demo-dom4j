

package org.dom4j.datatype;

import org.dom4j.AbstractTestCase;
import org.dom4j.Attribute;
import org.dom4j.Element;
import org.dom4j.Node;

import java.util.List;

/**
 * Abstract base class useful for implementation inheritence for testing XML
 * Schema Data Type integration.
 * 
 * @author <a href="mailto:jstrachan@apache.org">James Strachan </a>
 * @version $Revision: 1.3 $
 */
public class AbstractDataTypeTestCase extends AbstractTestCase {
    // Implementation methods
    // -------------------------------------------------------------------------
    protected void testNodes(String xpath, Class type) {

        List<Node> list = document.selectNodes(xpath);
        assertTrue("Results are not empty", !list.isEmpty());
        for (Node node : list) {
            if (node instanceof Element) {
                Element element = (Element) node;
                testDataType(element, element.getData(), type);
            } else if (node instanceof Attribute) {
                Attribute attribute = (Attribute) node;
                testDataType(attribute, attribute.getData(), type);
            } else {
                assertTrue("Did not find an attribute or element: " + node,false);
            }
        }
    }

    protected void testDataType(Node node, Object data, Class type) {
        assertTrue("Data object is not null", data != null);
        assertTrue("Data object is of the correct type. Expected: " + type.getName() + " and found: " + data.getClass().getName(),type.isAssignableFrom(data.getClass()));
    }
}

