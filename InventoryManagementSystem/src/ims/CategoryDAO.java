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
public class CategoryDAO {
    public List<String> getAllCategories() {
        List<String> categories = new ArrayList<>();
        String sql = "SELECT CATEGORY_NAME FROM CATEGORIES";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                categories.add(rs.getString("CATEGORY_NAME"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return categories;
    }
}
