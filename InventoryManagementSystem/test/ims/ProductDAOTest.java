/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package ims;

import org.junit.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import static org.junit.Assert.*;

/**
 *
 * @author ankur
 */
public class ProductDAOTest {

    private static Connection conn;
    private ProductDAO productDAO;

    @BeforeClass
    public static void setUpBeforeClass() throws SQLException {
        // Set up the connection to the database for the test
        conn = DatabaseConnection.getConnection();
    }

    @Before
    public void setUp() {
        // Set up before each test, create ProductDAO object
        productDAO = new ProductDAO();
    }

    @After
    public void tearDown() {
        // Clean up the database to remove the test data
        try {
            // Clear data that was inserted during the test to maintain test isolation
            String deleteSQL = "DELETE FROM Product WHERE productId LIKE 'TEST%'";
            try (PreparedStatement stmt = conn.prepareStatement(deleteSQL)) {
                stmt.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
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

    @Test
    public void testUpdateProductQuantity() {
        // Add a product first
        Product product = new Product("TEST123", "Test Supplier", "Test Category", "Test Brand",
                "Test Product", 100, 50.0);
        productDAO.addProduct(product);

        // Update the product quantity
        boolean result = productDAO.updateProductQuantity("TEST123", 200);

        // Assert that the quantity was updated successfully
        assertTrue("Product quantity should be updated successfully", result);

        // Retrieve the product and verify the updated quantity
        Product updatedProduct = productDAO.getProductById("TEST123");
        assertEquals("Product quantity should be updated to 200", 200, updatedProduct.getQuantity());
    }

    @Test
    public void testUpdateProductPrice() {
        // Add a product first
        Product product = new Product("TEST123", "Test Supplier", "Test Category", "Test Brand",
                "Test Product", 100, 50.0);
        productDAO.addProduct(product);

        // Update the product price
        boolean result = productDAO.updateProductPrice("TEST123", 60.0);

        // Assert that the price was updated successfully
        assertTrue("Product price should be updated successfully", result);

        // Retrieve the product and verify the updated price
        Product updatedProduct = productDAO.getProductById("TEST123");
        assertEquals(60.0, updatedProduct.getPrice(), 0.001);
    }

    @Test
    public void testRemoveProduct() {
        // Add a product first
        Product product = new Product("TEST123", "Test Supplier", "Test Category", "Test Brand",
                "Test Product", 100, 50.0);
        productDAO.addProduct(product);

        // Remove the product from the database
        boolean result = productDAO.removeProduct("TEST123");

        // Assert that the product was removed successfully
        assertTrue("Product should be removed successfully", result);

        // Verify that the product no longer exists in the database
        Product removedProduct = productDAO.getProductById("TEST123");
        assertNull("Product should be null after removal", removedProduct);
    }

    @Test
    public void testGetAllProducts() {
        // Add a few products first
        productDAO.addProduct(new Product("TEST127", "Supplier 1", "Category 1", "Brand 1", "Product 1", 50, 25.0));
        productDAO.addProduct(new Product("TEST128", "Supplier 2", "Category 2", "Brand 2", "Product 2", 30, 30.0));

        // Get all products
        List<Product> allProducts = productDAO.getAllProducts();

        // Assert that we retrieved at least two products
        assertTrue("There should be at least 2 products in the database", allProducts.size() >= 2);

        // Verify that the added products are present
        boolean foundProduct1 = false;
        boolean foundProduct2 = false;
        for (Product p : allProducts) {
            if (p.getProductId().equals("TEST127")) {
                foundProduct1 = true;
            }
            if (p.getProductId().equals("TEST128")) {
                foundProduct2 = true;
            }
        }

        assertTrue("Product with ID TEST127 should be in the list", foundProduct1);
        assertTrue("Product with ID TEST128 should be in the list", foundProduct2);
    }

    @Test
    public void testGetAllProductIds() {
        // Add some products first
        productDAO.addProduct(new Product("TEST129", "Supplier 1", "Category 1", "Brand 1", "Product 1", 50, 25.0));
        productDAO.addProduct(new Product("TEST130", "Supplier 2", "Category 2", "Brand 2", "Product 2", 30, 30.0));

        // Get all product IDs
        List<String> productIds = productDAO.getAllProductIds();

        // Assert that we retrieved the correct product IDs
        assertTrue("Product ID TEST129 should be in the list", productIds.contains("TEST129"));
        assertTrue("Product ID TEST130 should be in the list", productIds.contains("TEST130"));
    }
}
