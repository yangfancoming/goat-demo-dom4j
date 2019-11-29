

package org.dom4j.bean;

import org.dom4j.Attribute;
import org.dom4j.QName;

import java.util.AbstractList;

/**
 * <p>
 * <code>BeanAttributeList</code> implements a list of Attributes which are
 * the properties of a JavaBean.
 * </p>
 * 
 * @author <a href="mailto:james.strachan@metastuff.com">James Strachan </a>
 * @version $Revision: 1.9 $
 */
public class BeanAttributeList extends AbstractList<Attribute> {
    /** The BeanElement that this */
    private BeanElement parent;

    /** The BeanElement that this */
    private BeanMetaData beanMetaData;

    /** The attributes */
    private BeanAttribute[] attributes;

    public BeanAttributeList(BeanElement parent, BeanMetaData beanMetaData) {
        this.parent = parent;
        this.beanMetaData = beanMetaData;
        this.attributes = new BeanAttribute[beanMetaData.attributeCount()];
    }

    public BeanAttributeList(BeanElement parent) {
        this.parent = parent;

        Object data = parent.getData();
        Class<?> beanClass = (data != null) ? data.getClass() : null;
        this.beanMetaData = BeanMetaData.get(beanClass);
        this.attributes = new BeanAttribute[beanMetaData.attributeCount()];
    }

    public BeanAttribute attribute(String name) {
        int index = beanMetaData.getIndex(name);

        return attribute(index);
    }

    public BeanAttribute attribute(QName qname) {
        int index = beanMetaData.getIndex(qname);

        return attribute(index);
    }

    public BeanAttribute attribute(int index) {
        if ((index >= 0) && (index <= attributes.length)) {
            BeanAttribute attribute = attributes[index];

            if (attribute == null) {
                attribute = createAttribute(parent, index);
                attributes[index] = attribute;
            }

            return attribute;
        }

        return null;
    }

    public BeanElement getParent() {
        return parent;
    }

    public QName getQName(int index) {
        return beanMetaData.getQName(index);
    }

    public Object getData(int index) {
        return beanMetaData.getData(index, parent.getData());
    }

    public void setData(int index, Object data) {
        beanMetaData.setData(index, parent.getData(), data);
    }

    // List interface
    // -------------------------------------------------------------------------
    @Override
    public int size() {
        return attributes.length;
    }

    public BeanAttribute get(int index) {
        BeanAttribute attribute = attributes[index];

        if (attribute == null) {
            attribute = createAttribute(parent, index);
            attributes[index] = attribute;
        }

        return attribute;
    }

    public boolean add(BeanAttribute object) {
        throw new UnsupportedOperationException("add(Object) unsupported");
    }

    public void add(int index, BeanAttribute object) {
        throw new UnsupportedOperationException("add(int,Object) unsupported");
    }

    public BeanAttribute set(int index, BeanAttribute object) {
        throw new UnsupportedOperationException("set(int,Object) unsupported");
    }

    public boolean remove(Object object) {
        return false;
    }

    public BeanAttribute remove(int index) {
        BeanAttribute attribute = get(index);
        attribute.setValue(null);

        return attribute;
    }

    public void clear() {
        for (BeanAttribute attribute : attributes) {
            if (attribute != null) {
                attribute.setValue(null);
            }
        }
    }

    // Implementation methods
    // -------------------------------------------------------------------------
    protected BeanAttribute createAttribute(BeanElement element, int index) {
        return new BeanAttribute(this, index);
    }
}

