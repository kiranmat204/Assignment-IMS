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
import java.util.List;


/**
 *
 * @author kiranmat
 */
public class SaleProductForm extends JPanel{
    JLabel displayPercentageLabel;
    JTextField productIdField;
    JComboBox productIdBox;
    JLabel displayPriceLabel;
    JComboBox setSaleBox;
    double currentSale;
    double currentPrice;
    String[] salesSelections = {"0%","5%","10%","15%","20%","25%","30%","35%","40%","45%","50%","55%","60%","65%","70%","75%","80%","85%","90%","95%"};
    
    SaleProductForm(){
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        Font boldFont = new Font("Arial", Font.BOLD, 18);
        
        JPanel row1 = new JPanel(new FlowLayout(FlowLayout.LEFT,5,5));
        JLabel productIdLabel = new JLabel("Product ID:");
        productIdLabel.setFont(boldFont);
        
        //productIdField = new JTextField(50);
        //productIdField.setPreferredSize(new Dimension(180,25));
        
        productIdBox = new JComboBox();
        productIdBox.setPreferredSize(new Dimension(180,25));
        
        IdDAO idDAO = new IdDAO();
        List<String> productIds = idDAO.getAllIds();
        
        for(String id : productIds){
            productIdBox.addItem(id);
        }
        
        
        JButton productFindButton  = new JButton("Find");
        productFindButton.setFont(boldFont);
        productFindButton.setPreferredSize(new Dimension(100,25));
        productFindButton.setBackground(new Color(0, 51, 102));
        productFindButton.setForeground(Color.WHITE);
        productFindButton.setOpaque(true);
        productFindButton.setContentAreaFilled(true);
        productFindButton.setBorderPainted(false);
        
        row1.add(productIdLabel);
        row1.add(productIdBox);
        row1.add(productFindButton);
        
        JPanel row2 = new JPanel(new FlowLayout(FlowLayout.LEFT,5,5));
        
        JLabel salesPercentageLabel = new JLabel("Current Sale Percentage:");
        salesPercentageLabel.setFont(boldFont);
        
        displayPercentageLabel = new JLabel("null");
        displayPercentageLabel.setFont(boldFont);
        
        JLabel salesPriceLabel = new JLabel("Current Price:");
        salesPriceLabel.setFont(boldFont);
        
        
        displayPriceLabel = new JLabel("null");
        displayPriceLabel.setFont(boldFont);
        
        
        row2.add(salesPercentageLabel);
        row2.add(Box.createHorizontalStrut(20));
        row2.add(displayPercentageLabel);
        row2.add(Box.createHorizontalStrut(1200));
        row2.add(salesPriceLabel);
        row2.add(Box.createHorizontalStrut(115));
        row2.add(displayPriceLabel);
        
        JPanel row3 = new JPanel(new FlowLayout(FlowLayout.LEFT,5,5));
        
        JLabel setSaleLabel = new JLabel("Set Sale:");
        setSaleLabel.setFont(boldFont);
        
        setSaleBox = new JComboBox(salesSelections);
        setSaleBox.setFont(boldFont);
        setSaleBox.setPreferredSize(new Dimension(180,25));
        
        
        JButton setSaleButton = new JButton("Confirm");
        setSaleButton.setFont(boldFont);
        setSaleButton.setPreferredSize(new Dimension(180,25));
        setSaleButton.setBackground(new Color(0, 51, 102));
        setSaleButton.setForeground(Color.WHITE);
        setSaleButton.setOpaque(true);
        setSaleButton.setContentAreaFilled(true);
        setSaleButton.setBorderPainted(false);
        
        row3.add(setSaleLabel);
        row3.add(setSaleBox);
        row3.add(setSaleButton);
        
        add(row1);
        add(row2);
        add(row3);
        
        productFindButton.addActionListener(e -> findSalesPercentage());
        setSaleButton.addActionListener(e -> setNewSale());
    }

    private void findSalesPercentage() {
        String checkID = (String)productIdBox.getSelectedItem();
        
        if(checkID.trim().isEmpty()){
            displayPercentageLabel.setForeground(Color.red);
            displayPercentageLabel.setText("Please don't leave the field empty!");
            return;
        }
        
        String query = "SELECT sale, salePrice FROM PRODUCT WHERE productId = ?";
        
        try(Connection conn = DatabaseConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(query)){
            
            stmt.setString(1, checkID);
            ResultSet rs = stmt.executeQuery();
            if(rs.next()){
                String displaySale = rs.getString("sale");
                currentSale = Double.parseDouble(displaySale);
                
                String displayPrice = rs.getString("salePrice");
                currentPrice = Double.parseDouble(displayPrice);
                
                displayPercentageLabel.setForeground(Color.BLACK);
                displayPercentageLabel.setText(displaySale+"%");
                displayPriceLabel.setText("$"+displayPrice);
            }
            else{
                displayPercentageLabel.setForeground(Color.red);
                displayPercentageLabel.setText("Please enter a product that exists within the database!");
            }
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
    }
    
    private void setNewSale(){
        String checkID = (String)productIdBox.getSelectedItem();
        
        if(checkID.trim().isEmpty()){
            displayPercentageLabel.setForeground(Color.red);
            displayPercentageLabel.setText("Please don't leave the field empty!");
            return;
        }
        
        String selected = (String)setSaleBox.getSelectedItem();
        selected = selected.replace("%", "");
        
        double checkSaleInput;
        try{
            checkSaleInput = Double.parseDouble(selected);
        }
        catch(NumberFormatException ex){
            displayPercentageLabel.setForeground(Color.red);
            displayPercentageLabel.setText("Input is invalid!");
            return;
        }
       
        double orginalPrice = getActualPrice(checkID);
        
        if(orginalPrice < 0){
            displayPercentageLabel.setForeground(Color.red);
            displayPercentageLabel.setText("Error, not Price Found!");
            return;
        }
        
        double updatedPrice = orginalPrice * (1-checkSaleInput/100);
        
        String update = "UPDATE PRODUCT SET sale = ?, salePrice = ? WHERE productId = ?";
        try(Connection conn = DatabaseConnection.getConnection();
           PreparedStatement stmt = conn.prepareStatement(update)){
            
            stmt.setDouble(1, checkSaleInput);
            stmt.setDouble(2, updatedPrice);
            stmt.setString(3, checkID);
            stmt.executeUpdate();
            
            displayPercentageLabel.setForeground(Color.BLUE);
            displayPercentageLabel.setText(checkSaleInput + "%");
            displayPriceLabel.setText("$" + String.format("%.2f",updatedPrice));
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
    }

    private double getActualPrice(String checkId) {
        String query = "SELECT price FROM PRODUCT WHERE productId = ?";
        try(Connection conn = DatabaseConnection.getConnection();
           PreparedStatement stmt = conn.prepareStatement(query)){
            
            stmt.setString(1,checkId);
            ResultSet rs = stmt.executeQuery();
            
            if(rs.next()){
                return rs.getDouble("price");
            }
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
        return -1;
    }
}
