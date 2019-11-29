

package org.dom4j.dtd;

/**
 * <p>
 * <code>ExternalEntityDecl</code> represents an external entity declaration
 * in a DTD.
 * </p>
 * 
 * @author <a href="mailto:james.strachan@metastuff.com">James Strachan </a>
 * @version $Revision: 1.9 $
 */
public class ExternalEntityDecl implements Decl {
    /** Holds value of property name. */
    private String name;

    /** Holds value of property publicID. */
    private String publicID;

    /** Holds value of property systemID. */
    private String systemID;

    public ExternalEntityDecl() {
    }

    public ExternalEntityDecl(String name, String publicID, String systemID) {
        this.name = name;
        this.publicID = publicID;
        this.systemID = systemID;
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
     * Getter for property publicID.
     * 
     * @return Value of property publicID.
     */
    public String getPublicID() {
        return publicID;
    }

    /**
     * Setter for property publicID.
     * 
     * @param publicID
     *            New value of property publicID.
     */
    public void setPublicID(String publicID) {
        this.publicID = publicID;
    }

    /**
     * Getter for property systemID.
     * 
     * @return Value of property systemID.
     */
    public String getSystemID() {
        return systemID;
    }

    /**
     * Setter for property systemID.
     * 
     * @param systemID
     *            New value of property systemID.
     */
    public void setSystemID(String systemID) {
        this.systemID = systemID;
    }

    public String toString() {
        StringBuffer buffer = new StringBuffer("<!ENTITY ");

        if (name.startsWith("%")) {
            buffer.append("% ");
            buffer.append(name.substring(1));
        } else {
            buffer.append(name);
        }

        if (publicID != null) {
            buffer.append(" PUBLIC \"");
            buffer.append(publicID);
            buffer.append("\" ");

            if (systemID != null) {
                buffer.append("\"");
                buffer.append(systemID);
                buffer.append("\" ");
            }
        } else if (systemID != null) {
            buffer.append(" SYSTEM \"");
            buffer.append(systemID);
            buffer.append("\" ");
        }

        buffer.append(">");

        return buffer.toString();
    }
}

