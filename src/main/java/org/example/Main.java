package org.example;
import org.example.GUI.BuyerGUI;
import org.example.entity.Buyer;
import org.example.settings.ConnectionManager;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public final static String SQL = """
            Select * from buyer;
            """;

    public static void main(String[] args) {
        Connection connection = ConnectionManager.getConnection();;
            BuyerGUI buyerGUI = new BuyerGUI(connection);
    }

    public static List<Buyer> getListOfBuyers(Connection connection){
        try {
            PreparedStatement statement = connection.prepareStatement(SQL);
            ResultSet resultSet = statement.executeQuery();
            List<Buyer> buyerList = new ArrayList<>();
            while (resultSet.next()) {
                buyerList.add(new Buyer(
                        resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getString("email"),
                        resultSet.getString("phone_number")));
            }
            return  buyerList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}