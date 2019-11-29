

package org.dom4j.io;

import org.dom4j.Document;
import org.dom4j.Node;
import org.xml.sax.InputSource;
import org.xml.sax.XMLFilter;
import org.xml.sax.XMLReader;

import javax.xml.transform.sax.SAXSource;

/**
 * <code>DocumentSource</code> implements a JAXP {@link SAXSource}for a
 * {@link Document}.
 *
 * @author <a href="mailto:james.strachan@metastuff.com">James Strachan </a>
 * @version $Revision: 1.10 $
 */
public class DocumentSource extends SAXSource {
    /**
     * If {@link javax.xml.transform.TransformerFactory#getFeature}returns
     * <code>true</code> when passed this value as an argument then the
     * Transformer natively supports <i>dom4j </i>.
     */
    public static final String DOM4J_FEATURE 
            = "http://org.dom4j.io.DoucmentSource/feature";

    /** The XMLReader to use */
    private XMLReader xmlReader = new SAXWriter();

    /**
     * Creates a JAXP {@link SAXSource}for the given {@link Node}.
     * 
     * @param node
     *            DOCUMENT ME!
     */
    public DocumentSource(Node node) {
        setDocument(node.getDocument());
    }

    /**
     * Creates a JAXP {@link SAXSource}for the given {@link Document}.
     * 
     * @param document
     *            DOCUMENT ME!
     */
    public DocumentSource(Document document) {
        setDocument(document);
    }

    // Properties
    // -------------------------------------------------------------------------

    /**
     * DOCUMENT ME!
     * 
     * @return the document which is being used as the JAXP {@link SAXSource}
     */
    public Document getDocument() {
        DocumentInputSource source = (DocumentInputSource) getInputSource();
        return source.getDocument();
    }

    /**
     * Sets the document used as the JAXP {@link SAXSource}
     * 
     * @param document
     *            DOCUMENT ME!
     */
    public void setDocument(Document document) {
        super.setInputSource(new DocumentInputSource(document));
    }

    // Overloaded methods
    // -------------------------------------------------------------------------

    /**
     * DOCUMENT ME!
     * 
     * @return the XMLReader to be used for the JAXP {@link SAXSource}.
     */
    public XMLReader getXMLReader() {
        return xmlReader;
    }

    /**
     * This method is not supported as this source is always a {@link Document}
     * instance.
     * 
     * @param inputSource
     *            DOCUMENT ME!
     * 
     * @throws UnsupportedOperationException
     *             as this method is unsupported
     */
    public void setInputSource(InputSource inputSource)
            throws UnsupportedOperationException {
        if (inputSource instanceof DocumentInputSource) {
            super.setInputSource((DocumentInputSource) inputSource);
        } else {
            throw new UnsupportedOperationException();
        }
    }

    /**
     * Sets the XMLReader used for the JAXP {@link SAXSource}.
     * 
     * @param reader
     *            DOCUMENT ME!
     * 
     * @throws UnsupportedOperationException
     *             DOCUMENT ME!
     */
    public void setXMLReader(XMLReader reader)
            throws UnsupportedOperationException {
        if (reader instanceof SAXWriter) {
            this.xmlReader = (SAXWriter) reader;
        } else if (reader instanceof XMLFilter) {
            XMLFilter filter = (XMLFilter) reader;

            while (true) {
                XMLReader parent = filter.getParent();

                if (parent instanceof XMLFilter) {
                    filter = (XMLFilter) parent;
                } else {
                    break;
                }
            }

            // install filter in SAXWriter....
            filter.setParent(xmlReader);
            xmlReader = filter;
        } else {
            throw new UnsupportedOperationException();
        }
    }
}

