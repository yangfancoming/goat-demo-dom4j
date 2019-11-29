

package org.dom4j.xpath;

import org.dom4j.AbstractTestCase;
import org.dom4j.DocumentHelper;
import org.dom4j.Node;
import org.dom4j.XPath;
import org.jaxen.SimpleVariableContext;
import org.testng.annotations.BeforeClass;

import java.util.List;

/**
 * Test harness for the valueOf() function
 * 
 * @author <a href="mailto:james.strachan@metastuff.com">James Strachan </a>
 * @version $Revision: 1.4 $
 */
public class VariableTest extends AbstractTestCase {
    // TODO: uncomment these if jaxen bug is fixed
    // http://jira.codehaus.org/browse/JAXEN-73
    protected static String[] paths = {
            "$author",
//            "$author/@name",
//            "$root/author",
//            "$root/author[1]",
//            "$root/author[1]/@name",
//            "$author/@name"
        };

    private SimpleVariableContext variableContext = new SimpleVariableContext();

    private Node rootNode;

    private Node authorNode;


    public void testXPaths() throws Exception {
        for (String path : paths) {
            testXPath(path);
        }
    }

    protected void testXPath(String xpathText) {
        XPath xpath = createXPath(xpathText);
        List<Node> list = xpath.selectNodes(document);

        log("Searched path: " + xpathText + " found: " + list.size()
                + " result(s)");

        assertTrue("Results should not contain the root node", !list
                .contains(rootNode));
    }

    protected XPath createXPath(String xpath) {
        return DocumentHelper.createXPath(xpath, variableContext);
    }

    @BeforeClass
    public void setUp() throws Exception {
        super.setUp();

        rootNode = document.selectSingleNode("/root");
        authorNode = document.selectSingleNode("/root/author[1]");

        variableContext.setVariableValue("root", rootNode);
        variableContext.setVariableValue("author", authorNode);
    }
}

