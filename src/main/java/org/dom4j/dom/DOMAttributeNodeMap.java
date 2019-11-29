

package org.dom4j.dom;

import org.w3c.dom.Attr;
import org.w3c.dom.DOMException;
import org.w3c.dom.Node;

/**
 * <p>
 * <code>DOMAttributeNodeMap</code> implements a W3C NameNodeMap for the
 * attributes of an element.
 * </p>
 * 
 * @author <a href="mailto:james.strachan@metastuff.com">James Strachan </a>
 * @version $Revision: 1.8 $
 */
public class DOMAttributeNodeMap implements org.w3c.dom.NamedNodeMap {
    private DOMElement element;

    public DOMAttributeNodeMap(DOMElement element) {
        this.element = element;
    }

    // org.w3c.dom.NamedNodeMap interface
    // -------------------------------------------------------------------------
    public void foo() throws DOMException {
        DOMNodeHelper.notSupported();
    }

    public Node getNamedItem(String name) {
        return element.getAttributeNode(name);
    }

    public Node setNamedItem(Node arg) throws DOMException {
        if (arg instanceof Attr) {
            return element.setAttributeNode((Attr) arg);
        } else {
            throw new DOMException(DOMException.NOT_SUPPORTED_ERR,
                    "Node is not an Attr: " + arg);
        }
    }

    public Node removeNamedItem(String name) throws DOMException {
        Attr attr = element.getAttributeNode(name);

        if (attr == null) {
            throw new DOMException(DOMException.NOT_FOUND_ERR,
                    "No attribute named " + name);
        }

        return element.removeAttributeNode(attr);
    }

    public Node item(int index) {
        return DOMNodeHelper.asDOMAttr(element.attribute(index));
    }

    public int getLength() {
        return element.attributeCount();
    }

    public Node getNamedItemNS(String namespaceURI, String localName) {
        return element.getAttributeNodeNS(namespaceURI, localName);
    }

    public Node setNamedItemNS(Node arg) throws DOMException {
        if (arg instanceof Attr) {
            return element.setAttributeNodeNS((Attr) arg);
        } else {
            throw new DOMException(DOMException.NOT_SUPPORTED_ERR,
                    "Node is not an Attr: " + arg);
        }
    }

    public Node removeNamedItemNS(String namespaceURI, String localName)
            throws DOMException {
        Attr attr = element.getAttributeNodeNS(namespaceURI,
                localName);

        if (attr != null) {
            return element.removeAttributeNode(attr);
        }

        return attr;
    }
}

