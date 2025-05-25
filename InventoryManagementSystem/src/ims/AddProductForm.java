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
import java.awt.event.ActionEvent;


public class AddProductForm extends JPanel {

    public AddProductForm() {
        setLayout(new GridLayout(8, 2, 10, 10));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // padding

        // Create labels and text fields
        JLabel productIdLabel = new JLabel("Product ID:");
        JTextField productIdField = new JTextField();
        JLabel productNameLabel = new JLabel("Product Name:");
        JTextField productNameField = new JTextField();
        JLabel supplierLabel = new JLabel("Supplier:");
        JTextField supplierField = new JTextField();
        JLabel categoryLabel = new JLabel("Category:");
        JTextField categoryField = new JTextField();
        JLabel productBrandLabel = new JLabel("Product Brand:");
        JTextField productBrandField = new JTextField();
        JLabel quantityLabel = new JLabel("Quantity:");
        JTextField quantityField = new JTextField();
        JLabel priceLabel = new JLabel("Price:");
        JTextField priceField = new JTextField();

        JButton addButton = new JButton("Add Product");

        // Add components to the panel
        add(productIdLabel);
        add(productIdField);
        add(productNameLabel);
        add(productNameField);
        add(supplierLabel);
        add(supplierField);
        add(categoryLabel);
        add(categoryField);
        add(productBrandLabel);
        add(productBrandField);
        add(quantityLabel);
        add(quantityField);
        add(priceLabel);
        add(priceField);
        add(new JLabel());  // Empty label for alignment
        add(addButton);

        // Add button action
        addButton.addActionListener((ActionEvent e) -> {
            try {
                String productId = productIdField.getText();
                String productName = productNameField.getText();
                String supplier = supplierField.getText();
                String category = categoryField.getText();
                String productBrand = productBrandField.getText();
                int quantity = Integer.parseInt(quantityField.getText());
                double price = Double.parseDouble(priceField.getText());

                Product product = new Product(productId, supplier, category, productBrand, productName, quantity, price);

                ProductDAO productDAO = new ProductDAO();
                if (productDAO.addProduct(product)) {
                    JOptionPane.showMessageDialog(this, "Product added successfully!");
                    // Optionally, clear fields here
                } else {
                    JOptionPane.showMessageDialog(this, "Failed to add product.");
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Quantity and Price must be valid numbers.", "Input Error", JOptionPane.ERROR_MESSAGE);
            }
        });
    }
}