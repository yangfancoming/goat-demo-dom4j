

package org.dom4j.tree;

import org.dom4j.Node;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/**
 * <p>
 * <code>BackedList</code> represents a list of content of a {@link
 * org.dom4j.Branch}. Changes to the list will be reflected in the branch,
 * though changes to the branch will not be reflected in this list.
 * </p>
 * 
 * @author <a href="mailto:james.strachan@metastuff.com">James Strachan </a>
 * @version $Revision: 1.14 $
 */
public class BackedList<T extends Node> extends ArrayList<T> {
    /** The content of the Branch which is modified if I am modified */
    private List<Node> branchContent;

    /** The <code>AbstractBranch</code> instance which owns the content */
    private AbstractBranch branch;

    public BackedList(AbstractBranch branch, List<Node> branchContent) {
        this(branch, branchContent, branchContent.size());
    }

    public BackedList(AbstractBranch branch, List<Node> branchContent, int capacity) {
        super(capacity);
        this.branch = branch;
        this.branchContent = branchContent;
    }

    public BackedList(AbstractBranch branch, List<Node> branchContent,
            List<T> initialContent) {
        super(initialContent);
        this.branch = branch;
        this.branchContent = branchContent;
    }

    @Override
    public boolean add(T node) {
        branch.addNode(node);

        return super.add(node);
    }

    @Override
    public void add(int index, T node) {
        int size = size();

        if (index < 0) {
            throw new IndexOutOfBoundsException("Index value: " + index
                    + " is less than zero");
        } else if (index > size) {
            throw new IndexOutOfBoundsException("Index value: " + index
                    + " cannot be greater than " + "the size: " + size);
        }

        int realIndex;

        if (size == 0) {
            realIndex = branchContent.size();
        } else if (index < size) {
            realIndex = branchContent.indexOf(get(index));
        } else {
            realIndex = branchContent.indexOf(get(size - 1)) + 1;
        }

        branch.addNode(realIndex, node);
        super.add(index, node);
    }

    @Override
    public T set(int index, T node) {
        int realIndex = branchContent.indexOf(get(index));

        if (realIndex < 0) {
            realIndex = (index == 0) ? 0 : Integer.MAX_VALUE;
        }

        if (realIndex < branchContent.size()) {
            branch.removeNode(get(index));
            branch.addNode(realIndex, node);
        } else {
            branch.removeNode(get(index));
            branch.addNode(node);
        }

        branch.childAdded(node);

        return super.set(index, node);
    }

    @Override
    public boolean remove(Object object) {
        if (object instanceof Node) {
            branch.removeNode((Node) object);
        }

        return super.remove(object);
    }

    @Override
    public T remove(int index) {
        T node = super.remove(index);

        if (node != null) {
            branch.removeNode(node);
        }

        return node;
    }

    @Override
    public boolean addAll(Collection<? extends T> collection) {
        ensureCapacity(size() + collection.size());

        int count = size();

        for (Iterator<? extends T> iter = collection.iterator(); iter.hasNext(); count--) {
            add(iter.next());
        }

        return count != 0;
    }

    @Override
    public boolean addAll(int index, Collection<? extends T> collection) {
        ensureCapacity(size() + collection.size());

        int count = size();

        for (Iterator<? extends T> iter = collection.iterator(); iter.hasNext(); count--) {
            add(index++, iter.next());
        }

        return count != 0;
    }

    @Override
    public void clear() {
        for (Node node : this) {
            branchContent.remove(node);
            branch.childRemoved(node);
        }

        super.clear();
    }

    /**
     * Performs a local addition which is not forward through to the Branch or
     * backing list
     * 
     * @param node
     *            DOCUMENT ME!
     */
    public void addLocal(T node) {
        super.add(node);
    }
}

