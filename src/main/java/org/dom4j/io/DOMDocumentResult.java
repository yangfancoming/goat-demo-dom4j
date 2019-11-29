

package org.dom4j.io;

import org.w3c.dom.Document;
import org.xml.sax.ContentHandler;
import org.xml.sax.ext.LexicalHandler;

import javax.xml.transform.sax.SAXResult;

/**
 * <p>
 * <code>DOMDocumentResult</code> implements a JAXP {@link SAXResult} for a
 * {@link Document}.
 * </p>
 * 
 * @author <a href="mailto:james.strachan@metastuff.com">James Strachan </a>
 * @author Todd Wolff
 * @version $Revision: 1.1 $
 */
public class DOMDocumentResult extends SAXResult {
    private DOMSAXContentHandler contentHandler;

    public DOMDocumentResult() {
        this(new DOMSAXContentHandler());
    }

    public DOMDocumentResult(DOMSAXContentHandler contentHandler) {
        this.contentHandler = contentHandler;
        super.setHandler(this.contentHandler);
        super.setLexicalHandler(this.contentHandler);
    }

    /**
     * Retrieves w3c dom object generated via transformation
     * 
     * @return the Document created by the transformation
     */
    public Document getDocument() {
        return contentHandler.getDocument();
    }

    // Overloaded methods
    // -------------------------------------------------------------------------
    public void setHandler(ContentHandler handler) {
        if (handler instanceof DOMSAXContentHandler) {
            this.contentHandler = (DOMSAXContentHandler) handler;
            super.setHandler(this.contentHandler);
        }
    }

    public void setLexicalHandler(LexicalHandler handler) {
        if (handler instanceof DOMSAXContentHandler) {
            this.contentHandler = (DOMSAXContentHandler) handler;
            super.setLexicalHandler(this.contentHandler);
        }
    }
    
}

