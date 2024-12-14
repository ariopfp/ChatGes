import javax.swing.*;
import java.awt.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;

public class ChatGes {
    private final ChatServer server;
    private final String username;
    private final JPanel chatPanel;

    public ChatGes(String username, Color themeColor, ChatServer server) {
        this.username = username;
        this.server = server;
        this.chatPanel = new JPanel();

        // System.out.println("Initializing ChatGes for user: " + username + ", theme color: " + themeColor);
        server.addUser(username);
        server.addWindow(this);

        JFrame frame = new JFrame("Chat Group - " + username);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(400, 600);
        frame.setLayout(new BorderLayout());

        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(themeColor);
        headerPanel.setPreferredSize(new Dimension(frame.getWidth(), 50));
        headerPanel.setLayout(new BorderLayout());

        JLabel titleLabel = new JLabel("Chat Group: " + username, JLabel.LEFT);
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        headerPanel.add(titleLabel, BorderLayout.CENTER);

        chatPanel.setBackground(Color.DARK_GRAY);
        chatPanel.setLayout(new BoxLayout(chatPanel, BoxLayout.Y_AXIS)); // Vertical layout for messages
        JScrollPane scrollPane = new JScrollPane(chatPanel);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        JPanel footerPanel = new JPanel();
        footerPanel.setBackground(Color.GRAY);
        footerPanel.setPreferredSize(new Dimension(frame.getWidth(), 50));
        footerPanel.setLayout(new BorderLayout());

        JTextField textField = new JTextField();
        footerPanel.add(textField, BorderLayout.CENTER);

        JButton sendButton = new JButton("Send");
        sendButton.setBackground(themeColor);
        sendButton.setForeground(Color.WHITE);
        footerPanel.add(sendButton, BorderLayout.EAST);

        sendButton.addActionListener(e -> {
            String message = textField.getText().trim();
            if (!message.isEmpty()) {
                new Thread(() -> server.broadcastMessage(username, message)).start();
                textField.setText("");
            }
        });
        

        frame.add(headerPanel, BorderLayout.NORTH);
        frame.add(scrollPane, BorderLayout.CENTER);
        frame.add(footerPanel, BorderLayout.SOUTH);
        frame.setVisible(true);

        // Load chat history from the database
        loadChatHistory();
    }

    public void receiveMessage(String sender, String message, String time) {
        boolean isOwnMessage = sender.equals(username);
        JPanel messageBubble = createMessageBubble(sender, message, time, isOwnMessage);
        chatPanel.add(messageBubble);
        chatPanel.revalidate();
        chatPanel.repaint();
    }
    

    private JPanel createMessageBubble(String sender, String message, String time, boolean isOwnMessage) {
        JPanel bubblePanel = new JPanel();
        bubblePanel.setLayout(new BorderLayout());
        bubblePanel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        bubblePanel.setOpaque(false); // Transparansi untuk bubble wrapper
    
        // Label nama pengirim (atau "You" jika pengirim adalah user sendiri)
        JLabel senderLabel = new JLabel(isOwnMessage ? "You" : sender);
        senderLabel.setFont(new Font("Arial", Font.BOLD, 12));
        senderLabel.setForeground(isOwnMessage ? Color.GREEN : Color.CYAN);
        senderLabel.setHorizontalAlignment(isOwnMessage ? SwingConstants.RIGHT : SwingConstants.LEFT);
    
        // Bubble pesan
        JLabel messageLabel = new JLabel("<html><p style='width: 200px;'>" + message + "</p></html>");
        messageLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        messageLabel.setOpaque(true);
        messageLabel.setBackground(isOwnMessage ? new Color(173, 216, 230) : new Color(240, 240, 240));
        messageLabel.setForeground(Color.BLACK);
        messageLabel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
    
        // Label waktu pengiriman
        JLabel timeLabel = new JLabel(time);
        timeLabel.setFont(new Font("Arial", Font.ITALIC, 10));
        timeLabel.setForeground(Color.GRAY);
        timeLabel.setHorizontalAlignment(isOwnMessage ? SwingConstants.RIGHT : SwingConstants.LEFT);
    
        // Panel utama untuk mengatur layout nama pengirim, pesan, dan waktu
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setOpaque(false);
    
        if (!isOwnMessage) {
            contentPanel.add(senderLabel); // Tambahkan nama pengirim untuk user lain
        }
        contentPanel.add(messageLabel); // Tambahkan bubble pesan
        contentPanel.add(timeLabel);    // Tambahkan waktu pengiriman
    
        // Panel wrapper untuk mengatur posisi bubble (kiri atau kanan)
        JPanel wrapper = new JPanel(new FlowLayout(isOwnMessage ? FlowLayout.RIGHT : FlowLayout.LEFT));
        wrapper.add(contentPanel);
        wrapper.setOpaque(false);
    
        return wrapper;
    }
    

    private void loadChatHistory() {
        new Thread(() -> {
            try {
                ResultSet rs = DatabaseHelper.getMessages();
                while (rs != null && rs.next()) {
                    String sender = rs.getString("sender");
                    String message = rs.getString("message");
                    String timestamp = rs.getTimestamp("timestamp").toLocalDateTime().format(DateTimeFormatter.ofPattern("HH:mm"));
                    SwingUtilities.invokeLater(() -> receiveMessage(sender, message, timestamp)); // Pastikan GUI di-update di EDT
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }).start();
    }  


    public String getUsername() {
        return username;
    }
}
