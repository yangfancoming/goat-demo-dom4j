

package org.dom4j.xpath;

import org.dom4j.*;

import java.util.Iterator;
import java.util.List;

/**
 * Test harness for the GetPath() method
 * 
 * @author <a href="mailto:james.strachan@metastuff.com">James Strachan </a>
 * @version $Revision: 1.4 $
 */
public class GetPathTest extends AbstractTestCase {

    public void testGetPath() throws Exception {
        log("Testing paths");

        // testBranchPath( document );
        testPath(document, "/");

        Element root = document.getRootElement();

        testPath(root, "/root");

        List elements = root.elements();

        testPath((Node) elements.get(0), "/root/author", "/root/author[1]");

        for (int i = 0, size = elements.size(); i < size; i++) {
            String path = "/root/author";
            String uniquePath = "/root/author";
            String pathRel = "author";
            String uniquePathRel = "author";

            if (size > 1) {
                uniquePath = "/root/author[" + (i + 1) + "]";
                uniquePathRel = "author[" + (i + 1) + "]";
            }

            Element element = (Element) elements.get(i);
            testPath(element, path, uniquePath);
            testRelativePath(root, element, pathRel, uniquePathRel);

            Attribute attribute = element.attribute("name");
            testPath(attribute, path + "/@name", uniquePath + "/@name");
            testRelativePath(root, attribute, pathRel + "/@name", uniquePathRel
                    + "/@name");

            Element child = element.element("url");
            testPath(child, path + "/url", uniquePath + "/url");
            testRelativePath(root, child, pathRel + "/url", uniquePathRel
                    + "/url");
        }
    }

    public void testDefaultNamespace() throws Exception {
        Document doc = getDocument("/xml/test/defaultNamespace.xml");
        Element root = doc.getRootElement();
        testPath(root, "/*[name()='a']");

        Element child = (Element) root.elements().get(0);
        testPath(child, "/*[name()='a']/*[name()='b']");
        testRelativePath(root, child, "*[name()='b']");
    }

    public void testBug770410() {
        Document doc = DocumentHelper.createDocument();
        Element a = doc.addElement("a");
        Element b = a.addElement("b");
        Element c = b.addElement("c");

        b.detach();

        String relativePath = b.getPath(b);
        assertSame(b, b.selectSingleNode(relativePath));
    }

    public void testBug569927() {
        Document doc = DocumentHelper.createDocument();
        QName elName = DocumentFactory.getInstance().createQName("a", "ns",
                "uri://myuri");
        Element a = doc.addElement(elName);
        QName attName = DocumentFactory.getInstance().createQName("attribute",
                "ns", "uri://myuri");
        a = a.addAttribute(attName, "test");

        Attribute att = a.attribute(attName);

        assertSame(att, doc.selectSingleNode(att.getPath()));
        assertSame(att, doc.selectSingleNode(att.getUniquePath()));
    }

    protected void testPath(Node node, String value) {
        testPath(node, value, value);
    }

    protected void testPath(Node node, String path, String uniquePath) {
        assertEquals("getPath expression should be what is expected", path,
                node.getPath());
        assertEquals("getUniquePath expression should be what is expected",
                uniquePath, node.getUniquePath());
    }

    protected void testRelativePath(Element context, Node node, String path) {
        testRelativePath(context, node, path, path);
    }

    protected void testRelativePath(Element context, Node node, String pathRel,
            String uniquePathRel) {
        assertEquals("relative getPath expression should be what is expected",
                pathRel, node.getPath(context));
        assertEquals("relative getUniquePath expression not correct",
                uniquePathRel, node.getUniquePath(context));
    }

    protected void testBranchPath(Branch branch) {
        testNodePath(branch);

        if (branch instanceof Element) {
            Element element = (Element) branch;

            for (Iterator iter = element.attributeIterator(); iter.hasNext();) {
                Node node = (Node) iter.next();
                testNodePath(node);
            }
        }

        for (Iterator iter = branch.nodeIterator(); iter.hasNext();) {
            Node node = (Node) iter.next();

            if (node instanceof Branch) {
                testBranchPath((Branch) node);
            } else {
                testNodePath(node);
            }
        }
    }

    protected void testNodePath(Node node) {
        String path = node.getPath();

        log("Path: " + path + " node: " + node);
    }
}

