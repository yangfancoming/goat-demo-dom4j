

package org.dom4j;

import org.jaxen.FunctionContext;
import org.jaxen.NamespaceContext;
import org.jaxen.VariableContext;

import java.util.List;
import java.util.Map;

/**
 * XPath represents an XPath expression after it has been parsed
 * from a String.
 *
 * @author <a href="mailto:james.strachan@metastuff.com">James Strachan </a>
 * @version $Revision: 1.20 $
 */
@SuppressWarnings("unused")
public interface XPath extends NodeFilter {
	/**
	 * getText will return the textual version of the XPath
	 * expression.
	 *
	 * @return the textual format of the XPath expression.
	 */
	String getText();

	/**
	 * matches returns true if the given node matches the XPath
	 * expression. To be more precise when evaluating this XPath expression on
	 * the given node the result set must include the node.
	 *
	 * @param node DOCUMENT ME!
	 * @return true if the given node matches this XPath expression
	 */
	boolean matches(Node node);

	/**
	 * evaluate evaluates an XPath expression and returns the
	 * result as an {@link Object}. The object returned can either be a {@link
	 * List} of {@link Node}instances, a {@link Node}instance, a {@link
	 * String} or a {@link Number}instance depending on the XPath expression.
	 *
	 * @param context is either a node or a list of nodes on which to evalute the
	 *                XPath
	 * @return the value of the XPath expression as a {@link List}of {@link
	 * Node} instances, a {@link Node}instance, a {@link String}or a
	 * {@link Number}instance depending on the XPath expression.
	 */
	Object evaluate(Object context);

	/**
	 * selectObject evaluates an XPath expression and returns the
	 * result as an {@link Object}. The object returned can either be a {@link
	 * List} of {@link Node}instances, a {@link Node}instance, a {@link
	 * String} or a {@link Number}instance depending on the XPath expression.
	 *
	 * @param context is either a node or a list of nodes on which to evalute the
	 *                XPath
	 * @return the value of the XPath expression as a {@link List}of {@link
	 * Node} instances, a {@link Node}instance, a {@link String}or a
	 * {@link Number}instance depending on the XPath expression.
	 * @deprecated please use evaluate(Object) instead. WILL BE REMOVED IN
	 * dom4j-1.6 !!
	 */
	Object selectObject(Object context);

	/**
	 * selectNodes performs this XPath expression on the given
	 * {@link Node}or {@link List}of {@link Node}s instances appending all the results together into a single list.
	 * @param context is either a node or a list of nodes on which to evalute the  XPath
	 * @return the results of all the XPath evaluations as a single list
	 */
	List<Node> selectNodes(Object context);

	/**
	 * selectNodes evaluates the XPath expression on the given
	 * {@link Node}or {@link List}of {@link Node}s and returns the result as
	 * a List of Node s sorted by the sort XPath
	 * expression.
	 *
	 * @param context   is either a node or a list of nodes on which to evalute the
	 *                  XPath
	 * @param sortXPath is the XPath expression to sort by
	 * @return a list of Node instances
	 */
	List<Node> selectNodes(Object context, XPath sortXPath);

	/**
	 * selectNodes evaluates the XPath expression on the given
	 * {@link Node}or {@link List}of {@link Node}s and returns the result as
	 * a List of Node s sorted by the sort XPath
	 * expression.
	 *
	 * @param context   is either a node or a list of nodes on which to evalute the
	 *                  XPath
	 * @param sortXPath is the XPath expression to sort by
	 * @param distinct  specifies whether or not duplicate values of the sort
	 *                  expression are allowed. If this parameter is true then only
	 *                  distinct sort expressions values are included in the result
	 * @return a list of Node instances
	 */
	List<Node> selectNodes(Object context, XPath sortXPath, boolean distinct);

