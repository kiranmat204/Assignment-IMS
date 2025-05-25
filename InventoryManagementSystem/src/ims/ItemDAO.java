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
public class ItemDAO {
    public List<String> getAllItemNames() {
        List<String> itemNames = new ArrayList<>();
        String sql = "SELECT itemName FROM items";  // Adjust column name if needed

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                itemNames.add(rs.getString("itemName"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return itemNames;
    }
}
