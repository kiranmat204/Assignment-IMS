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

/**
 *
 * @author kiranmat
 */
public class IdDAO {
    public List<String> getAllIds() {
        List<String> ids = new ArrayList<>();
        String sql = "SELECT productId FROM Product";

        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                ids.add(rs.getString("productId"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return ids;
    }
}
