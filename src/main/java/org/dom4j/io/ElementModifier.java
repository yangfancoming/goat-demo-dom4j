

package org.dom4j.io;

import org.dom4j.Element;

/**
 * ElementModifier defines a modifier of {@link org.dom4j.Element}objects. <br>
 * It can be used in the event based {@link org.dom4j.io.SAXModifier}, in order
 * to modify elements on the fly, rather than waiting until the complete
 * document is parsed.
 * 
 * @author Wonne Keysers (Realsoftware.be)
 */
public interface ElementModifier {
    /**
     * Called by an event based processor when an elements closing tag is
     * encountered. This method must return the modified version of the provided
     * {@link org.dom4j.Element}or null if it has to be removed from the
     * document. <br>
     * The incoming {@link org.dom4j.Element}is disconnected from the DOM4J
     * tree. This means that navigation to the elements parent {@link
     * org.dom4j.Element} and {@link org.dom4j.Document}are not available. Only
     * the element itself can be modified!
     * 
     * @param element
     *            {@link org.dom4j.Element}to be parsed
     * 
     * @return the modified {@link org.dom4j.Element}
     * 
     * @throws Exception
     *             of any kind
     */
    Element modifyElement(Element element) throws Exception;
}

