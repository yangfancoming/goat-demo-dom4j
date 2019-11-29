

package org.dom4j.tree;

import org.dom4j.CharacterData;
import org.dom4j.Element;

/**
 * <p>
 * <code>AbstractCharacterData</code> is an abstract base class for tree
 * implementors to use for implementation inheritence.
 * </p>
 * 
 * @author <a href="mailto:jstrachan@apache.org">James Strachan </a>
 * @version $Revision: 1.12 $
 */
public abstract class AbstractCharacterData extends AbstractNode implements CharacterData {
    public AbstractCharacterData() {
    }

    public String getPath(Element context) {
        Element parent = getParent();

        return ((parent != null) && (parent != context)) ? (parent
                .getPath(context) + "/text()") : "text()";
    }

    public String getUniquePath(Element context) {
        Element parent = getParent();

        return ((parent != null) && (parent != context)) ? (parent
                .getUniquePath(context) + "/text()") : "text()";
    }

    public void appendText(String text) {
        setText(getText() + text);
    }
}

