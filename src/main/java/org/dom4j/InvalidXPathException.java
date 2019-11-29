

package org.dom4j;

/**
 * <code>InvalidXPathException</code> is thrown when an invalid XPath
 * expression is used to traverse an XML document
 *
 * @version $Revision: 1.6 $
 */
public class InvalidXPathException extends IllegalArgumentException {
    public InvalidXPathException(String xpath) {
        super("Invalid XPath expression: " + xpath);
    }

    public InvalidXPathException(String xpath, String reason) {
        super("Invalid XPath expression: " + xpath + " " + reason);
    }
}

