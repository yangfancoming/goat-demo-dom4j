

package org.dom4j.datatype;

import com.sun.msv.datatype.DatabindableDatatype;
import com.sun.msv.datatype.SerializationContext;
import com.sun.msv.datatype.xsd.XSDatatype;
import org.dom4j.Element;
import org.dom4j.Namespace;
import org.dom4j.Node;
import org.dom4j.QName;
import org.dom4j.tree.DefaultElement;
import org.relaxng.datatype.DatatypeException;
import org.relaxng.datatype.ValidationContext;

/**
 * <p>
 * <code>DatatypeElement</code> represents an Element which supports the <a
 * href="http://www.w3.org/TR/xmlschema-2/">XML Schema Data Types </a>
 * specification.
 * </p>
 * 
 * @author <a href="mailto:james.strachan@metastuff.com">James Strachan </a>
 * @version $Revision: 1.9 $
 */
public class DatatypeElement extends DefaultElement implements
        SerializationContext, ValidationContext {
    /** The <code>XSDatatype</code> of the <code>Attribute</code> */
    private XSDatatype datatype;

    /** The data (Object) value of the <code>Attribute</code> */
    private Object data;

    public DatatypeElement(QName qname, XSDatatype datatype) {
        super(qname);
        this.datatype = datatype;
    }

    public DatatypeElement(QName qname, int attributeCount, XSDatatype type) {
        super(qname, attributeCount);
        this.datatype = type;
    }

    public String toString() {
        return getClass().getName() + hashCode() + " [Element: <"
                + getQualifiedName() + " attributes: " + attributeList()
                + " data: " + getData() + " />]";
    }

    /**
     * Returns the MSV XSDatatype for this node
     * 
     * @return DOCUMENT ME!
     */
    public XSDatatype getXSDatatype() {
        return datatype;
    }

    // SerializationContext interface
    // -------------------------------------------------------------------------
    public String getNamespacePrefix(String uri) {
        Namespace namespace = getNamespaceForURI(uri);

        return (namespace != null) ? namespace.getPrefix() : null;
    }

    // ValidationContext interface
    // -------------------------------------------------------------------------
    public String getBaseUri() {
        // XXXX: could we use a Document for this?
        return null;
    }

    public boolean isNotation(String notationName) {
        // XXXX: no way to do this yet in dom4j so assume false
        return false;
    }

    public boolean isUnparsedEntity(String entityName) {
        // XXXX: no way to do this yet in dom4j so assume valid
        return true;
    }

    public String resolveNamespacePrefix(String prefix) {
        Namespace namespace = getNamespaceForPrefix(prefix);

        if (namespace != null) {
            return namespace.getURI();
        }

        return null;
    }

    // Element interface
    // -------------------------------------------------------------------------
    public Object getData() {
        if (data == null) {
            String text = getTextTrim();

            if ((text != null) && (text.length() > 0)) {
                if (datatype instanceof DatabindableDatatype) {
                    DatabindableDatatype bind = datatype;
                    data = bind.createJavaObject(text, this);
                } else {
                    data = datatype.createValue(text, this);
                }
            }
        }

        return data;
    }

    public void setData(Object data) {
        String s = datatype.convertToLexicalValue(data, this);
        validate(s);
        this.data = data;
        setText(s);
    }

    public Element addText(String text) {
        validate(text);

        return super.addText(text);
    }

    public void setText(String text) {
        validate(text);
        super.setText(text);
    }

    // Implementation methods
    // -------------------------------------------------------------------------

    /**
     * Override to force lazy recreation of data object
     * 
     * @param node
     *            DOCUMENT ME!
     */
    protected void childAdded(Node node) {
        data = null;
        super.childAdded(node);
    }

    /**
     * Override to force lazy recreation of data object
     * 
     * @param node
     *            DOCUMENT ME!
     */
    protected void childRemoved(Node node) {
        data = null;
        super.childRemoved(node);
    }

    protected void validate(String text) throws IllegalArgumentException {
        try {
            datatype.checkValid(text, this);
        } catch (DatatypeException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }
}

