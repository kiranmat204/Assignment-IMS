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

public class UpdateProductForm {

    private JFrame frame;

    public UpdateProductForm() {
        frame = new JFrame("Update Product");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(400, 400);
        frame.setLocationRelativeTo(null);
        frame.setLayout(new GridLayout(8, 2));

        // Create labels and text fields
        JLabel productIdLabel = new JLabel("Product ID:");
        JTextField productIdField = new JTextField();
        JLabel quantityLabel = new JLabel("New Quantity:");
        JTextField quantityField = new JTextField();
        JLabel priceLabel = new JLabel("New Price:");
        JTextField priceField = new JTextField();

        JButton updateButton = new JButton("Update Product");

        // Add components to the frame
        frame.add(productIdLabel);
        frame.add(productIdField);
        frame.add(quantityLabel);
        frame.add(quantityField);
        frame.add(priceLabel);
        frame.add(priceField);
        frame.add(new JLabel());  // Empty label for alignment
        frame.add(updateButton);

        // Add button action to handle updating the product
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String productId = productIdField.getText();
                int newQuantity = Integer.parseInt(quantityField.getText());
                double newPrice = Double.parseDouble(priceField.getText());

                // Update the product using ProductDAO
                ProductDAO productDAO = new ProductDAO();
                boolean result = productDAO.updateProductQuantity(productId, newQuantity);
                result = result && productDAO.updateProductPrice(productId, newPrice);

                if (result) {
                    JOptionPane.showMessageDialog(frame, "Product updated successfully!");
                    frame.dispose(); // Close form after successful update
                } else {
                    JOptionPane.showMessageDialog(frame, "Failed to update product.");
                }
            }
        });

        frame.setVisible(true);
    }
}
