

package org.dom4j.tree;

import org.dom4j.Element;
import org.dom4j.Node;

import java.util.Iterator;

/**
 * <p>
 * <code>ElementIterator</code> is a filtering {@link Iterator}which filters
 * out objects which do not implement the {@link Element}interface.
 * </p>
 * 
 * @author <a href="mailto:james.strachan@metastuff.com">James Strachan </a>
 * @version $Revision: 1.8 $
 * 
 * @deprecated THIS CLASS WILL BE REMOVED IN dom4j-1.6 !!
 */
public class ElementIterator extends FilterIterator<Node> {
    public ElementIterator(Iterator<Node> proxy) {
        super(proxy);
    }

    /**
     * DOCUMENT ME!
     * 
     * @param element
     *            DOCUMENT ME!
     * 
     * @return true if the given element implements the {@link Element}
     *         interface
     */
    protected boolean matches(Node element) {
        return element instanceof Element;
    }
}

