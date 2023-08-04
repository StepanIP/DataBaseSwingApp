package org.example.GUI.component;

import org.example.entity.Buyer;

import javax.swing.table.AbstractTableModel;
import java.util.List;

public class BuyerTableModel extends AbstractTableModel {
    private final List<Buyer> BUYERS;
    private final String[] columnNames = {"id", "Name", "Email", "PhoneNumber"};

    public BuyerTableModel(List<Buyer> buyers) {
        this.BUYERS = buyers;
    }

    @Override
    public int getRowCount() {
        return BUYERS.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Buyer buyer = BUYERS.get(rowIndex);
        return switch (columnIndex) {
            case 0 -> buyer.getId();
            case 1 -> buyer.getName();
            case 2 -> buyer.getEmail();
            case 3 -> buyer.getPhone_number();
            default -> null;
        };
    }


    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return columnIndex != 0;
    }

    @Override
    public void setValueAt(Object value, int rowIndex, int columnIndex) {
        Buyer buyer = BUYERS.get(rowIndex);
        switch (columnIndex) {
            case 1 -> buyer.setName((String) value);
            case 2 -> buyer.setEmail((String) value);
            case 3 -> buyer.setPhone_number((String) value);
            default -> throw new IllegalArgumentException("Invalid column index");
        }
    }
}
