

package org.dom4j;

/**
 * Tests the use of null attribute values
 * 
 * @author <a href="mailto:jstrachan@apache.org">James Strachan </a>
 * @version $Revision: 1.3 $
 */
public class NullAttributesTest extends AbstractTestCase {
    protected DocumentFactory factory = DocumentFactory.getInstance();
    protected Document document = factory.createDocument();
    protected Element element = document.addElement("root");


    public void testStringNames()  {
        element.addAttribute("foo", null);

        Attribute attribute = element.attribute("foo");
        assertTrue(attribute == null);

        element.addAttribute("foo", "123");
        attribute = element.attribute("foo");
        assertTrue(attribute != null);

        element.addAttribute("foo", null);
        attribute = element.attribute("foo");
        assertTrue(attribute == null);
    }

    public void testQNames()  {
        QName bar = QName.get("bar");

        element.addAttribute(bar, null);

        Attribute attribute = element.attribute(bar);
        assertTrue(attribute == null);

        element.addAttribute(bar, "123");
        attribute = element.attribute(bar);
        assertTrue(attribute != null);

        element.addAttribute(bar, null);
        attribute = element.attribute(bar);
        assertTrue(attribute == null);
    }

    public void testAttributes()  {
        Attribute attribute = factory.createAttribute(element, "v", null);

        assertTrue(attribute.getText() == null);
        assertTrue(attribute.getValue() == null);

        element.add(attribute);
        attribute = element.attribute("v");
        assertTrue(attribute == null);

        attribute = factory.createAttribute(element, "v", "123");
        element.add(attribute);
        attribute = element.attribute("v");
        assertTrue(attribute != null);

        attribute = factory.createAttribute(element, "v", null);
        element.add(attribute);
        attribute = element.attribute("v");
        assertTrue(attribute == null);
    }
}

