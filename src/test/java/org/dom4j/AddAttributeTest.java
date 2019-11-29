

package org.dom4j;

/**
 * A test harness to test the addAttribute() methods on attributes
 * @author <a href="mailto:maartenc@users.sourceforge.net">Maarten Coene </a>
 */
public class AddAttributeTest extends AbstractTestCase {

    public void testAddAttributeNormalValue()  {
        String testAttributeName = "testAtt";
        String testAttributeValue = "testValue";

        Node authorNode = document.selectSingleNode("//root/author[1]");

        assertTrue(authorNode instanceof Element);

        Element authorEl = (Element) authorNode;
        authorEl.addAttribute(testAttributeName, testAttributeValue);

        assertEquals(3, authorEl.attributeCount());
        assertEquals(testAttributeValue, authorEl.attributeValue(testAttributeName));
    }

    public void testAddAttributeNullValue()  {
        String testAttributeName = "location";
        String testAttributeValue = null;

        Node authorNode = document.selectSingleNode("//root/author[1]");

        assertTrue(authorNode instanceof Element);

        Element authorEl = (Element) authorNode;
        authorEl.addAttribute(testAttributeName, testAttributeValue);

        assertEquals(1, authorEl.attributeCount());
        assertNull(authorEl.attributeValue(testAttributeName));
    }
}

