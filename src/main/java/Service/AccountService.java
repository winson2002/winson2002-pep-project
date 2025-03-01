package Service;

import Model.Account;
import DAO.AccountDAO;

public class AccountService {
    private AccountDAO accountDAO;

    public AccountService() {
        accountDAO = new AccountDAO();
    }

    public AccountService(AccountDAO accountDAO){
        this.accountDAO = accountDAO;
    }

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

    public Account login(String username, String password) {
        Account account = accountDAO.getAccount(username);
        if(account != null && account.getPassword().equals(password)) {
            return account;
        }
        return null;
    }
}