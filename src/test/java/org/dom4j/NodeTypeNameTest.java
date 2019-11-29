

package org.dom4j;

import java.util.Iterator;

/**
 * Tests the getNodeNameType() method
 * 
 * @author <a href="mailto:jstrachan@apache.org">James Strachan </a>
 * @version $Revision: 1.4 $
 */
public class NodeTypeNameTest extends AbstractTestCase {

    public void testDocument() throws Exception {
        testDocument(getDocument());
    }

    public void testCDATA() throws Exception {
        testDocument("/xml/cdata.xml");
    }

    public void testNamespaces() throws Exception {
        testDocument("/xml/namespaces.xml");
        testDocument("/xml/testNamespaces.xml");
    }

    public void testPI() throws Exception {
        testDocument("/xml/testPI.xml");
    }

    public void testInline() throws Exception {
        testDocument("/xml/inline.xml");
    }

    // Implementation methods
    // -------------------------------------------------------------------------
    protected void testDocument(String fileName) throws Exception {
        Document document = getDocument(fileName);
        testDocument(document);
    }

    protected void testDocument(Document document) throws Exception {
        assertEquals(document.getNodeTypeName(), "Document");

        DocumentType docType = document.getDocType();

        if (docType != null) {
            assertEquals(docType.getNodeTypeName(), "DocumentType");
        }

        testElement(document.getRootElement());
    }

    protected void testElement(Element element) {
        assertEquals(element.getNodeTypeName(), "Element");

        for (Iterator iter = element.attributeIterator(); iter.hasNext();) {
            Attribute attribute = (Attribute) iter.next();
            assertEquals(attribute.getNodeTypeName(), "Attribute");
        }

        for (Iterator iter = element.nodeIterator(); iter.hasNext();) {
            Node node = (Node) iter.next();
            String nodeTypeName = node.getNodeTypeName();

            if (node instanceof Attribute) {
                assertEquals(nodeTypeName, "Attribute");
            } else if (node instanceof CDATA) {
                assertEquals(nodeTypeName, "CDATA");
            } else if (node instanceof Comment) {
                assertEquals(nodeTypeName, "Comment");
            } else if (node instanceof Element) {
                assertEquals(nodeTypeName, "Element");
                testElement((Element) node);
            } else if (node instanceof Entity) {
                assertEquals(nodeTypeName, "Entity");
            } else if (node instanceof Element) {
                assertEquals(nodeTypeName, "Element");
            } else if (node instanceof Namespace) {
                assertEquals(nodeTypeName, "Namespace");
            } else if (node instanceof ProcessingInstruction) {
                assertEquals(nodeTypeName, "ProcessingInstruction");
            } else if (node instanceof Text) {
                assertEquals(nodeTypeName, "Text");
            } else {
                assertTrue("Invalid node type: " + nodeTypeName + " for node: "
                        + node, false);
            }
        }
    }
}

