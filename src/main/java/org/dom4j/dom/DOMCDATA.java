

package org.dom4j.dom;

import org.dom4j.CDATA;
import org.dom4j.Element;
import org.dom4j.tree.DefaultCDATA;
import org.w3c.dom.*;

/**
 * <p>
 * <code>DOMCDATA</code> implements a CDATA Section which supports the W3C DOM
 * API.
 * </p>
 * 
 * @author <a href="mailto:jstrachan@apache.org">James Strachan </a>
 * @version $Revision: 1.12 $
 */
public class DOMCDATA extends DefaultCDATA implements CDATASection {
    public DOMCDATA(String text) {
        super(text);
    }

    public DOMCDATA(Element parent, String text) {
        super(parent, text);
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
        return "#cdata-section";
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
                "CDATASection nodes cannot have children");
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

    // org.w3c.dom.CharacterData interface
    // -------------------------------------------------------------------------
    public String getData() throws DOMException {
        return DOMNodeHelper.getData(this);
    }

    public void setData(String data) throws DOMException {
        DOMNodeHelper.setData(this, data);
    }

    public int getLength() {
        return DOMNodeHelper.getLength(this);
    }

    public String substringData(int offset, int count) throws DOMException {
        return DOMNodeHelper.substringData(this, offset, count);
    }

    public void appendData(String arg) throws DOMException {
        DOMNodeHelper.appendData(this, arg);
    }

    public void insertData(int offset, String arg) throws DOMException {
        DOMNodeHelper.insertData(this, offset, arg);
    }

    public void deleteData(int offset, int count) throws DOMException {
        DOMNodeHelper.deleteData(this, offset, count);
    }

    public void replaceData(int offset, int count, String arg)
            throws DOMException {
        DOMNodeHelper.replaceData(this, offset, count, arg);
    }

    // org.w3c.dom.Text interface
    // -------------------------------------------------------------------------
    public Text splitText(int offset) throws DOMException {
        if (isReadOnly()) {
            throw new DOMException(DOMException.NO_MODIFICATION_ALLOWED_ERR,
                    "CharacterData node is read only: " + this);
        } else {
            String text = getText();
            int length = (text != null) ? text.length() : 0;

            if ((offset < 0) || (offset >= length)) {
                throw new DOMException(DOMException.INDEX_SIZE_ERR,
                        "No text at offset: " + offset);
            } else {
                String start = text.substring(0, offset);
                String rest = text.substring(offset);
                setText(start);

                Element parent = getParent();
                CDATA newText = createCDATA(rest);

                if (parent != null) {
                    parent.add(newText);
                }

                return DOMNodeHelper.asDOMText(newText);
            }
        }
    }

    // Implementation methods
    // -------------------------------------------------------------------------
    protected CDATA createCDATA(String text) {
        return new DOMCDATA(text);
    }

    public boolean isElementContentWhitespace() {
        DOMNodeHelper.notSupported();
        return false;
    }

    public String getWholeText() {
        DOMNodeHelper.notSupported();
        return null;
    }

    public Text replaceWholeText(String content) throws DOMException {
        DOMNodeHelper.notSupported();
        return null;
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

