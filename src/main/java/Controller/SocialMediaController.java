package Controller;

import Model.Account;
import Model.Message;
import Service.AccountService;
import Service.MessageService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.javalin.Javalin;
import io.javalin.http.Context;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller. The endpoints you will need can be
 * found in readme.md as well as the test cases. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
public class SocialMediaController {
    AccountService accountService;
    MessageService messageService;

    public SocialMediaController() {
        accountService = new AccountService();
        messageService = new MessageService();
    }

    /**
     * In order for the test cases to work, you will need to write the endpoints in the startAPI() method, as the test
     * suite must receive a Javalin object from this method.
     * @return a Javalin app object which defines the behavior of the Javalin controller.
     */
    public Javalin startAPI() {
        Javalin app = Javalin.create();

        app.post("/register", this::userRegistrationHandler);
        app.post("/login", this::userLoginHandler);
        app.post("/messages", this::createMessageHandler);
        app.get("/messages", this::retrieveAllMessagesHandler);
        app.get("/messages/{message_id}", this::retrieveMessageByMessageIdHandler);
        app.delete("/messages/{message_id}", this::deleteMessageByMessageIdHandler);
        app.patch("/messages/{message_id}", this::updateMessageTextHandler);
        app.get("/accounts/{account_id}/messages", this::retrieveAllMessagesForUser);
        
        return app;
    }

    /**
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     * @throws JsonProcessingException
     */
    private void userRegistrationHandler(Context context) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Account account = mapper.readValue(context.body(), Account.class);
        Account addedAccount = accountService.addAccount(account);
        if (addedAccount == null) {
            context.status(400);
        } else {
            context.json(mapper.writeValueAsString(addedAccount));
        }
    }

     /**
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     * @throws JsonProcessingException
     */
    private void userLoginHandler(Context context) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Account account = mapper.readValue(context.body(), Account.class);
        Account verifiedAccount = accountService.verifyAccount(account);
        if (verifiedAccount == null) {
            context.status(401);
        } else {
            context.json(mapper.writeValueAsString(verifiedAccount));
        }
    }

     /**
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     * @throws JsonProcessingException
     */
    private void createMessageHandler(Context context) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Message message = mapper.readValue(context.body(), Message.class);
        Message addedMessage = messageService.addMessage(message);
        if (addedMessage == null) {
            context.status(400);
        } else {
            context.json(mapper.writeValueAsString(addedMessage));
        }
    }

     /**
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */
    private void retrieveAllMessagesHandler(Context context) {
        context.json(messageService.getAllMessages());
    }

     /**
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */
    private void retrieveMessageByMessageIdHandler(Context context) {
        int message_id = Integer.parseInt(context.pathParam("message_id"));
        if (messageService.getMessageByMessageId(message_id) == null) {
            context.status(200);
        } else {
            context.json(messageService.getMessageByMessageId(message_id));
        }
    }

     /**
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */
    private void deleteMessageByMessageIdHandler(Context context) {
        int message_id = Integer.parseInt(context.pathParam("message_id"));
        if (messageService.getMessageByMessageId(message_id) == null) {
            context.status(200);
        } else {
            context.json(messageService.deleteMessageByMessageId(message_id));
        }
    }

     /**
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     * @throws JsonProcessingException
     */
    private void updateMessageTextHandler(Context context) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Message message = mapper.readValue(context.body(), Message.class);
        int message_id = Integer.parseInt(context.pathParam("message_id"));
        Message updatedMessage = messageService.updateMessage(message, message_id);
        System.out.println(updatedMessage);
        if (updatedMessage == null) {
            context.status(400);
        } else {
            context.json(mapper.writeValueAsString(updatedMessage));
        }
    }

     /**
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */
    private void retrieveAllMessagesForUser(Context context) {
        int account_id = Integer.parseInt(context.pathParam("account_id"));
        context.json(messageService.getAllMessagesForUser(account_id));
    }
}