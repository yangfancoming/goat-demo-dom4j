

package org.dom4j.swing;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.XPath;
import org.jaxen.VariableContext;

import java.io.Serializable;
import java.util.*;

/**
 * <p>
 * <code>XMLTableDefinition</code> represents a table definition based on
 * XPath expression evaluated on an XML document.
 * </p>
 * 
 * @author <a href="mailto:jstrachan@apache.org">James Strachan </a>
 * @version $Revision: 1.8 $
 */
public class XMLTableDefinition implements Serializable, VariableContext {
    /** Holds value of property rowXPath. */
    private XPath rowXPath;

    /** The columns to display in this table */
    private List<XMLTableColumnDefinition> columns = new ArrayList<XMLTableColumnDefinition>();

    /** integer index array cache */
    private XMLTableColumnDefinition[] columnArray;

    /** name index cache */
    private Map<String, XMLTableColumnDefinition> columnNameIndex;

    /** for cross-row variables */
    private VariableContext variableContext;

    /** stores the current row value for the variableContext */
    private Object rowValue;

    public XMLTableDefinition() {
    }

    /**
     * Loads an XML table definition from an XML definition document
     * 
     * @param definition
     *            DOCUMENT ME!
     * 
     * @return DOCUMENT ME!
     */
    public static XMLTableDefinition load(Document definition) {
        return load(definition.getRootElement());
    }

    /**
     * Loads an XML table definition from an XML definition document
     * 
     * @param definition
     *            DOCUMENT ME!
     * 
     * @return DOCUMENT ME!
     */
    public static XMLTableDefinition load(Element definition) {
        XMLTableDefinition answer = new XMLTableDefinition();
        answer.setRowExpression(definition.attributeValue("select"));

        for (Iterator<Element> iter = definition.elementIterator("column"); iter
                .hasNext();) {
            Element element = iter.next();
            String expression = element.attributeValue("select");
            String name = element.getText();
            String typeName = element.attributeValue("type", "string");
            String columnXPath = element.attributeValue("columnNameXPath");
            int type = XMLTableColumnDefinition.parseType(typeName);

            if (columnXPath != null) {
                answer.addColumnWithXPathName(columnXPath, expression, type);
            } else {
                answer.addColumn(name, expression, type);
            }
        }

        return answer;
    }

    public Class<?> getColumnClass(int columnIndex) {
        return getColumn(columnIndex).getColumnClass();
    }

    public int getColumnCount() {
        return columns.size();
    }

    /**
     * DOCUMENT ME!
     * 
     * @param columnIndex
     *            DOCUMENT ME!
     * 
     * @return the static column name. This is used if there is no
     *         columnNameXPath
     */
    public String getColumnName(int columnIndex) {
        return getColumn(columnIndex).getName();
    }

    /**
     * DOCUMENT ME!
     * 
     * @param columnIndex
     *            DOCUMENT ME!
     * 
     * @return the XPath expression used to evaluate the value of cells in this
     *         column
     */
    public XPath getColumnXPath(int columnIndex) {
        return getColumn(columnIndex).getXPath();
    }

    /**
     * DOCUMENT ME!
     * 
     * @param columnIndex
     *            DOCUMENT ME!
     * 
     * @return the XPath expresssion used to create the column name, if there is
     *         one or null if there is no XPath expression to name the column.
     */
    public XPath getColumnNameXPath(int columnIndex) {
        return getColumn(columnIndex).getColumnNameXPath();
    }

    public synchronized Object getValueAt(Object row, int columnIndex) {
        XMLTableColumnDefinition column = getColumn(columnIndex);
        Object answer = null;

        synchronized (this) {
            this.rowValue = row;
            answer = column.getValue(row);
            this.rowValue = null;
        }

        return answer;
    }

    public void addColumn(String name, String expression) {
        addColumn(name, expression, XMLTableColumnDefinition.OBJECT_TYPE);
    }

    public void addColumn(String name, String expression, int type) {
        XPath xpath = createColumnXPath(expression);
        addColumn(new XMLTableColumnDefinition(name, xpath, type));
    }

    public void addColumnWithXPathName(String columnNameXPathExpression,
            String expression, int type) {
        XPath columnNameXPath = createColumnXPath(columnNameXPathExpression);
        XPath xpath = createColumnXPath(expression);
        addColumn(new XMLTableColumnDefinition(columnNameXPath, xpath, type));
    }

    public void addStringColumn(String name, String expression) {
        addColumn(name, expression, XMLTableColumnDefinition.STRING_TYPE);
    }

    public void addNumberColumn(String name, String expression) {
        addColumn(name, expression, XMLTableColumnDefinition.NUMBER_TYPE);
    }

    public void addColumn(XMLTableColumnDefinition column) {
        clearCaches();
        columns.add(column);
    }

    public void removeColumn(XMLTableColumnDefinition column) {
        clearCaches();
        columns.remove(column);
    }

    public void clear() {
        clearCaches();
        columns.clear();
    }

    public XMLTableColumnDefinition getColumn(int index) {
        if (columnArray == null) {
            columnArray = new XMLTableColumnDefinition[columns.size()];
            columns.toArray(columnArray);
        }

        return columnArray[index];
    }

    public XMLTableColumnDefinition getColumn(String columnName) {
        if (columnNameIndex == null) {
            columnNameIndex = new HashMap<String, XMLTableColumnDefinition>();

            for (XMLTableColumnDefinition column : columns) {
                columnNameIndex.put(column.getName(), column);
            }
        }

        return (XMLTableColumnDefinition) columnNameIndex.get(columnName);
    }

    /**
     * Getter for property rowXPath.
     * 
     * @return Value of property rowXPath.
     */
    public XPath getRowXPath() {
        return rowXPath;
    }

    /**
     * Setter for property rowXPath.
     * 
     * @param rowXPath
     *            New value of property rowXPath.
     */
    public void setRowXPath(XPath rowXPath) {
        this.rowXPath = rowXPath;
    }

    public void setRowExpression(String xpath) {
        setRowXPath(createXPath(xpath));
    }

    // VariableContext interface
    // -------------------------------------------------------------------------
    public Object getVariableValue(String namespaceURI, String prefix,
            String localName) {
        XMLTableColumnDefinition column = getColumn(localName);

        if (column != null) {
            return column.getValue(rowValue);
        }

        return null;
    }

    // Implementation methods
    // -------------------------------------------------------------------------
    protected XPath createXPath(String expression) {
        return DocumentHelper.createXPath(expression);
    }

    protected XPath createColumnXPath(String expression) {
        XPath xpath = createXPath(expression);

        // associate my variable context
        xpath.setVariableContext(this);

        return xpath;
    }

    protected void clearCaches() {
        columnArray = null;
        columnNameIndex = null;
    }

    protected void handleException(Exception e) {
        // #### should use jakarta commons-logging
        System.out.println("Caught: " + e);
    }
}

