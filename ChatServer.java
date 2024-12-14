import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ChatServer implements ChatServerInterface {
    private final ExecutorService executor = Executors.newCachedThreadPool();
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");
    private final List<ChatClient> connectedClients = new ArrayList<>();
    private final List<ChatGes> activeWindows = new ArrayList<>();

    @Override
    public void addUser(String username) {
        connectedClients.add(new ChatClient(username));
        // System.out.println("User added: " + username);
    }

    public void addWindow(ChatGes chatWindow) {
        activeWindows.add(chatWindow);
        // System.out.println("Window added for user: " + chatWindow.getUsername());
    }

    public void removeWindow(ChatGes chatWindow) {
        activeWindows.remove(chatWindow);
        // System.out.println("Window removed for user: " + chatWindow.getUsername());
    }

    @Override
    public void removeUser(String username) {
        connectedClients.removeIf(client -> client.getUsername().equals(username));
        // System.out.println("User removed: " + username);
    }

    @Override
    public void broadcastMessage(String username, String message) {
        executor.submit(() -> {
            String currentTime = LocalDateTime.now().format(TIME_FORMATTER);
            // System.out.println("Broadcasting message from " + username + " at " + currentTime + ": " + message);

            // Simpan pesan ke database
            DatabaseHelper.saveMessage(username, message);

            // Kirim pesan ke semua jendela
            for (ChatGes chatWindow : activeWindows) {
                chatWindow.receiveMessage(username, message, currentTime);
            }
        });
    }    

    @Override
    public List<String> getConnectedUsers() {
        List<String> userList = new ArrayList<>();
        for (ChatClient client : connectedClients) {
            userList.add(client.getUsername());
        }
        return userList;
    }
}