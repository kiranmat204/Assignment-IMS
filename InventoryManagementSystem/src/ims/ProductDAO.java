/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ims;

import static ims.DatabaseConnection.getConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ankur
 */
public class ProductDAO {

    public boolean addProduct(Product product) {
        String sql = "INSERT INTO Product (productId, productName, quantity, productBrand, price, category, supplier, sale, salePrice) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, product.getProductId());
            stmt.setString(2, product.getProductName());
            stmt.setInt(3, product.getQuantity());
            stmt.setString(4, product.getProductBrand());
            stmt.setDouble(5, product.getPrice());
            stmt.setString(6, product.getCategory());
            stmt.setString(7, product.getSupplier());
            stmt.setDouble(8, 0);
            stmt.setDouble(9, product.getPrice());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Failed to Add product!");
            return false;
        }
    }

    // Get a product by ID
    public Product getProductById(String productId) {
        Product product = null;
        try (
                Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Product WHERE productId = ?")) {
            stmt.setString(1, productId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                product = new Product(
                        rs.getString("productId"),
                        rs.getString("supplier"),
                        rs.getString("category"),
                        rs.getString("productBrand"),
                        rs.getString("productName"),
                        rs.getInt("quantity"),
                        rs.getDouble("price"),
                        rs.getDouble("sale"),
                        rs.getDouble("salePrice")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Or log this
        }
        return product;
    }

    // Update product quantity
    public boolean updateProductQuantity(String productId, int newQuantity) {
        String sql = "UPDATE Product SET quantity = ? WHERE productId = ?";
        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, newQuantity);
            stmt.setString(2, productId);
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Update product price
    public boolean updateProductPrice(String productId, double newPrice) {
        String sql = "UPDATE Product SET price = ? WHERE productId = ?";
        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setDouble(1, newPrice);
            stmt.setString(2, productId);
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Remove product from database
    public boolean removeProduct(String productId) {
        String sql = "DELETE FROM Product WHERE productId = ?";
        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, productId);
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Get all products
    public List<Product> getAllProducts() {
        List<Product> productList = new ArrayList<>();
        String sql = "SELECT * FROM Product";

        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Product product = new Product(
                        rs.getString("productId"), // productId
                        rs.getString("supplier"), // supplier
                        rs.getString("category"), // category
                        rs.getString("productBrand"), // productBrand
                        rs.getString("productName"), // productName
                        rs.getInt("quantity"), // quantity
                        rs.getDouble("price"), // price
                        rs.getDouble("sale"),
                        rs.getDouble("salePrice")
                );
                productList.add(product);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return productList;
    }

    public List<String> getAllProductIds() {
        List<String> productIds = new ArrayList<>();
        String sql = "SELECT productId FROM Product";

        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                productIds.add(rs.getString("productId"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return productIds;
    }
}
