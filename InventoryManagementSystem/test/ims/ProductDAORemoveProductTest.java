/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package ims;

import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import java.sql.SQLException;
import java.sql.Connection;

/**
 *
 * @author ankur
 */
public class ProductDAORemoveProductTest {

    private static Connection conn;
    private ProductDAO productDAO;

    @BeforeClass
    public static void setUpBeforeClass() throws SQLException {
        // Initialize the connection before running the tests
        conn = DatabaseConnection.getConnection();
    }

    @Before
    public void setUp() throws SQLException {
        // Disable auto-commit to manage transactions manually
        conn.setAutoCommit(false); // Start a transaction for each test case
        productDAO = new ProductDAO();
    }

    @Test
    public void testRemoveProduct() {
        // Use a unique Product ID to prevent conflicts
        String uniqueProductId = "TEST" + System.currentTimeMillis(); // Unique ID for each test run

        // Create a product to add and then remove
        Product product = new Product(uniqueProductId, "Test Supplier", "Test Category", "Test Brand",
                "Test Product", 100, 50.0);
        
        // Add the product to the database
        productDAO.addProduct(product);

        // Now remove the product
        boolean removeResult = productDAO.removeProduct(uniqueProductId);

        // Assert that the product was removed successfully
        assertTrue("Product should be removed successfully", removeResult);

        // Try to retrieve the product and verify that it has been removed
        Product removedProduct = productDAO.getProductById(uniqueProductId);
        assertNull("The product should be null after removal", removedProduct);
    }

    @After
    public void tearDown() throws SQLException {
        // Rollback the transaction to discard any changes made during the test
        if (conn != null) {
            conn.rollback(); // This will discard any test data changes
            conn.setAutoCommit(true); // Reset auto-commit to its default state
        }
    }

    @AfterClass
    public static void tearDownAfterClass() throws SQLException {
        if (conn != null) {
            conn.close(); // Close the connection after all tests
        }
    }
}