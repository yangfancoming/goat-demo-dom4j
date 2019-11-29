

package org.dom4j.dom;

import org.dom4j.tree.DefaultDocumentType;
import org.w3c.dom.*;

/**
 * <p>
 * <code>DOMDocumentType</code> implements a DocumentType node which supports
 * the W3C DOM API.
 * </p>
 * 
 * @author <a href="mailto:james.strachan@metastuff.com">James Strachan </a>
 * @version $Revision: 1.11 $
 */
public class DOMDocumentType extends DefaultDocumentType implements
        DocumentType {
    public DOMDocumentType() {
    }

    public DOMDocumentType(String elementName, String systemID) {
        super(elementName, systemID);
    }

    public DOMDocumentType(String name, String publicID, String systemID) {
        super(name, publicID, systemID);
    }

    // org.w3c.dom.Node interface
    // -------------------------------------------------------------------------
    public boolean supports(String feature, String version) {
        return DOMNodeHelper.supports(this, feature, version);
    }

    public String getNamespaceURI() {
        return DOMNodeHelper.getNamespaceURI(this);
    }

    public String getPrefix() {
        return DOMNodeHelper.getPrefix(this);
    }

    public void setPrefix(String prefix) throws DOMException {
        DOMNodeHelper.setPrefix(this, prefix);
    }

    public String getLocalName() {
        return DOMNodeHelper.getLocalName(this);
    }

    public String getNodeName() {
        return getName();
    }

    // already part of API
    //
    // public short getNodeType();
    public String getNodeValue() throws DOMException {
        return null;
    }

    public void setNodeValue(String nodeValue) throws DOMException {
    }

    public Node getParentNode() {
        return DOMNodeHelper.getParentNode(this);
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
        throw new DOMException(DOMException.HIERARCHY_REQUEST_ERR,
                "DocumentType nodes cannot have children");
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

    // org.w3c.dom.DocumentType interface
    // -------------------------------------------------------------------------
    public NamedNodeMap getEntities() {
        return null;
    }

    public NamedNodeMap getNotations() {
        return null;
    }

    public String getPublicId() {
        return getPublicID();
    }

    public String getSystemId() {
        return getSystemID();
    }

    public String getInternalSubset() {
        return getElementName();
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
        DOMNodeHelper.notSupported();
        return null;
    }

    public void setTextContent(String textContent) throws DOMException {
        DOMNodeHelper.notSupported();
    }

    public boolean isSameNode(Node other) {
        DOMNodeHelper.notSupported();
        return false;
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

    public short getNodeType() {
        DOMNodeHelper.notSupported();
        return 0;
    }
}

