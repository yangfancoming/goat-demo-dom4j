

package org.dom4j;

import org.testng.annotations.BeforeClass;

import java.util.Iterator;
import java.util.List;

/**
 * A test harness to test the Iterator API in DOM4J
 * 
 * @author <a href="mailto:jdoughty@jdoughty@cs.gmu.edu">Jonathan Doughty </a>
 * @version $Revision: 1.4 $
 */
public class IteratorTest extends AbstractTestCase {
    private static final int NUMELE = 10;

    protected Document iterDocument;

    @BeforeClass
    public void setUp() throws Exception {
        super.setUp();
        iterDocument = DocumentHelper.createDocument();

        Element root = iterDocument.addElement("root");

        for (int i = 0; i < NUMELE; i++) {
            root.addElement("iterator-test").addAttribute("instance",
                    Integer.toString(i));
        }
    }


    public void testElementCount() throws Exception {
        Element root = iterDocument.getRootElement();
        assertTrue("Has root element", root != null);

        List elements = root.elements("iterator-test");
        int elementSize = elements.size();
        assertTrue("Root has " + elementSize + " children", (elements != null)
                && (elementSize == NUMELE));
    }

    public void testPlainIteration() throws Exception {
        Element root = iterDocument.getRootElement();
        List elements = root.elements("iterator-test");
        Iterator iter = root.elementIterator("iterator-test");
        int elementSize = elements.size();

        int count = 0;

        for (; iter.hasNext();) {
            Element e = (Element) iter.next();
            assertEquals("instance " + e.attribute("instance").getValue()
                    + " equals " + count, e.attribute("instance").getValue(),
                    Integer.toString(count));
            count++;
        }

        assertTrue(elementSize + " elements iterated", count == elementSize);
    }

    public void testSkipAlternates() throws Exception {
        Element root = iterDocument.getRootElement();
        List elements = root.elements("iterator-test");
        Iterator iter = root.elementIterator("iterator-test");
        int elementSize = elements.size();
        int count = 0;

        for (; iter.hasNext();) {
            Element e = (Element) iter.next();
            assertEquals("instance " + e.attribute("instance").getValue()
                    + " equals " + (count * 2), e.attribute("instance")
                    .getValue(), Integer.toString(count * 2));
            iter.next();
            count++;
        }

        assertTrue((elementSize / 2) + " alternate elements iterated",
                count == (elementSize / 2));
    }

    public void testNoHasNext() throws Exception {
        Element root = iterDocument.getRootElement();
        List elements = root.elements("iterator-test");
        Iterator iter = root.elementIterator("iterator-test");
        int elementSize = elements.size();
        int count = 0;
        Element e = null;

        for (; count < elementSize;) {
            e = (Element) iter.next();
            assertEquals("instance " + e.attribute("instance").getValue()
                    + " equals " + count, e.attribute("instance").getValue(),
                    Integer.toString(count));
            System.out.println("instance " + e.attribute("instance").getValue()
                    + " equals " + count);
            count++;
        }

        try {
            e = (Element) iter.next();

            if (e != null) {
                // Real Iterators wouldn't get here
                assertTrue("no more elements,value instead is "
                        + e.attribute("instance").getValue(), e == null);
            }
        } catch (Exception exp) {
            assertTrue("Real iterators throw NoSuchElementException",
                    exp instanceof java.util.NoSuchElementException);
        }
    }

    public void testExtraHasNexts() throws Exception {
        Element root = iterDocument.getRootElement();
        List elements = root.elements("iterator-test");
        Iterator iter = root.elementIterator("iterator-test");
        int elementSize = elements.size();
        int count = 0;

        for (; iter.hasNext();) {
            Element e = (Element) iter.next();
            assertEquals("instance " + e.attribute("instance").getValue()
                    + " equals " + count, e.attribute("instance").getValue(),
                    Integer.toString(count));
            iter.hasNext();
            count++;
        }

        assertTrue(elementSize + " elements iterated with extra hasNexts",
                count == elementSize);
    }
}

