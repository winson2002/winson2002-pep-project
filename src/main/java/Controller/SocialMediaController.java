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
        app.get("messages/{message_id}", this::getMessageWithIDHandler);
        app.delete("messages/{message_id}", this::deleteMessageWithIDHandler);
        app.patch("messages/{message_id}", this::updateMessageWithIdHandler);
        app.get("accounts/{account_id}/messages", this::getMessageWithAccountIDHandler);

        return app;
    }

    private void registerAccountHandler(Context ctx) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Account account = mapper.readValue(ctx.body(), Account.class);

        if(account.getUsername() == null || account.getUsername().trim().isEmpty()) {
            ctx.json("Username cannot be blank").status(400);
            return;
        }

        if(account.getPassword() == null || account.getPassword().length() < 4) {
            ctx.json("Password must be longer than 4 characters").status(400);
            return;
        }

        Account existingAccount = accountService.duplicatedAccount(account.getUsername());
        if(existingAccount != null) {
            ctx.json("Username already exist").status(400);
        }

        Account registeredAccount = accountService.registerAccount(account);
        if(registeredAccount != null) {
            ctx.json(registeredAccount).status(200);
        }
        else {
            ctx.json("Registration failed due to unknown error").status(400);
        }
    }

    private void loginHandler(Context ctx) {

    }

    private void createMessageHandler(Context ctx) {

    }

    private void getAllMessageHandler(Context ctx) {

    }

    private void getMessageWithIDHandler(Context ctx) {

    }

    private void deleteMessageWithIDHandler(Context ctx) {

    }

    private void updateMessageWithIdHandler(Context ctx) {

    }

    private void getMessageWithAccountIDHandler(Context ctx) {

    }

    /**
     * This is an example handler for an example endpoint.
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */
    private void exampleHandler(Context context) {
        context.json("sample text");
    }


}