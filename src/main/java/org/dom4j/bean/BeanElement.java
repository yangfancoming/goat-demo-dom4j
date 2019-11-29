

package org.dom4j.bean;

import org.dom4j.*;
import org.dom4j.tree.DefaultElement;
import org.dom4j.tree.NamespaceStack;
import org.xml.sax.Attributes;

import java.util.List;

/**
 * <p>
 * <code>BeanElement</code> uses a Java Bean to store its attributes.
 * </p>
 * 
 * @author <a href="mailto:jstrachan@apache.org">James Strachan </a>
 * @version $Revision: 1.15 $
 */
public class BeanElement extends DefaultElement {
    /** The <code>DocumentFactory</code> instance used by default */
    private static final DocumentFactory DOCUMENT_FACTORY = BeanDocumentFactory
            .getInstance();

    /** The JavaBean which defines my attributes */
    private Object bean;

    public BeanElement(String name, Object bean) {
        this(DOCUMENT_FACTORY.createQName(name), bean);
    }

    public BeanElement(String name, Namespace namespace, Object bean) {
        this(DOCUMENT_FACTORY.createQName(name, namespace), bean);
    }

    public BeanElement(QName qname, Object bean) {
        super(qname);
        this.bean = bean;
    }

    public BeanElement(QName qname) {
        super(qname);
    }

    /**
     * DOCUMENT ME!
     * 
     * @return the JavaBean associated with this element
     */
    public Object getData() {
        return bean;
    }

    public void setData(Object data) {
        this.bean = data;

        // force the attributeList to be lazily
        // created next time an attribute related
        // method is called again.
        setAttributeList(null);
    }

    public BeanAttribute attribute(String name) {
        return getBeanAttributeList().attribute(name);
    }

    public BeanAttribute attribute(QName qname) {
        return getBeanAttributeList().attribute(qname);
    }

    public Element addAttribute(String name, String value) {
        Attribute attribute = attribute(name);

        if (attribute != null) {
            attribute.setValue(value);
        }

        return this;
    }

    public Element addAttribute(QName qName, String value) {
        Attribute attribute = attribute(qName);

        if (attribute != null) {
            attribute.setValue(value);
        }

        return this;
    }

    public void setAttributes(List<Attribute> attributes) {
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    // Method overridden from AbstractElement
    public void setAttributes(Attributes attributes,
                              NamespaceStack namespaceStack, boolean noNamespaceAttributes) {
        String className = attributes.getValue("class");

        if (className != null) {
            try {
                Class<?> beanClass = Class.forName(className, true,
                        BeanElement.class.getClassLoader());
                this.setData(beanClass.newInstance());

                for (int i = 0; i < attributes.getLength(); i++) {
                    String attributeName = attributes.getLocalName(i);

                    if (!"class".equalsIgnoreCase(attributeName)) {
                        addAttribute(attributeName, attributes.getValue(i));
                    }
                }
            } catch (Exception ex) {
                // What to do here?
                ((BeanDocumentFactory) this.getDocumentFactory())
                        .handleException(ex);
            }
        } else {
            super.setAttributes(attributes, namespaceStack,
                    noNamespaceAttributes);
        }
    }

    // Implementation methods
    // -------------------------------------------------------------------------
    protected DocumentFactory getDocumentFactory() {
        return DOCUMENT_FACTORY;
    }

    protected BeanAttributeList getBeanAttributeList() {
        return (BeanAttributeList) attributeList();
    }

    /**
     * A Factory Method pattern which lazily creates a List implementation used
     * to store content
     * 
     * @return DOCUMENT ME!
     */
    protected List<Attribute> createAttributeList() {
        return new BeanAttributeList(this);
    }

    protected List<Attribute> createAttributeList(int size) {
        return new BeanAttributeList(this);
    }
}

