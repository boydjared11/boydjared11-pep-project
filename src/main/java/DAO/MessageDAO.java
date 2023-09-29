package DAO;

import Model.Message;
import Model.Account;
import Util.ConnectionUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MessageDAO {

    /**
     * TODO: Add a flight record into the database which matches the values contained in the flight object.
     * You can use the getters already written in the Flight class to retrieve its values (getDeparture_city(),
     * getArrival_city()). The flight_id will be automatically generated by the SQL database, and JDBC will be able
     * to retrieve the generated ID automatically. That means that you when you insert the flight, you only need
     * to define the departure_city and arrival_city values (two columns total!)
     *
     * @param flight an object modelling a Flight. the flight object does not contain a flight ID.
     */
    public Message insertMessage(Message message) {
        Connection connection = ConnectionUtil.getConnection();
        try {
            String sql = "INSERT INTO message (posted_by, message_text, time_posted_epoch) VALUES (?, ?, ?);" ;

            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            preparedStatement.setInt(1, message.getPosted_by());
            preparedStatement.setString(2, message.getMessage_text());
            preparedStatement.setLong(3, message.getTime_posted_epoch());

            preparedStatement.executeUpdate();
            ResultSet pkeyResultSet = preparedStatement.getGeneratedKeys();
            if (pkeyResultSet.next()) {
                int generated_message_id = (int) pkeyResultSet.getLong(1);
                return new Message(generated_message_id, message.getPosted_by(), message.getMessage_text(), message.getTime_posted_epoch());
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
    public Account postedByRefersToRealExisitingUser(int posted_by) {
        Connection connection = ConnectionUtil.getConnection();
        try {
            String sql = "SELECT * FROM account WHERE account_id = ?;";
            
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, posted_by);

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

    /**
     * TODO: Retrieve all flights from the flight table.
     *
     * You only need to change the sql String and set preparedStatement parameters.
     *
     * @return all flights.
     */
    public List<Message> getAllMessages() {
        Connection connection = ConnectionUtil.getConnection();
        List<Message> messages = new ArrayList<>();
        try {
            String sql = "SELECT * FROM message;";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                Message message = new Message(rs.getInt("message_id"), rs.getInt("posted_by"),
                        rs.getString("message_text"), rs.getLong("time_posted_epoch"));
                messages.add(message);
            }
        }catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return messages;
    }

    /**
     * TODO: Use the FlightDAO to update an existing flight from the database.
     * You should first check that the flight ID already exists. To do this, you could use an if statement that checks
     * if flightDAO.getFlightById returns null for the flight's ID, as this would indicate that the flight id does not
     * exist.
     *
     * @param flight_id the ID of the flight to be modified.
     * @param flight an object containing all data that should replace the values contained by the existing flight_id.
     *         the flight object does not contain a flight ID.
     * @return the newly updated flight if the update operation was successful. Return null if the update operation was
     *         unsuccessful. We do this to inform our application about successful/unsuccessful operations. (eg, the
     *         user should have some insight if they attempted to edit a nonexistent flight.)
     */
    public void updateMessage(Message message, int message_id) {
        Connection connection = ConnectionUtil.getConnection();
        try {
            String sql = "UPDATE message SET message_text = ? WHERE message_id = ?;";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, message.getMessage_text());
            preparedStatement.setInt(2, message_id);

            preparedStatement.executeUpdate();
        }catch (SQLException e) {
            System.out.println(e.getMessage());
        }
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
    public Message getMessageByMessageId(int message_id) {
        Connection connection = ConnectionUtil.getConnection();
        try {
            String sql = "SELECT * FROM message WHERE message_id = ?;";
            
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, message_id);

            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()){
                Message message = new Message(rs.getInt("message_id"), rs.getInt("posted_by"),
                        rs.getString("message_text"), rs.getLong("time_posted_epoch"));
                return message;
            }
        } catch(SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
}
