import javax.swing.*;
import java.awt.*;

public class ChatGes {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Chat Group");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 600);
        frame.setLayout(new BorderLayout());

        JPanel headerPanel = new JPanel();
        headerPanel.setPreferredSize(new Dimension(frame.getWidth(), 50));
        headerPanel.setLayout(new BorderLayout());

        JLabel titleLabel = new JLabel("Chat Group", JLabel.LEFT);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        headerPanel.add(titleLabel, BorderLayout.CENTER);

        JPanel chatPanel = new JPanel();
        chatPanel.setLayout(new BorderLayout());

        JTextArea chatArea = new JTextArea();
        chatArea.setEditable(false);
        chatArea.setFont(new Font("Arial", Font.PLAIN, 14));
        JScrollPane scrollPane = new JScrollPane(chatArea);
        chatPanel.add(scrollPane, BorderLayout.CENTER);

        JPanel footerPanel = new JPanel();
        footerPanel.setPreferredSize(new Dimension(frame.getWidth(), 50));
        footerPanel.setLayout(new BorderLayout());

        JTextField textField = new JTextField();
        footerPanel.add(textField, BorderLayout.CENTER);

        JButton sendButton = new JButton("Send");
        footerPanel.add(sendButton, BorderLayout.EAST);

        frame.add(headerPanel, BorderLayout.NORTH);
        frame.add(chatPanel, BorderLayout.CENTER);
        frame.add(footerPanel, BorderLayout.SOUTH);

        frame.setVisible(true);
    }
}
