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
        frame.setSize(400, 450);
        frame.setLocationRelativeTo(null);
        
        
        frame.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10,10,10,10);
        
        

        // Create labels and text fields
        JLabel productIdLabel = new JLabel("Product ID:");
        JTextField productIdField = new JTextField(15);
        productIdField.setPreferredSize(new Dimension(180, 30));
        
        
        JLabel productNameLabel = new JLabel("Product Name:");
        JTextField productNameField = new JTextField(15);
        productNameField.setPreferredSize(new Dimension(180, 30));
        
        JLabel supplierLabel = new JLabel("Supplier:");
        JTextField supplierField = new JTextField(15);
        supplierField.setPreferredSize(new Dimension(180, 30));
        
        JLabel categoryLabel = new JLabel("Category:");
        JTextField categoryField = new JTextField(15);
        categoryField.setPreferredSize(new Dimension(180, 30));
        
        JLabel productBrandLabel = new JLabel("Product Brand:");
        JTextField productBrandField = new JTextField(15);
        productBrandField.setPreferredSize(new Dimension(180, 30));
        
        JLabel quantityLabel = new JLabel("Quantity:");
        JTextField quantityField = new JTextField(15);
        quantityField.setPreferredSize(new Dimension(180, 30));
        
        JLabel priceLabel = new JLabel("Price:");
        JTextField priceField = new JTextField(15);
        priceField.setPreferredSize(new Dimension(180, 30));

        JButton addButton = new JButton("Add Product");

        // Add components to the frame
        gbc.gridx=0;
        gbc.gridy=0;
        gbc.anchor = GridBagConstraints.WEST;
        frame.add(productIdLabel,gbc);
        
        gbc.gridx=1;
        gbc.gridy=0;
        frame.add(productIdField,gbc);
        
        gbc.gridx=0;
        gbc.gridy=1;
        gbc.anchor = GridBagConstraints.WEST;
        frame.add(productNameLabel,gbc);
        
        gbc.gridx=1;
        gbc.gridy=1;
        frame.add(productNameField,gbc);
        
        gbc.gridx=0;
        gbc.gridy=2;
        gbc.anchor = GridBagConstraints.WEST;
        frame.add(supplierLabel,gbc);
        
        gbc.gridx=1;
        gbc.gridy=2;
        frame.add(supplierField,gbc);
        
        gbc.gridx=0;
        gbc.gridy=3;
        gbc.anchor = GridBagConstraints.WEST;
        frame.add(categoryLabel,gbc);
        
        gbc.gridx=1;
        gbc.gridy=3;
        frame.add(categoryField,gbc);
        
        gbc.gridx=0;
        gbc.gridy=4;
        gbc.anchor = GridBagConstraints.WEST;
        frame.add(productBrandLabel,gbc);
        
        gbc.gridx=1;
        gbc.gridy=4;
        frame.add(productBrandField,gbc);
        
        gbc.gridx=0;
        gbc.gridy=5;
        gbc.anchor = GridBagConstraints.WEST;
        frame.add(quantityLabel,gbc);
        
        gbc.gridx=1;
        gbc.gridy=5;
        frame.add(quantityField,gbc);
        
        gbc.gridx=0;
        gbc.gridy=6;
        gbc.anchor = GridBagConstraints.WEST;
        frame.add(priceLabel,gbc);
        
        gbc.gridx=1;
        gbc.gridy=6;
        frame.add(priceField,gbc);
        
        //frame.add(new JLabel());  // Empty label for alignment
        
        gbc.gridx=1;
        gbc.gridy=7;
        frame.add(addButton,gbc);

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
