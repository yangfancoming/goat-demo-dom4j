

package org.dom4j.tree;

import org.dom4j.Element;
import org.dom4j.Namespace;
import org.dom4j.QName;

/**
 * <p>
 * <code>DefaultAttribute</code> implements a doubly linked node which
 * supports the parent relationship and is mutable.
 * </p>
 * 
 * @author <a href="mailto:jstrachan@apache.org">James Strachan </a>
 * @version $Revision: 1.13 $
 */
public class DefaultAttribute extends FlyweightAttribute {
    /** The parent of this node */
    private Element parent;

    public DefaultAttribute(QName qname) {
        super(qname);
    }

    public DefaultAttribute(QName qname, String value) {
        super(qname, value);
    }

    public DefaultAttribute(Element parent, QName qname, String value) {
        super(qname, value);
        this.parent = parent;
    }

    /**
     * Creates the <code>Attribute</code> with the specified local name and
     * value.
     * 
     * @param name
     *            is the name of the attribute
     * @param value
     *            is the value of the attribute
     */
    public DefaultAttribute(String name, String value) {
        super(name, value);
    }

    /**
     * Creates the <code>Attribute</code> with the specified local name, value
     * and <code>Namespace</code>.
     * 
     * @param name
     *            is the name of the attribute
     * @param value
     *            is the value of the attribute
     * @param namespace
     *            is the namespace of the attribute
     */
    public DefaultAttribute(String name, String value, Namespace namespace) {
        super(name, value, namespace);
    }

    /**
     * Creates the <code>Attribute</code> with the specified local name, value
     * and <code>Namespace</code>.
     * 
     * @param parent
     *            is the parent element
     * @param name
     *            is the name of the attribute
     * @param value
     *            is the value of the attribute
     * @param namespace
     *            is the namespace of the attribute
     */
    public DefaultAttribute(Element parent, String name, String value,
                            Namespace namespace) {
        super(name, value, namespace);
        this.parent = parent;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Element getParent() {
        return parent;
    }

    public void setParent(Element parent) {
        this.parent = parent;
    }

    public boolean supportsParent() {
        return true;
    }

    public boolean isReadOnly() {
        return false;
    }
}

