

package org.dom4j.tree;

import org.dom4j.dtd.Decl;

import java.util.List;

/**
 * <p>
 * <code>DefaultDocumentType</code> is the DOM4J default implementation of an
 * XML document type.
 * </p>
 * 
 * @author <a href="mailto:james.strachan@metastuff.com">James Strachan </a>
 * @version $Revision: 1.10 $
 */
public class DefaultDocumentType extends AbstractDocumentType {
    /** The root element name of the document typ */
    protected String elementName;

    /** Holds value of property publicID. */
    private String publicID;

    /** Holds value of property systemID. */
    private String systemID;

    /** The internal DTD declarations */
    private List<Decl> internalDeclarations;

    /** The external DTD declarations */
    private List<Decl> externalDeclarations;

    public DefaultDocumentType() {
    }

    /**
     * <p>
     * This will create a new <code>DocumentType</code> with a reference to
     * the external DTD
     * </p>
     * 
     * @param elementName
     *            is the root element name of the document type
     * @param systemID
     *            is the system ID of the external DTD
     */
    public DefaultDocumentType(String elementName, String systemID) {
        this.elementName = elementName;
        this.systemID = systemID;
    }

    /**
     * <p>
     * This will create a new <code>DocumentType</code> with a reference to
     * the external DTD
     * </p>
     * 
     * @param elementName
     *            is the root element name of the document type
     * @param publicID
     *            is the public ID of the DTD
     * @param systemID
     *            is the system ID of the DTD
     */
    public DefaultDocumentType(String elementName, String publicID,
            String systemID) {
        this.elementName = elementName;
        this.publicID = publicID;
        this.systemID = systemID;
    }

    public String getElementName() {
        return elementName;
    }

    public void setElementName(String elementName) {
        this.elementName = elementName;
    }

    /**
     * DOCUMENT ME!
     * 
     * @return the public ID of the document type
     */
    public String getPublicID() {
        return publicID;
    }

    /**
     * Sets the public ID of the document type
     * 
     * @param publicID
     *            DOCUMENT ME!
     */
    public void setPublicID(String publicID) {
        this.publicID = publicID;
    }

    /**
     * DOCUMENT ME!
     * 
     * @return the system ID of the document type
     */
    public String getSystemID() {
        return systemID;
    }

    /**
     * Sets the system ID of the document type
     * 
     * @param systemID
     *            DOCUMENT ME!
     */
    public void setSystemID(String systemID) {
        this.systemID = systemID;
    }

    public List<Decl> getInternalDeclarations() {
        return internalDeclarations;
    }

    public void setInternalDeclarations(List<Decl> internalDeclarations) {
        this.internalDeclarations = internalDeclarations;
    }

    public List<Decl> getExternalDeclarations() {
        return externalDeclarations;
    }

    public void setExternalDeclarations(List<Decl> externalDeclarations) {
        this.externalDeclarations = externalDeclarations;
    }
}

