

package org.dom4j.rule;

import org.dom4j.AbstractTestCase;
import org.dom4j.DocumentFactory;

/**
 * Tests the priority behaviour of Pattern.
 * 
 * @author <a href="mailto:jstrachan@apache.org">James Strachan </a>
 * @version $Revision: 1.3 $
 */
public class PriorityTest extends AbstractTestCase {
    public void testNameNode() throws Exception {
        testPriority("foo", 0);
    }

    public void testFilter() throws Exception {
        testPriority("foo[@id='123']", 0.5);
    }

    public void testURI() throws Exception {
        testPriority("foo:*", -0.25);
    }

    public void testAnyNode() throws Exception {
        testPriority("*", -0.5);
    }

    protected void testPriority(String expr, double priority) throws Exception {
        System.out.println("parsing: " + expr);

        Pattern pattern = DocumentFactory.getInstance().createPattern(expr);
        double d = pattern.getPriority();

        System.out.println("expr: " + expr + " has priority: " + d);
        System.out.println("pattern: " + pattern);

        assertEquals("expr: " + expr, new Double(priority), new Double(d));
    }
}

