

package org.dom4j.util;

/**
 * <p>
 * <code>SimpleSingleton</code> is an implementation of the SingletonStrategy
 * interface used to provide common factory access for the same object instance.
 * This implementation will create a new instance from the class specified and
 * will not create a new one unless it is reset.
 * </p>
 * 
 * @author <a href="mailto:ddlucas@users.sourceforge.net">David Lucas </a>
 * @version $Revision: 1.3 $
 */

public class SimpleSingleton<T> implements SingletonStrategy<T> {
    private String singletonClassName = null;

    private T singletonInstance = null;

    public SimpleSingleton() {
    }

    public T instance() {
        return singletonInstance;
    }

    public void reset() {
        if (singletonClassName != null) {
            Class<T> clazz;
            try {
                clazz = (Class<T>) Thread.currentThread().getContextClassLoader().loadClass(
                        singletonClassName);
                singletonInstance = clazz.newInstance();
            } catch (Exception ignore) {
                try {
                    clazz = (Class<T>) Class.forName(singletonClassName);
                    singletonInstance = clazz.newInstance();
                } catch (Exception ignore2) {
                }
            }

        }
    }

    public void setSingletonClassName(String singletonClassName) {
        this.singletonClassName = singletonClassName;
        reset();
    }

}

