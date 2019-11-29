

package org.dom4j.xpath;

import org.dom4j.*;
import org.dom4j.io.SAXReader;
import org.testng.annotations.BeforeTest;

import java.io.File;
import java.util.List;

/**
 * Test harness for the namespace axis
 * 
 * @author <a href="mailto:james.strachan@metastuff.com">James Strachan </a>
 * @version $Revision: 1.3 $
 */
public class NamespaceTest extends AbstractTestCase {
    protected static String[] paths = {"namespace::*",
            "/Template/Application1/namespace::*",
            "/Template/Application1/namespace::xplt", "//namespace::*"};


    public void testXPaths() throws Exception {
      for (String path : paths) {
        testXPath(path);
      }
    }

    // Implementation methods
    // -------------------------------------------------------------------------
    protected void testXPath(String xpathText) {
        XPath xpath = DocumentHelper.createXPath(xpathText);
        List<Node> list = xpath.selectNodes(document);

        log("Searched path: " + xpathText + " found: " + list.size()
                + " result(s)");

      for (Node node : list) {
        log("Found Result: " + node);

        assertTrue("Results should be Namespace objects",
                node instanceof Namespace);

        Namespace namespace = (Namespace) node;

        log("Parent node: " + namespace.getParent());

        assertTrue("Results should support the parent relationship",
                namespace.supportsParent());
        assertTrue(
                "Results should contain reference to the parent element",
                namespace.getParent() != null);
        assertTrue("Results should contain reference to the document",
                namespace.getDocument() != null);
      }
    }

  @BeforeTest
    public void setUp() throws Exception {
        super.setUp();
        document = new SAXReader().read(new File("xml/testNamespaces.xml"));
    }
}

