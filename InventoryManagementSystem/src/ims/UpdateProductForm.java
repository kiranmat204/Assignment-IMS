/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ims;

/**
 *
 * @author ankur
 */


//import ims.core.ProductDAO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UpdateProductForm extends JPanel {

    public UpdateProductForm() {
        setLayout(new GridLayout(4, 2, 10, 10));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // Padding around panel

        // Create labels and text fields
        JLabel productIdLabel = new JLabel("Product ID:");
        JTextField productIdField = new JTextField();
        JLabel quantityLabel = new JLabel("New Quantity:");
        JTextField quantityField = new JTextField();
        JLabel priceLabel = new JLabel("New Price:");
        JTextField priceField = new JTextField();

        JButton updateButton = new JButton("Update Product");

        // Add components to the panel
        add(productIdLabel);
        add(productIdField);
        add(quantityLabel);
        add(quantityField);
        add(priceLabel);
        add(priceField);
        add(new JLabel());  // Empty label for spacing
        add(updateButton);

        // Update button logic
        updateButton.addActionListener((ActionEvent e) -> {
            try {
                String productId = productIdField.getText().trim();
                int newQuantity = Integer.parseInt(quantityField.getText().trim());
                double newPrice = Double.parseDouble(priceField.getText().trim());

                ProductDAO productDAO = new ProductDAO();
                boolean result = productDAO.updateProductQuantity(productId, newQuantity)
                                && productDAO.updateProductPrice(productId, newPrice);

                if (result) {
                    JOptionPane.showMessageDialog(this, "Product updated successfully!");
                } else {
                    JOptionPane.showMessageDialog(this, "Failed to update product.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Quantity and Price must be valid numbers.", "Input Error", JOptionPane.ERROR_MESSAGE);
            }
        });
    }
}