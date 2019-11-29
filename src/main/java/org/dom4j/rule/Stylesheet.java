

package org.dom4j.rule;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.XPath;

import java.util.List;

/**
 * <p>
 * <code>Stylesheet</code> implements an XSLT stylesheet such that rules can
 * be added to the stylesheet and the stylesheet can be applied to a source
 * document or node.
 * </p>
 * 
 * @author <a href="mailto:james.strachan@metastuff.com">James Strachan </a>
 * @version $Revision: 1.14 $
 */
public class Stylesheet {
    private RuleManager ruleManager = new RuleManager();

    /** Holds value of property mode. */
    private String modeName;

    /**
     * Creates a new empty stylesheet.
     */
    public Stylesheet() {
    }

    /**
     * Add a rule to this stylesheet.
     * 
     * @param rule
     *            the rule to add
     */
    public void addRule(Rule rule) {
        ruleManager.addRule(rule);
    }

    /**
     * Removes the specified rule from this stylesheet.
     * 
     * @param rule
     *            the rule to remove
     */
    public void removeRule(Rule rule) {
        ruleManager.removeRule(rule);
    }

    public void run(List<Node> list) throws Exception {
        run(list, this.modeName);
    }

    public void run(List<Node> list, String mode) throws Exception {
        for (Node node : list) {
            run(node, mode);
        }
    }

    public void run(Node node) throws Exception {
        run(node, this.modeName);
    }

    public void run(Node node, String mode) throws Exception {
        Mode mod = ruleManager.getMode(mode);
        mod.fireRule(node);
    }

    /**
     * Processes the result of the xpath expression. The xpath expression is
     * evaluated against the provided input object.
     * 
     * @param input
     *            the input object
     * @param xpath
     *            the xpath expression
     * @throws Exception
     *             if something goes wrong
     */
    public void applyTemplates(Object input, XPath xpath) throws Exception {
        applyTemplates(input, xpath, this.modeName);
    }

    /**
     * Processes the result of the xpath expression in the given mode. The xpath
     * expression is evaluated against the provided input object.
     * 
     * @param input
     *            the input object
     * @param xpath
     *            the xpath expression
     * @param mode
     *            the mode
     * @throws Exception
     *             if something goes wrong
     */
    public void applyTemplates(Object input, XPath xpath, String mode)
            throws Exception {
        Mode mod = ruleManager.getMode(mode);

        List<Node> list = xpath.selectNodes(input);
        for (Node current : list) {
            mod.fireRule(current);
        }
    }

    /**
     * If input is a <code>Node</code>, this will processes all of the
     * children of that node. If input is a <code>List</code> of
     * <code>Nodes</code>s, these nodes will be iterated and all children of
     * each node will be processed.
     * 
     * @param node the input object
     * @throws Exception
     *             if something goes wrong
     */
    public void applyTemplates(Node node) throws Exception {
        applyTemplates(node, this.modeName);
    }

    /**
     * If input is a <code>Node</code>, this will processes all of the
     * children of that node. If input is a <code>List</code> of
     * <code>Nodes</code>s, these nodes will be iterated and all children of
     * each node will be processed.
     *
     * @param element the input object
     * @throws Exception
     *             if something goes wrong
     */
    public void applyTemplates(Element element) throws Exception {
        applyTemplates(element, this.modeName);
    }

    /**
     * If input is a <code>Node</code>, this will processes all of the
     * children of that node. If input is a <code>List</code> of
     * <code>Nodes</code>s, these nodes will be iterated and all children of
     * each node will be processed.
     *
     * @param document the input object
     * @throws Exception
     *             if something goes wrong
     */
    public void applyTemplates(Document document) throws Exception {
        applyTemplates(document, this.modeName);
    }

