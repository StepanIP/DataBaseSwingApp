package org.example.DAO;

import org.example.entity.Buyer;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BuyerDAO {

    private static int before;

    private static int after;


    private final static String SELECT_BY_ID = """
            Select * from buyer order by id
            """;

    private final static String DELETE = """
                DELETE FROM buyer WHERE id = ?;
            """;

    private final static String SELECT_ALL = """
            Select count(*) from buyer as total;
            """;

    private final static String ADD_BUYER = """
            insert into buyer(id,name, email, phone_number) values (?,?,?,?);
            """;

    public static List<Buyer> getBuyers(ResultSet resultSet) throws SQLException {
        List<Buyer> buyerList = new ArrayList<>();
        while (resultSet.next()) {
            buyerList.add(new Buyer(
                    resultSet.getInt("id"),
                    resultSet.getString("name"),
                    resultSet.getString("email"),
                    resultSet.getString("phone_number")));
        }

        return buyerList;
    }

    public static int deleteBuyer(Connection connection, int id) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL);
        before = calculateChanges(preparedStatement);
        preparedStatement = connection.prepareStatement(DELETE);
        preparedStatement.setInt(1, id);
        preparedStatement.executeUpdate();
        preparedStatement = connection.prepareStatement(SELECT_ALL);
        after = calculateChanges(preparedStatement);
        return before - after;
    }

    public static int insertBuyer(Connection connection, int id, String name, String email, String phone_number) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL);
        before = calculateChanges(preparedStatement);
        preparedStatement = connection.prepareStatement(ADD_BUYER);
        preparedStatement.setInt(1, id);
        preparedStatement.setString(2, name);
        preparedStatement.setString(3, email);
        preparedStatement.setString(4, phone_number);
        preparedStatement.executeQuery();
        preparedStatement = connection.prepareStatement(SELECT_ALL);
        after = calculateChanges(preparedStatement);
        return after - before;
    }

    private static int calculateChanges(PreparedStatement preparedStatement) throws SQLException {
        int amount = 0;
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            amount = resultSet.getInt(1);
        }
        return amount;
    }

    public static int insertBuyers(Connection connection, List<Buyer> buyers) throws SQLException {
        int insertedCount = 0;
        PreparedStatement statement = connection.prepareStatement(ADD_BUYER);
        for (Buyer buyer : buyers) {
            statement.setInt(1, buyer.getId());
            statement.setString(2, buyer.getName());
            statement.setString(3, buyer.getEmail());
            statement.setString(4, buyer.getPhone_number());
            statement.addBatch();
        }
        int[] result = statement.executeBatch();
        for (int i : result) {
            if (i == Statement.EXECUTE_FAILED) {
                throw new SQLException("Batch insert failed");
            } else {
                insertedCount++;
            }
        }
        return insertedCount;
    }

}
