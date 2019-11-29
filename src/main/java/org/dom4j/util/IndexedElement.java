

package org.dom4j.util;

import org.dom4j.Attribute;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.QName;
import org.dom4j.tree.BackedList;
import org.dom4j.tree.DefaultElement;

import java.util.*;

/**
 * <code>IndexedElement</code> is an implementation of {@link Element}which
 * maintains an index of the attributes and elements it contains to optimise
 * lookups via name.
 *
 * @author <a href="mailto:james.strachan@metastuff.com">James Strachan </a>
 * @version $Revision: 1.10 $
 */
@SuppressWarnings("unused")
public class IndexedElement extends DefaultElement {
    /**
     * Lazily constructed index for elements
     *
     * Keys are of type QName and String
     *
     * Values are of type Element and List&lt;Element&gt;
     */

    private Map<Object, Object> elementIndex;

    /**
     * Lazily constructed index for attributes

     * Keys are of type QName and String
     *
     * Values are of type &lt;A&gt;
     */
    private Map<Object, Attribute> attributeIndex;

    public IndexedElement(String name) {
        super(name);
    }

    public IndexedElement(QName qname) {
        super(qname);
    }

    public IndexedElement(QName qname, int attributeCount) {
        super(qname, attributeCount);
    }

    public Attribute attribute(String name) {
        return attributeIndex().get(name);
    }

    public Attribute attribute(QName qName) {
        return attributeIndex().get(qName);
    }

    public Element element(String name) {
        return asElement(elementIndex().get(name));
    }

    public Element element(QName qName) {
        return asElement(elementIndex().get(qName));
    }

    public List<Element> elements(String name) {
        return asElementList(elementIndex().get(name));
    }

    public List<Element> elements(QName qName) {
        return asElementList(elementIndex().get(qName));
    }

    // Implementation methods
    // -------------------------------------------------------------------------
    protected Element asElement(Object object) {
        if (object instanceof Element) {
            return (Element) object;
        } else if (object != null) {
            List<Element> list = (List<Element>) object;

            if (list.size() >= 1) {
                return list.get(0);
            }
        }

        return null;
    }

    protected List<Element> asElementList(Object object) {
        if (object instanceof Element) {
            return createSingleResultList((Element) object);
        } else if (object != null) {
            List<Element> list = (List<Element>) object;
            BackedList<Element> answer = createResultList();

            for (Element aList : list) {
                answer.addLocal(aList);
            }

            return answer;
        }

        return createEmptyList();
    }

    /**
     * DOCUMENT ME!
     * 
     * @param object
     *            DOCUMENT ME!
     * 
     * @return DOCUMENT ME!
     * 
     * @deprecated WILL BE REMOVED IN dom4j-1.6 !!
     */
    protected Iterator<Element> asElementIterator(Object object) {
        return asElementList(object).iterator();
    }

    // #### could we override the add(Element) remove(Element methods?
    protected void addNode(Node node) {
        super.addNode(node);

        if ((elementIndex != null) && node instanceof Element) {
            addToElementIndex((Element) node);
        } else if ((attributeIndex != null) && node instanceof Attribute) {
            addToAttributeIndex((Attribute) node);
        }
    }

    protected boolean removeNode(Node node) {
        if (super.removeNode(node)) {
            if ((elementIndex != null) && node instanceof Element) {
                removeFromElementIndex((Element) node);
            } else if ((attributeIndex != null) && node instanceof Attribute) {
                removeFromAttributeIndex((Attribute) node);
            }

            return true;
        }

        return false;
    }

    protected Map<Object, Attribute> attributeIndex() {
        if (attributeIndex == null) {
            attributeIndex = createAttributeIndex();

            for (Iterator<Attribute> iter = attributeIterator(); iter.hasNext();) {
                addToAttributeIndex(iter.next());
            }
        }

        return attributeIndex;
    }

    protected Map<Object, Object> elementIndex() {
        if (elementIndex == null) {
            elementIndex = createElementIndex();

            for (Iterator<Element> iter = elementIterator(); iter.hasNext();) {
                addToElementIndex(iter.next());
            }
        }

        return elementIndex;
    }

    /**
     * A Factory Method to create the index for attributes
     * 
     * @return DOCUMENT ME!
     */
    protected Map<Object, Attribute> createAttributeIndex() {

        return createIndex();
    }

    /**
     * A Factory Method to create the index for elements
     * 
     * @return DOCUMENT ME!
     */
    protected Map<Object, Object> createElementIndex() {

        return createIndex();
    }

    protected void addToElementIndex(Element element) {
        QName qName = element.getQName();
        String name = qName.getName();
        addToElementIndex(qName, element);
        addToElementIndex(name, element);
    }

    protected void addToElementIndex(Object key, Element value) {
        Object oldValue = elementIndex.get(key);

        if (oldValue == null) {
            elementIndex.put(key, value);
        } else {
            if (oldValue instanceof List) {
                List<Element> list = (List<Element>) oldValue;
                list.add(value);
            } else {
                List<Element> list = createList();
                list.add((Element) oldValue);
                list.add(value);
                elementIndex.put(key, list);
            }
        }
    }

    protected void removeFromElementIndex(Element element) {
        QName qName = element.getQName();
        String name = qName.getName();
        removeFromElementIndex(qName, element);
        removeFromElementIndex(name, element);
    }

    protected void removeFromElementIndex(Object key, Element value) {
        Object oldValue = elementIndex.get(key);

        if (oldValue instanceof List) {
            List<Element> list = (List<Element>) oldValue;
            list.remove(value);
        } else {
            elementIndex.remove(key);
        }
    }

    protected void addToAttributeIndex(Attribute attribute) {
        QName qName = attribute.getQName();
        String name = qName.getName();
        addToAttributeIndex(qName, attribute);
        addToAttributeIndex(name, attribute);
    }

    protected void addToAttributeIndex(Object key, Attribute value) {
        Object oldValue = attributeIndex.get(key);

        if (oldValue != null) {
            attributeIndex.put(key, value);
        }
    }

    protected void removeFromAttributeIndex(Attribute attribute) {
        QName qName = attribute.getQName();
        String name = qName.getName();
        removeFromAttributeIndex(qName, attribute);
        removeFromAttributeIndex(name, attribute);
    }

    protected void removeFromAttributeIndex(Object key, Attribute value) {
        Object oldValue = attributeIndex.get(key);

        if ((oldValue != null) && oldValue.equals(value)) {
            attributeIndex.remove(key);
        }
    }

    /**
     * Factory method to return a new map implementation for indices
     *
     * @param <T> DOCUMENT ME!
     * @return DOCUMENT ME!
     */
    protected <T> Map<Object, T> createIndex() {
        return new HashMap<Object, T>();
    }

    /**
     * Factory method to return a list implementation for indices
     *
     * @param <T> DOCUMENT ME!
     * @return DOCUMENT ME!
     */
    protected <T extends Node> List<T> createList() {
        return new ArrayList<T>();
    }
}

