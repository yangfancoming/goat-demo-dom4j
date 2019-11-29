

package org.dom4j.datatype;

import com.sun.msv.datatype.xsd.XSDatatype;
import org.dom4j.Attribute;
import org.dom4j.DocumentFactory;
import org.dom4j.Element;
import org.dom4j.QName;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * <code>DatatypeElementFactory</code> is a factory for a specific Element in
 * an XML Schema.
 * </p>
 * 
 * @author <a href="mailto:jstrachan@apache.org">James Strachan </a>
 * @author Yuxin Ruan
 * @version $Revision: 1.9 $
 */
public class DatatypeElementFactory extends DocumentFactory {
    private QName elementQName;

    /**
     * Cache of <code>XSDatatype</code> instances per Attribute
     * <code>QName</code>
     */
    private Map<QName, XSDatatype> attributeXSDatatypes = new HashMap<QName, XSDatatype>();

    /**
     * Cache of <code>XSDatatype</code> instances per child Element
     * <code>QName</code>
     */
    private Map<QName, XSDatatype> childrenXSDatatypes = new HashMap<QName, XSDatatype>();

    public DatatypeElementFactory(QName elementQName) {
        this.elementQName = elementQName;
    }

    /**
     * DOCUMENT ME!
     * 
     * @return the QName this element factory is associated with
     */
    public QName getQName() {
        return elementQName;
    }

    /**
     * DOCUMENT ME!
     * 
     * @param attributeQName
     *            DOCUMENT ME!
     * 
     * @return the <code>XSDatatype</code> associated with the given Attribute
     *         QName
     */
    public XSDatatype getAttributeXSDatatype(QName attributeQName) {
        return attributeXSDatatypes.get(attributeQName);
    }

    /**
     * Registers the given <code>XSDatatype</code> for the given
     * &lt;attribute&gt; QNames
     * 
     * @param attributeQName
     *            DOCUMENT ME!
     * @param type
     *            DOCUMENT ME!
     */
    public void setAttributeXSDatatype(QName attributeQName, XSDatatype type) {
        attributeXSDatatypes.put(attributeQName, type);
    }

    /**
     * DOCUMENT ME!
     * 
     * @param qname
     *            DOCUMENT ME!
     * 
     * @return the <code>XSDatatype</code> associated with the given child
     *         Element QName
     */
    public XSDatatype getChildElementXSDatatype(QName qname) {
        return childrenXSDatatypes.get(qname);
    }

    public void setChildElementXSDatatype(QName qname, XSDatatype dataType) {
        childrenXSDatatypes.put(qname, dataType);
    }

    // DocumentFactory methods
    // -------------------------------------------------------------------------
    public Element createElement(QName qname) {
        // the element may have its own element factory!
        // use factory from the qname for datatype
        XSDatatype dataType = getChildElementXSDatatype(qname);

        if (dataType != null) {
            return new DatatypeElement(qname, dataType);
        }

        DocumentFactory factory = qname.getDocumentFactory();

        if (factory instanceof DatatypeElementFactory) {
            DatatypeElementFactory dtFactory = (DatatypeElementFactory) factory;
            dataType = dtFactory.getChildElementXSDatatype(qname);

            if (dataType != null) {
                return new DatatypeElement(qname, dataType);
            }
        }

        return super.createElement(qname);
    }

    public Attribute createAttribute(Element owner, QName qname, String value) {
        XSDatatype dataType = getAttributeXSDatatype(qname);

        if (dataType == null) {
            return super.createAttribute(owner, qname, value);
        } else {
            return new DatatypeAttribute(qname, dataType, value);
        }
    }
}

