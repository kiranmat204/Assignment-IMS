/*
     * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
     * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ims;

/**
 *
 * @author ankur
 */
import javax.swing.*;
import java.awt.*;

public class MainMenu {

    private JFrame frame;
    ProductDAO productDAO = new ProductDAO();

    public void showMainMenu() {
        System.out.println("Testing Derby connection...");

        try {
            Class.forName("org.apache.derby.jdbc.ClientDriver");
            System.out.println("SUCCESS: Derby driver loaded!");
        } catch (ClassNotFoundException e) {
            JOptionPane.showMessageDialog(frame,
                    "Failed to load Derby driver!\n" + e.getMessage(),
                    "Driver Error",
                    JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }

        frame = new JFrame("Inventory Management System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setLocationRelativeTo(null);

        Font buttonFont = new Font("SansSerif", Font.BOLD, 30);

        // Create background panel with BorderLayout
        BackgroundPanel backgroundPanel = new BackgroundPanel("src/images/1.jpg");
        backgroundPanel.setLayout(new BorderLayout());

        // LEFT PANEL: Button list
        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false);
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(50, 50, 0, 0)); // padding

        JButton addProductButton = new JButton("Add Product");
        JButton viewProductsButton = new JButton("View Products");
        JButton updateProductButton = new JButton("Update Product");
        JButton deleteProductButton = new JButton("Delete Product");

        addProductButton.setFont(buttonFont);
        viewProductsButton.setFont(buttonFont);
        updateProductButton.setFont(buttonFont);
        deleteProductButton.setFont(buttonFont);

        buttonPanel.add(addProductButton);
        buttonPanel.add(Box.createVerticalStrut(20));
        buttonPanel.add(viewProductsButton);
        buttonPanel.add(Box.createVerticalStrut(20));
        buttonPanel.add(updateProductButton);
        buttonPanel.add(Box.createVerticalStrut(20));
        buttonPanel.add(deleteProductButton);

        // TOP RIGHT: Logout button
        JButton logoutButton = new JButton("Logout");
        logoutButton.setFont(new Font("SansSerif", Font.BOLD, 18));
        logoutButton.addActionListener(e -> {
            frame.dispose();
            main(null); // Restart login
        });

        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        topPanel.setOpaque(false);
        topPanel.add(logoutButton);

        // Add panels to background
        backgroundPanel.add(topPanel, BorderLayout.NORTH);
        backgroundPanel.add(buttonPanel, BorderLayout.WEST);

        productDAO.createProductTable();

        frame.setContentPane(backgroundPanel);
        frame.setVisible(true);

        // Button actions
        addProductButton.addActionListener(e -> new AddProductForm());
        viewProductsButton.addActionListener(e -> new ViewProductsPanel());
        updateProductButton.addActionListener(e -> new UpdateProductForm());
        deleteProductButton.addActionListener(e -> new DeleteProductForm());
    }

    public static void main(String[] args) {
        boolean authenticated = false;
        while (!authenticated) {
            LoginForm loginForm = new LoginForm();
            int loginResult = loginForm.showLoginDialog();

            if (loginResult == LoginForm.LOGIN_SUCCESS) {
                authenticated = true;
                new MainMenu().showMainMenu();
            } else if (loginResult == LoginForm.LOGIN_TRY_AGAIN) {
                // Loop continues
            } else {
                JOptionPane.showMessageDialog(null, "Exiting application. Login required.");
                System.exit(0);
            }
        }
    }
}