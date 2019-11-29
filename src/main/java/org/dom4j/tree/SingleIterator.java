

package org.dom4j.tree;

import java.util.Iterator;

/**
 * <p>
 * <code>SingleIterator</code> is an {@link Iterator}over a single object
 * instance.
 * </p>
 * 
 * @author <a href="mailto:james.strachan@metastuff.com">James Strachan </a>
 * @version $Revision: 1.9 $
 */
public class SingleIterator<T> implements Iterator<T> {
    private boolean first = true;

    private T object;

    public SingleIterator(T object) {
        this.object = object;
    }

    public boolean hasNext() {
        return first;
    }

    public T next() {
        T answer = object;
        object = null;
        first = false;

        return answer;
    }

    public void remove() {
        throw new UnsupportedOperationException("remove() is not supported by "
                + "this iterator");
    }
}

