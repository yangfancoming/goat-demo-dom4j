

package org.dom4j.tree;

import org.dom4j.Namespace;

import java.lang.ref.WeakReference;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * <p>
 * <code>NamespaceCache</code> caches instances of
 * <code>DefaultNamespace</code> for reuse both across documents and within
 * documents.
 * </p>
 * 
 * @author <a href="mailto:james.strachan@metastuff.com">James Strachan </a>
 * @author Maarten Coene
 * @author Brett Finnell
 * @version $Revision: 1.15 $
 */
public class NamespaceCache {

    /**
     * Cache of {@link Map}instances indexed by URI which contain caches of
     * {@link Namespace}for each prefix
     */
    protected static Map<String, Map<String, WeakReference<Namespace>>> cache;

    /**
     * Cache of {@link Namespace}instances indexed by URI for default
     * namespaces with no prefixes
     */
    protected static Map<String, WeakReference<Namespace>> noPrefixCache;

    static {
        cache = new ConcurrentHashMap<String, Map<String, WeakReference<Namespace>>>();
        noPrefixCache = new ConcurrentHashMap<String, WeakReference<Namespace>>();
    }

    /**
     * DOCUMENT ME!
     * 
     * @param prefix
     *            DOCUMENT ME!
     * @param uri
     *            DOCUMENT ME!
     * 
     * @return the namespace for the given prefix and uri
     */
    public Namespace get(String prefix, String uri) {
        Map<String, WeakReference<Namespace>> uriCache = getURICache(uri);
        WeakReference<Namespace> ref = uriCache.get(prefix);
        Namespace answer = null;

        if (ref != null) {
            answer = ref.get();
        }

        if (answer == null) {
            synchronized (uriCache) {
                ref = uriCache.get(prefix);

                if (ref != null) {
                    answer = ref.get();
                }

                if (answer == null) {
                    answer = createNamespace(prefix, uri);
                    uriCache.put(prefix, new WeakReference<Namespace>(answer));
                }
            }
        }

        return answer;
    }

    /**
     * DOCUMENT ME!
     * 
     * @param uri
     *            DOCUMENT ME!
     * 
     * @return the name model for the given name and namepsace
     */
    public Namespace get(String uri) {
        WeakReference<Namespace> ref = noPrefixCache.get(uri);
        Namespace answer = null;

        if (ref != null) {
            answer = ref.get();
        }

        if (answer == null) {
            synchronized (noPrefixCache) {
                ref = noPrefixCache.get(uri);

                if (ref != null) {
                    answer = ref.get();
                }

                if (answer == null) {
                    answer = createNamespace("", uri);
                    noPrefixCache.put(uri, new WeakReference<Namespace>(answer));
                }
            }
        }

        return answer;
    }

    /**
     * DOCUMENT ME!
     * 
     * @param uri
     *            DOCUMENT ME!
     * 
     * @return the cache for the given namespace URI. If one does not currently
     *         exist it is created.
     */
    protected Map<String, WeakReference<Namespace>> getURICache(String uri) {
        Map<String, WeakReference<Namespace>> answer = cache.get(uri);

        if (answer == null) {
            synchronized (cache) {
                answer = cache.get(uri);

                if (answer == null) {
                    answer = new ConcurrentHashMap<String, WeakReference<Namespace>>();
                    cache.put(uri, answer);
                }
            }
        }

        return answer;
    }

    /**
     * A factory method to create {@link Namespace}instance
     * 
     * @param prefix
     *            DOCUMENT ME!
     * @param uri
     *            DOCUMENT ME!
     * 
     * @return a newly created {@link Namespace}instance.
     */
    protected Namespace createNamespace(String prefix, String uri) {
        return new Namespace(prefix, uri);
    }
}

