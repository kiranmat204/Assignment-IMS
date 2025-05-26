/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ims;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;


/**
 *
 * @author kiranmat
 */
public class SaleProductForm extends JPanel{
    JLabel displayPercentageLabel;
    JTextField productIdField;
    
    SaleProductForm(){
        setLayout(new GridLayout(8,2,10,10));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        Font boldFont = new Font("Arial", Font.BOLD, 18);
        
        JLabel productIdLabel = new JLabel("Product ID:");
        productIdLabel.setFont(boldFont);
        productIdField = new JTextField();
        
        JButton productFindButton  = new JButton("Find");
        productFindButton.setFont(boldFont);
        
        JLabel salesPercentageLabel = new JLabel("Current Sale Percentage");
        salesPercentageLabel.setFont(boldFont);
        
        displayPercentageLabel = new JLabel("0");
        displayPercentageLabel.setFont(boldFont);
        
        add(productIdLabel);
        add(productIdField);
        
        add(new JLabel());
        add(salesPercentageLabel);
        add(displayPercentageLabel);
        
        productFindButton.addActionListener(l -> findSalesPercentage());
    }

    private void findSalesPercentage() {
        String checkID = productIdField.getText();
        
        String query = "SELECT salesPercentage FROM PRODUCT WHERE productId = ?";
        
        try(Connection conn = DatabaseConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(query)){
            
            
            stmt.setString(1, checkID);
            ResultSet rs = stmt.executeQuery();
            
            
            if(rs.next()){
                String display = rs.getString("salesPercentage");
                displayPercentageLabel.setText(display+"%");
            }
            else{
                displayPercentageLabel.setText("Product is not present in the database!");
            }
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
    }
}
