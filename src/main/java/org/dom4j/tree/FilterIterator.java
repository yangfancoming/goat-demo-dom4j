

package org.dom4j.tree;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * <p>
 * <code>FilterIterator</code> is an abstract base class which is useful for
 * implementors of {@link Iterator}which filter an existing iterator.
 * </p>
 * 
 * @author <a href="mailto:james.strachan@metastuff.com">James Strachan </a>
 * @version $Revision: 1.10 $
 * 
 * @deprecated THIS CLASS WILL BE REMOVED IN dom4j-1.6 !!
 */
public abstract class FilterIterator<T> implements Iterator<T> {
    protected Iterator<T> proxy;

    private T next;

    private boolean first = true;

    public FilterIterator(Iterator<T> proxy) {
        this.proxy = proxy;
    }

    public boolean hasNext() {
        if (first) {
            next = findNext();
            first = false;
        }

        return next != null;
    }

    public T next() throws NoSuchElementException {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }

        T answer = this.next;
        this.next = findNext();

        return answer;
    }

    /**
     * Always throws UnsupportedOperationException as this class does look-ahead
     * with its internal iterator.
     * 
     * @throws UnsupportedOperationException
     *             always
     */
    public void remove() {
        throw new UnsupportedOperationException();
    }

    /**
     * Filter method to perform some matching on the given element.
     * 
     * @param element
     *            DOCUMENT ME!
     * 
     * @return true if the given element matches the filter and should be appear
     *         in the iteration
     */
    protected abstract boolean matches(T element);

    protected T findNext() {
        if (proxy != null) {
            while (proxy.hasNext()) {
                T nextObject = proxy.next();

                if ((nextObject != null) && matches(nextObject)) {
                    return nextObject;
                }
            }

            proxy = null;
        }

        return null;
    }
}

