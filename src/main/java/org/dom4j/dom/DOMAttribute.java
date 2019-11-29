

package org.dom4j.dom;

import org.dom4j.Element;
import org.dom4j.QName;
import org.dom4j.tree.DefaultAttribute;
import org.w3c.dom.*;

/**
 * <p>
 * <code>DOMAttribute</code> implements a doubly linked attribute which
 * supports the W3C DOM API.
 * </p>
 * 
 * @author <a href="mailto:jstrachan@apache.org">James Strachan </a>
 * @version $Revision: 1.14 $
 */
public class DOMAttribute extends DefaultAttribute implements Attr {
    public DOMAttribute(QName qname) {
        super(qname);
    }

    public DOMAttribute(QName qname, String value) {
        super(qname, value);
    }

    public DOMAttribute(Element parent, QName qname, String value) {
        super(parent, qname, value);
    }

    // org.w3c.dom.Node interface
    // -------------------------------------------------------------------------
    public boolean supports(String feature, String version) {
        return DOMNodeHelper.supports(this, feature, version);
    }

    public String getNamespaceURI() {
        return getQName().getNamespaceURI();
    }

    public String getPrefix() {
        return getQName().getNamespacePrefix();
    }

    public void setPrefix(String prefix) throws DOMException {
        DOMNodeHelper.setPrefix(this, prefix);
    }

    public String getLocalName() {
        return getQName().getName();
    }

    public String getNodeName() {
        return getName();
    }

    // already part of API
    //
    // public short getNodeType();
    public String getNodeValue() throws DOMException {
        return DOMNodeHelper.getNodeValue(this);
    }

    public void setNodeValue(String nodeValue) throws DOMException {
        DOMNodeHelper.setNodeValue(this, nodeValue);
    }

    public Node getParentNode() {
        // Per http://www.w3.org/TR/DOM-Level-2-Core/core.html#ID-637646024
        // and the NIST conformance tests, Attr.getParentNode() should always
        // return null
        return null;
    }

    public NodeList getChildNodes() {
        return DOMNodeHelper.getChildNodes(this);
    }

    public Node getFirstChild() {
        return DOMNodeHelper.getFirstChild(this);
    }

    public Node getLastChild() {
        return DOMNodeHelper.getLastChild(this);
    }

    public Node getPreviousSibling() {
        return DOMNodeHelper.getPreviousSibling(this);
    }

    public Node getNextSibling() {
        return DOMNodeHelper.getNextSibling(this);
    }

    public NamedNodeMap getAttributes() {
        return null;
    }

    public Document getOwnerDocument() {
        return DOMNodeHelper.getOwnerDocument(this);
    }

    public Node insertBefore(Node newChild,
                             Node refChild) throws DOMException {
        checkNewChildNode(newChild);

        return DOMNodeHelper.insertBefore(this, newChild, refChild);
    }

    public Node replaceChild(Node newChild,
                             Node oldChild) throws DOMException {
        checkNewChildNode(newChild);

        return DOMNodeHelper.replaceChild(this, newChild, oldChild);
    }

    public Node removeChild(Node oldChild)
            throws DOMException {
        return DOMNodeHelper.removeChild(this, oldChild);
    }

    public Node appendChild(Node newChild)
            throws DOMException {
        checkNewChildNode(newChild);

        return DOMNodeHelper.appendChild(this, newChild);
    }

    private void checkNewChildNode(Node newChild)
            throws DOMException {
        final int nodeType = newChild.getNodeType();

        if (!((nodeType == Node.TEXT_NODE)
                || (nodeType == Node.ENTITY_REFERENCE_NODE))) {
            throw new DOMException(DOMException.HIERARCHY_REQUEST_ERR,
                    "The node cannot be a child of attribute");
        }
    }

    public boolean hasChildNodes() {
        return DOMNodeHelper.hasChildNodes(this);
    }

    public Node cloneNode(boolean deep) {
        return DOMNodeHelper.cloneNode(this, deep);
    }

    public void normalize() {
        DOMNodeHelper.normalize(this);
    }

    public boolean isSupported(String feature, String version) {
        return DOMNodeHelper.isSupported(this, feature, version);
    }

    public boolean hasAttributes() {
        return DOMNodeHelper.hasAttributes(this);
    }

    // org.w3c.dom.Attr interface
    // -------------------------------------------------------------------------
    // public String getName();
    public boolean getSpecified() {
        return true;
    }

    // public String getValue();
    // public void setValue(String value) throws DOMException;
    public org.w3c.dom.Element getOwnerElement() {
        return DOMNodeHelper.asDOMElement(getParent());
    }

    public TypeInfo getSchemaTypeInfo() {
        DOMNodeHelper.notSupported();
        return null;
    }

    public boolean isId() {
        DOMNodeHelper.notSupported();
        return false;
    }

    public String getBaseURI() {
        DOMNodeHelper.notSupported();
        return null;
    }

    public short compareDocumentPosition(Node other) throws DOMException {
        DOMNodeHelper.notSupported();
        return 0;
    }

    public String getTextContent() throws DOMException {
        return getValue();
    }

    public void setTextContent(String textContent) throws DOMException {
        this.setValue(textContent);
    }

    public boolean isSameNode(Node other) {
        return DOMNodeHelper.isNodeSame(this, other);
    }

    public String lookupPrefix(String namespaceURI) {
        DOMNodeHelper.notSupported();
        return null;
    }

    public boolean isDefaultNamespace(String namespaceURI) {
        DOMNodeHelper.notSupported();
        return false;
    }

    public String lookupNamespaceURI(String prefix) {
        DOMNodeHelper.notSupported();
        return null;
    }

    public boolean isEqualNode(Node other) {
        return DOMNodeHelper.isNodeEquals(this, other);
    }

    public Object getFeature(String feature, String version) {
        DOMNodeHelper.notSupported();
        return null;
    }

    public Object setUserData(String key, Object data, UserDataHandler handler) {
        DOMNodeHelper.notSupported();
        return null;
    }

    public Object getUserData(String key) {
        DOMNodeHelper.notSupported();
        return null;
    }
}

