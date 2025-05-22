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
        frame.setSize(400, 260);
        frame.setLocationRelativeTo(null);
        
        frame.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10,10,10,10);

        // Create labels and text fields
        JLabel productIdLabel = new JLabel("Product ID:");
        JTextField productIdField = new JTextField(15);
        productIdField.setPreferredSize(new Dimension(180, 30));
        
        JLabel quantityLabel = new JLabel("New Quantity:");
        JTextField quantityField = new JTextField(15);
        quantityField.setPreferredSize(new Dimension(180, 30));
        
        JLabel priceLabel = new JLabel("New Price:");
        JTextField priceField = new JTextField(15);
        priceField.setPreferredSize(new Dimension(180, 30));

        JButton updateButton = new JButton("Update Product");

        // Add components to the frame
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        frame.add(productIdLabel,gbc);
        
        gbc.gridx = 1;
        gbc.gridy = 0;
        frame.add(productIdField,gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.WEST;
        frame.add(quantityLabel,gbc);
        
        gbc.gridx = 1;
        gbc.gridy = 1;
        frame.add(quantityField,gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.WEST;
        frame.add(priceLabel,gbc);
        
        gbc.gridx = 1;
        gbc.gridy = 2;
        frame.add(priceField,gbc);
        
        //frame.add(new JLabel());  // Empty label for alignment
        
        gbc.gridx = 1;
        gbc.gridy = 3;
        frame.add(updateButton,gbc);

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
