package DAO;

import Model.Account;
import Util.ConnectionUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AccountDAO {
    public Account insertAccount(Account account) {
        try(Connection connection = ConnectionUtil.getConnection();) {
            String sql = "INSERT INTO account(username, password) VALUES(?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            preparedStatement.setString(1, account.getUsername());
            preparedStatement.setString(2, account.getPassword());
            
            preparedStatement.executeUpdate();

            ResultSet pkeyResultSet = preparedStatement.getGeneratedKeys();
            if(pkeyResultSet.next()){
                int generated_account_id = (int) pkeyResultSet.getLong(1);
                return new Account(generated_account_id, account.getUsername(), account.getPassword());
            }
        } catch(SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public Account duplicateAccount(String username) {
        try(Connection connection = ConnectionUtil.getConnection();) {
            String sql = "SELECT * FROM account WHERE username=?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, username);

            ResultSet rs = preparedStatement.executeQuery();
            if(rs.next()) {
                return new Account(rs.getInt("account_id"), rs.getString("username"), rs.getString("password"));
            }
        } catch(SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public Account checkAccount(Account account) {
        try(Connection connection = ConnectionUtil.getConnection();) {
            String sql = "SELECT password FROM account WHERE username=?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, account.getUsername());

            
        } catch(SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
}
