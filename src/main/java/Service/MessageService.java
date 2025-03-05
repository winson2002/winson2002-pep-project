package Service;

import Model.Message;
import DAO.MessageDAO;

import java.util.List;

public class MessageService {
    private MessageDAO messageDAO;

    /**
     * no-args constructor for creating a new MessageService with a new MessageDAO.
     */
    public MessageService() {
        messageDAO = new MessageDAO();
    }

    /**
     * Constructor for a MessageService when a MessageDAO is provided.
     * 
     * @param messageDAO
     */
    public MessageService(MessageDAO messageDAO){
        this.messageDAO = messageDAO;
    }

    /**
     * Checks if message is empty or longer than 255 characters and if there is an account with that ID.
     * 
     * @param userID integer given to accounts during account creation
     * @param text not empty, not longer than 255 characters
     * @param time when current message is created
     * @return info of the message
     */
    public Message createMessage(int userID, String text, long time) {
        if(text == null || text.trim().isEmpty() || text.length() > 255) {
            return null;
        }

        if(messageDAO.realPerson(userID) != null) {
            return messageDAO.postMessage(userID, text, time);
        }
        return null;
    }

    /**
     * Gets all messages in the database.
     * 
     * @return list of messages
     */
    public List<Message> getAllMessages() {
        return messageDAO.getAllBooks();
    }

    /**
     * Gets all messages matching with the ID of the message.
     * 
     * @param messageID integer given to messages during message creation
     * @return info of the message
     */
    public Message getMessageByID(int messageID) {
        return messageDAO.getMessageByID(messageID);
    }

    /**
     * Search for the message using the ID, stores the info of the message, and then deletes it.
     * 
     * @param messageID used to search for message
     * @return info of the deleted message
     */
    public Message deleteMessageByID(int messageID) {
        Message deletedMessage = messageDAO.getMessageByID(messageID);
        messageDAO.deleteMessage(messageID);
        return deletedMessage;
    }

    /**
     * Check if new message is not empty and not longer than 255 characters before replacing the old 
     * message by looking up the message ID.
     * 
     * @param messageID used to search for message
     * @param text not empty, not longer than 255 characters
     * @return info of updated message
     */
    public Message updateMessageByID(int messageID, String text) {
        if(text == null || text.trim().isEmpty() || text.length() > 255) {
            return null;
        }

        if(messageDAO.getMessageByID(messageID) != null) {
            messageDAO.updateMessage(messageID, text);
            return messageDAO.getMessageByID(messageID);
        }
        return null;
    }

    /**
     * Gets all messages matching with the ID of the account.
     * 
     * @param accountID used to search for all messages in the account
     * @return list of messages
     */
    public List<Message> getMessagesByAccount(int accountID) {
        return messageDAO.getAllMessagesByAccount(accountID);
    }
}