    /**
     * If input is a <code>Node</code>, this will processes all of the
     * children of that node. If input is a <code>List</code> of
     * <code>Nodes</code>s, these nodes will be iterated and all children of
     * each node will be processed.
     *
     * @param list the input object
     * @throws Exception
     *             if something goes wrong
     */
    public void applyTemplates(List<Node> list) throws Exception {
        applyTemplates(list, this.modeName);
    }

    /**
     * Processes the input object in the given mode. If input is a
     * <code>Node</code>, this will processes all of the children of that
     * node. If input is a <code>List</code> of <code>Nodes</code>s, these
     * nodes will be iterated and all children of each node will be processed.
     * 
     * @param node the input object
     * @param mode
     *            the mode
     * @throws Exception
     *             if something goes wrong
     */
    public void applyTemplates(Node node, String mode) throws Exception {
        if (node instanceof Element) {
            applyTemplates((Element) node, mode);
        } else if (node instanceof Document) {
            applyTemplates((Document) node, mode);
        }
    }

    /**
     * Processes the input object in the given mode. If input is a
     * <code>Node</code>, this will processes all of the children of that
     * node. If input is a <code>List</code> of <code>Nodes</code>s, these
     * nodes will be iterated and all children of each node will be processed.
     *
     * @param element the input object
     * @param mode
     *            the mode
     * @throws Exception
     *             if something goes wrong
     */
    public void applyTemplates(Element element, String mode) throws Exception {
        Mode mod = ruleManager.getMode(mode);

        // iterate through all children
        for (int i = 0, size = element.nodeCount(); i < size; i++) {
            Node node = element.node(i);
            mod.fireRule(node);
        }
    }

    /**
     * Processes the input object in the given mode. If input is a
     * <code>Node</code>, this will processes all of the children of that
     * node. If input is a <code>List</code> of <code>Nodes</code>s, these
     * nodes will be iterated and all children of each node will be processed.
     *
     * @param document the input object
     * @param mode
     *            the mode
     * @throws Exception
     *             if something goes wrong
     */
    public void applyTemplates(Document document, String mode) throws Exception {
        Mode mod = ruleManager.getMode(mode);

        // iterate through all children
        for (int i = 0, size = document.nodeCount(); i < size; i++) {
            Node node = document.node(i);
            mod.fireRule(node);
        }
    }

    /**
     * Processes the input object in the given mode. If input is a
     * <code>Node</code>, this will processes all of the children of that
     * node. If input is a <code>List</code> of <code>Nodes</code>s, these
     * nodes will be iterated and all children of each node will be processed.
     *
     * @param list list of Elements or Documents
     * @param mode
     *            the mode
     * @throws Exception
     *             if something goes wrong
     */
    public void applyTemplates(List<? extends Node> list, String mode) throws Exception {
        for (Node node : list) {
            if (node instanceof Element) {
                applyTemplates((Element) node, mode);
            } else if (node instanceof Document) {
                applyTemplates((Document) node, mode);
            }
        }
    }

    public void clear() {
        ruleManager.clear();
    }

    // Properties
    // -------------------------------------------------------------------------

    /**
     * DOCUMENT ME!
     * 
     * @return the name of the mode the stylesheet uses by default
     */
    public String getModeName() {
        return modeName;
    }

    /**
     * Sets the name of the mode that the stylesheet uses by default.
     * 
     * @param modeName
     *            DOCUMENT ME!
     */
    public void setModeName(String modeName) {
        this.modeName = modeName;
    }

    /**
     * DOCUMENT ME!
     * 
     * @return the default value-of action which is used in the default rules
     *         for the pattern "text()|&#64;"
     */
    public Action getValueOfAction() {
        return ruleManager.getValueOfAction();
    }

    /**
     * Sets the default value-of action which is used in the default rules for
     * the pattern "text()|&#64;"
     * 
     * @param valueOfAction
     *            DOCUMENT ME!
     */
    public void setValueOfAction(Action valueOfAction) {
        ruleManager.setValueOfAction(valueOfAction);
    }
}

