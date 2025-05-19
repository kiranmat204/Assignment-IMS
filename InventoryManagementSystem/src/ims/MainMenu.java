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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainMenu {

    private JFrame frame;

    ProductDAO productDAO = new ProductDAO();

    public MainMenu() {
        
        System.out.println("Testing Derby connection...");

        try {
            Class.forName("org.apache.derby.jdbc.ClientDriver");
            System.out.println("SUCCESS: Derby driver loaded!");
        } catch (ClassNotFoundException e) {
            JOptionPane.showMessageDialog(frame,
                    "Failed to load Derby driver!\n" + e.getMessage(),
                    "Driver Error",
                    JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
        frame = new JFrame("Inventory Management System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 300);
        frame.setLocationRelativeTo(null);

        // Create buttons
        JButton addProductButton = new JButton("Add Product");
        JButton viewProductsButton = new JButton("View Products");
        JButton updateProductButton = new JButton("Update Product");
        JButton deleteProductButton = new JButton("Delete Product");

        productDAO.createProductTable();

        // Set layout
        frame.setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS));

        // Add buttons to frame
        frame.add(addProductButton);
        frame.add(viewProductsButton);
        frame.add(updateProductButton);
        frame.add(deleteProductButton);

        // Button actions
        addProductButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new AddProductForm(); // Opens Add Product Form
            }
        });

        viewProductsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ViewProductsPanel(); // Opens View Products
            }
        });

        updateProductButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new UpdateProductForm(); // Opens Update Product Form
            }
        });

        deleteProductButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Implement Delete Product functionality here
                new DeleteProductForm();
            }
        });

        frame.setVisible(true);
    }

    public static void main(String[] args) {
        new MainMenu();  // Launch the main menu
    }
}
