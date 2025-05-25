/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ims;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 * @author kiranmat
 */
public class DeleteProductForm extends JPanel {

    public DeleteProductForm() {
        setLayout(new GridBagLayout());
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // Padding around panel
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        JLabel productIDLabel = new JLabel("Product ID:");
        JTextField productIDField = new JTextField(15);
        JButton inputConfirmButton = new JButton("Confirm Delete");

        // Add Product ID label
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        add(productIDLabel, gbc);

        // Add Product ID text field
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        add(productIDField, gbc);

        // Add Confirm Delete button
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        add(inputConfirmButton, gbc);

        // Button action
        inputConfirmButton.addActionListener((ActionEvent e) -> {
            String productId = productIDField.getText().trim();

            if (productId.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter a Product ID.", "Input Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            ProductDAO productDAO = new ProductDAO();
            boolean result = productDAO.removeProduct(productId);
            if (result) {
                JOptionPane.showMessageDialog(this, "Product deleted successfully!");
                productIDField.setText("");
            } else {
                JOptionPane.showMessageDialog(this, "Product ID not found!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
    }
}