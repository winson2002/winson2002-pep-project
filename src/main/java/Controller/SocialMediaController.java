package Controller;

import Model.Account;
import Model.Message;
import Service.AccountService;
import Service.MessageService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.javalin.Javalin;
import io.javalin.http.Context;

import static org.mockito.Mockito.mockConstruction;

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

    private void getAllMessageHandler(Context ctx) {
        List<Message> messages = messageService.getAllMessages();
        ctx.json(messages).status(200);
    }

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