/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ims;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ankur
 */
public class SupplierDAO {

    public List<String> getAllSuppliers() {
        List<String> suppliers = new ArrayList<>();
        String sql = "SELECT SUPPLIERNAME FROM SUPPLIER";

        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                suppliers.add(rs.getString("SUPPLIERNAME"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return suppliers;
    }
    
    public boolean addSupplier(String supplierName) {
    String sql = "INSERT INTO SUPPLIER (SUPPLIERNAME) VALUES (?)";
    try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
        stmt.setString(1, supplierName);
        int rows = stmt.executeUpdate();
        return rows > 0;
    } catch (SQLException e) {
        e.printStackTrace();
        return false;
    }
}
}
