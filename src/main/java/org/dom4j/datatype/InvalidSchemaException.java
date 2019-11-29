

package org.dom4j.datatype;

/**
 * <p>
 * <code>InvalidSchemaException</code> is thrown when an invalid XML Schema
 * document is used
 * </p>
 * 
 * @version $Revision: 1.6 $
 */
public class InvalidSchemaException extends IllegalArgumentException {
    public InvalidSchemaException(String reason) {
        super(reason);
    }
}

