package Controller;

import Model.Account;
import Model.Message;
import Service.AccountService;
import Service.MessageService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.javalin.Javalin;
import io.javalin.http.Context;

import java.util.List;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller. The endpoints you will need can be
 * found in readme.md as well as the test cases. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
public class SocialMediaController {
    AccountService accountService;
    MessageService messageService;

    public SocialMediaController(){
        this.accountService = new AccountService();
        this.messageService = new MessageService();
    }

    /**
     * In order for the test cases to work, you will need to write the endpoints in the startAPI() method, as the test
     * suite must receive a Javalin object from this method.
     * @return a Javalin app object which defines the behavior of the Javalin controller.
     */
    public Javalin startAPI() {
        Javalin app = Javalin.create();
        app.get("example-endpoint", this::exampleHandler);
        app.post("register", this::registerAccountHandler);
        app.post("login", this::loginHandler);
        app.post("messages", this::createMessageHandler);
        app.get("messages", this::getAllMessageHandler);
        app.get("messages/{message_id}", this::getMessageByIDHandler);
        app.delete("messages/{message_id}", this::deleteMessageByIDHandler);
        app.patch("messages/{message_id}", this::updateMessageByIdHandler);
        app.get("accounts/{account_id}/messages", this::getMessagesWithAccountIDHandler);

        return app;
    }

    /**
     * Handler method for registering an account.
     * AccountService will use the registerAccount method to check the requirements to create an account.
     * If all requirements are met, the account will be made and display the JSON of the account as well as a status 200.
     * If requirements are not met, it will display a status 400 (client error).
     * 
     * @param ctx the context object handles information HTTP requests and generates responses within Javalin
     * @throws JsonProcessingException will be thrown if there is an issue converting Json into an object
     */
    private void registerAccountHandler(Context ctx) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Account account = mapper.readValue(ctx.body(), Account.class);

        Account registerAccount = accountService.registerAccount(account.getUsername(), account.getPassword());
        if(registerAccount != null) {
            ctx.json(registerAccount).status(200);
        }
        else {
            ctx.status(400);
        }
    }

    /**
     * Handler method for logining in the user.
     * AccountService will use the login methodf to check if credentials are correct.
     * If credentials are correct, it will display the JSON of the account as well as a status 200.
     * If credentials are incorrect, it will display a status 401 (unauthorized).
     * 
     * @param ctx the context object handles information HTTP requests and generates responses within Javalin
     * @throws JsonProcessingException will be thrown if there is an issue converting Json into an object
     */
    private void loginHandler(Context ctx) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Account account = mapper.readValue(ctx.body(), Account.class);
        
        Account validAccount = accountService.login(account.getUsername(), account.getPassword());
        if(validAccount != null) {
            ctx.json(validAccount).status(200);
        }
        else {
            ctx.status(401);
        }
    }

    /**
     * Handler method for creating a message.
     * MessageService will use the createMessage method to check for requirements of the message.
     * If requirements are met, it will display the JSON of the message as well as a status 200.
     * If requirements are not met, it will display a status 400 (client error).
     * 
     * @param ctx the context object handles information HTTP requests and generates responses within Javalin
     * @throws JsonProcessingException will be thrown if there is an issue converting Json into an object
     */
    private void createMessageHandler(Context ctx) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Message message = mapper.readValue(ctx.body(), Message.class);

        Message postingMessage = messageService.createMessage(message.getPosted_by(), message.getMessage_text(), message.getTime_posted_epoch());
        if(postingMessage != null) {
            ctx.json(postingMessage).status(200);
        }
        else {
            ctx.status(400);
        }
    }

    /**
     * Handler method for getting all messages.
     * MessageService will use the getAllMessages to retrieve all messages in the database and store them in a list.
     * The output will display the JSON of all messages in the database as well as a status 200.
     * 
     * @param ctx the context object handles information HTTP requests and generates responses within Javalin
     */
    private void getAllMessageHandler(Context ctx) {
        List<Message> messages = messageService.getAllMessages();
        ctx.json(messages).status(200);
    }

    /**
     * Handler method for getting message using the ID of the message.
     * MessageService will use the getMessageByID method to find the message that matches the ID of the message.
     * If there is a match, it will display the JSON of the message as well as a status 200.
     * If there is no match, it will display an empty string as well as a status 200.
     * 
     * @param ctx the context object handles information HTTP requests and generates responses within Javalin
     */
    private void getMessageByIDHandler(Context ctx)  {
        int message_id = Integer.parseInt(ctx.pathParam("message_id"));
        Message messageByID = messageService.getMessageByID(message_id);
        if(messageByID != null) {
            ctx.json(messageByID).status(200);
        }
        else {
            ctx.result("").status(200);
        }
    }

    /**
     * Handler method for deleting a message by the ID of the message.
     * MessageService will use the deleteMessageByID method to find the message with the ID and delete it from the database.
     * If there is a match, it will display the JSON of the deleted message as well as a status 200.
     * If there is no match, it will display an empty string as well as a status 200.
     * 
     * @param ctx the context object handles information HTTP requests and generates responses within Javalin
     */
    private void deleteMessageByIDHandler(Context ctx) {
        int message_id = Integer.parseInt(ctx.pathParam("message_id"));
        Message deleteMessage = messageService.deleteMessageByID(message_id);
        if(deleteMessage != null) {
            ctx.json(deleteMessage).status(200);
        }
        else {
            ctx.result("").status(200);
        }
    }

    /**
     * Handler method for updating message by the ID of the message.
     * MessageService will use the updateMessageByID method to find the message with the correct ID.
     * If the message is found, updateMessageByID will replace the old message with the new message, but 
     * the new message will need to meet specific requirements.
     * If the update is successful, it will display the JSON of the updated message as well as a status 200.
     * If the requirements of the new message is not met, it will display a status 400 (client error).
     * 
     * @param ctx the context object handles information HTTP requests and generates responses within Javalin
     * @throws JsonProcessingException will be thrown if there is an issue converting Json into an object
     */
    private void updateMessageByIdHandler(Context ctx) throws JsonProcessingException{
        ObjectMapper mapper = new ObjectMapper();
        Message message = mapper.readValue(ctx.body(), Message.class);

        int message_id = Integer.parseInt(ctx.pathParam("message_id"));
        Message updateMessage = messageService.updateMessageByID(message_id, message.getMessage_text());
        if(updateMessage != null) {
            ctx.json(updateMessage).status(200);
        }
        else {
            ctx.status(400);
        }
    }

    /**
     * Handler method for getting all messages associated with the user's account ID.
     * MessageService will use the getMessagesByAccount method to search for the account that matches the ID.
     * If the account is found, it will retrieve all the messages in that account and store it in a list.
     * The output will display the JSON of all the retrieved messages as well as a status 200.
     * 
     * @param ctx the context object handles information HTTP requests and generates responses within Javalin
     */
    private void getMessagesWithAccountIDHandler(Context ctx) {
        int account_id = Integer.parseInt(ctx.pathParam("account_id"));
        List<Message> messages = messageService.getMessagesByAccount(account_id);
        ctx.json(messages).status(200);
    }

    /**
     * This is an example handler for an example endpoint.
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */
    private void exampleHandler(Context context) {
        context.json("sample text");
    }
}