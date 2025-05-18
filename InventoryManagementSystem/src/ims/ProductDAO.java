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
public class ProductDAO {

    public void createProductTable() {
        String sql = "CREATE TABLE Product (" +
                "productId VARCHAR(20) PRIMARY KEY," +
                "productName VARCHAR(100)," +
                "quantity INT," +
                "productBrand VARCHAR(100)," +
                "price DOUBLE," +
                "category VARCHAR(50)," +
                "supplier VARCHAR(100)" +
                ")";

        try (Connection conn = DatabaseConnection.getConnection(); Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
            System.out.println("Product table created successfully");
        } catch (SQLException e) {
            // If table already exists, this will throw an exception
            System.out.println("Error creating table: " + e.getMessage());

        }
    }

    public boolean addProduct(Product product) {
        String sql = "INSERT INTO Product (productId, productName, quantity, productBrand, price, category, supplier) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, product.getProductId());
            stmt.setString(2, product.getProductName());
            stmt.setInt(3, product.getQuantity());
            stmt.setString(4, product.getProductBrand());
            stmt.setDouble(5, product.getPrice());
            stmt.setString(6, product.getCategory());
            stmt.setString(7, product.getSupplier());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Failed to Add product!");
            return false;
        }
    }

    // Get a product by ID
    public Product getProductById(String productId) {
        String sql = "SELECT * FROM Product WHERE productId = ?";
        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, productId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Product(
                        rs.getString("productId"), // productId
                        rs.getString("supplier"), // supplier
                        rs.getString("category"), // category
                        rs.getString("productBrand"), // productBrand
                        rs.getString("productName"), // productName
                        rs.getInt("quantity"), // quantity
                        rs.getDouble("price") // price
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
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
                        rs.getDouble("price") // price
                );
                productList.add(product);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return productList;
    }
}
