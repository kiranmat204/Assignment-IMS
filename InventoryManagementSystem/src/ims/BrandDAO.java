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
public class BrandDAO {
    public List<String> getAllBrands() {
        List<String> brands = new ArrayList<>();
        String sql = "SELECT BRAND_NAME FROM brands";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                brands.add(rs.getString("BRAND_NAME"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return brands;
    }
}