

package org.dom4j.bean;

import org.dom4j.DocumentFactory;
import org.dom4j.QName;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * <code>BeanMetaData</code> contains metadata about a bean class.
 * </p>
 * 
 * @author <a href="mailto:james.strachan@metastuff.com">James Strachan </a>
 * @version $Revision: 1.10 $
 */
public class BeanMetaData {
    /** Empty arguments for reflection calls */
    protected static final Object[] NULL_ARGS = {};

    /** Singleton cache */
    private static Map<Class<?>, BeanMetaData> singletonCache = new HashMap<Class<?>, BeanMetaData>();

    private static final DocumentFactory DOCUMENT_FACTORY = BeanDocumentFactory
            .getInstance();

    /** The class of the bean */
    private Class<?> beanClass;

    /** Property descriptors for the bean */
    private PropertyDescriptor[] propertyDescriptors;

    /** QNames for the attributes */
    private QName[] qNames;

    /** Read methods used for getting properties */
    private Method[] readMethods;

    /** Write methods used for setting properties */
    private Method[] writeMethods;

    /** Index of names and QNames to indices
     * Keys are type of QName and String
     */
    private Map<Object, Integer> nameMap = new HashMap<Object, Integer>();

    public BeanMetaData(Class<?> beanClass) {
        this.beanClass = beanClass;

        if (beanClass != null) {
            try {
                BeanInfo beanInfo = Introspector.getBeanInfo(beanClass);
                propertyDescriptors = beanInfo.getPropertyDescriptors();
            } catch (IntrospectionException e) {
                handleException(e);
            }
        }

        if (propertyDescriptors == null) {
            propertyDescriptors = new PropertyDescriptor[0];
        }

        int size = propertyDescriptors.length;
        qNames = new QName[size];
        readMethods = new Method[size];
        writeMethods = new Method[size];

        for (int i = 0; i < size; i++) {
            PropertyDescriptor propertyDescriptor = propertyDescriptors[i];
            String name = propertyDescriptor.getName();
            QName qName = DOCUMENT_FACTORY.createQName(name);
            qNames[i] = qName;
            readMethods[i] = propertyDescriptor.getReadMethod();
            writeMethods[i] = propertyDescriptor.getWriteMethod();

            nameMap.put(name, i);
            nameMap.put(qName, i);
        }
    }

    /**
     * Static helper method to find and cache meta data objects for bean types
     * 
     * @param beanClass
     *            DOCUMENT ME!
     * 
     * @return DOCUMENT ME!
     */
    public static BeanMetaData get(Class<?> beanClass) {
        BeanMetaData answer = singletonCache.get(beanClass);

        if (answer == null) {
            answer = new BeanMetaData(beanClass);
            singletonCache.put(beanClass, answer);
        }

        return answer;
    }

    /**
     * DOCUMENT ME!
     * 
     * @return the number of attribtutes for this bean type
     */
    public int attributeCount() {
        return propertyDescriptors.length;
    }

    public BeanAttributeList createAttributeList(BeanElement parent) {
        return new BeanAttributeList(parent, this);
    }

    public QName getQName(int index) {
        return qNames[index];
    }

    public int getIndex(String name) {
        Integer index = nameMap.get(name);

        return (index != null) ? index : (-1);
    }

    public int getIndex(QName qName) {
        Integer index = nameMap.get(qName);

        return (index != null) ? index : (-1);
    }

    public Object getData(int index, Object bean) {
        try {
            Method method = readMethods[index];

            return method.invoke(bean, NULL_ARGS);
        } catch (Exception e) {
            handleException(e);

            return null;
        }
    }

    public void setData(int index, Object bean, Object data) {
        try {
            Method method = writeMethods[index];
            Object[] args = {data};
            method.invoke(bean, args);
        } catch (Exception e) {
            handleException(e);
        }
    }

    // Implementation methods
    // -------------------------------------------------------------------------
    protected void handleException(Exception e) {
        // ignore introspection exceptions
    }
}

