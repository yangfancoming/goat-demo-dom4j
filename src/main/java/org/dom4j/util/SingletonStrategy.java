

package org.dom4j.util;

/**
 * <code>SingletonStrategy</code> is an interface used to provide common
 * factory access for the same object based on an implementation strategy for
 * singleton. Right now there are two that accompany this interface:
 * SimpleSingleton and PerThreadSingleton. This will replace previous usage of
 * ThreadLocal to allow for alternate strategies.
 *
 * @author <a href="mailto:ddlucas@users.sourceforge.net">David Lucas </a>
 * @version $Revision: 1.2 $
 */
public interface SingletonStrategy<T> {
    /**
     * @return a singleton instance of the class specified in setSingletonClass
     */
    T instance();

    /**
     * reset the instance to a new instance for the implemented strategy
     */
    void reset();

    /**
     * set a singleton class name that will be used to create the singleton
     * based on the strategy implementation of this interface. The default
     * constructor of the class will be used and must be public.
     * @param singletonClassName DOCUMENT ME!
     */
    void setSingletonClassName(String singletonClassName);
}

