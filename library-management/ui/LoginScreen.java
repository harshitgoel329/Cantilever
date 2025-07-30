package ui;

import dao.UserDAO;
import model.User;

import javax.swing.*;
import java.awt.*;

public class LoginScreen extends JFrame {

    private final JTextField usernameField = new JTextField();
    private final JPasswordField passwordField = new JPasswordField();
    private final UserDAO userDAO = new UserDAO();

    public LoginScreen() {
        setTitle("📚 Library Login");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JLabel title = new JLabel("Library Management System", JLabel.CENTER);
        title.setFont(new Font("SansSerif", Font.BOLD, 18));
        title.setBorder(BorderFactory.createEmptyBorder(20, 10, 10, 10));

        JPanel form = new JPanel(new GridLayout(5, 1, 10, 10));
        form.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));

        usernameField.setPreferredSize(new Dimension(200, 30));
        passwordField.setPreferredSize(new Dimension(200, 30));

        JButton loginBtn = new JButton("🔐 Login");
        JButton registerBtn = new JButton("📝 Register");

        loginBtn.addActionListener(e -> login());
        registerBtn.addActionListener(e -> register());

        form.add(new JLabel("Username:"));
        form.add(usernameField);
        form.add(new JLabel("Password:"));
        form.add(passwordField);
        form.add(loginBtn);
        form.add(registerBtn);

        add(title, BorderLayout.NORTH);
        add(form, BorderLayout.CENTER);

        setVisible(true);
    }

    private void login() {
        String user = usernameField.getText().trim();
        String pass = new String(passwordField.getPassword());

        User u = userDAO.login(user, pass);
        if (u != null) {
            JOptionPane.showMessageDialog(this, "Login successful!");
            dispose();
            new DashboardUI(); // go to dashboard
        } else {
            JOptionPane.showMessageDialog(this, "Invalid credentials.");
        }
    }

    private void register() {
        String user = usernameField.getText().trim();
        String pass = new String(passwordField.getPassword());

        if (user.isEmpty() || pass.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Fill in all fields.");
            return;
        }

        boolean success = userDAO.register(user, pass);
        
        if (success) {
            JOptionPane.showMessageDialog(this, "Registration successful. You can now log in.");
        } else {
            JOptionPane.showMessageDialog(this, "User already exists.");
        }
    }
}
