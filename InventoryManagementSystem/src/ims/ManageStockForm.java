/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ims;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.table.DefaultTableModel;
import java.sql.*;

/**
 *
 * @author kiranmat
 */
public class ManageStockForm extends JPanel{
    private JComboBox<String> idBox;
    private JLabel stockLabel;
    private DefaultTableModel model;
    private JTable table;
    private JScrollPane scrollPane;
    
    
    public ManageStockForm(){
        setLayout(null);
        
        String[] coloumnNames = {"Product ID", "Product", "Category", "Stock", "Market Price", "Retail Price"};
        
        model = new DefaultTableModel(coloumnNames, 0){
             @Override
             public boolean isCellEditable(int row, int col){
                 return false;
             }
        };
        
        JLabel quickAddLabel = new JLabel("Quick Add Stock:");
        quickAddLabel.setBounds(50,510,600,30);
        quickAddLabel.setFont(new Font("Arial", Font.BOLD, 24));
        
        table = new JTable(model);
        scrollPane = new JScrollPane(table);
        scrollPane.setBounds(50, 50, 1000, 450);
        
        Font boldFont = new Font("Arial", Font.BOLD, 18);
        
        // Row 2
        JLabel itemLabel = new JLabel("Item:");
        itemLabel.setBounds(50, 550, 100, 30);
        itemLabel.setFont(boldFont);
        
        idBox = new JComboBox();
        idBox.setBounds(150,550,400,30);
        
        JLabel displayItemStock = new JLabel("Stock: ");
        displayItemStock.setBounds(800, 550, 100, 30);
        displayItemStock.setFont(boldFont);
        
        // Row 3
        stockLabel = new JLabel("Set Stock:");
        stockLabel.setBounds(50, 600, 100, 30);
        stockLabel.setFont(boldFont);
        
        JTextField inputNewStock = new JTextField();
        inputNewStock.setBounds(150, 600, 200, 30);
        
        //Row 4 
        JButton addStock = new JButton("Add");
        addStock.setBounds(200, 650, 400, 30);
        addStock.setBackground(new Color(0, 51, 102));
        addStock.setForeground(Color.WHITE);
        addStock.setOpaque(true);
        addStock.setBorderPainted(false);
        
        JButton deductStock = new JButton("Remove");
        deductStock.setBounds(650, 650, 400, 30);
        deductStock.setBackground(new Color(213,47,47));
        deductStock.setForeground(Color.WHITE);
        deductStock.setOpaque(true);
        deductStock.setBorderPainted(false);
        
        add(scrollPane);
        add(itemLabel);
        add(idBox);
        add(displayItemStock);
        add(stockLabel);
        add(inputNewStock);
        add(addStock);
        add(deductStock);
        add(quickAddLabel);
        
        loadProductData();
        
        idBox.addActionListener(e -> getIdStock());
    }

    private void getIdStock() {
        String selected = (String) idBox.getSelectedItem();
        
        String query = "SELECT quantity FROM PRODUCT WHERE productId = ?";
        try (Connection conn = DatabaseConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(query)){
            
            stmt.setString(1, selected);
            ResultSet rs = stmt.executeQuery();
            
            if(rs.next()){
                int displayQuantity = rs.getInt("quantity");
                stockLabel.setText("Stock: "+displayQuantity);
            }
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
    }
    
    private void loadProductData(){
        String query = "SELECT productId, productName, category, quantity, price, salePrice FROM Product"
                + " WHERE quantity < 6 ORDER BY quantity ASC";
        
        try(Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(query);
                ResultSet rs = stmt.executeQuery();){
            
            while(rs.next()){
                String productId = rs.getString("productId");
                String productName = rs.getString("productName");
                String category = rs.getString("category");
                int quantity = rs.getInt("quantity");
                double price = rs.getDouble("price");
                double salePrice = rs.getDouble("salePrice");
                
                
                Object[] row = {
                    productId,
                    productName,
                    category,
                    quantity,
                    String.format("$%.2f",price),
                    String.format("$%.2f",salePrice)
                };
                
                model.addRow(row);
            }
        }
        catch(SQLException ex){
            ex.printStackTrace();
        }
    }
}
