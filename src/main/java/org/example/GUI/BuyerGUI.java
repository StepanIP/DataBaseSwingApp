package org.example.GUI;

import org.example.DAO.BuyerDAO;
import org.example.GUI.component.BuyerTableModel;
import org.example.GUI.component.CustomTableColumnModel;
import org.example.Main;
import org.example.entity.Buyer;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;


public class BuyerGUI extends JFrame {
    JTable table;

    int sizeBefore;

    int sizeAfter;

    List<Buyer> buyers;

    private int insertedData;

    JScrollPane scrollPane;

    public BuyerGUI(Connection connection) {
        JPanel panel = new JPanel();

        buyers = Main.getListOfBuyers(connection);

        scrollPane = initializeTable(buyers);

        sizeBefore = buyers.size();

        JButton DELETE = new JButton("DELETE");
        DELETE.addActionListener(e -> {
            try {
                PreparedStatement preparedStatement = connection.prepareStatement("select id from buyer where id=?");
                preparedStatement.setInt(1, (Integer) table.getValueAt(table.getSelectedRow(), table.getSelectedColumn()));
                ResultSet isExist = preparedStatement.executeQuery();
                if (isExist.next()) {
                    System.out.println(table.getValueAt(table.getSelectedRow(), table.getSelectedColumn()));
                    System.out.println(BuyerDAO.deleteBuyer(connection, (Integer) table.getValueAt(table.getSelectedRow(), table.getSelectedColumn())));
                }
                System.out.println(buyers);
                int index = -1;
                int counter = 0;
                for(Buyer b : buyers){
                    if(b.getId()-(int)table.getValueAt(table.getSelectedRow(), table.getSelectedColumn())==0){
                        index=counter;
                    }
                    counter++;
                }
                buyers.remove(index);
                if (sizeBefore == sizeAfter) {
                    sizeBefore -= 1;
                } else {
                    sizeAfter -= 1;
                }
                table.revalidate();
                table.repaint();

            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        });

        JButton ADD = new JButton("ADD COLUMN");
        JButton COMMIT = new JButton("COMMIT");

        ADD.addActionListener(e -> {
            buyers.add(new Buyer(buyers.get(buyers.size()-1).getId()+1));
            sizeAfter = buyers.size();
            table.revalidate();
            table.repaint();
        });

        COMMIT.addActionListener(e -> {
            int amountOfInsertedData = sizeAfter - sizeBefore;
            List<Buyer> newBuyers = buyers.subList(buyers.size() - amountOfInsertedData, buyers.size());
            try {
                insertedData += BuyerDAO.insertBuyers(connection, newBuyers);
                sizeBefore += amountOfInsertedData;
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        });

        panel.add(scrollPane);
        panel.add(DELETE);
        panel.add(ADD);
        panel.add(COMMIT);
        add(panel);
        setVisible(true);
        setLocationRelativeTo(null);
        setSize(new Dimension(500, 550));
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle("Example od database");
    }

    public JScrollPane initializeTable(List<Buyer> buyers) {
        BuyerTableModel model = new BuyerTableModel(buyers);
        table = new JTable(model, new CustomTableColumnModel());
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setSize(table.getSize());
        return scrollPane;
    }

}


