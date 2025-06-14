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
        setLayout(new GridLayout(9, 2, 10, 10));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        Font boldFont = new Font("Arial", Font.BOLD, 18);

        JLabel productIdLabel = new JLabel("Product ID:");
        productIdLabel.setFont(boldFont);
        JTextField productIdField = new JTextField();

        JLabel productNameLabel = new JLabel("Product Name:");
        productNameLabel.setFont(boldFont);
        JComboBox<String> productNameComboBox = new JComboBox<>();
        productNameComboBox.setEnabled(false);

        JLabel supplierLabel = new JLabel("Supplier:");
        supplierLabel.setFont(boldFont);
        JComboBox<String> supplierComboBox = new JComboBox<>();
        supplierComboBox.setEditable(true);

        JLabel categoryLabel = new JLabel("Category:");
        categoryLabel.setFont(boldFont);
        JComboBox<String> categoryComboBox = new JComboBox<>();

        JLabel productBrandLabel = new JLabel("Product Brand:");
        productBrandLabel.setFont(boldFont);
        JComboBox<String> productBrandComboBox = new JComboBox<>();

        JLabel quantityLabel = new JLabel("Quantity:");
        quantityLabel.setFont(boldFont);
        JTextField quantityField = new JTextField();

        JLabel priceLabel = new JLabel("Price:");
        priceLabel.setFont(boldFont);
        JTextField priceField = new JTextField();
        
//        JLabel retailPriceLabel = new JLabel("Retail Price:");
//        retailPriceLabel.setFont(boldFont);
//        JTextField retailPriceField = new JTextField();

        JButton addButton = new JButton("Add Product");
        addButton.setFont(boldFont);
        addButton.setBackground(new Color(0, 51, 102));
        addButton.setForeground(Color.WHITE);
        addButton.setOpaque(true);
        addButton.setContentAreaFilled(true);
        addButton.setBorderPainted(false);

        // Add components to panel
        add(productIdLabel);
        add(productIdField);
        add(productNameLabel);
        add(productNameComboBox);
        add(supplierLabel);
        add(supplierComboBox);
        add(categoryLabel);
        add(categoryComboBox);
        add(productBrandLabel);
        add(productBrandComboBox);
        add(quantityLabel);
        add(quantityField);
        add(priceLabel);
        add(priceField);
