package Service;

import Model.Account;
import DAO.AccountDAO;

public class AccountService {
    AccountDAO accountDAO;

    /**
     * No-args constructor for an accountService instantiates a plain accountDAO.
     * There is no need to modify this constructor.
     */
    public AccountService(){
        accountDAO = new AccountDAO();
    }

    /**
     * Constructor for an accountService when an accountDAO is provided.
     * This is used for when a mock accountDAO that exhibits mock behavior is used in the test cases.
     * This would allow the testing of AccountService independently of AccountDAO.
     * There is no need to modify this constructor.
     * @param accountDAO
     */
    public AccountService(AccountDAO accountDAO){
        this.accountDAO = accountDAO;
    }

    /**
     * Uses the FlightDAO to add a new flight to the database.
     * This method should also return the added flight. A distinction should be made between *transient* and
     * *persisted* objects - the *transient* account Object given as the parameter will not contain the account's id,
     * because it is not yet a database record. When this method is used, it should return the full persisted account,
     * which will contain the account's id. This way, any part of the application that uses this method has
     * all information about the new account, because knowing the new account's ID is necessary. This means that the
     * method should return the Account returned by the accountDAO's insertAccount method, and not the account provided by
     * the parameter 'account'.
     *
     * @param account an object representing a new Account.
     * @return the newly added account if the add operation was successful, including the account_id. We do this to
     *         inform and provide the front-end client with information about the added Account.
     */
    public Account addAccount(Account account) {
        if (account.getUsername() != "" & account.getPassword().length() >= 4 & accountDAO.getAccountByUsername(account.getUsername()) == null) {
            return accountDAO.insertAccount(account);
        } else {
            return null;
        }   
    }
    
    public Account verifyAccount(Account account) {
        return accountDAO.getAccountByUsernameAndPassword(account);
    }
}
