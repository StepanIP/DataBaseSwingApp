package org.example.GUI.component;

import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.TableColumn;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.table.TableColumn;


public class CustomTableColumnModel extends DefaultTableColumnModel {
    public CustomTableColumnModel() {
        addColumn(createColumn(0, "id"));
        addColumn(createColumn(1, "Name"));
        addColumn(createColumn(2, "Email"));
        addColumn(createColumn(3, "PhoneNumber"));
    }

    private TableColumn createColumn(int columnIndex, String columnName) {
        TableColumn column = new TableColumn(columnIndex);
        column.setHeaderValue(columnName);

        return column;
    }
}
