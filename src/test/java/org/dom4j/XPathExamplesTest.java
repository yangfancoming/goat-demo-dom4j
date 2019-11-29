

package org.dom4j;

import org.dom4j.io.SAXReader;
import org.dom4j.rule.Pattern;

import java.util.Iterator;
import java.util.List;

/**
 * Performs a number of unit test cases on the XPath engine
 * 
 * @author <a href="mailto:jstrachan@apache.org">James Strachan </a>
 * @version $Revision: 1.5 $
 */
public class XPathExamplesTest extends AbstractTestCase {
    protected SAXReader xmlReader = new SAXReader();

    /** The document on which the tests are being run */
    protected Document testDocument;

    /** The context node on which the tests are being run */
    protected Node testContext;

    /** factory for XPath, Patterns and nodes */
    protected DocumentFactory factory = DocumentFactory.getInstance();


    public void testXPaths() throws Exception {
        Document document = getDocument("/xml/test/xpath/tests.xml");
        Element root = document.getRootElement();
        Iterator iter = root.elementIterator("document");
        while (iter.hasNext()) {
            Element documentTest = (Element) iter.next();
            testDocument(documentTest);
        }
    }

    // Implementation methods
    // -------------------------------------------------------------------------
    protected void testDocument(Element documentTest) throws Exception {
        String url = documentTest.attributeValue("url");
        testDocument = xmlReader.read(getFile(url));
        assertTrue("Loaded test document: " + url, testDocument != null);

        log("Loaded document: " + url);

        for (Iterator iter = documentTest.elementIterator("context"); iter
                .hasNext();) {
            Element context = (Element) iter.next();
            testContext(documentTest, context);
        }
    }

    protected void testContext(Element documentTest, Element context)
            throws Exception {
        String xpath = context.attributeValue("select");

        List list = testDocument.selectNodes(xpath);

        assertTrue("Found at least one context nodes to test for path: "
                + xpath, (list != null) && (list.size() > 0));

        for (Iterator iter = list.iterator(); iter.hasNext();) {
            Object object = iter.next();
            assertTrue("Context node is a Node: " + object,
                    object instanceof Node);
            testContext = (Node) object;

            log("Context is now: " + testContext);
            runTests(documentTest, context);
            log("");
        }
    }

    protected void runTests(Element documentTest, Element context)
            throws Exception {
        for (Iterator iter = context.elementIterator("test"); iter.hasNext();) {
            Element test = (Element) iter.next();
            runTest(documentTest, context, test);
        }

        for (Iterator it = context.elementIterator("valueOf"); it.hasNext();) {
            Element valueOf = (Element) it.next();
            testValueOf(documentTest, context, valueOf);
        }

        for (Iterator it = context.elementIterator("pattern"); it.hasNext();) {
            Element pattern = (Element) it.next();
            testPattern(documentTest, context, pattern);
        }

        Iterator it = context.elementIterator("fileter");

        while (it.hasNext()) {
            Element filter = (Element) it.next();
            testFilter(documentTest, context, filter);
        }
    }

    protected void runTest(Element documentTest, Element context, Element test)
            throws Exception {
        String xpath = test.attributeValue("select");

        String description = "Path: " + xpath;

        String exception = test.attributeValue("exception");
        if ((exception != null) && exception.equals("true")) {
            try {
                testContext.selectNodes(xpath);
                fail("Exception was not thrown");
            } catch (XPathException e) {
                return;
            }
        }
        
        String count = test.attributeValue("count");

        if (count != null) {
            int expectedSize = Integer.parseInt(count);
            List results = testContext.selectNodes(xpath);

            log(description + " found result size: " + results.size());

            assertEquals(description + " wrong result size", expectedSize,
                    results.size());
        }

        Element valueOf = test.element("valueOf");

        if (valueOf != null) {
            Node node = testContext.selectSingleNode(xpath);
            assertTrue(description + " found node", node != null);

            String expected = valueOf.getText();
            String result = node.valueOf(valueOf.attributeValue("select"));

            log(description);
            log("\texpected: " + expected + " result: " + result);

            assertEquals(description, expected, result);
        }
    }

    protected void testValueOf(Element documentTest, Element context,
            Element valueOf) throws Exception {
        String xpath = valueOf.attributeValue("select");
        String description = "valueOf: " + xpath;

        String expected = valueOf.getText();
        String result = testContext.valueOf(xpath);

        log(description);
        log("\texpected: " + expected + " result: " + result);

        assertEquals(description, expected, result);
    }

    protected void testPattern(Element documentTest, Element context,
            Element patternElement) throws Exception {
        String match = patternElement.attributeValue("match");
        String description = "match: " + match;

        log("");
        log(description);

        Pattern pattern = factory.createPattern(match);

        assertTrue(description, pattern.matches(testContext));
    }

    protected void testFilter(Element documentTest, Element context,
            Element pattern) throws Exception {
        String match = pattern.attributeValue("match");
        String description = "match: " + match;

        assertTrue(description, testContext.matches(match));
    }
}

