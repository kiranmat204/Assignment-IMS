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

/**
 *
 * @author kiranmat
 */
public class ManageStockForm extends JPanel{
    JComboBox<String> idBox;
    JLabel stockLabel;
    public ManageStockForm(){
        setLayout(null);
        
        Font boldFont = new Font("Arial", Font.BOLD, 18);
        
        // Row 1
        JLabel itemLabel = new JLabel("Item:");
        itemLabel.setBounds(50, 50, 100, 30);
        itemLabel.setFont(boldFont);
        
        idBox = new JComboBox();
        idBox.setBounds(150,50,400,30);
        
        JLabel displayItemStock = new JLabel("Stock: ");
        displayItemStock.setBounds(800, 50, 100, 30);
        displayItemStock.setFont(boldFont);
        
        // Row 2
        stockLabel = new JLabel("Set Stock:");
        stockLabel.setBounds(50, 100, 100, 30);
        stockLabel.setFont(boldFont);
        
        JTextField inputNewStock = new JTextField();
        inputNewStock.setBounds(150, 100, 200, 30);
        
        //Row 3 
        JButton addStock = new JButton("Add");
        addStock.setBounds(200, 150, 400, 30);
        addStock.setBackground(new Color(0, 51, 102));
        addStock.setForeground(Color.WHITE);
        addStock.setOpaque(true);
        addStock.setBorderPainted(false);
        
        JButton deductStock = new JButton("Remove");
        deductStock.setBounds(650, 150, 400, 30);
        deductStock.setBackground(new Color(213,47,47));
        deductStock.setForeground(Color.WHITE);
        deductStock.setOpaque(true);
        deductStock.setBorderPainted(false);
        
        add(itemLabel);
        add(idBox);
        add(displayItemStock);
        add(stockLabel);
        add(inputNewStock);
        add(addStock);
        add(deductStock);
        
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
}
