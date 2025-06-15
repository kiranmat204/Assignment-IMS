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
public class ProductDAOUpdateProductTest {

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
    public void testUpdateProductQuantity() {
        // Use a unique Product ID to prevent conflicts
        String uniqueProductId = "TEST" + System.currentTimeMillis(); // Unique ID for each test run

        // Add a product
        Product product = new Product(uniqueProductId, "Test Supplier", "Test Category", "Test Brand",
                "Test Product", 100, 50.0);
        boolean addResult = productDAO.addProduct(product);

        // Assert that the product was added successfully
        assertTrue("Product should be added successfully", addResult);

        // Update the product quantity
        boolean updateResult = productDAO.updateProductQuantity(uniqueProductId, 200);

        // Assert that the quantity was updated successfully
        assertTrue("Product quantity should be updated successfully", updateResult);

        // Retrieve the product and verify the updated quantity
        Product updatedProduct = productDAO.getProductById(uniqueProductId);
        assertEquals("Product quantity should be updated to 200", 200, updatedProduct.getQuantity());
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