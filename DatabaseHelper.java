import java.sql.*;
import java.time.LocalDateTime;

public class DatabaseHelper {
    private static final String URL = "jdbc:mysql://localhost:3306/chatges";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    private static Connection connection;

    static {
        try {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            // System.out.println("Database connected successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to connect to the database.");
        }
    }

    public static Connection getConnection() {
        return connection;
    }

    public static void saveMessage(String sender, String message) {
        String sql = "INSERT INTO messages (sender, message, timestamp) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, sender);
            stmt.setString(2, message);
            stmt.setTimestamp(3, Timestamp.valueOf(LocalDateTime.now()));
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public static ResultSet getMessages() {
        String sql = "SELECT sender, message, timestamp FROM messages ORDER BY timestamp ASC";
        try {
            return connection.createStatement().executeQuery(sql);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public static void clearMessages() {
        String sql = "DELETE FROM messages";
        try (Statement stmt = connection.createStatement()) {
            stmt.executeUpdate(sql);
            System.out.println("All chat messages have been cleared.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }    
}
