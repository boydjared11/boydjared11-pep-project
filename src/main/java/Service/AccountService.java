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
     * TODO: Use the FlightDAO to add a new flight to the database.
     *
     * This method should also return the added flight. A distinction should be made between *transient* and
     * *persisted* objects - the *transient* flight Object given as the parameter will not contain the flight's id,
     * because it is not yet a database record. When this method is used, it should return the full persisted flight,
     * which will contain the flight's id. This way, any part of the application that uses this method has
     * all information about the new flight, because knowing the new flight's ID is necessary. This means that the
     * method should return the Flight returned by the flightDAO's insertFlight method, and not the flight provided by
     * the parameter 'flight'.
     *
     * @param flight an object representing a new Flight.
     * @return the newly added flight if the add operation was successful, including the flight_id. We do this to
     *         inform our provide the front-end client with information about the added Flight.
     */
    public Account addAccount(Account account) {
        if (account.getUsername() != "" & account.getPassword().length() >= 4 & accountDAO.getAccountByUsername(account.getUsername()) == null) {
            return accountDAO.insertAccount(account);
        } else {
            return null;
        }   
    }
    
}
