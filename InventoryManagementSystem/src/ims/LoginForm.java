/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ims;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

/**
 *
 * @author ankur
 */
public class LoginForm {
    
    private JPasswordField passwordField;
    private JTextField usernameField;
    
    public static final int LOGIN_SUCCESS = 1;
    public static final int LOGIN_TRY_AGAIN = 0;
    public static final int LOGIN_CANCEL = -1;

    public int showLoginDialog() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        usernameField = new JTextField(15);
        passwordField = new JPasswordField(15);

        panel.add(new JLabel("Username:"));
        panel.add(usernameField);
        panel.add(Box.createVerticalStrut(10));
        panel.add(new JLabel("Password:"));
        panel.add(passwordField);

        int result = JOptionPane.showConfirmDialog(null, panel,
                "Login", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            String user = usernameField.getText();
            String pass = new String(passwordField.getPassword());

            if (authenticate(user, pass)) {
                return LOGIN_SUCCESS;
            } else {
                int retry = JOptionPane.showOptionDialog(null,
                        "Invalid credentials. Try again?",
                        "Login Failed",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.WARNING_MESSAGE,
                        null,
                        new Object[]{"Try Again", "Exit"},
                        "Try Again");

                if (retry == JOptionPane.YES_OPTION) {
                    return LOGIN_TRY_AGAIN;
                } else {
                    return LOGIN_CANCEL;
                }
            }
        } else {
            return LOGIN_CANCEL;
        }
    }

    private boolean authenticate(String username, String password) {
        // Replace with DB logic later
        return "admin".equals(username) && "password".equals(password);
    }
}
