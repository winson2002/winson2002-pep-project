package DAO;

import Model.Account;
import Model.Message;
import Util.ConnectionUtil;

import java.sql.*;

public class MessageDAO {
    public Message insertAccount() {
        try(Connection connection = ConnectionUtil.getConnection();) {
            
            }
        } catch(SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
}
