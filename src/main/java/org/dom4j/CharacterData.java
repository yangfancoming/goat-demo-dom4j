

package org.dom4j;

/**
 * <code>CharacterData</code> is a marker interface for character based nodes
 * such as the <code>CDATA</code>,<code>Comment</code> and
 * <code>Text</code> nodes.
 *
 * @author <a href="mailto:jstrachan@apache.org">James Strachan </a>
 * @version $Revision: 1.9 $
 */
@SuppressWarnings("unused")
public interface CharacterData extends Node {
    /**
     * Appends the given text to this nodes text value. Calling this method is
     * equivalent of the code <code>node.setText(node.getText() + text)</code>
     * but allows for possible implementation optimisations (such as a text
     * based node storing a StringBuffer internally
     * 
     * @param text
     *            the text to append
     */
    void appendText(String text);
}

