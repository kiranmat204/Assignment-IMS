/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ims;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;

/**
 *
 * @author ankur
 */
public class DeleteProductForm extends JPanel {

    private JComboBox<String> productIdComboBox;
    private JTextField brandField, categoryField, quantityField, priceField, supplierField;
    private ProductDAO productDAO;
    private JTextField productNameField;

    public DeleteProductForm() {
        productDAO = new ProductDAO();
        setLayout(new BorderLayout(15, 15));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        Font labelFont = new Font("Arial", Font.BOLD, 18);
        Font fieldFont = new Font("Arial", Font.PLAIN, 16);

        // Dropdown for product IDs
        productIdComboBox = new JComboBox<>();
        productIdComboBox.setFont(fieldFont);
        productIdComboBox.addItem("--- Select Product ID ---");
        List<String> productIds = productDAO.getAllProductIds();
        for (String id : productIds) {
            productIdComboBox.addItem(id);
        }

        // Fields
        brandField = createInfoField(fieldFont);
        productNameField = createInfoField(fieldFont);
        categoryField = createInfoField(fieldFont);
        quantityField = createInfoField(fieldFont);
        priceField = createInfoField(fieldFont);
        supplierField = createInfoField(fieldFont);

        // Product details layout
        JPanel detailsPanel = new JPanel(new GridLayout(4, 4, 15, 15));
        detailsPanel.add(createLabel("Product ID:", labelFont));
        detailsPanel.add(productIdComboBox);
        detailsPanel.add(createLabel("Brand:", labelFont));
        detailsPanel.add(brandField);

        detailsPanel.add(createLabel("Product Name:", labelFont));
        detailsPanel.add(productNameField);

        detailsPanel.add(createLabel("Category:", labelFont));
        detailsPanel.add(categoryField);
        detailsPanel.add(createLabel("Quantity:", labelFont));
        detailsPanel.add(quantityField);

        detailsPanel.add(createLabel("Price:", labelFont));
        detailsPanel.add(priceField);
        detailsPanel.add(createLabel("Supplier:", labelFont));
        detailsPanel.add(supplierField);

        // Delete button
        JButton deleteButton = new JButton("Delete Product");
        deleteButton.setFont(labelFont);
        deleteButton.setBackground(new Color(153, 0, 0));
        deleteButton.setForeground(Color.WHITE);
        deleteButton.setFocusPainted(false);
        deleteButton.setBorderPainted(false);
        deleteButton.setOpaque(true);

        // Add panels to layout
        add(detailsPanel, BorderLayout.NORTH);
        add(deleteButton, BorderLayout.SOUTH);

        // Load details when a product ID is selected
        productIdComboBox.addActionListener(e -> {
            String selectedId = (String) productIdComboBox.getSelectedItem();
            if (selectedId != null && !selectedId.equals("--- Select Product ID ---")) {
                loadProductDetails(selectedId);
            } else {
                clearFormFields();
            }
        });

        // Delete confirmation logic
        deleteButton.addActionListener((ActionEvent e) -> {
            String productId = (String) productIdComboBox.getSelectedItem();

            if (productId == null || productId.equals("--- Select Product ID ---")) {
                JOptionPane.showMessageDialog(this, "Please select a valid Product ID.", "Input Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            int choice = JOptionPane.showConfirmDialog(this,
                    "Do you really want to delete this product?",
                    "Confirm Deletion",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.WARNING_MESSAGE);

            if (choice == JOptionPane.YES_OPTION) {
                boolean result = productDAO.removeProduct(productId);
                if (result) {
                    JOptionPane.showMessageDialog(this, "Product deleted successfully!");
                    productIdComboBox.setSelectedIndex(0); // Reset dropdown
                    clearFormFields();
                } else if (choice == JOptionPane.NO_OPTION) {
                    // Clear fields and reset dropdown on No
                    productIdComboBox.setSelectedIndex(0);
                    clearFormFields();
                }
            } else if (choice == JOptionPane.NO_OPTION) {
                // Clear fields and reset dropdown on No
                productIdComboBox.setSelectedIndex(0);
                clearFormFields();
            }

        });
    }

    private JTextField createInfoField(Font font) {
        JTextField field = new JTextField(10);
        field.setEditable(false);
        field.setFont(font);
        return field;
    }

    private JLabel createLabel(String text, Font font) {
        JLabel label = new JLabel(text);
        label.setFont(font);
        return label;
    }

    private void loadProductDetails(String productId) {
        Product product = productDAO.getProductById(productId);
        if (product != null) {
            brandField.setText(product.getProductBrand());
            productNameField.setText(product.getProductName());
            categoryField.setText(product.getCategory());
            quantityField.setText(String.valueOf(product.getQuantity()));
            priceField.setText(String.valueOf(product.getPrice()));
            supplierField.setText(product.getSupplier());
        }
    }

    private void clearFormFields() {
        brandField.setText("");
        categoryField.setText("");
        quantityField.setText("");
        priceField.setText("");
        supplierField.setText("");
    }
}
