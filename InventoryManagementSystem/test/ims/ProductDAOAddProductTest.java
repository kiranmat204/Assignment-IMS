/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package ims;

import org.junit.*;
import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.SQLException;

/**
 *
 * @author ankur
 */
public class ProductDAOAddProductTest {

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
        conn.setAutoCommit(false); // Start a transaction
        productDAO = new ProductDAO();
    }

    @Test
    public void testAddProduct() {
        // Create a Product object for testing
        Product product = new Product("TEST123", "Test Supplier", "Test Category", "Test Brand",
                "Test Product", 100, 50.0);

        // Add the product to the database
        boolean result = productDAO.addProduct(product);

        // Assert that the product was added successfully
        assertTrue("Product should be added successfully", result);

        // Verify that the product exists in the database
        Product retrievedProduct = productDAO.getProductById("TEST123");
        assertNotNull("Product should exist in the database", retrievedProduct);
        assertEquals("Product ID should match", "TEST123", retrievedProduct.getProductId());
    }

    @After
    public void tearDown() throws SQLException {
        // Rollback the transaction after each test to discard changes made during the test
        conn.rollback();
        conn.setAutoCommit(true); // Reset auto-commit
    }
}