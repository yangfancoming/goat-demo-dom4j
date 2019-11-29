

package org.dom4j.tree;

import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.QName;

import java.util.Iterator;

/**
 * <p>
 * <code>ElementQNameIterator</code> is a filtering {@link Iterator}which
 * filters out objects which do not implement the {@link Element}interface and
 * are not of the correct fully qualified element name.
 * </p>
 * 
 * @author <a href="mailto:james.strachan@metastuff.com">James Strachan </a>
 * @version $Revision: 1.7 $
 * 
 * @deprecated THIS CLASS WILL BE REMOVED IN dom4j-1.6 !!
 */
public class ElementQNameIterator extends FilterIterator<Node> {
    private QName qName;

    public ElementQNameIterator(Iterator<Node> proxy, QName qName) {
        super(proxy);
        this.qName = qName;
    }

    /**
     * DOCUMENT ME!
     * 
     * @param object
     *            DOCUMENT ME!
     * 
     * @return true if the given element implements the {@link Element}
     *         interface and matches the given {@link QName}
     */
    protected boolean matches(Node object) {
        if (object instanceof Element) {
            Element element = (Element) object;

            return qName.equals(element.getQName());
        }

        return false;
    }
}

