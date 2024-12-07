import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class CreateUser extends JFrame implements ActionListener {

    private JTextField nameField;
    private JButton createButton;
    private JButton chooseColorButton;

    private Color selectedColor = Color.LIGHT_GRAY; 

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

        getRootPane().setDefaultButton(createButton);

        createButton.addActionListener(this);
        chooseColorButton.addActionListener(this);

        panel.add(nameLabel);
        panel.add(nameField);
        panel.add(new JLabel()); 
        panel.add(chooseColorButton);
        panel.add(new JLabel()); 
        panel.add(createButton);

        add(panel);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == createButton) {
            System.out.println("Create Button Clicked");
            String username = nameField.getText().trim();
            if (!username.isEmpty()) {
                selectedColor = selectedColor != null ? selectedColor : Color.LIGHT_GRAY;
                System.out.println("Opening ChatGes for user: " + username + " with theme color: " + selectedColor);
                SwingUtilities.invokeLater(() -> new ChatGes(username, selectedColor));
                nameField.setText(""); 
            } else {
                JOptionPane.showMessageDialog(this, "Name cannot be empty!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else if (e.getSource() == chooseColorButton) {
            System.out.println("Choose Color Button Clicked");
            selectedColor = JColorChooser.showDialog(this, "Choose Theme Color", selectedColor);
            if (selectedColor != null) {
                System.out.println("Selected Color: " + selectedColor);
                getContentPane().setBackground(selectedColor);
                revalidate();
                repaint();
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
