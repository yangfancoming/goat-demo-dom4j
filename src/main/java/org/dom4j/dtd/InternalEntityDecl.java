

package org.dom4j.dtd;

/**
 * <p>
 * <code>InternalEntityDecl</code> represents an internal entity declaration
 * in a DTD.
 * </p>
 * 
 * @author <a href="mailto:james.strachan@metastuff.com">James Strachan </a>
 * @version $Revision: 1.9 $
 */
public class InternalEntityDecl implements Decl {
    /** Holds value of property name. */
    private String name;

    /** Holds value of property value. */
    private String value;

    public InternalEntityDecl() {
    }

    public InternalEntityDecl(String name, String value) {
        this.name = name;
        this.value = value;
    }

    /**
     * Getter for property name.
     * 
     * @return Value of property name.
     */
    public String getName() {
        return name;
    }

    /**
     * Setter for property name.
     * 
     * @param name
     *            New value of property name.
     */
    public void setName(String name) {
        this.name = name;
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

    public String toString() {
        StringBuffer buffer = new StringBuffer("<!ENTITY ");

        if (name.startsWith("%")) {
            buffer.append("% ");
            buffer.append(name.substring(1));
        } else {
            buffer.append(name);
        }

        buffer.append(" \"");
        buffer.append(escapeEntityValue(value));
        buffer.append("\">");

        return buffer.toString();
    }

    private String escapeEntityValue(String text) {
        StringBuffer result = new StringBuffer();

        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);

            switch (c) {
                case '<':
                    result.append("&#38;#60;");

                    break;

                case '>':
                    result.append("&#62;");

                    break;

                case '&':
                    result.append("&#38;#38;");

                    break;

                case '\'':
                    result.append("&#39;");

                    break;

                case '\"':
                    result.append("&#34;");

                    break;

                default:

                    if (c < 32) {
                        result.append("&#" + (int) c + ";");
                    } else {
                        result.append(c);
                    }

                    break;
            }
        }

        return result.toString();
    }
}

