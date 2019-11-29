

package org.dom4j.xpp;

import org.dom4j.Attribute;
import org.dom4j.DocumentFactory;
import org.dom4j.Element;
import org.dom4j.QName;
import org.dom4j.tree.AbstractElement;
import org.gjt.xpp.XmlPullParserException;
import org.gjt.xpp.XmlStartTag;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * <p>
 * <code>ProxyXmlStartTag</code> implements the XPP XmlSmartTag interface
 * while creating a dom4j Element underneath.
 * </p>
 * 
 * @author <a href="mailto:jstrachan@apache.org">James Strachan </a>
 * @version $Revision: 1.8 $
 */
public class ProxyXmlStartTag implements XmlStartTag {
    /** The element being constructed */
    private Element element;

    /** The factory used to create new elements */
    private DocumentFactory factory = DocumentFactory.getInstance();

    public ProxyXmlStartTag() {
    }

    public ProxyXmlStartTag(Element element) {
        this.element = element;
    }

    // XmlStartTag interface
    // -------------------------------------------------------------------------
    public void resetStartTag() {
        this.element = null;
    }

    public int getAttributeCount() {
        return (element != null) ? element.attributeCount() : 0;
    }

    public String getAttributeNamespaceUri(int index) {
        if (element != null) {
            Attribute attribute = element.attribute(index);

            if (attribute != null) {
                return attribute.getNamespaceURI();
            }
        }

        return null;
    }

    public String getAttributeLocalName(int index) {
        if (element != null) {
            Attribute attribute = element.attribute(index);

            if (attribute != null) {
                return attribute.getName();
            }
        }

        return null;
    }

    public String getAttributePrefix(int index) {
        if (element != null) {
            Attribute attribute = element.attribute(index);

            if (attribute != null) {
                String prefix = attribute.getNamespacePrefix();

                if ((prefix != null) && (prefix.length() > 0)) {
                    return prefix;
                }
            }
        }

        return null;
    }

    public String getAttributeRawName(int index) {
        if (element != null) {
            Attribute attribute = element.attribute(index);

            if (attribute != null) {
                return attribute.getQualifiedName();
            }
        }

        return null;
    }

    public String getAttributeValue(int index) {
        if (element != null) {
            Attribute attribute = element.attribute(index);

            if (attribute != null) {
                return attribute.getValue();
            }
        }

        return null;
    }

    public String getAttributeValueFromRawName(String rawName) {
        if (element != null) {
            for (Iterator<Attribute> iter = element.attributeIterator(); iter.hasNext();) {
                Attribute attribute = iter.next();

                if (rawName.equals(attribute.getQualifiedName())) {
                    return attribute.getValue();
                }
            }
        }

        return null;
    }

    public String getAttributeValueFromName(String namespaceURI,
            String localName) {
        if (element != null) {
            for (Iterator<Attribute> iter = element.attributeIterator(); iter.hasNext();) {
                Attribute attribute = iter.next();

                if (namespaceURI.equals(attribute.getNamespaceURI())
                        && localName.equals(attribute.getName())) {
                    return attribute.getValue();
                }
            }
        }

        return null;
    }

    public boolean isAttributeNamespaceDeclaration(int index) {
        if (element != null) {
            Attribute attribute = element.attribute(index);

            if (attribute != null) {
                return "xmlns".equals(attribute.getNamespacePrefix());
            }
        }

        return false;
    }

    /**
     * parameters modeled after SAX2 attribute approach
     * 
     * @param namespaceURI
     *            DOCUMENT ME!
     * @param localName
     *            DOCUMENT ME!
     * @param rawName
     *            DOCUMENT ME!
     * @param value
     *            DOCUMENT ME!
     * 
     * @throws XmlPullParserException
     *             DOCUMENT ME!
     */
    public void addAttribute(String namespaceURI, String localName,
            String rawName, String value) throws XmlPullParserException {
        QName qname = QName.get(rawName, namespaceURI);
        element.addAttribute(qname, value);
    }

    public void addAttribute(String namespaceURI, String localName,
            String rawName, String value, boolean isNamespaceDeclaration)
            throws XmlPullParserException {
        if (isNamespaceDeclaration) {
            String prefix = "";
            int idx = rawName.indexOf(':');

            if (idx > 0) {
                prefix = rawName.substring(0, idx);
            }

            element.addNamespace(prefix, namespaceURI);
        } else {
            QName qname = QName.get(rawName, namespaceURI);
            element.addAttribute(qname, value);
        }
    }

    public void ensureAttributesCapacity(int minCapacity)
            throws XmlPullParserException {
        if (element instanceof AbstractElement) {
            AbstractElement elementImpl = (AbstractElement) element;
            elementImpl.ensureAttributesCapacity(minCapacity);
        }
    }

    /**
     * remove all atribute
     * 
     * @throws XmlPullParserException
     *             DOCUMENT ME!
     */
    public void removeAtttributes() throws XmlPullParserException {
        if (element != null) {
            element.setAttributes(new ArrayList());

            // ##### FIXME
            // adding this method would be nice...
            // element.clearAttributes();
        }
    }

    public String getLocalName() {
        return element.getName();
    }

    public String getNamespaceUri() {
        return element.getNamespaceURI();
    }

    public String getPrefix() {
        return element.getNamespacePrefix();
    }

    public String getRawName() {
        return element.getQualifiedName();
    }

    public void modifyTag(String namespaceURI, String lName, String rawName) {
        this.element = factory.createElement(rawName, namespaceURI);
    }

    public void resetTag() {
        this.element = null;
    }

    // Properties
    // -------------------------------------------------------------------------
    public DocumentFactory getDocumentFactory() {
        return factory;
    }

    public void setDocumentFactory(DocumentFactory documentFactory) {
        this.factory = documentFactory;
    }

    public Element getElement() {
        return element;
    }
}

