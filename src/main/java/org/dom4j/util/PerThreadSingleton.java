

package org.dom4j.util;

import java.lang.ref.WeakReference;

/**
 * <p>
 * <code>PerThreadSingleton</code> is an implementation of the
 * SingletonStrategy used to provide common factory access to a single object
 * instance based on an implementation strategy for one object instance per
 * thread. This is useful in replace of the ThreadLocal usage.
 * </p>
 * 
 * @author <a href="mailto:ddlucas@users.sourceforge.net">David Lucas </a>
 * @version $Revision: 1.3 $
 */

public class PerThreadSingleton<T> implements SingletonStrategy<T> {
    private String singletonClassName = null;

    private ThreadLocal<WeakReference<T>> perThreadCache = new ThreadLocal<WeakReference<T>>();

    public PerThreadSingleton() {
    }

    public void reset() {
        perThreadCache = new ThreadLocal<WeakReference<T>>();
    }

    public T instance() {
        T singletonInstancePerThread = null;
        // use weak reference to prevent cyclic reference during GC
        WeakReference<T> ref = (WeakReference<T>) perThreadCache.get();
        // singletonInstancePerThread=perThreadCache.get();
        // if (singletonInstancePerThread==null) {
        if (ref == null || ref.get() == null) {
            Class<T> clazz = null;
            try {
                clazz = (Class<T>) Thread.currentThread().getContextClassLoader().loadClass(
                        singletonClassName);
                singletonInstancePerThread = clazz.newInstance();
            } catch (Exception ignore) {
                try {
                    clazz = (Class<T>) Class.forName(singletonClassName);
                    singletonInstancePerThread = clazz.newInstance();
                } catch (Exception ignore2) {
                }
            }
            perThreadCache.set(new WeakReference<T>(singletonInstancePerThread));
        } else {
            singletonInstancePerThread = ref.get();
        }
        return singletonInstancePerThread;
    }

    public void setSingletonClassName(String singletonClassName) {
        this.singletonClassName = singletonClassName;
    }

}

