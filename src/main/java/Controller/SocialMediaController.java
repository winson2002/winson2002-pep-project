package Controller;

import Model.Account;
import Model.Message;
//import Service.AccountService;
//import Service.AccountService;
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
    /**
     * In order for the test cases to work, you will need to write the endpoints in the startAPI() method, as the test
     * suite must receive a Javalin object from this method.
     * @return a Javalin app object which defines the behavior of the Javalin controller.
     */
    public Javalin startAPI() {
        Javalin app = Javalin.create();
        app.get("example-endpoint", this::exampleHandler);
        app.post("localhost:8080/register", this::register);
        app.post("localhost:8080/login", this::login);
        app.post("localhost:8080/messages", this::createMessage);
        app.get("localhost:8080/messages", this::getAllMessage);
        app.get("localhost:8080/messages/{message_id}", this::getMessageWithID);
        app.delete("localhost:8080/messages/{message_id}", this::deleteMessageWithID);
        app.patch("localhost:8080/messages/{message_id}", this::updateMessageWithId);
        app.get("localhost:8080/accounts/{account_id}/messages", this::getMessageWithAccountID);

        return app;
    }

    private void register(Context ctx) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Account account = mapper.readValue(ctx.body(), Account.class);
    }

    private void login(Context ctx) {

    }

    private void createMessage(Context ctx) {

    }

    private void getAllMessage(Context ctx) {

    }

    private void getMessageWithID(Context ctx) {

    }

    private void deleteMessageWithID(Context ctx) {

    }

    private void updateMessageWithId(Context ctx) {

    }

    private void getMessageWithAccountID(Context ctx) {

    }

    /**
     * This is an example handler for an example endpoint.
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */
    private void exampleHandler(Context context) {
        context.json("sample text");
    }


}