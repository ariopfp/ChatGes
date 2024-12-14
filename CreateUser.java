import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class CreateUser extends JFrame implements ActionListener {
    private JTextField nameField;
    private JButton createButton;
    private JButton chooseColorButton;
    private JButton resetChatButton;

    private Color selectedColor = Color.LIGHT_GRAY;
    private final ChatServer server = new ChatServer();

    public CreateUser() {
        setTitle("Create New User");
        setSize(400, 250);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 2, 10, 10));

        JLabel nameLabel = new JLabel("Enter Name:");
        nameField = new JTextField(15);
        createButton = new JButton("Create User");
        chooseColorButton = new JButton("Choose Theme Color");
        resetChatButton = new JButton("Reset Chat");

        getRootPane().setDefaultButton(createButton);

        createButton.addActionListener(this);
        chooseColorButton.addActionListener(this);
        resetChatButton.addActionListener(this);

        panel.add(nameLabel);
        panel.add(nameField);
        panel.add(new JLabel());
        panel.add(chooseColorButton);
        panel.add(new JLabel());
        panel.add(createButton);
        panel.add(new JLabel());
        panel.add(resetChatButton);

        add(panel);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == createButton) {
            String username = nameField.getText().trim();
            if (!username.isEmpty()) {
                new ChatGes(username, selectedColor, server);
                nameField.setText("");
            } else {
                JOptionPane.showMessageDialog(this, "Name cannot be empty!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else if (e.getSource() == chooseColorButton) {
            selectedColor = JColorChooser.showDialog(this, "Choose Theme Color", selectedColor);
        } else if (e.getSource() == resetChatButton) {
            int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to reset the chat?", "Confirm Reset", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                DatabaseHelper.clearMessages(); // Panggil fungsi untuk menghapus data
                JOptionPane.showMessageDialog(this, "Chat has been reset successfully!", "Reset Successful", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            CreateUser createUser = new CreateUser();
            createUser.setVisible(true);
        });
    }
}
