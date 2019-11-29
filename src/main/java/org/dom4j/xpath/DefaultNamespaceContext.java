

package org.dom4j.xpath;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.Namespace;
import org.dom4j.Node;
import org.jaxen.NamespaceContext;

import java.io.Serializable;

/**
 * <p>
 * <code>DefaultNamespaceContext</code> implements a Jaxen NamespaceContext
 * such that a context node is used to determine the current XPath namespace
 * prefixes and namespace URIs available.
 * </p>
 * 
 * @author <a href="mailto:jstrachan@apache.org">James Strachan </a>
 */
public class DefaultNamespaceContext implements NamespaceContext, Serializable {
    private final Element element;

    public DefaultNamespaceContext(Element element) {
        this.element = element;
    }

    public static DefaultNamespaceContext create(Object node) {
        Element element = null;

        if (node instanceof Element) {
            element = (Element) node;
        } else if (node instanceof Document) {
            Document doc = (Document) node;
            element = doc.getRootElement();
        } else if (node instanceof Node) {
            element = ((Node) node).getParent();
        }

        if (element != null) {
            return new DefaultNamespaceContext(element);
        }

        return null;
    }

    public String translateNamespacePrefixToUri(String prefix) {
        if ((prefix != null) && (prefix.length() > 0)) {
            Namespace ns = element.getNamespaceForPrefix(prefix);

            if (ns != null) {
                return ns.getURI();
            }
        }

        return null;
    }
}

