package Service;

import Model.Message;
import DAO.MessageDAO;

public class MessageService {
    MessageDAO messageDAO;

    /**
     * No-args constructor for a messageService instantiates a plain messageDAO.
     * There is no need to modify this constructor.
     */
    public MessageService(){
        messageDAO = new MessageDAO();
    }

    /**
     * Constructor for a messageService when a messageDAO is provided.
     * This is used for when a mock messageDAO that exhibits mock behavior is used in the test cases.
     * This would allow the testing of MessageService independently of MessageDAO.
     * There is no need to modify this constructor.
     * @param messageDAO
     */
    public MessageService(MessageDAO messageDAO){
        this.messageDAO = messageDAO;
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
    public Message addMessage(Message message) {
        if (message.getMessage_text() != "" & message.getMessage_text().length() < 255 & messageDAO.postedByRefersToRealExisitingUser(message.getPosted_by()) != null) {
            return messageDAO.insertMessage(message);
        } else {
            return null;
        }   
    }
}
