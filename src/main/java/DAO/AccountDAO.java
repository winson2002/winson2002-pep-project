package DAO;

import Model.Account;
import Util.ConnectionUtil;

import java.sql.*;

public class AccountDAO {
    /**
     * Inserting account info into the database.
     * 
     * @param username unique identifier for the user
     * @param password associated with the username
     * @return the inserted account
     */
    public Account insertAccount(String username, String password) {
        try(Connection connection = ConnectionUtil.getConnection();) {
            String sql = "INSERT INTO account(username, password) VALUES(?, ?)";
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            ps.setString(1, username);
            ps.setString(2, password);
            
            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            while(rs.next()){
                return new Account(rs.getInt(1), username, password);
            }
        } catch(SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    /**
     * Selecting an account that matches the username.
     * 
     * @param username what we need
     * @return the retrieved account
     */
    public Account getAccount(String username) {
        try(Connection connection = ConnectionUtil.getConnection();) {
            String sql = "SELECT * FROM account WHERE username=?";
            PreparedStatement ps = connection.prepareStatement(sql);

            ps.setString(1, username);
            
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                return new Account(rs.getInt("account_id"), rs.getString("username"), rs.getString("password"));
            }
        } catch(SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
}