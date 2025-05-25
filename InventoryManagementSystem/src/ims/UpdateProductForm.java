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
        Font boldFont = new Font("Arial", Font.BOLD, 20);
        

        // Create labels and text fields
        JLabel productIdLabel = new JLabel("Product ID:");
        productIdLabel.setFont(boldFont);
        JTextField productIdField = new JTextField();
        productIdField.setPreferredSize(new Dimension(100, 10));
        
        JLabel quantityLabel = new JLabel("New Quantity:");
        quantityLabel.setFont(boldFont);
        JTextField quantityField = new JTextField();
        quantityField.setPreferredSize(new Dimension(100, 10));
        
        JLabel priceLabel = new JLabel("New Price:");
        priceLabel.setFont(boldFont);
        JTextField priceField = new JTextField();
        priceField.setPreferredSize(new Dimension(100, 10));
        

        
        
        JButton updateButton = new JButton("Update Product");
        updateButton.setFont(boldFont);

        // Set background to light blue and text to white
        updateButton.setBackground(new Color(0, 51, 102)); // dark blue
        updateButton.setForeground(Color.WHITE);             // White text

        // Optional: Improve visual appearance
        updateButton.setFocusPainted(false);
        updateButton.setBorderPainted(false);
        updateButton.setOpaque(true);

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
                boolean result = productDAO.updateProductQuantity(productId, newQuantity) &&
                        productDAO.updateProductPrice(productId, newPrice);

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
