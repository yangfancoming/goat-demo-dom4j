

package org.dom4j.io;

import org.dom4j.Document;
import org.xml.sax.ContentHandler;
import org.xml.sax.ext.LexicalHandler;

import javax.xml.transform.sax.SAXResult;

/**
 * <p>
 * <code>DocumentResult</code> implements a JAXP {@link SAXResult}for a
 * {@link Document}.
 * </p>
 * 
 * @author <a href="mailto:james.strachan@metastuff.com">James Strachan </a>
 * @version $Revision: 1.8 $
 */
public class DocumentResult extends SAXResult {
    private SAXContentHandler contentHandler;

    public DocumentResult() {
        this(new SAXContentHandler());
    }

    public DocumentResult(SAXContentHandler contentHandler) {
        this.contentHandler = contentHandler;
        super.setHandler(this.contentHandler);
        super.setLexicalHandler(this.contentHandler);
    }

    /**
     * DOCUMENT ME!
     * 
     * @return the Document created by the transformation
     */
    public Document getDocument() {
        return contentHandler.getDocument();
    }

    // Overloaded methods
    // -------------------------------------------------------------------------
    public void setHandler(ContentHandler handler) {
        if (handler instanceof SAXContentHandler) {
            this.contentHandler = (SAXContentHandler) handler;
            super.setHandler(this.contentHandler);
        }
    }

    public void setLexicalHandler(LexicalHandler handler) {
        if (handler instanceof SAXContentHandler) {
            this.contentHandler = (SAXContentHandler) handler;
            super.setLexicalHandler(this.contentHandler);
        }
    }
}