	/**
	 * selectSingleNode evaluates this XPath expression on the
	 * given {@link Node}or {@link List}of {@link Node}s and returns the
	 * result as a single Node instance.
	 *
	 * @param context is either a node or a list of nodes on which to evalute the
	 *                XPath
	 * @return a single matching Node instance
	 */
	Node selectSingleNode(Object context);

	/**
	 * valueOf evaluates this XPath expression and returns the
	 * textual representation of the results using the XPath string() function.
	 *
	 * @param context is either a node or a list of nodes on which to evalute the
	 *                XPath
	 * @return the string representation of the results of the XPath expression
	 */
	String valueOf(Object context);

	/**
	 * numberValueOf evaluates an XPath expression and returns
	 * the numeric value of the XPath expression if the XPath expression results
	 * is a number, or null if the result is not a number.
	 *
	 * @param context is either a node or a list of nodes on which to evalute the
	 *                XPath
	 * @return the numeric result of the XPath expression or null if the result
	 * is not a number.
	 */
	Number numberValueOf(Object context);

	/**
	 * Retrieve a boolean-value interpretation of this XPath expression when
	 * evaluated against a given context.
	 *
	 * The boolean-value of the expression is determined per the
	 * boolean(..) core function as defined in the XPath
	 * specification. This means that an expression that selects zero nodes will
	 * return false, while an expression that selects
	 * one-or-more nodes will return true.
	 *
	 * @param context The node, nodeset or Context object for evaluation. This value can be null
	 * @return The boolean-value interpretation of this expression.
	 * @since 1.5
	 */
	boolean booleanValueOf(Object context);

	/**
	 * sort sorts the given List of Nodes using this XPath
	 * expression as a {@link java.util.Comparator}.
	 *
	 * @param list is the list of Nodes to sort
	 */
	void sort(List<Node> list);

	/**
	 * sort sorts the given List of Nodes using this XPath
	 * expression as a {@link java.util.Comparator}and optionally removing
	 * duplicates.
	 *
	 * @param list     is the list of Nodes to sort
	 * @param distinct if true then duplicate values (using the sortXPath for
	 *                 comparisions) will be removed from the List
	 */
	void sort(List<Node> list, boolean distinct);

	/**
	 * DOCUMENT ME!
	 *
	 * @return the current function context
	 */
	FunctionContext getFunctionContext();

	/**
	 * Sets the function context to be used when evaluating XPath expressions
	 *
	 * @param functionContext DOCUMENT ME!
	 */
	void setFunctionContext(FunctionContext functionContext);

	/**
	 * DOCUMENT ME!
	 *
	 * @return the current namespace context
	 */
	NamespaceContext getNamespaceContext();

	/**
	 * Sets the namespace context to be used when evaluating XPath expressions
	 *
	 * @param namespaceContext DOCUMENT ME!
	 */
	void setNamespaceContext(NamespaceContext namespaceContext);

	/**
	 * Sets the current NamespaceContext from a Map where the keys are the
	 * String namespace prefixes and the values are the namespace URIs.
	 *
	 * For example:
	 * <pre>
	 * Map uris = new HashMap();
	 * uris.put("SOAP-ENV", "http://schemas.xmlsoap.org/soap/envelope/");
	 * uris.put("m", "urn:xmethodsBabelFish");
	 * XPath xpath = document
	 *         .createXPath("SOAP-ENV:Envelope/SOAP-ENV:Body/m:BabelFish");
	 * xpath.setNamespaceURIs(uris);
	 * Node babelfish = xpath.selectSingleNode(document);
	 * </pre>
	 *
	 * @param map the map containing the namespace mappings
	 */
	void setNamespaceURIs(Map<String, String> map);

	/**
	 * DOCUMENT ME!
	 *
	 * @return the current variable context
	 */
	VariableContext getVariableContext();

	/**
	 * Sets the variable context to be used when evaluating XPath expressions
	 *
	 * @param variableContext DOCUMENT ME!
	 */
	void setVariableContext(VariableContext variableContext);
}

