

package org.dom4j;

import org.dom4j.tree.DefaultElement;
import org.dom4j.xpath.DefaultXPath;

import java.util.List;

/**
 * A test harness to test XPath expression evaluation in DOM4J
 * 
 * @author <a href="mailto:james.strachan@metastuff.com">James Strachan </a>
 * @version $Revision: 1.4 $
 */
public class XPathTest extends AbstractTestCase {
    protected static String[] paths = {
            ".",
            "*",
            "/",
            "/.",
            "/*",
            "/node()",
            "/child::node()",
            "/self::node()",
            "root",
            "/root",
            "/root/author",
            "text()",
            "//author",
            "//author/text()",
            "//@location",
            "//attribute::*",
            "//namespace::*",
            "normalize-space(/root)",
            "//author[@location]",
            "//author[@location='UK']",
            "root|author",
            "//*[.='James Strachan']",
            "//root/author[1]",
            "normalize-space(/root/author)",
            "normalize-space(' a  b  c  d ')",
            "//root|//author[1]|//author[2]",
            "//root/author[2]",
            "//root/author[3]"};


    public void testBug1116471() throws Exception {
        String xml = "<a><b>Water T &amp; D-46816</b></a>";
        String expected = "Water T & D-46816";

        Document doc = DocumentHelper.parseText(xml);
        String result = (String) doc.selectObject("string(a/b[1])");
        
        assertEquals("xpath result not correct", expected, result);
        
        Node node = doc.selectSingleNode("a/b");
        String result2 = node.getStringValue();
        
        assertEquals("xpath result not correct", expected, result2);        
    }
    
    public void testXPaths() {
        int size = paths.length;
        for (int i = 0; i < size; i++) {
            testXPath(paths[i]);
        }
    }

    public void testCreateXPathBug()  {
        Element element = new DefaultElement("foo");
        XPath xpath = element.createXPath("//bar");
        assertTrue(("created a valid XPath: " + xpath) != null);
    }

    public void testBug857704() throws Exception {
        Document doc = DocumentHelper.parseText("<foo xmlns:bar='http://blort'/>");
        doc.selectNodes("//*[preceding-sibling::*]"); // shouldn't throw NPE
    }

    public void testBooleanValueOf() throws Exception {
        Document doc = DocumentHelper.parseText("<root><foo>blah</foo></root>");

        XPath path = new DefaultXPath("//root");
        assertTrue(path.booleanValueOf(doc));

        path = new DefaultXPath("//root2");
        assertFalse(path.booleanValueOf(doc));
    }

    // Implementation methods
    // -------------------------------------------------------------------------
    protected void testXPath(String xpathExpression) {
        log("Searched path: " + xpathExpression);

        XPath xpath = DocumentHelper.createXPath(xpathExpression);

        List list = xpath.selectNodes(document);

        if (list == null) {
            log("null");
        } else {
            log("[");

            for (int i = 0, size = list.size(); i < size; i++) {
                Object object = list.get(i);

                String text = "null";

                if (object instanceof Node) {
                    Node node = (Node) object;

                    text = node.asXML();
                } else if (object != null) {
                    text = object.toString();
                }

                log("    " + text);
            }

            log("]");
        }

        log("...........................................");
    }
}

