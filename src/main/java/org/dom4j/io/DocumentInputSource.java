

package org.dom4j.io;

import org.dom4j.Document;
import org.xml.sax.InputSource;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;

/**
 * <p>
 * <code>DocumentInputSource</code> implements a SAX {@link InputSource}for a
 * {@link Document}.
 * </p>
 * 
 * @author <a href="mailto:james.strachan@metastuff.com">James Strachan </a>
 * @version $Revision: 1.8 $
 */
class DocumentInputSource extends InputSource {
    /** The document source */
    private Document document;

    public DocumentInputSource() {
    }

    public DocumentInputSource(Document document) {
        this.document = document;
        setSystemId(document.getName());
    }

    // Properties
    // -------------------------------------------------------------------------

    /**
     * DOCUMENT ME!
     * 
     * @return the document which is being used as the SAX {@link InputSource}
     */
    public Document getDocument() {
        return document;
    }

    /**
     * Sets the document used as the SAX {@link InputSource}
     * 
     * @param document
     *            DOCUMENT ME!
     */
    public void setDocument(Document document) {
        this.document = document;
        setSystemId(document.getName());
    }

    // Overloaded methods
    // -------------------------------------------------------------------------

    /**
     * This method is not supported as this source is always a {@linkDocument}
     * instance.
     * 
     * @param characterStream
     *            DOCUMENT ME!
     * 
     * @throws UnsupportedOperationException
     *             as this method is unsupported
     */
    public void setCharacterStream(Reader characterStream)
            throws UnsupportedOperationException {
        throw new UnsupportedOperationException();
    }

    /**
     * Note this method is quite inefficent, it turns the in memory XML tree
     * object model into a single block of text which can then be read by other
     * XML parsers. Should only be used with care.
     * 
     * @return DOCUMENT ME!
     */
    public Reader getCharacterStream() {
        try {
            StringWriter out = new StringWriter();
            XMLWriter writer = new XMLWriter(out);
            writer.write(document);
            writer.flush();

            return new StringReader(out.toString());
        } catch (final IOException e) {
            // this should never really happen
            // but for completeness we'll return a Reader
            // with the embedded exception inside it
            return new Reader() {
                public int read(char[] ch, int offset, int length)
                        throws IOException {
                    throw e;
                }

                public void close() throws IOException {
                }
            };
        }
    }
}

