import java.awt.*;
import java.util.List;

public interface ChatServerInterface {
    void addUser(String username);
    void removeUser(String username);
    void broadcastMessage(String username, String message);
    List<String> getConnectedUsers();
}
