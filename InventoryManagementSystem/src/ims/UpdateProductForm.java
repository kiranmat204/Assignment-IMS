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
import java.util.List;

public class UpdateProductForm extends JPanel {

    private JComboBox<String> productIdComboBox;
    private JTextField brandField;
    private JTextField categoryField;
    private JTextField quantityField;
    private JTextField priceField;
    private JTextField supplierField;
    private JTextField newQuantityField;
    private JTextField newPriceField;
    private JTextField newRetailPriceField;
    private ProductDAO productDAO;
    private JTextField productNameField;

    public UpdateProductForm() {
        productDAO = new ProductDAO();

        setLayout(new BorderLayout(15, 15));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        Font labelFont = new Font("Arial", Font.BOLD, 18);
        Font fieldFont = new Font("Arial", Font.PLAIN, 16);

        // Fetch product IDs
        List<String> productIds = productDAO.getAllProductIds();

        // Initialize fields
        productIdComboBox = new JComboBox<>();
        productIdComboBox.addItem("--- Select Product ID ---");
        for (String id : productIds) {
            productIdComboBox.addItem(id);
        }
        productIdComboBox.setFont(fieldFont);

        brandField = createInfoField(fieldFont);
        productNameField = createInfoField(fieldFont);
        categoryField = createInfoField(fieldFont);
        quantityField = createInfoField(fieldFont);
        priceField = createInfoField(fieldFont);
        supplierField = createInfoField(fieldFont);
        newQuantityField = new JTextField(10);
        newPriceField = new JTextField(10);
        newRetailPriceField = new JTextField(10);
        newQuantityField.setFont(fieldFont);
        newPriceField.setFont(fieldFont);
        newRetailPriceField.setFont(fieldFont);

        // Details panel (3 rows of 2 columns)
        JPanel productDetailsPanel = new JPanel(new GridLayout(8, 2, 15, 15));
        productDetailsPanel.add(createLabel("Product ID:", labelFont));
        productDetailsPanel.add(productIdComboBox);
        productDetailsPanel.add(createLabel("Brand:", labelFont));
        productDetailsPanel.add(brandField);

        productDetailsPanel.add(createLabel("Product Name:", labelFont));
        productDetailsPanel.add(productNameField);

        productDetailsPanel.add(createLabel("Category:", labelFont));
        productDetailsPanel.add(categoryField);
        productDetailsPanel.add(createLabel("Current Quantity:", labelFont));
        productDetailsPanel.add(quantityField);

        productDetailsPanel.add(createLabel("Price:", labelFont));
        productDetailsPanel.add(priceField);
        productDetailsPanel.add(createLabel("Supplier:", labelFont));
        productDetailsPanel.add(supplierField);

        // Update panel for new values
        JPanel updateFieldsPanel = new JPanel(new GridLayout(2, 4, 15, 10));
        updateFieldsPanel.add(createLabel("New Quantity:", labelFont));
        updateFieldsPanel.add(newQuantityField);
        updateFieldsPanel.add(createLabel("New Price:", labelFont));
        updateFieldsPanel.add(newPriceField);
        updateFieldsPanel.add(createLabel("New Retail Price:", labelFont));
        updateFieldsPanel.add(newRetailPriceField);
        updateFieldsPanel.add(new JLabel(""));
        updateFieldsPanel.add(new JLabel(""));

        // Update Button
        JButton updateButton = new JButton("Update Product");
        updateButton.setFont(labelFont);
        updateButton.setBackground(new Color(0, 51, 102));
        updateButton.setForeground(Color.WHITE);
        updateButton.setFocusPainted(false);
        updateButton.setBorderPainted(false);
        updateButton.setOpaque(true);

        // Layout all parts
        JPanel centerPanel = new JPanel(new BorderLayout(10, 10));
        centerPanel.add(updateFieldsPanel, BorderLayout.NORTH);
        centerPanel.add(updateButton, BorderLayout.SOUTH);

        add(productDetailsPanel, BorderLayout.NORTH);
        add(centerPanel, BorderLayout.CENTER);

        // Load product details when selection changes
        productIdComboBox.addActionListener(e -> {
            String selectedId = (String) productIdComboBox.getSelectedItem();
            if (selectedId != null && !selectedId.equals("--- Select Product ID ---")) {
                loadProductDetails(selectedId);
            } else {
                clearFormFields(); // Reset if default item is selected
            }
        });

        // Update logic
        updateButton.addActionListener((ActionEvent e) -> {
            try {
                String productId = (String) productIdComboBox.getSelectedItem();
                int newQuantity = Integer.parseInt(newQuantityField.getText().trim());
                double newPrice = Double.parseDouble(newPriceField.getText().trim());
                double newRetailPrice = Double.parseDouble(newRetailPriceField.getText().trim());
                
                if(newRetailPrice < newPrice){
                    JOptionPane.showMessageDialog(this, "Product retail price can't be lower than actual price!", "Invalid", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                boolean result = productDAO.updateProductQuantity(productId, newQuantity) &&
                        productDAO.updateProductPrice(productId, newPrice) &&
                        productDAO.updateRetailPrice(productId, newRetailPrice);

                if (result) {
                    JOptionPane.showMessageDialog(this, "Product updated successfully!");
                    productIdComboBox.setSelectedIndex(0); // Reset to "--- Select Product ID ---"
                    clearFormFields();
                } else {
                    JOptionPane.showMessageDialog(this, "Failed to update product.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Please enter valid numbers.", "Input Error", JOptionPane.ERROR_MESSAGE);
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
            newQuantityField.setText("");
            newPriceField.setText("");
            newRetailPriceField.setText("");
        }

    }

    private void clearFormFields() {
        brandField.setText("");
        categoryField.setText("");
        quantityField.setText("");
        priceField.setText("");
        supplierField.setText("");
        newQuantityField.setText("");
        newPriceField.setText("");
        newRetailPriceField.setText("");
    }
}
