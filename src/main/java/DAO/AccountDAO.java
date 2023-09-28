package DAO;

import Model.Account;
import Util.ConnectionUtil;

import java.sql.*;

public class AccountDAO {

    /**
     * TODO: Add a flight record into the database which matches the values contained in the flight object.
     * You can use the getters already written in the Flight class to retrieve its values (getDeparture_city(),
     * getArrival_city()). The flight_id will be automatically generated by the SQL database, and JDBC will be able
     * to retrieve the generated ID automatically. That means that you when you insert the flight, you only need
     * to define the departure_city and arrival_city values (two columns total!)
     *
     * @param flight an object modelling a Flight. the flight object does not contain a flight ID.
     */
    public Account insertAccount(Account account) {
        Connection connection = ConnectionUtil.getConnection();
        try {
            String sql = "INSERT INTO account (username, password) VALUES (?, ?);" ;

            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            preparedStatement.setString(1, account.getUsername());
            preparedStatement.setString(2, account.getPassword());

            preparedStatement.executeUpdate();
            ResultSet pkeyResultSet = preparedStatement.getGeneratedKeys();
            if (pkeyResultSet.next()) {
                int generated_account_id = (int) pkeyResultSet.getLong(1);
                return new Account(generated_account_id, account.getUsername(), account.getPassword());
            }
        }catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    /**
     * TODO: Retrieve a specific flight using its flight ID.
     *
     * You only need to change the sql String and set preparedStatement parameters.
     *
     * Remember that the format of a select where statement written as a Java String looks something like this:
     * String sql = "select * from TableName where ColumnName = ?";
     * The question marks will be filled in by the preparedStatement setString, setInt, etc methods. they follow
     * this format, where the first argument identifies the question mark to be filled (left to right, starting
     * from zero) and the second argument identifies the value to be used:
     * preparedStatement.setInt(1,int1);
     *
     * @param id a flight ID.
     */
    public Account getAccountByUsername(String username) {
        Connection connection = ConnectionUtil.getConnection();
        try {
            String sql = "SELECT * FROM account WHERE username = ?;";
            
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, username);

            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()){
                Account account = new Account(rs.getInt("account_id"), rs.getString("username"),
                        rs.getString("password"));
                return account;
            }
        } catch(SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public Account getAccountByUsernameAndPassword(Account account) {
        Connection connection = ConnectionUtil.getConnection();
        try {
            String sql = "SELECT * FROM account WHERE username = ? AND password = ?;";
            
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, account.getUsername());
            preparedStatement.setString(2, account.getPassword());

            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                return new Account(rs.getInt("account_id"), rs.getString("username"), rs.getString("password"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
}
