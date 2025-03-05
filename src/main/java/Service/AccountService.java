package Service;

import Model.Account;
import DAO.AccountDAO;

public class AccountService {
    private AccountDAO accountDAO;

    /**
     * no-args constructor for creating a new AccountService with a new AuthorDAO.
     */
    public AccountService() {
        accountDAO = new AccountDAO();
    }

    /**
     * Constructor for a AccountService when a AccountDAO is provided.
     * 
     * @param accountDAO
     */
    public AccountService(AccountDAO accountDAO){
        this.accountDAO = accountDAO;
    }

    /**
     * Checks if username is empty, if password is empty or less than 4 characters, or if there is a duplicate account.
     * 
     * @param username not empty, no duplicates
     * @param password not empty, longer than 4 characters
     * @return info of the new account
     */
    public Account registerAccount(String username, String password) {
        if(username == null || username.trim().isEmpty()) {
            return null;
        }

        if(password == null || password.length() < 4) {
            return null;
        }

        if(accountDAO.getAccount(username) != null) {
            return null;
        }

        return accountDAO.insertAccount(username, password);
    }

    /**
     * Checks if there is an account with the username and if the password is associated with the username.
     * @param username valid account
     * @param password associated with the username
     * @return info of the logged account
     */
    public Account login(String username, String password) {
        Account account = accountDAO.getAccount(username);
        if(account != null && account.getPassword().equals(password)) {
            return account;
        }
        return null;
    }
}