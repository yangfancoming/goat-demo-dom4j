

package org.dom4j.bean;

import org.dom4j.Attribute;
import org.dom4j.DocumentFactory;
import org.dom4j.Element;
import org.dom4j.QName;
import org.dom4j.tree.DefaultAttribute;
import org.xml.sax.Attributes;

/**
 * <p>
 * <code>BeanDocumentFactory</code> is a factory of DOM4J objects which may be
 * BeanElements which are backed by JavaBeans and their properties.
 * </p>
 * 
 * <p>
 * The tree built allows full XPath expressions from anywhere on the tree.
 * </p>
 * 
 * @author <a href="mailto:jstrachan@apache.org">James Strachan </a>
 * @version $Revision: 1.14 $
 */
public class BeanDocumentFactory extends DocumentFactory {
    /** The Singleton instance */
    private static BeanDocumentFactory singleton = new BeanDocumentFactory();

    /**
     * <p>
     * Access to the singleton instance of this factory.
     * </p>
     * 
     * @return the default singleon instance
     */
    public static DocumentFactory getInstance() {
        return singleton;
    }

    // Factory methods
    public Element createElement(QName qname) {
        Object bean = createBean(qname);

        if (bean == null) {
            return new BeanElement(qname);
        } else {
            return new BeanElement(qname, bean);
        }
    }

    public Element createElement(QName qname, Attributes attributes) {
        Object bean = createBean(qname, attributes);

        if (bean == null) {
            return new BeanElement(qname);
        } else {
            return new BeanElement(qname, bean);
        }
    }

    public Attribute createAttribute(Element owner, QName qname, String value) {
        return new DefaultAttribute(qname, value);
    }

    // Implementation methods
    protected Object createBean(QName qname) {
        return null;
    }

    protected Object createBean(QName qname, Attributes attributes) {
        String value = attributes.getValue("class");

        if (value != null) {
            try {
                Class<?> beanClass = Class.forName(value, true,
                        BeanDocumentFactory.class.getClassLoader());

                return beanClass.newInstance();
            } catch (Exception e) {
                handleException(e);
            }
        }

        return null;
    }

    protected void handleException(Exception e) {
        // ignore introspection exceptions
        System.out.println("#### Warning: couldn't create bean: " + e);
    }
}

