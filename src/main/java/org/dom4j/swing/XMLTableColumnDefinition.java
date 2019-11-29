

package org.dom4j.swing;

import org.dom4j.DocumentHelper;
import org.dom4j.Node;
import org.dom4j.XPath;

import java.io.Serializable;

/**
 * <p>
 * <code>XMLTableColumnDefinition</code> a column within a table definition.
 * </p>
 * 
 * @author <a href="mailto:jstrachan@apache.org">James Strachan </a>
 * @version $Revision: 1.10 $
 */
public class XMLTableColumnDefinition implements Serializable {
    public static final int OBJECT_TYPE = 0;

    public static final int STRING_TYPE = 1;

    public static final int NUMBER_TYPE = 2;

    public static final int NODE_TYPE = 3;

    /** Holds value of property type. */
    private int type;

    /** Holds value of property name. */
    private String name;

    /** Holds value of property xpath. */
    private XPath xpath;

    /** Holds the XPath used for the column name */
    private XPath columnNameXPath;

    public XMLTableColumnDefinition() {
    }

    public XMLTableColumnDefinition(String name, String expression, int type) {
        this.name = name;
        this.type = type;
        this.xpath = createXPath(expression);
    }

    public XMLTableColumnDefinition(String name, XPath xpath, int type) {
        this.name = name;
        this.xpath = xpath;
        this.type = type;
    }

    public XMLTableColumnDefinition(XPath columnXPath, XPath xpath, int type) {
        this.xpath = xpath;
        this.columnNameXPath = columnXPath;
        this.type = type;
    }

    public static int parseType(String typeName) {
        if ((typeName != null) && (typeName.length() > 0)) {
            if (typeName.equals("string")) {
                return STRING_TYPE;
            } else if (typeName.equals("number")) {
                return NUMBER_TYPE;
            } else if (typeName.equals("node")) {
                return NODE_TYPE;
            }
        }

        return OBJECT_TYPE;
    }

    public Class<?> getColumnClass() {
        switch (type) {
            case STRING_TYPE:
                return String.class;

            case NUMBER_TYPE:
                return Number.class;

            case NODE_TYPE:
                return Node.class;

            default:
                return Object.class;
        }
    }

    public Object getValue(Object row) {
        switch (type) {
            case STRING_TYPE:
                return xpath.valueOf(row);

            case NUMBER_TYPE:
                return xpath.numberValueOf(row);

            case NODE_TYPE:
                return xpath.selectSingleNode(row);

            default:
                return xpath.evaluate(row);
        }
    }

    // Properties
    // -------------------------------------------------------------------------

    /**
     * Getter for property type.
     * 
     * @return Value of property type.
     */
    public int getType() {
        return type;
    }

    /**
     * Setter for property type.
     * 
     * @param type
     *            New value of property type.
     */
    public void setType(int type) {
        this.type = type;
    }

    /**
     * Getter for property name.
     * 
     * @return Value of property name.
     */
    public String getName() {
        return name;
    }

    /**
     * Setter for property name.
     * 
     * @param name
     *            New value of property name.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Getter for property xpath.
     * 
     * @return Value of property xpath.
     */
    public XPath getXPath() {
        return xpath;
    }

    /**
     * Setter for property xpath.
     * 
     * @param xPath
     *            New value of property xpath.
     */
    public void setXPath(XPath xPath) {
        this.xpath = xPath;
    }

    /**
     * DOCUMENT ME!
     * 
     * @return the XPath used to create the column name
     */
    public XPath getColumnNameXPath() {
        return columnNameXPath;
    }

    /**
     * Setter for property columnNameXPath.
     * 
     * @param columnNameXPath
     *            New value of property xpath.
     */
    public void setColumnNameXPath(XPath columnNameXPath) {
        this.columnNameXPath = columnNameXPath;
    }

    // Implementation methods
    // -------------------------------------------------------------------------
    protected XPath createXPath(String expression) {
        return DocumentHelper.createXPath(expression);
    }

    protected void handleException(Exception e) {
        // #### should use jakarta commons-logging
        System.out.println("Caught: " + e);
    }
}

