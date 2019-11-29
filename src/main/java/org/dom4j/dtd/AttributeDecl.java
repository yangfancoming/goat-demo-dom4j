

package org.dom4j.dtd;

/**
 * <p>
 * <code>AttributeDecl</code> represents an attribute declaration in a DTD.
 * </p>
 * 
 * @author <a href="mailto:james.strachan@metastuff.com">James Strachan </a>
 * @version $Revision: 1.7 $
 */
public class AttributeDecl implements Decl {
    /** Holds value of property elementName. */
    private String elementName;

    /** Holds value of property attributeName. */
    private String attributeName;

    /** Holds value of property type. */
    private String type;

    /** Holds value of property value. */
    private String value;

    /** Holds value of property valueDefault. */
    private String valueDefault;

    public AttributeDecl() {
    }

    public AttributeDecl(String elementName, String attributeName, String type,
            String valueDefault, String value) {
        this.elementName = elementName;
        this.attributeName = attributeName;
        this.type = type;
        this.value = value;
        this.valueDefault = valueDefault;
    }

    /**
     * Getter for property elementName.
     * 
     * @return Value of property elementName.
     */
    public String getElementName() {
        return elementName;
    }

    /**
     * Setter for property elementName.
     * 
     * @param elementName
     *            New value of property elementName.
     */
    public void setElementName(String elementName) {
        this.elementName = elementName;
    }

    /**
     * Getter for property attributeName.
     * 
     * @return Value of property attributeName.
     */
    public String getAttributeName() {
        return attributeName;
    }

    /**
     * Setter for property attributeName.
     * 
     * @param attributeName
     *            New value of property attributeName.
     */
    public void setAttributeName(String attributeName) {
        this.attributeName = attributeName;
    }

    /**
     * Getter for property type.
     * 
     * @return Value of property type.
     */
    public String getType() {
        return type;
    }

    /**
     * Setter for property type.
     * 
     * @param type
     *            New value of property type.
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Getter for property value.
     * 
     * @return Value of property value.
     */
    public String getValue() {
        return value;
    }

    /**
     * Setter for property value.
     * 
     * @param value
     *            New value of property value.
     */
    public void setValue(String value) {
        this.value = value;
    }

    /**
     * Getter for property valueDefault.
     * 
     * @return Value of property valueDefault.
     */
    public String getValueDefault() {
        return valueDefault;
    }

    /**
     * Setter for property valueDefault.
     * 
     * @param valueDefault
     *            New value of property valueDefault.
     */
    public void setValueDefault(String valueDefault) {
        this.valueDefault = valueDefault;
    }

    public String toString() {
        StringBuffer buffer = new StringBuffer("<!ATTLIST ");
        buffer.append(elementName);
        buffer.append(" ");
        buffer.append(attributeName);
        buffer.append(" ");
        buffer.append(type);
        buffer.append(" ");

        if (valueDefault != null) {
            buffer.append(valueDefault);

            if (valueDefault.equals("#FIXED")) {
                buffer.append(" \"");
                buffer.append(value);
                buffer.append("\"");
            }
        } else {
            buffer.append("\"");
            buffer.append(value);
            buffer.append("\"");
        }

        buffer.append(">");

        return buffer.toString();
    }
}

