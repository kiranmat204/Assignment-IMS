/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ims;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author ankur
 */
public class SalesDAO {
    
    public void updateSale(SaleRecord sale) {
        String sql = "UPDATE sales SET quantity = ?, salePrice = ?, discount = ? " +
                     "WHERE invoiceNumber = ? AND productId = ?";

        try (Connection conn = DatabaseConnection.getConnection();  // ✅ Use your helper class
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, sale.getQuantity());
            stmt.setDouble(2, sale.getSalePrice());
            stmt.setDouble(3, sale.getDiscount());
            stmt.setString(4, sale.getInvoiceNumber());
            stmt.setString(5, sale.getProductId());

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Failed to update invoice.");
        }
    }

    public List<SaleRecord> getAllSales() {
        List<SaleRecord> salesList = new ArrayList<>();

        String query = "SELECT s.invoiceNumber, s.productId, p.productName, s.quantity, s.salePrice, s.discount " +
               "FROM SALES s " +
               "LEFT JOIN PRODUCT p ON s.productId = p.productId";

        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(query); ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                String invoiceNumber = rs.getString("invoiceNumber");
                String productId = rs.getString("productId");
                String productName = rs.getString("productName");
                
                int quantity = rs.getInt("quantity");
                double salePrice = rs.getDouble("salePrice");
                double discount = rs.getDouble("discount");

                

                salesList.add(new SaleRecord(invoiceNumber, productId, productName, quantity, salePrice, discount));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return salesList;
    }
    
    
}
