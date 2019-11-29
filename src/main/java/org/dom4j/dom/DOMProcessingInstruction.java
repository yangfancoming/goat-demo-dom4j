

package org.dom4j.dom;

import org.dom4j.Element;
import org.dom4j.tree.DefaultProcessingInstruction;
import org.w3c.dom.*;

import java.util.Map;

/**
 * <p>
 * <code>DOMProcessingInstruction</code> implements a ProcessingInstruction
 * node which supports the W3C DOM API.
 * </p>
 * 
 * @author <a href="mailto:jstrachan@apache.org">James Strachan </a>
 * @version $Revision: 1.12 $
 */
public class DOMProcessingInstruction extends DefaultProcessingInstruction
        implements ProcessingInstruction {
    public DOMProcessingInstruction(String target, Map<String, String> values) {
        super(target, values);
    }

    public DOMProcessingInstruction(String target, String values) {
        super(target, values);
    }

    public DOMProcessingInstruction(Element parent, String target, String val) {
        super(parent, target, val);
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
                "PI nodes cannot have children");
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

    // org.w3c.dom.ProcessingInstruction interface
    // -------------------------------------------------------------------------
    // public String getTarget();
    public String getData() {
        return getText();
    }

    public void setData(String data) throws DOMException {
        if (isReadOnly()) {
            throw new DOMException(DOMException.NO_MODIFICATION_ALLOWED_ERR,
                    "This ProcessingInstruction is read only");
        } else {
            setText(data);
        }
    }

    // Implementation methods
    // -------------------------------------------------------------------------

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
        DOMNodeHelper.notSupported();
        return false;
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
        throw new UnsupportedOperationException("Not supported yet.");
    }
}

