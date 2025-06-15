/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package ims;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.*;

import static org.junit.Assert.*;

/**
 *
 * @author ankur
 */
public class SaleProductFormTest {
    private Connection conn;

    @Before
    public void setUp() throws Exception {
        conn = DatabaseConnection.getConnection();
        insertTestProduct();
    }

    @After
    public void tearDown() throws Exception {
        deleteTestSales();
        deleteTestProducts();
        if (conn != null) conn.close();
    }

    private void insertTestProduct() throws SQLException {
        String sql = "INSERT INTO PRODUCT (productId, productBrand, productName, category, supplier, price, quantity) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, "TEST_001");
            stmt.setString(2, "TestBrand");
            stmt.setString(3, "TestProduct");
            stmt.setString(4, "TestCategory");
            stmt.setString(5, "TestSupplier");
            stmt.setDouble(6, 10.0);
            stmt.setInt(7, 100);
            stmt.executeUpdate();
        }
    }

    private void deleteTestProducts() throws SQLException {
        try (PreparedStatement stmt = conn.prepareStatement("DELETE FROM PRODUCT WHERE productId LIKE 'TEST_%'")) {
            stmt.executeUpdate();
        }
    }

    private void deleteTestSales() throws SQLException {
        try (PreparedStatement stmt = conn.prepareStatement("DELETE FROM SALES WHERE invoiceNumber LIKE 'TEST_%'")) {
            stmt.executeUpdate();
        }
    }

    @Test
    public void testSaleSubmission() throws Exception {
        String invoiceNumber = "TEST_INV_001";
        String productId = "TEST_001";
        int quantitySold = 5;
        double salePrice = 15.0;
        double discount = 10.0;

        // Insert sale
        String insertSQL = "INSERT INTO SALES (invoiceNumber, productId, quantity, salePrice, discount) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(insertSQL)) {
            stmt.setString(1, invoiceNumber);
            stmt.setString(2, productId);
            stmt.setInt(3, quantitySold);
            stmt.setDouble(4, salePrice);
            stmt.setDouble(5, discount);
            stmt.executeUpdate();
        }

        // Verify
        String verifySQL = "SELECT * FROM SALES WHERE invoiceNumber = ?";
        try (PreparedStatement stmt = conn.prepareStatement(verifySQL)) {
            stmt.setString(1, invoiceNumber);
            ResultSet rs = stmt.executeQuery();
            assertTrue("Sale record not inserted", rs.next());
            assertEquals("Incorrect quantity", quantitySold, rs.getInt("quantity"));
            assertEquals("Incorrect discount", discount, rs.getDouble("discount"), 0.001);
        }
    }
}
