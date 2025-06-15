/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ims;

import javax.swing.*;
import java.awt.*;
import java.sql.*;
import javax.swing.table.DefaultTableModel;


/**
 *
 * @author kiranmat
 */
public class ManageStockForm extends JPanel{
    private DefaultTableModel model;
    private JTable table;
    private JScrollPane scrollPane;

    public ManageStockForm() {
        setLayout(null);
        
        // Table column names
        String[] columnNames = {"Product ID", "Product", "Category", "Stock"};
        
        // Initialize table model with non-editable cells
        model = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int col) {
                return false; // Make table non-editable
            }
        };

        // Table for displaying low stock items
        table = new JTable(model);
        scrollPane = new JScrollPane(table);
        scrollPane.setBounds(50, 100, 1000, 450); // Adjusted position and size
        
        // Title label: Low Stock Report
        JLabel titleLabel = new JLabel("Low Stock Report", SwingConstants.CENTER);
        titleLabel.setBounds(50, 20, 1000, 40); // Positioned at the top
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        
        add(scrollPane);
        add(titleLabel);
        
        // Change the font of column headers to bold and adjust the font size
        Font headerFont = new Font("Arial", Font.BOLD, 18);
        table.getTableHeader().setFont(headerFont);  // Set header font to bold with size 18

        // Optionally, you can change the font size for the table cells too
        Font cellFont = new Font("Arial", Font.PLAIN, 16);
        table.setFont(cellFont);  // Set font for table content
        
        // Load low stock products
        loadProductData();
    }

    // Load only low stock items (quantity < 5)
    public void loadProductData() {
        model.setRowCount(0);  // Clear the existing rows
        String query = "SELECT productId, productName, category, quantity FROM Product"
                + " WHERE quantity <= 5 ORDER BY quantity ASC";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                String productId = rs.getString("productId");
                String productName = rs.getString("productName");
                String category = rs.getString("category");
                int quantity = rs.getInt("quantity");

                // Add a row to the table for each low stock product
                Object[] row = {
                    productId,
                    productName,
                    category,
                    quantity
                };
                
                model.addRow(row);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    
    public JTable getTable() {
    return table;
}
}
