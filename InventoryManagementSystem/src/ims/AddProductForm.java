/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ims;

/**
 *
 * @author ankur
 */

//import ims.core.Product;
//import ims.core.ProductDAO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddProductForm {
    private JFrame frame;

    public AddProductForm() {
        frame = new JFrame("Add Product");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(400, 400);
        frame.setLocationRelativeTo(null);
        frame.setLayout(new GridLayout(8, 2));

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

        // Add components to the frame
        frame.add(productIdLabel);
        frame.add(productIdField);
        frame.add(productNameLabel);
        frame.add(productNameField);
        frame.add(supplierLabel);
        frame.add(supplierField);
        frame.add(categoryLabel);
        frame.add(categoryField);
        frame.add(productBrandLabel);
        frame.add(productBrandField);
        frame.add(quantityLabel);
        frame.add(quantityField);
        frame.add(priceLabel);
        frame.add(priceField);
        frame.add(new JLabel());  // Empty label for alignment
        frame.add(addButton);

        // Add button action
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            try{
                String productId = productIdField.getText();
                String productName = productNameField.getText();
                String supplier = supplierField.getText();
                String category = categoryField.getText();
                String productBrand = productBrandField.getText();
                int quantity = Integer.parseInt(quantityField.getText());
                double price = Double.parseDouble(priceField.getText());

                Product product = new Product(productId, supplier, category, productBrand, productName, quantity, price);

                // You can use ProductDAO here to add the product to the database
                ProductDAO productDAO = new ProductDAO();
                if (productDAO.addProduct(product)) {
                    JOptionPane.showMessageDialog(frame, "Product added successfully!");
                    frame.dispose(); // Close form after successful addition
                } else {
                    JOptionPane.showMessageDialog(frame, "Failed to add product.");
                }
            }catch(NumberFormatException ex){
                
                JOptionPane.showMessageDialog(frame, "Quantity and Price must be valid numbers.", "Input Error", JOptionPane.ERROR_MESSAGE);
            }
            }
        });

        frame.setVisible(true);
    }
}
