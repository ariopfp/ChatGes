import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class ChatGes {
    public ChatGes(String username, Color themeColor){
        System.out.println("Initializing ChatGes for user: " + username + ",theme color: " + themeColor);

        JFrame frame = new JFrame("Chat Group-" + username);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 600);
        frame.setLayout(new BorderLayout());

        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(themeColor);
        headerPanel.setPreferredSize(new Dimension(frame.getWidth(), 50));
        headerPanel.setLayout(new BorderLayout());

        JLabel titleLabel = new JLabel("Chat Group :" + username, JLabel.LEFT);
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        headerPanel.add(titleLabel, BorderLayout.CENTER);

        JPanel chatPanel = new JPanel();
        chatPanel.setBackground(Color.DARK_GRAY);
        chatPanel.setLayout(new BorderLayout());

        JTextArea chatArea = new JTextArea();
        chatArea.setEditable(false);
        chatArea.setBackground(Color.DARK_GRAY);
        chatArea.setForeground(Color.WHITE);
        chatArea.setFont(new Font("Arial", Font.PLAIN, 14));
        chatArea.setLineWrap(true);
        chatArea.setWrapStyleWord(true);
        JScrollPane scrollPane = new JScrollPane(chatArea);
        chatPanel.add(scrollPane, BorderLayout.CENTER);

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

        frame.add(headerPanel, BorderLayout.NORTH);
        frame.add(chatPanel, BorderLayout.CENTER);
        frame.add(footerPanel, BorderLayout.SOUTH);

        sendButton.addActionListener(e -> {
            String message = textField.getText().trim();
            if (!message.isEmpty()) {
                chatArea.append(username + ": " + message + "\n"); 
                textField.setText(""); 
            }
        });

        frame.setVisible(true);
    }
}