//        add(retailPriceLabel);
//        add(retailPriceField);
        add(new JLabel());
        add(addButton);

        // DAOs
        ProductDAO dao = new ProductDAO();
        ItemDAO itemDAO = new ItemDAO();
        CategoryDAO categoryDAO = new CategoryDAO();
        BrandDAO brandDAO = new BrandDAO();
        SupplierDAO supplierDAO = new SupplierDAO();

        // Populate combo boxes
        productNameComboBox.addItem("-- Select Product --");
        for (String item : itemDAO.getAllItemNames()) {
            productNameComboBox.addItem(item);
        }

        categoryComboBox.addItem("-- Select Category --");

        for (String category : categoryDAO.getAllCategories()) {
            categoryComboBox.addItem(category);
        }

        productBrandComboBox.addItem("-- Select Brand --");
        for (String brand : brandDAO.getAllBrands()) {
            productBrandComboBox.addItem(brand);
        }

        supplierComboBox.addItem("-- Select Supplier --");
        for (String supplier : supplierDAO.getAllSuppliers()) {
            supplierComboBox.addItem(supplier);
        }

        // Product ID focus lost: check if exists
        productIdField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                String id = productIdField.getText().trim();
                if (id.isEmpty()) {
                    productNameComboBox.setEnabled(false);
                    return;
                }

                Product product = dao.getProductById(id);
                if (product != null) {
                    JOptionPane.showMessageDialog(AddProductForm.this,
                            "Product ID already exists.",
                            "Duplicate Product ID", JOptionPane.WARNING_MESSAGE);
                    productNameComboBox.setEnabled(true);
                    productNameComboBox.requestFocus();
                } else {
                    productNameComboBox.setEnabled(true);
                }
            }
        });

        // Verify Product Name against existing ID
        productNameComboBox.setInputVerifier(new InputVerifier() {
            @Override
            public boolean verify(JComponent input) {
                String id = productIdField.getText().trim();
                String name = (String) productNameComboBox.getSelectedItem();

                if (id.isEmpty() || name == null || name.equals("-- Select Product --")) {
                    return true;
                }

                Product product = dao.getProductById(id);
                if (product != null && !product.getProductName().equalsIgnoreCase(name)) {
                    JOptionPane.showMessageDialog(AddProductForm.this,
                            "Product name does not match the existing Product ID!",
                            "Name Mismatch", JOptionPane.ERROR_MESSAGE);
                    return false;
                }
                return true;
            }
        });

        // Add button logic
        addButton.addActionListener((ActionEvent e) -> {
            try {
                String id = productIdField.getText().trim();
                String name = (String) productNameComboBox.getSelectedItem();
                String supplier = ((String) supplierComboBox.getEditor().getItem()).trim();
                String category = (String) categoryComboBox.getSelectedItem();
                String brand = (String) productBrandComboBox.getSelectedItem();
                int qty = Integer.parseInt(quantityField.getText().trim());
                double price = Double.parseDouble(priceField.getText().trim());
                //double retailPrice = Double.parseDouble(retailPriceField.getText().trim());
                        

                if (name == null || name.equals("-- Select Product --") ||
                        category == null || category.equals("-- Select Category --") ||
                        brand == null || brand.equals("-- Select Brand --")) {
                    JOptionPane.showMessageDialog(this, "Please select valid options from dropdowns.", "Input Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Check if supplier exists
                if (!supplierDAO.getAllSuppliers().contains(supplier)) {
                    supplierDAO.addSupplier(supplier); // You need to create this method
                }

                boolean existsInCombo = false;
                for (int i = 0; i < supplierComboBox.getItemCount(); i++) {
                    if (supplierComboBox.getItemAt(i).equalsIgnoreCase(supplier)) {
                        existsInCombo = true;
                        break;
                    }
                }
                if (!existsInCombo) {
                    supplierComboBox.addItem(supplier);
                }
                supplierComboBox.setSelectedItem(supplier); // Optional: re-select it

                Product existing = dao.getProductById(id);

                if (existing != null) {
                    if (existing.getProductName().equalsIgnoreCase(name)) {
                        JOptionPane.showMessageDialog(this, "Product already exists!", "Duplicate", JOptionPane.WARNING_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(this, "Product name mismatch for this ID!", "Invalid", JOptionPane.ERROR_MESSAGE);
                    }
                    return;
                }
                
//                if(retailPrice < price){
//                    JOptionPane.showMessageDialog(this, "Product retail price can't be lower than actual price!", "Invalid", JOptionPane.ERROR_MESSAGE);
//                    return;
//                }

                //Product newProduct = new Product(id, supplier, category, brand, name, qty, price, retailPrice, 0, retailPrice);
                Product newProduct = new Product(id, supplier, category, brand, name, qty, price);
                boolean success = dao.addProduct(newProduct);
                if (success) {
                    JOptionPane.showMessageDialog(this, "Product added successfully!");
                    productIdField.setText("");
                    productNameComboBox.setSelectedIndex(0);
                    categoryComboBox.setSelectedIndex(0);
                    productBrandComboBox.setSelectedIndex(0);
                    supplierComboBox.setSelectedItem("");
                    quantityField.setText("");
                    priceField.setText("");
                    //retailPriceField.setText("");
                    
                    productNameComboBox.setEnabled(false);
                } else {
                    JOptionPane.showMessageDialog(this, "Failed to add product.");
                }

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Quantity and Price must be valid numbers.", "Input Error", JOptionPane.ERROR_MESSAGE);
            }
        });
    }
}
