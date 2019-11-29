

package org.dom4j.swing;

import org.dom4j.AbstractTestCase;
import org.dom4j.Document;

import javax.swing.table.TableModel;

/**
 * Tests the Swing TableModel using a dom4j document.
 * 
 * @author <a href="mailto:jstrachan@apache.org">James Strachan </a>
 * @version $Revision: 1.4 $
 */
public class TableModelTest extends AbstractTestCase {
    public void testServletTable() throws Exception {
        Document document = getDocument("/xml/web.xml");

        XMLTableDefinition tableDefinition = new XMLTableDefinition();
        tableDefinition.setRowExpression("/web-app/servlet");
        tableDefinition.addStringColumn("Name", "servlet-name");
        tableDefinition.addStringColumn("Class", "servlet-class");

        String mapping = "../servlet-mapping[servlet-name=$Name]/url-pattern";
        tableDefinition.addStringColumn("Mapping", mapping);

        XMLTableModel tableModel = new XMLTableModel(tableDefinition, document);

        // now lets test the values come out
        assertEquals("correct row count", tableModel.getRowCount(), 2);
        assertEquals("correct column count", tableModel.getColumnCount(), 3);

        assertColumnNameEquals(tableModel, 0, "Name");
        assertColumnNameEquals(tableModel, 1, "Class");
        assertColumnNameEquals(tableModel, 2, "Mapping");

        assertCellEquals(tableModel, 0, 0, "snoop");
        assertCellEquals(tableModel, 1, 0, "file");
        assertCellEquals(tableModel, 0, 1, "SnoopServlet");
        assertCellEquals(tableModel, 1, 1, "ViewFile");
        assertCellEquals(tableModel, 0, 2, "/foo/snoop");
        assertCellEquals(tableModel, 1, 2, "");
    }

    public void testServletTableViaXMLDescription() throws Exception {
        Document definition = getDocument("/xml/swing/tableForWeb.xml");
        Document document = getDocument("/xml/web.xml");

        XMLTableModel tableModel = new XMLTableModel(definition, document);

        // now lets test the values come out
        assertEquals("correct row count", tableModel.getRowCount(), 2);
        assertEquals("correct column count", tableModel.getColumnCount(), 3);

        assertColumnNameEquals(tableModel, 0, "Name");
        assertColumnNameEquals(tableModel, 1, "Class");
        assertColumnNameEquals(tableModel, 2, "Mapping");

        assertCellEquals(tableModel, 0, 0, "snoop");
        assertCellEquals(tableModel, 1, 0, "file");
        assertCellEquals(tableModel, 0, 1, "SnoopServlet");
        assertCellEquals(tableModel, 1, 1, "ViewFile");
        assertCellEquals(tableModel, 0, 2, "/foo/snoop");
        assertCellEquals(tableModel, 1, 2, "");
    }

    protected void assertColumnNameEquals(TableModel tableModel,
            int columnIndex, String name) {
        assertEquals("Column name correct for index: " + columnIndex, name,
                tableModel.getColumnName(columnIndex));
    }

    protected void assertCellEquals(TableModel tableModel, int rowIndex,
            int columnIndex, Object value) {
        assertEquals("Cell value at row: " + rowIndex + " col: " + columnIndex,
                value, tableModel.getValueAt(rowIndex, columnIndex));
    }
}

