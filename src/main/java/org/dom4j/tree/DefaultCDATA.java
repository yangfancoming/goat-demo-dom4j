

package org.dom4j.tree;

import org.dom4j.Element;

/**
 * <p>
 * <code>DefaultCDATA</code> is the default CDATA implementation. It is a
 * doubly linked node which supports the parent relationship and can be modified
 * in place.
 * </p>
 * 
 * @author <a href="mailto:jstrachan@apache.org">James Strachan </a>
 * @version $Revision: 1.12 $
 */
public class DefaultCDATA extends FlyweightCDATA {
    /** The parent of this node */
    private Element parent;

    /**
     * DOCUMENT ME!
     * 
     * @param text
     *            is the CDATA text
     */
    public DefaultCDATA(String text) {
        super(text);
    }

    /**
     * DOCUMENT ME!
     * 
     * @param parent
     *            is the parent element
     * @param text
     *            is the CDATA text
     */
    public DefaultCDATA(Element parent, String text) {
        super(text);
        this.parent = parent;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Element getParent() {
        return parent;
    }

    public void setParent(Element parent) {
        this.parent = parent;
    }

    public boolean supportsParent() {
        return true;
    }

    public boolean isReadOnly() {
        return false;
    }
}

