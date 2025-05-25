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
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

public class AddProductForm extends JPanel {

    public AddProductForm() {
        setLayout(new GridLayout(8, 2, 10, 10));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        Font boldFont = new Font("Arial", Font.BOLD, 18);

        JLabel productIdLabel = new JLabel("Product ID:");
        productIdLabel.setFont(boldFont);
        JTextField productIdField = new JTextField();

        JLabel productNameLabel = new JLabel("Product Name:");
        productNameLabel.setFont(boldFont);
        JTextField productNameField = new JTextField();
        productNameField.setEnabled(false);

        JLabel supplierLabel = new JLabel("Supplier:");
        supplierLabel.setFont(boldFont);
        JTextField supplierField = new JTextField();

        JLabel categoryLabel = new JLabel("Category:");
        categoryLabel.setFont(boldFont);
        JTextField categoryField = new JTextField();

        JLabel productBrandLabel = new JLabel("Product Brand:");
        productBrandLabel.setFont(boldFont);
        JTextField productBrandField = new JTextField();

        JLabel quantityLabel = new JLabel("Quantity:");
        quantityLabel.setFont(boldFont);
        JTextField quantityField = new JTextField();

        JLabel priceLabel = new JLabel("Price:");
        priceLabel.setFont(boldFont);
        JTextField priceField = new JTextField();

        JButton addButton = new JButton("Add Product");
        addButton = new JButton("Add Product");
        addButton.setFont(boldFont);
        addButton.setBackground(new Color(0, 51, 102));
        addButton.setForeground(Color.WHITE);
        addButton.setOpaque(true);
        addButton.setContentAreaFilled(true);
        addButton.setBorderPainted(false);

        // Add to panel
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
        add(new JLabel());
        add(addButton);

        ProductDAO dao = new ProductDAO();

        // InputVerifier for Product ID
        productIdField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                String id = productIdField.getText().trim();
                System.out.println("User entered ID: " + id); // DEBUG

                if (id.isEmpty()) {
                    productNameField.setEnabled(false);
                    return;
                }

                Product product = dao.getProductById(id);
                System.out.println("Product found: " + (product != null)); // DEBUG

                if (product != null) {
                    JOptionPane.showMessageDialog(AddProductForm.this,
                            "Product ID already exists.",
                            "Duplicate Product ID", JOptionPane.WARNING_MESSAGE);
                    productNameField.setEnabled(true);
                    productNameField.requestFocus();
                } else {
                    productNameField.setEnabled(true);
                }
            }
        });

        // InputVerifier for Product Name
        productNameField.setInputVerifier(new InputVerifier() {
            @Override
            public boolean verify(JComponent input) {
                String id = productIdField.getText().trim();
                String name = productNameField.getText().trim();

                if (id.isEmpty() || name.isEmpty()) {
                    return true; // Nothing to check
                }

                Product product = dao.getProductById(id);
                if (product != null) {
                    if (!product.getProductName().equalsIgnoreCase(name)) {
                        JOptionPane.showMessageDialog(AddProductForm.this,
                                "Product name does not match the existing Product ID!",
                                "Name Mismatch", JOptionPane.ERROR_MESSAGE);
                        return false;  // don't allow focus to leave Product Name field
                    }
                }
                return true;
            }
        });

        addButton.addActionListener((ActionEvent e) -> {
            try {
                String id = productIdField.getText().trim();
                String name = productNameField.getText().trim();
                String supplier = supplierField.getText().trim();
                String category = categoryField.getText().trim();
                String brand = productBrandField.getText().trim();
                int qty = Integer.parseInt(quantityField.getText().trim());
                double price = Double.parseDouble(priceField.getText().trim());

                Product existing = dao.getProductById(id);

                if (existing != null) {
                    if (existing.getProductName().equalsIgnoreCase(name)) {
                        JOptionPane.showMessageDialog(this, "Product already exists!", "Duplicate", JOptionPane.WARNING_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(this, "Product name mismatch for this ID!", "Invalid", JOptionPane.ERROR_MESSAGE);
                    }
                    return;
                }

                Product newProduct = new Product(id, supplier, category, brand, name, qty, price);
                boolean success = dao.addProduct(newProduct);
                if (success) {
                    JOptionPane.showMessageDialog(this, "Product added successfully!");
                    productIdField.setText("");
                    productNameField.setText("");
                    supplierField.setText("");
                    categoryField.setText("");
                    productBrandField.setText("");
                    quantityField.setText("");
                    priceField.setText("");
                    productNameField.setEnabled(false);
                } else {
                    JOptionPane.showMessageDialog(this, "Failed to add product.");
                }

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Quantity and Price must be valid numbers.", "Input Error", JOptionPane.ERROR_MESSAGE);
            }
        });
    }
}
