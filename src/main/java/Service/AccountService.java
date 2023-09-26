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
    
}
