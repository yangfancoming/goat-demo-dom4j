

package org.dom4j.xpath;

import org.dom4j.AbstractTestCase;
import org.dom4j.DocumentHelper;
import org.dom4j.XPath;
import org.dom4j.io.SAXReader;
import org.jaxen.SimpleNamespaceContext;
import org.testng.annotations.BeforeClass;

import java.io.File;
import java.util.List;

/**
 * Tests finding items using a namespace prefix
 * 
 * @author <a href="mailto:jstrachan@apache.org">James Strachan </a>
 * @version $Revision: 1.3 $
 */
public class PrefixTest extends AbstractTestCase {
    protected static String[] paths = {"//xplt:anyElement", "//xpl:insertText",
            "/Template/Application1/xpl:insertText",
            "/Template/Application2/xpl:insertText"};


    public void testXPaths() throws Exception {
        int size = paths.length;

        for (int i = 0; i < size; i++) {
            testXPath(paths[i]);
        }
    }

    // Implementation methods
    // -------------------------------------------------------------------------
    protected void testXPath(String xpathText) {
        XPath xpath = DocumentHelper.createXPath(xpathText);

        SimpleNamespaceContext context = new SimpleNamespaceContext();
        context.addNamespace("xplt", "www.xxxx.com");
        context.addNamespace("xpl", "www.xxxx.com");
        xpath.setNamespaceContext(context);

        List list = xpath.selectNodes(document);

        log("Searched path: " + xpathText + " found: " + list.size()
                + " result(s)");

        assertTrue("Should have found at lest one result", list.size() > 0);
    }

    @BeforeClass
    public void setUp() throws Exception {
        super.setUp();
        document = new SAXReader().read(new File("xml/testNamespaces.xml"));
    }
}

