import javax.swing.*;
import java.awt.*;

public class CreateUser extends JFrame {

    private JTextField nameField;
    private JButton createButton;
    private JButton chooseColorButton;

    public CreateUser() {
        setTitle("Create New User");
        setSize(400, 200);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 2, 10, 10));

        JLabel nameLabel = new JLabel("Enter Name:");
        nameField = new JTextField(15);
        createButton = new JButton("Create User");
        chooseColorButton = new JButton("Choose Theme Color");

        panel.add(nameLabel);
        panel.add(nameField);
        panel.add(new JLabel());
        panel.add(chooseColorButton);
        panel.add(new JLabel());
        panel.add(createButton);

        add(panel);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            CreateUser createUser = new CreateUser();
            createUser.setVisible(true);
        });
    }
}
