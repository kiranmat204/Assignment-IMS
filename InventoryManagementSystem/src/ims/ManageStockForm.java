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
import java.util.List;
import java.util.ArrayList;

/**
 *
 * @author kiranmat
 */
public class ManageStockForm extends JPanel{
    private JComboBox<String> idBox;
    private JLabel stockLabel;
    private JLabel displayItemStock;
    private DefaultTableModel model;
    private JTable table;
    private JScrollPane scrollPane;
    private JTextField inputNewStock;
    
    
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
        idBox.addItem("Select");
        
        List<String> ids = getLowStockIds();
        
        for(String id : ids){
            idBox.addItem(id);
        }
        
        displayItemStock = new JLabel("Stock: ");
        displayItemStock.setBounds(800, 550, 100, 30);
        displayItemStock.setFont(boldFont);
        
        // Row 3
        stockLabel = new JLabel("Set Stock:");
        stockLabel.setBounds(50, 600, 100, 30);
        stockLabel.setFont(boldFont);
        
        inputNewStock = new JTextField();
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
        addStock.addActionListener(e -> handleAddStock());
        deductStock.addActionListener(e -> handleDeductStock());
    }
    
    private void refreshIdComboBox(){
        idBox.removeAllItems();
        idBox.addItem("Select");
        
        List<String> ids = getLowStockIds();
        
        for(String id : ids){
            idBox.addItem(id);
        }
        
        idBox.setSelectedIndex(0);
    }

    private int getIdStock() {
        String selected = (String) idBox.getSelectedItem();
        
        String query = "SELECT quantity FROM PRODUCT WHERE productId = ?";
        try (Connection conn = DatabaseConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(query)){
            
            stmt.setString(1, selected);
            ResultSet rs = stmt.executeQuery();
            
            if(rs.next()){
                int displayQuantity = rs.getInt("quantity");
                displayItemStock.setText("Stock: "+displayQuantity);
                return displayQuantity;
            }
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
        return -1;
    }
    
    private List<String> getLowStockIds(){
        List<String> list = new ArrayList<>();
        String query = "SELECT productId FROM Product WHERE quantity < 6";
        try(Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(query);
                ResultSet rs = stmt.executeQuery()){
            while(rs.next()){
                String productId = rs.getString("productId");
                list.add(productId);
            }
        }
        catch(SQLException ex){
            ex.printStackTrace();
        }
        
        return list;
    }
    
    private void loadProductData(){
        model.setRowCount(0);
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

    private void handleAddStock() {
        String checkID = (String) idBox.getSelectedItem();
        String stockText = inputNewStock.getText().trim();
        
        if(checkID == null || checkID.isEmpty()){
            JOptionPane.showMessageDialog(this,"Please select an ID!");
            return;
        }
        
        if(stockText.isEmpty()){
            JOptionPane.showMessageDialog(this,"Enter an amount to add!");
            return;
        }
        
        try{
            int stockAdd = Integer.parseInt(stockText);
            if (stockAdd <= 0){
                JOptionPane.showMessageDialog(this,"Enter an amount greater than 0!");
                return;
            }
            
            int currentStock = getIdStock();
            if(currentStock == -1){
                JOptionPane.showMessageDialog(this,"Error getting stock!");
                return;
            }
            
            int newStock = currentStock+stockAdd;
            
            if(updateStock(checkID, newStock)){
                displayItemStock.setText("Stock: "+newStock);
                inputNewStock.setText("");
                
                loadProductData();
                refreshIdComboBox();
                
                JOptionPane.showMessageDialog(this, stockAdd+" stock successfully added!");
            }
            else{
                JOptionPane.showMessageDialog(this, "Stock failed to add!");
            }
        }
        catch(NumberFormatException ex){
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this,"Enter a numerical value");
        }
    }

    private void handleDeductStock() {
        String checkID = (String) idBox.getSelectedItem();
        String stockText = inputNewStock.getText().trim();
        
        if(checkID == null || checkID.isEmpty()){
            JOptionPane.showMessageDialog(this,"Please select an ID!");
            return;
        }
        
        if(stockText.isEmpty()){
            JOptionPane.showMessageDialog(this,"Enter an amount to add!");
            return;
        }
        
        try{
            int stockDeduct = Integer.parseInt(stockText);
            if (stockDeduct <= 0){
                JOptionPane.showMessageDialog(this,"Enter an amount greater than 0!");
                return;
            }
            
            int currentStock = getIdStock();
            if(currentStock == -1){
                JOptionPane.showMessageDialog(this,"Error getting stock!");
                return;
            }
            
            int newStock = currentStock-stockDeduct;
            
            if(stockDeduct > currentStock){
                JOptionPane.showMessageDialog(this,"Unable to remove. " + stockDeduct + "is greater than current stock!");
                return;
            }
            
            if(updateStock(checkID, newStock)){
                displayItemStock.setText("Stock: "+newStock);
                inputNewStock.setText("");
                
                loadProductData();
                refreshIdComboBox();
                
                JOptionPane.showMessageDialog(this, stockDeduct+" stock successfully deducted!");
            }
            else{
                JOptionPane.showMessageDialog(this, "Stock failed to remove!");
            }
        }
        catch(NumberFormatException ex){
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this,"Enter a numerical value");
        }
    }

    private boolean updateStock(String productId, int newStock) {
        String query = "UPDATE Product SET quantity = ? WHERE productId = ?";
        
        try(Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(query);){
            
            stmt.setInt(1, newStock);
            stmt.setString(2, productId);
            
            return stmt.executeUpdate() > 0;
        }
        catch(SQLException ex){
            ex.printStackTrace();
        }
        return false;
    }
}
