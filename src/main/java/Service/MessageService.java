package Service;

import Model.Message;
import DAO.AccountDAO;
import DAO.MessageDAO;

import java.util.List;

public class MessageService {
    private MessageDAO messageDAO;

    public MessageService() {
        messageDAO = new MessageDAO();
    }

    public MessageService(MessageDAO messageDAO){
        this.messageDAO = messageDAO;
    }

    public Message createMessage(int userID, String text, long time) {
        if(text == null || text.trim().isEmpty() || text.length() > 255) {
            return null;
        }

        if(messageDAO.realPerson(userID) != null) {
            return messageDAO.postMessage(userID, text, time);
        }
        return null;
    }

    public List<Message> getAllMessages() {
        return messageDAO.getAllBooks();
    }

    public Message getMessageByID(int messageID) {
        return messageDAO.getMessageByID(messageID);
    }

    public Message deleteMessageByID(int messageID) {
        Message deletedMessage = messageDAO.getMessageByID(messageID);
        messageDAO.deleteMessage(messageID);
        return deletedMessage;
    }

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

    public List<Message> getMessagesByAccount(int accountID) {
        return messageDAO.getAllMessagesByAccount(accountID);
    }
}