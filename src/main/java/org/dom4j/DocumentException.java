

package org.dom4j;

/**
 * <code>DocumentException</code> is a nested Exception which may be thrown during the processing of a DOM4J document.
 *
 * @author <a href="mailto:james.strachan@metastuff.com">James Strachan </a>
 * @author Filip Jirsák
 */
public class DocumentException extends Exception {

    public DocumentException() {
    }

    public DocumentException(String message) {
        super(message);
    }

    public DocumentException(String message, Throwable cause) {
        super(message, cause);
    }

    public DocumentException(Throwable cause) {
        super(cause);
    }

    @Deprecated
    public Throwable getNestedException() {
        return null;
    }
}

