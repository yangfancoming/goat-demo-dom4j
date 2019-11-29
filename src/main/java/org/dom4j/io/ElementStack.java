

package org.dom4j.io;

import org.dom4j.Element;
import org.dom4j.ElementHandler;
import org.dom4j.ElementPath;

/**
 * <p>
 * <code>ElementStack</code> is used internally inside the {@link
 * SAXContentHandler} to maintain a stack of {@link Element}instances. It opens
 * an integration possibility allowing derivations to prune the tree when a node
 * is complete.
 * </p>
 * 
 * @author <a href="mailto:james.strachan@metastuff.com">James Strachan </a>
 * @version $Revision: 1.14 $
 */
class ElementStack implements ElementPath {
    /** stack of <code>Element</code> objects */
    protected Element[] stack;

    /** index of the item at the top of the stack or -1 if the stack is empty */
    protected int lastElementIndex = -1;

    private DispatchHandler handler = null;

    public ElementStack() {
        this(50);
    }

    public ElementStack(int defaultCapacity) {
        stack = new Element[defaultCapacity];
    }

    public void setDispatchHandler(DispatchHandler dispatchHandler) {
        this.handler = dispatchHandler;
    }

    public DispatchHandler getDispatchHandler() {
        return this.handler;
    }

    /**
     * Peeks at the top element on the stack without changing the contents of
     * the stack.
     */
    public void clear() {
        lastElementIndex = -1;
    }

    /**
     * Peeks at the top element on the stack without changing the contents of
     * the stack.
     * 
     * @return the current element on the stack
     */
    public Element peekElement() {
        if (lastElementIndex < 0) {
            return null;
        }

        return stack[lastElementIndex];
    }

    /**
     * Pops the element off the stack
     * 
     * @return the element that has just been popped off the stack
     */
    public Element popElement() {
        if (lastElementIndex < 0) {
            return null;
        }

        return stack[lastElementIndex--];
    }

    /**
     * Pushes a new element onto the stack
     * 
     * @param element
     *            DOCUMENT ME!
     */
    public void pushElement(Element element) {
        int length = stack.length;

        if (++lastElementIndex >= length) {
            reallocate(length * 2);
        }

        stack[lastElementIndex] = element;
    }

    /**
     * Reallocates the stack to the given size
     * 
     * @param size
     *            DOCUMENT ME!
     */
    protected void reallocate(int size) {
        Element[] oldStack = stack;
        stack = new Element[size];
        System.arraycopy(oldStack, 0, stack, 0, oldStack.length);
    }

    // The ElementPath Interface
    //
    public int size() {
        return lastElementIndex + 1;
    }

    public Element getElement(int depth) {
        Element element;

        try {
            element = (Element) stack[depth];
        } catch (ArrayIndexOutOfBoundsException e) {
            element = null;
        }

        return element;
    }

    public String getPath() {
        if (handler == null) {
            setDispatchHandler(new DispatchHandler());
        }

        return handler.getPath();
    }

    public Element getCurrent() {
        return peekElement();
    }

    public void addHandler(String path, ElementHandler elementHandler) {
        this.handler.addHandler(getHandlerPath(path), elementHandler);
    }

    public void removeHandler(String path) {
        this.handler.removeHandler(getHandlerPath(path));
    }

    /**
     * DOCUMENT ME!
     * 
     * @param path
     *            DOCUMENT ME!
     * 
     * @return true when an <code>ElementHandler</code> is registered for the
     *         specified path.
     */
    public boolean containsHandler(String path) {
        return this.handler.containsHandler(path);
    }

    private String getHandlerPath(String path) {
        String handlerPath;

        if (this.handler == null) {
            setDispatchHandler(new DispatchHandler());
        }

        if (path.startsWith("/")) {
            handlerPath = path;
        } else if (getPath().equals("/")) {
            handlerPath = getPath() + path;
        } else {
            handlerPath = getPath() + "/" + path;
        }

        return handlerPath;
    }
}

