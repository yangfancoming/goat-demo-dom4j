

package org.dom4j.datatype;

import org.dom4j.*;
import org.dom4j.io.SAXReader;
import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;

/**
 * <p>
 * <code>DatatypeDocumentFactory</code> is a factory of XML objects which
 * support the <a href="http://www.w3.org/TR/xmlschema-2/">XML Schema Data Types
 * </a> specification.
 * </p>
 * 
 * @author <a href="mailto:jstrachan@apache.org">James Strachan </a>
 * @version $Revision: 1.9 $
 */
public class DatatypeDocumentFactory extends DocumentFactory {
    // XXXX: I don't think interning of QNames is necessary
    private static final boolean DO_INTERN_QNAME = false;

    /** The Singleton instance */
    protected static transient DatatypeDocumentFactory singleton
            = new DatatypeDocumentFactory();

    private static final Namespace XSI_NAMESPACE = Namespace.get("xsi",
            "http://www.w3.org/2001/XMLSchema-instance");

    private static final QName XSI_SCHEMA_LOCATION = QName.get(
            "schemaLocation", XSI_NAMESPACE);

    private static final QName XSI_NO_SCHEMA_LOCATION = QName.get(
            "noNamespaceSchemaLocation", XSI_NAMESPACE);

    /** The builder of XML Schemas */
    private SchemaParser schemaBuilder;

    /** reader of XML Schemas */
    private SAXReader xmlSchemaReader = new SAXReader();

    /** If schemas are automatically loaded when parsing instance documents */
    private boolean autoLoadSchema = true;

    public DatatypeDocumentFactory() {
        schemaBuilder = new SchemaParser(this);
    }

    /**
     * <p>
     * Access to the singleton instance of this factory.
     * </p>
     * 
     * @return the default singleon instance
     */
    public static DocumentFactory getInstance() {
        return singleton;
    }

    /**
     * Loads the given XML Schema document into this factory so schema-aware
     * Document, Elements and Attributes will be created by this factory.
     * 
     * @param schemaDocument
     *            is an XML Schema Document instance.
     */
    public void loadSchema(Document schemaDocument) {
        schemaBuilder.build(schemaDocument);
    }

    public void loadSchema(Document schemaDocument, Namespace targetNamespace) {
        schemaBuilder.build(schemaDocument, targetNamespace);
    }

    /**
     * Registers the given <code>DatatypeElementFactory</code> for the given
     * &lt;element&gt; schema element
     * 
     * @param elementQName
     *            DOCUMENT ME!
     * 
     * @return DOCUMENT ME!
     */
    public DatatypeElementFactory getElementFactory(QName elementQName) {
        DatatypeElementFactory result = null;
        
        if (DO_INTERN_QNAME) {
            elementQName = intern(elementQName);
        }

        DocumentFactory factory = elementQName.getDocumentFactory();
        if (factory instanceof DatatypeElementFactory) {
            result = (DatatypeElementFactory) factory;
        }
        
        return result;
    }

    // DocumentFactory methods
    // -------------------------------------------------------------------------
    public Attribute createAttribute(Element owner, QName qname, String value) {
        if (autoLoadSchema && qname.equals(XSI_NO_SCHEMA_LOCATION)) {
            Document document = (owner != null) ? owner.getDocument() : null;
            loadSchema(document, value);
        } else if (autoLoadSchema && qname.equals(XSI_SCHEMA_LOCATION)) {
            Document document = (owner != null) ? owner.getDocument() : null;
            String uri = value.substring(0, value.indexOf(' '));
            Namespace namespace = owner.getNamespaceForURI(uri);
            loadSchema(document, value.substring(value.indexOf(' ') + 1),
                    namespace);
        }

        return super.createAttribute(owner, qname, value);
    }

    // Implementation methods
    // -------------------------------------------------------------------------
    protected void loadSchema(Document document, String schemaInstanceURI) {
        try {
            EntityResolver resolver = document.getEntityResolver();

            if (resolver == null) {
                String msg = "No EntityResolver available for resolving URI: ";
                throw new InvalidSchemaException(msg + schemaInstanceURI);
            }

            InputSource inputSource = resolver.resolveEntity(null,
                    schemaInstanceURI);

            if (resolver == null) {
                throw new InvalidSchemaException("Could not resolve the URI: "
                        + schemaInstanceURI);
            }

            Document schemaDocument = xmlSchemaReader.read(inputSource);
            loadSchema(schemaDocument);
        } catch (Exception e) {
            System.out.println("Failed to load schema: " + schemaInstanceURI);
            System.out.println("Caught: " + e);
            e.printStackTrace();
            throw new InvalidSchemaException("Failed to load schema: "
                    + schemaInstanceURI);
        }
    }

    protected void loadSchema(Document document, String schemaInstanceURI,
                              Namespace namespace) {
        try {
            EntityResolver resolver = document.getEntityResolver();

            if (resolver == null) {
                String msg = "No EntityResolver available for resolving URI: ";
                throw new InvalidSchemaException(msg + schemaInstanceURI);
            }

            InputSource inputSource = resolver.resolveEntity(null,
                    schemaInstanceURI);

            if (resolver == null) {
                throw new InvalidSchemaException("Could not resolve the URI: "
                        + schemaInstanceURI);
            }

            Document schemaDocument = xmlSchemaReader.read(inputSource);
            loadSchema(schemaDocument, namespace);
        } catch (Exception e) {
            System.out.println("Failed to load schema: " + schemaInstanceURI);
            System.out.println("Caught: " + e);
            e.printStackTrace();
            throw new InvalidSchemaException("Failed to load schema: "
                    + schemaInstanceURI);
        }
    }
}

