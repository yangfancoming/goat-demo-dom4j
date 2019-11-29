

package org.dom4j.dom;

import org.dom4j.AbstractTestCase;
import org.dom4j.io.DOMWriter;
import org.dom4j.io.SAXReader;
import org.testng.annotations.BeforeClass;
import org.w3c.dom.DOMException;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.StringReader;

/**
 * A test harness to test the native DOM implementation of dom4j
 * @version $Revision: 1.4 $
 */
public class DOMTest extends AbstractTestCase {
    /** Elements. */
    private long elements;

    /** Attributes. */
    private long attributes;

    /** Characters. */
    private long characters;


    public void testCount() throws Exception {
        DOMWriter domWriter = new DOMWriter();

        long start = System.currentTimeMillis();
        org.w3c.dom.Document domDocument = domWriter.write(document);
        long end = System.currentTimeMillis();

        System.out.println("Converting to a W3C Document took: "  + (end - start) + " milliseconds");
        traverse(domDocument);
        log("elements: " + elements + " attributes: " + attributes + " characters: " + characters);

    }

    public void testNamespace() throws Exception {
        String xml = "<prefix:root xmlns:prefix=\"myuri\" />";
        SAXReader xmlReader = new SAXReader(DOMDocumentFactory.getInstance());
        DOMDocument d = (DOMDocument) xmlReader.read(new StringReader(xml));
        assertEquals("namespace prefix not correct", "prefix", d.getRootElement().getNamespace().getPrefix());
        assertEquals("namespace uri not correct", "myuri", d.getRootElement().getNamespace().getURI());
        System.out.println(d.asXML());
    }

    /**
     * Tests the bug found by Soumanjoy
     * 
     * @throws Exception
     *             DOCUMENT ME!
     */
    public void testClassCastBug() throws Exception {
        DOMDocument oDocument = new DOMDocument("Root");
        org.w3c.dom.Element oParent = oDocument.createElement("Parent");

        // <-- Fails here when the code is broken.
        oParent.setAttribute("name", "N01");
        oParent.setAttribute("id", "ID01");
        oDocument.appendChild(oParent); // <-- Fails here, Error message is
        // below
    }

    public void testReplaceChild() throws Exception {
        DOMDocument document = new DOMDocument("Root");
        org.w3c.dom.Element parent = document.createElement("Parent");
        org.w3c.dom.Element first = document.createElement("FirstChild");
        org.w3c.dom.Element second = document.createElement("SecondChild");
        org.w3c.dom.Element third = document.createElement("ThirdChild");

        document.appendChild(parent);
        parent.appendChild(first);
        parent.appendChild(second);
        parent.appendChild(third);

        org.w3c.dom.Element newFirst = document.createElement("NewFirst");
        org.w3c.dom.Element oldFirst = (org.w3c.dom.Element) parent
                .replaceChild(newFirst, first);

        /* check the return value of replaceChild */
        assertEquals(oldFirst, first);

        /* make sure the old node has been replaced */
        NodeList children = parent.getChildNodes();
        Node firstChild = children.item(0);
        assertEquals(Node.ELEMENT_NODE, firstChild.getNodeType());
        assertEquals(newFirst, firstChild);

        /* try to replace a node that doesn't exist */
        org.w3c.dom.Element badNode = document.createElement("No-Child");

        try {
            parent.replaceChild(newFirst, badNode);
            fail("DOMException not thrown");
        } catch (DOMException e) {
            assertEquals(DOMException.NOT_FOUND_ERR, e.code);
        }
    }

    // Implementation methods
    // -------------------------------------------------------------------------
    @BeforeClass
    public void setUp() throws Exception {
        super.setUp();

        SAXReader reader = new SAXReader(DOMDocumentFactory.getInstance());
        document = getDocument("/xml/contents.xml", reader);
    }

    /**
     * Traverses the specified node, recursively.
     * 
     * @param node
     *            DOCUMENT ME!
     */
    protected void traverse(Node node) {
        // is there anything to do?
        if (node == null) {
            return;
        }
        int type = node.getNodeType();
        switch (type) {
            case Node.DOCUMENT_NODE: {
                elements = 0;
                attributes = 0;
                characters = 0;
                traverse(((org.w3c.dom.Document) node).getDocumentElement());

                break;
            }

            case Node.ELEMENT_NODE: {
                elements++;

                NamedNodeMap attrs = node.getAttributes();

                if (attrs != null) {
                    attributes += attrs.getLength();
                }
                NodeList children = node.getChildNodes();
                if (children != null) {
                    int len = children.getLength();

                    for (int i = 0; i < len; i++) {
                        traverse(children.item(i));
                    }
                }

                break;
            }

            case Node.ENTITY_REFERENCE_NODE: {
                NodeList children = node.getChildNodes();

                if (children != null) {
                    int len = children.getLength();

                    for (int i = 0; i < len; i++) {
                        traverse(children.item(i));
                    }
                }

                break;
            }

            case Node.CDATA_SECTION_NODE: {
                characters += node.getNodeValue().length();

                break;
            }

            case Node.TEXT_NODE: {
                characters += node.getNodeValue().length();

                break;
            }

            default:
                break;
        }
    }
}

