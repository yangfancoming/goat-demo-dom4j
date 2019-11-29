

package org.dom4j.bean;

import org.dom4j.Element;
import org.dom4j.QName;
import org.dom4j.tree.AbstractAttribute;

/**
 * <p>
 * <code>BeanAttribute</code> represents a mutable XML attribute which is
 * backed by a property of the JavaBean of its parent element.
 * </p>
 * 
 * @author <a href="mailto:james.strachan@metastuff.com">James Strachan </a>
 * @version $Revision: 1.7 $
 */
public class BeanAttribute extends AbstractAttribute {
    /** The list of Bean attributes */
    private final BeanAttributeList beanList;

    /** The index in the Bean attribute list */
    private final int index;

    public BeanAttribute(BeanAttributeList beanList, int index) {
        this.beanList = beanList;
        this.index = index;
    }

    public QName getQName() {
        return beanList.getQName(index);
    }

    public Element getParent() {
        return beanList.getParent();
    }

    public String getValue() {
        Object data = getData();

        return (data != null) ? data.toString() : null;
    }

    public void setValue(String data) {
        beanList.setData(index, data);
    }

    public Object getData() {
        return beanList.getData(index);
    }

    public void setData(Object data) {
        beanList.setData(index, data);
    }
}

