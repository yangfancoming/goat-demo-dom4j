

package org.dom4j.dom;

import org.dom4j.DocumentFactory;
import org.dom4j.QName;
import org.dom4j.tree.DefaultDocument;
import org.w3c.dom.*;

import java.util.ArrayList;

/**
 * <p>
 * <code>DOMDocument</code> implements an XML document which supports the W3C
 * DOM API.
 * </p>
 * 
 * @author <a href="mailto:jstrachan@apache.org">James Strachan </a>
 * @version $Revision: 1.17 $
 */
public class DOMDocument extends DefaultDocument implements Document {
    /** The <code>DocumentFactory</code> instance used by default */
    private static final DOMDocumentFactory DOCUMENT_FACTORY
            = (DOMDocumentFactory) DOMDocumentFactory.getInstance();

    public DOMDocument() {
        init();
    }

    public DOMDocument(String name) {
        super(name);
        init();
    }

    public DOMDocument(DOMElement rootElement) {
        super(rootElement);
        init();
    }

    public DOMDocument(DOMDocumentType docType) {
        super(docType);
        init();
    }

    public DOMDocument(DOMElement rootElement, DOMDocumentType docType) {
        super(rootElement, docType);
        init();
    }

    public DOMDocument(String name, DOMElement rootElement,
            DOMDocumentType docType) {
        super(name, rootElement, docType);
        init();
    }

    private void init() {
        setDocumentFactory(DOCUMENT_FACTORY);
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
        return "#document";
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
        return DOMNodeHelper.createNodeList(content());
    }

    public Node getFirstChild() {
        return DOMNodeHelper.asDOMNode(node(0));
    }

    public Node getLastChild() {
        return DOMNodeHelper.asDOMNode(node(nodeCount() - 1));
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
        return null;
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

        if (!((nodeType == Node.ELEMENT_NODE)
                || (nodeType == Node.COMMENT_NODE)
                || (nodeType == Node.PROCESSING_INSTRUCTION_NODE)
                || (nodeType == Node.DOCUMENT_TYPE_NODE))) {
            throw new DOMException(DOMException.HIERARCHY_REQUEST_ERR,
                    "Given node cannot be a child of document");
        }
    }

    public boolean hasChildNodes() {
        return nodeCount() > 0;
    }

    public Node cloneNode(boolean deep) {
        return DOMNodeHelper.cloneNode(this, deep);
    }

    public boolean isSupported(String feature, String version) {
        return DOMNodeHelper.isSupported(this, feature, version);
    }

    public boolean hasAttributes() {
        return DOMNodeHelper.hasAttributes(this);
    }

    // org.w3c.dom.Document interface
    // -------------------------------------------------------------------------
    public NodeList getElementsByTagName(String name) {
        ArrayList<org.dom4j.Node> list = new ArrayList<org.dom4j.Node>();
        DOMNodeHelper.appendElementsByTagName(list, this, name);

        return DOMNodeHelper.createNodeList(list);
    }

    public NodeList getElementsByTagNameNS(String namespace, String name) {
        ArrayList<org.dom4j.Node> list = new ArrayList<org.dom4j.Node>();
        DOMNodeHelper.appendElementsByTagNameNS(list, this, namespace, name);

        return DOMNodeHelper.createNodeList(list);
    }

    public DocumentType getDoctype() {
        return DOMNodeHelper.asDOMDocumentType(getDocType());
    }

    public DOMImplementation getImplementation() {
        if (getDocumentFactory() instanceof DOMImplementation) {
            return (DOMImplementation) getDocumentFactory();
        } else {
            return DOCUMENT_FACTORY;
        }
    }

    public Element getDocumentElement() {
        return DOMNodeHelper.asDOMElement(getRootElement());
    }

    public Element createElement(String name) throws DOMException {
        return (Element) getDocumentFactory().createElement(name);
    }

    public DocumentFragment createDocumentFragment() {
        DOMNodeHelper.notSupported();

        return null;
    }

    public Text createTextNode(String data) {
        return (Text) getDocumentFactory().createText(data);
    }

    public Comment createComment(String data) {
        return (Comment) getDocumentFactory().createComment(data);
    }

    public CDATASection createCDATASection(String data) throws DOMException {
        return (CDATASection) getDocumentFactory().createCDATA(data);
    }

    public ProcessingInstruction createProcessingInstruction(String target,
            String data) throws DOMException {
        return (ProcessingInstruction) getDocumentFactory()
                .createProcessingInstruction(target, data);
    }

    public Attr createAttribute(String name) throws DOMException {
        QName qname = getDocumentFactory().createQName(name);

        return (Attr) getDocumentFactory().createAttribute(null, qname, "");
    }

    public EntityReference createEntityReference(String name)
            throws DOMException {
        return (EntityReference) getDocumentFactory().createEntity(name, null);
    }

    public Node importNode(Node importedNode,
                           boolean deep) throws DOMException {
        DOMNodeHelper.notSupported();

        return null;
    }

    public Element createElementNS(String namespaceURI,
            String qualifiedName) throws DOMException {
        QName qname = getDocumentFactory().createQName(qualifiedName,
                namespaceURI);

        return (Element) getDocumentFactory().createElement(qname);
    }

    public Attr createAttributeNS(String namespaceURI,
            String qualifiedName) throws DOMException {
        QName qname = getDocumentFactory().createQName(qualifiedName,
                namespaceURI);

        return (Attr) getDocumentFactory().createAttribute(null,
                qname, null);
    }

    public Element getElementById(String elementId) {
        return DOMNodeHelper.asDOMElement(elementByID(elementId));
    }

    // Implementation methods
    // -------------------------------------------------------------------------
    protected DocumentFactory getDocumentFactory() {
        if (super.getDocumentFactory() == null) {
            return DOCUMENT_FACTORY;
        } else {
            return super.getDocumentFactory();
        }
    }

    public String getInputEncoding() {
        DOMNodeHelper.notSupported();
        return null;
    }

    public String getXmlEncoding() {
        DOMNodeHelper.notSupported();
        return null;
    }

    public boolean getXmlStandalone() {
        DOMNodeHelper.notSupported();
        return false;
    }

    public void setXmlStandalone(boolean xmlStandalone) throws DOMException {
        DOMNodeHelper.notSupported();
    }

    public String getXmlVersion() {
        DOMNodeHelper.notSupported();
        return null;
    }

    public void setXmlVersion(String xmlVersion) throws DOMException {
        DOMNodeHelper.notSupported();
    }

    public boolean getStrictErrorChecking() {
        DOMNodeHelper.notSupported();
        return false;
    }

    public void setStrictErrorChecking(boolean strictErrorChecking) {
        DOMNodeHelper.notSupported();
    }

    public String getDocumentURI() {
        DOMNodeHelper.notSupported();
        return null;
    }

    public void setDocumentURI(String documentURI) {
        DOMNodeHelper.notSupported();
    }

    public Node adoptNode(Node source) throws DOMException {
        DOMNodeHelper.notSupported();
        return null;
    }

    public DOMConfiguration getDomConfig() {
        DOMNodeHelper.notSupported();
        return null;
    }

    public void normalizeDocument() {
        DOMNodeHelper.notSupported();
    }

    public Node renameNode(Node n, String namespaceURI, String qualifiedName) throws DOMException {
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
        DOMNodeHelper.notSupported();
        return null;
    }
}

