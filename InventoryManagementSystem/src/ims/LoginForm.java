/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ims;


import javax.swing.*;
import java.awt.*;

/**
 *
 * @author ankur
 */
public class LoginForm extends JDialog {

    private JPasswordField passwordField;
    private JTextField usernameField;

    public static final int LOGIN_SUCCESS = 1;
    public static final int LOGIN_TRY_AGAIN = 0;
    public static final int LOGIN_CANCEL = -1;

    private int loginResult = LOGIN_CANCEL;

    public LoginForm() {
        setTitle("Login");
        setSize(400, 250);  // Set window size here
        setModal(true);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());  // Use GridBagLayout for alignment
        panel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;

        Font labelFont = new Font("SansSerif", Font.BOLD, 14);

        usernameField = new JTextField(15);
        passwordField = new JPasswordField(15);

        // Username label
        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setFont(labelFont);
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(usernameLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        panel.add(usernameField, gbc);

        // Password label
        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setFont(labelFont);
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(passwordLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        panel.add(passwordField, gbc);

        // Buttons
        JButton loginButton = new JButton("Login");
        JButton cancelButton = new JButton("Cancel");

        loginButton.addActionListener(e -> {
            if (authenticate(usernameField.getText(), new String(passwordField.getPassword()))) {
                loginResult = LOGIN_SUCCESS;
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Invalid credentials.", "Login Failed", JOptionPane.WARNING_MESSAGE);
                loginResult = LOGIN_TRY_AGAIN;
            }
        });

        cancelButton.addActionListener(e -> {
            loginResult = LOGIN_CANCEL;
            dispose();
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(loginButton);
        buttonPanel.add(cancelButton);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(buttonPanel, gbc);

        setContentPane(panel);
    }

    public int showLoginDialog() {
        setVisible(true);
        return loginResult;
    }

    private boolean authenticate(String username, String password) {
        return "admin".equals(username) && "password".equals(password);
    }
}