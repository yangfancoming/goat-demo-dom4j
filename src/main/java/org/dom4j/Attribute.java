

package org.dom4j;

/**
 * <code>Attribute</code> defines an XML attribute. An attribute may have a
 * name, an optional namespace and a value.
 *
 * @author <a href="mailto:james.strachan@metastuff.com">James Strachan </a>
 * @version $Revision: 1.9 $
 */
@SuppressWarnings("unused")
public interface Attribute extends Node {
	/**
	 * Returns the <code>QName</code> of this attribute which represents the
	 * local name, the qualified name and the <code>Namespace</code>.
	 *
	 * @return the <code>QName</code> associated with this attribute
	 */
	QName getQName();

	/**
	 * Returns the <code>Namespace</code> of this element if one exists
	 * otherwise null is returned returned.
	 *
	 * @return the <code>Namespace</code> associated with this node
	 */
	Namespace getNamespace();

	/**
	 * Sets the <code>Namespace</code> of this element or if this element is
	 * read only then an <code>UnsupportedOperationException</code> is thrown.
	 *
	 * @param namespace is the <code>Namespace</code> to associate with this element
	 */
	void setNamespace(Namespace namespace);

	/**
	 * Returns the namespace prefix of this element if one exists otherwise an
	 * empty <code>String</code> is returned.
	 *
	 * @return the prefix of the <code>Namespace</code> of this element or an
	 * empty <code>String</code>
	 */
	String getNamespacePrefix();

	/**
	 * Returns the URI mapped to the namespace of this element if one exists
	 * otherwise an empty <code>String</code> is returned.
	 *
	 * @return the URI for the <code>Namespace</code> of this element or an
	 * empty <code>String</code>
	 */
	String getNamespaceURI();

	/**
	 * Returns the fully qualified name of this element.
	 *
	 * This will be the same as the value returned from {@link Node#getName()}
	 * if this element has no namespace attached to this element or an
	 * expression of the form
	 * <pre>
	 * getNamespacePrefix() + &quot;:&quot; + getName()
	 * </pre>
	 * will be returned.
	 *
	 * @return the fully qualified name of the element
	 */
	String getQualifiedName();

	/**
	 * Returns the value of the attribute. This method returns the same value as
	 * the {@link Node#getText()}method.
	 *
	 * @return the value of the attribute
	 */
	String getValue();

	/**
	 * Sets the value of this attribute or this method will throw an
	 * <code>UnsupportedOperationException</code> if it is read-only.
	 *
	 * @param value is the new value of this attribute
	 */
	void setValue(String value);

	/**
	 * Accesses the data of this attribute which may implement data typing
	 * bindings such as <code>XML Schema</code> or <code>Java Bean</code>
	 * bindings or will return the same value as {@link Node#getText()}.
	 *
	 * @return the attribute data
	 */
	Object getData();

	/**
	 * Sets the data value of this attribute if this element supports data
	 * binding or calls {@link Node#setText(String)}if it doesn't.
	 *
	 * @param data the attribute data
	 */
	void setData(Object data);
}

