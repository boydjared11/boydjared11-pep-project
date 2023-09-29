package Service;

import Model.Message;
import DAO.MessageDAO;

import java.util.List;

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
     * Uses the MessageDAO to add a new message to the database.
     * This method should also return the added message. A distinction should be made between *transient* and
     * *persisted* objects - the *transient* message Object given as the parameter will not contain the message's id,
     * because it is not yet a database record. When this method is used, it should return the full persisted message,
     * which will contain the message's id. This way, any part of the application that uses this method has
     * all information about the new message, because knowing the new message's ID is necessary. This means that the
     * method should return the Message returned by the messageDAO's insertMessage method, and not the message provided by
     * the parameter 'flight'.
     *
     * @param flight an object representing a new Message.
     * @return the newly added message if the add operation was successful, including the message_id. We do this to
     *         inform and provide the front-end client with information about the added Message.
     */
    public Message addMessage(Message message) {
        if (message.getMessage_text() != "" & message.getMessage_text().length() < 255 & messageDAO.postedByRefersToRealExisitingUser(message.getPosted_by()) != null) {
            return messageDAO.insertMessage(message);
        } else {
            return null;
        }   
    }

    /**
     * Uses the MessageDAO to retrieve a List containing all messages.
     *
     * @return all messages in the database.
     */
    public List<Message> getAllMessages() {
        return messageDAO.getAllMessages();
    }

    public Message getMessageByMessageId(int message_id) {
        return messageDAO.getMessageByMessageId(message_id);
    }

    public Message deleteMessageByMessageId(int message_id) {
        Message temp = messageDAO.getMessageByMessageId(message_id);
        messageDAO.deleteMessageByMessageId(message_id);
        return temp;
    }

    /**
     * Uses the MessageDAO to update an existing message from the database.
     * First checks that the message ID already exists. To do this, an if statement is used that checks
     * if messageDAO.getMessageByMessageId returns null for the message's ID, as this would indicate that the message id does not
     * exist.
     *
     * @param message an object containing all data that should replace the values contained by the existing message_id.
     *         the message object does not contain a message ID.
     * @param message_id the ID of the message to be modified.
     * @return the newly updated message if the update operation was successful. Return null if the update operation was
     *         unsuccessful. We do this to inform our application about successful/unsuccessful operations. (eg, the
     *         user should have some insight if they attempted to edit a nonexistent flight.)
     */
    public Message updateMessage(Message message, int message_id) {
        if (messageDAO.getMessageByMessageId(message_id) == null | message.getMessage_text() == "" | message.getMessage_text().length() >= 255) {
            return null;
        } else {
            messageDAO.updateMessage(message, message_id);
            return messageDAO.getMessageByMessageId(message_id);
        }
    }

    /**
     * Uses the MessageDAO to retrieve a List containing all messages posted by a particular user.
     *
     * @return all messages posted by a particular user in the database.
     */
    public List<Message> getAllMessagesForUser(int account_id) {
        return messageDAO.getAllMessagesForUser(account_id);
    }
}
