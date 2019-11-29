

package org.dom4j.tree;

import org.dom4j.*;

import java.util.List;

/**
 * <p>
 * <code>BaseElement</code> is a useful base class for implemementation
 * inheritence of an XML element.
 * </p>
 * 
 * @author <a href="mailto:jstrachan@apache.org">James Strachan </a>
 * @version $Revision: 1.9 $
 */
public class BaseElement extends AbstractElement {
    /** The <code>QName</code> for this element */
    private QName qname;

    /**
     * Stores the parent branch of this node which is either a Document if this
     * element is the root element in a document, or another Element if it is a
     * child of the root document, or null if it has not been added to a
     * document yet.
     */
    private Branch parentBranch;

    /** List of content nodes. */
    protected List<Node> content;

    /** list of attributes */
    protected List<Attribute> attributes;

    public BaseElement(String name) {
        this.qname = getDocumentFactory().createQName(name);
    }

    public BaseElement(QName qname) {
        this.qname = qname;
    }

    public BaseElement(String name, Namespace namespace) {
        this.qname = getDocumentFactory().createQName(name, namespace);
    }

    public Element getParent() {
        Element result = null;

        if (parentBranch instanceof Element) {
            result = (Element) parentBranch;
        }

        return result;
    }

    public void setParent(Element parent) {
        if (parentBranch instanceof Element || (parent != null)) {
            parentBranch = parent;
        }
    }

    public Document getDocument() {
        if (parentBranch instanceof Document) {
            return (Document) parentBranch;
        } else if (parentBranch instanceof Element) {
            Element parent = (Element) parentBranch;

            return parent.getDocument();
        }

        return null;
    }

    public void setDocument(Document document) {
        if (parentBranch instanceof Document || (document != null)) {
            parentBranch = document;
        }
    }

    public boolean supportsParent() {
        return true;
    }

    public QName getQName() {
        return qname;
    }

    public void setQName(QName name) {
        this.qname = name;
    }

    public void clearContent() {
        contentList().clear();
    }

    public void setContent(List<Node> content) {
        this.content = content;

        if (content instanceof ContentListFacade) {
            this.content = ((ContentListFacade<Node>) content).getBackingList();
        }
    }

    public void setAttributes(List<Attribute> attributes) {
        this.attributes = attributes;

        if (attributes instanceof ContentListFacade) {
            this.attributes = ((ContentListFacade<Attribute>) attributes).getBackingList();
        }
    }

    // Implementation methods
    // -------------------------------------------------------------------------
    protected List<Node> contentList() {
        if (content == null) {
            content = createContentList();
        }

        return content;
    }

    protected List<Attribute> attributeList() {
        if (attributes == null) {
            attributes = createAttributeList();
        }

        return attributes;
    }

    protected List<Attribute> attributeList(int size) {
        if (attributes == null) {
            attributes = createAttributeList(size);
        }

        return attributes;
    }

    protected void setAttributeList(List<Attribute> attributeList) {
        this.attributes = attributeList;
    }
}

