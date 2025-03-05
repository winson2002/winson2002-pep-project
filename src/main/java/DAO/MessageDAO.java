package DAO;

import Model.Message;
import Util.ConnectionUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MessageDAO {
    /**
     * Inserting message info into the database.
     * 
     * @param userID account ID belonging to the user
     * @param text the message
     * @param time when the message was inserted
     * @return the inserted message
     */
    public Message postMessage(int userID, String text, long time) {
        try(Connection connection = ConnectionUtil.getConnection();) {
            String sql = "INSERT INTO message(posted_by, message_text, time_posted_epoch) VALUES(?, ?, ?)";
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            ps.setInt(1, userID);
            ps.setString(2, text);
            ps.setLong(3, time);
            
            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            while(rs.next()){
                return new Message(rs.getInt(1), userID, text, time);
            }
        } catch(SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    /**
     * Check if the account ID is in the database.
     * 
     * @param userID account ID belonging to the user
     * @return the retrieved messages
     */
    public Message realPerson(int userID) {
        try(Connection connection = ConnectionUtil.getConnection();) {
            String sql = "SELECT * FROM message WHERE posted_by=?";
            PreparedStatement ps = connection.prepareStatement(sql);

            ps.setInt(1, userID);
            
            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                return new Message(rs.getInt("message_id"), rs.getInt("posted_by"), rs.getString("message_text"), rs.getLong("time_posted_epoch"));
            }
        } catch(SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    /**
     * Store all the messages in the database into a list.
     * 
     * @return all messages
     */
    public List<Message> getAllBooks() {
        List<Message> messages = new ArrayList<>();
        try(Connection connection = ConnectionUtil.getConnection();) {
            String sql = "SELECT * FROM message";
            PreparedStatement ps = connection.prepareStatement(sql);
            
            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                Message message = new Message(rs.getInt("message_id"), rs.getInt("posted_by"), rs.getString("message_text"), rs.getLong("time_posted_epoch"));
                messages.add(message);
            }
        } catch(SQLException e) {
            System.out.println(e.getMessage());
        }
        return messages;
    }

    /**
     * Search message using message ID in the database.
     * 
     * @param messageID ID associated with the message
     * @return the retrieved message
     */
    public Message getMessageByID(int messageID) {
        try(Connection connection = ConnectionUtil.getConnection();) {
            String sql = "SELECT * FROM message WHERE message_id=?";
            PreparedStatement ps = connection.prepareStatement(sql);

            ps.setInt(1, messageID);
            
            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                return new Message(rs.getInt("message_id"), rs.getInt("posted_by"), rs.getString("message_text"), rs.getLong("time_posted_epoch"));
            }
        } catch(SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    /**
     * Deletes message associated with the message ID from the database.
     * 
     * @param messageID ID associated with the message
     */
    public void deleteMessage(int messageID) {
        try(Connection connection = ConnectionUtil.getConnection();) {
            String sql = "DELETE FROM message WHERE message_id=?";
            PreparedStatement ps = connection.prepareStatement(sql);

            ps.setInt(1, messageID);
            
            ps.executeUpdate();
        } catch(SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Update current message with new message using message ID.
     * 
     * @param messageID ID associated with the message
     * @param text new message
     */
    public void updateMessage(int messageID, String text) {
        try(Connection connection = ConnectionUtil.getConnection();) {
            String sql = "UPDATE message SET message_text=? WHERE message_id=?";
            PreparedStatement ps = connection.prepareStatement(sql);

            ps.setString(1, text);
            ps.setInt(2, messageID);

            ps.executeUpdate();
        } catch(SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Search all messages associated with user ID in the database.
     * 
     * @param accountID ID belonging to the user
     * @return list of all messages
     */
    public List<Message> getAllMessagesByAccount(int accountID) {
        List<Message> messages = new ArrayList<>();
        try(Connection connection = ConnectionUtil.getConnection();) {
            String sql = "SELECT * FROM message WHERE posted_by=?";
            PreparedStatement ps = connection.prepareStatement(sql);
            
            ps.setInt(1, accountID);

            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                Message message = new Message(rs.getInt("message_id"), rs.getInt("posted_by"), rs.getString("message_text"), rs.getLong("time_posted_epoch"));
                messages.add(message);
            }
        } catch(SQLException e) {
            System.out.println(e.getMessage());
        }
        return messages;
    }
}