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
}
