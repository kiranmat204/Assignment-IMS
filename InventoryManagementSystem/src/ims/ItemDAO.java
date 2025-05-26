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
        List<String> items = new ArrayList<>();
        String sql = "SELECT ITEMNAME FROM ITEMS";

        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                items.add(rs.getString("ITEMNAME"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return items;
    }
}
