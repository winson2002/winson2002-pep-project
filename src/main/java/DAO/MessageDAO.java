package DAO;

import Model.Account;
import Model.Message;
import Util.ConnectionUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MessageDAO {
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

    public Message deleteMessage(int messageID) {
        try(Connection connection = ConnectionUtil.getConnection();) {
            String sql = "DELETE FROM message WHERE message_id=?";
            PreparedStatement ps = connection.prepareStatement(sql);

            ps.setInt(1, messageID);
            
            ps.executeUpdate();
        } catch(SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
}