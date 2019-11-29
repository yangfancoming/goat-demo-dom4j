

package org.dom4j.swing;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.XPath;

import javax.swing.table.AbstractTableModel;
import java.util.List;

/**
 * <p>
 * <code>XMLTableDefinition</code> repro.
 * </p>
 * 
 * @author <a href="mailto:jstrachan@apache.org">James Strachan </a>
 * @version $Revision: 1.8 $
 */
public class XMLTableModel extends AbstractTableModel {
    /** Holds value of property definition. */
    private XMLTableDefinition definition;

    /** Holds value of property source. */
    private Object source;

    /** The rows evaluated from the row XPath expression */
    private List<Node> rows;

    /**
     * Creates a TableModel from an XML table definition document and an XML
     * source
     * 
     * @param tableDefinition
     *            DOCUMENT ME!
     * @param source
     *            DOCUMENT ME!
     */
    public XMLTableModel(Element tableDefinition, Object source) {
        this(XMLTableDefinition.load(tableDefinition), source);
    }

    /**
     * Creates a TableModel from an XML table definition document and an XML
     * source
     * 
     * @param tableDefinition
     *            DOCUMENT ME!
     * @param source
     *            DOCUMENT ME!
     */
    public XMLTableModel(Document tableDefinition, Object source) {
        this(XMLTableDefinition.load(tableDefinition), source);
    }

    public XMLTableModel(XMLTableDefinition definition, Object source) {
        this.definition = definition;
        this.source = source;
    }

    public Object getRowValue(int rowIndex) {
        return getRows().get(rowIndex);
    }

    public List<Node> getRows() {
        if (rows == null) {
            rows = definition.getRowXPath().selectNodes(source);
        }

        return rows;
    }

    // TableModel interface
    // -------------------------------------------------------------------------
    public Class<?> getColumnClass(int columnIndex) {
        return definition.getColumnClass(columnIndex);
    }

    public int getColumnCount() {
        return definition.getColumnCount();
    }

    public String getColumnName(int columnIndex) {
        XPath xpath = definition.getColumnNameXPath(columnIndex);

        if (xpath != null) {
            System.out.println("Evaluating column xpath: " + xpath + " value: "
                    + xpath.valueOf(source));

            return xpath.valueOf(source);
        }

        return definition.getColumnName(columnIndex);
    }

    public Object getValueAt(int rowIndex, int columnIndex) {
        try {
            Object row = getRowValue(rowIndex);

            return definition.getValueAt(row, columnIndex);
        } catch (Exception e) {
            handleException(e);

            return null;
        }
    }

    public int getRowCount() {
        return getRows().size();
    }

    // Properties
    // -------------------------------------------------------------------------

    /**
     * Getter for property definition.
     * 
     * @return Value of property definition.
     */
    public XMLTableDefinition getDefinition() {
        return definition;
    }

    /**
     * Setter for property definition.
     * 
     * @param definition
     *            New value of property definition.
     */
    public void setDefinition(XMLTableDefinition definition) {
        this.definition = definition;
    }

    /**
     * Getter for the XML source, which is usually a Node or List of nodes.
     * 
     * @return Value of property source.
     */
    public Object getSource() {
        return source;
    }

    /**
     * Setter for the XML source, which is usually a Node or List of nodes.
     * 
     * @param source
     *            New value of property source.
     */
    public void setSource(Object source) {
        this.source = source;
        this.rows = null;
    }

    // Implementation methods
    // -------------------------------------------------------------------------
    protected void handleException(Exception e) {
        // #### should use jakarta commons-logging
        System.out.println("Caught: " + e);
    }
}

