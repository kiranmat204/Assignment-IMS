/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package ims;

import org.junit.*;
import java.sql.*;
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
    public static void setUpDatabase() throws SQLException {
        conn = DatabaseConnection.getConnection();

        try (Statement stmt = conn.createStatement()) {
            String createTableSQL = "CREATE TABLE Product (" +
                    "productId VARCHAR(255) PRIMARY KEY, " +
                    "productName VARCHAR(255), " +
                    "quantity INT, " +
                    "productBrand VARCHAR(255), " +
                    "price DOUBLE, " +
                    "category VARCHAR(255), " +
                    "supplier VARCHAR(255), " +
                    "sale DOUBLE, " +
                    "retailPrice DOUBLE, " +
                    "salePrice DOUBLE)";
            stmt.execute(createTableSQL);
        } catch (SQLException e) {
            if (!e.getSQLState().equals("X0Y32")) {
                throw e;
            }
        }
    }

    @Before
    public void setUp() {
        productDAO = new ProductDAO();
    }

    @After
    public void cleanUp() throws SQLException {
        try (Statement stmt = conn.createStatement()) {
            stmt.execute("DELETE FROM Product");
        }
    }

    @AfterClass
    public static void tearDown() throws SQLException {
        try (Statement stmt = conn.createStatement()) {
            stmt.execute("DROP TABLE Product");
        }
        conn.close();
    }

    @Test
    public void testAddProduct() throws SQLException {
        Product testProduct = new Product("P123", "Supplier A", "Category A", "Brand A", "Product A", 10, 100.0);
        boolean result = productDAO.addProduct(testProduct);
        assertTrue(result);

        String query = "SELECT * FROM Product WHERE productId = 'P123'";
        try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
            assertTrue(rs.next());
            assertEquals("Product A", rs.getString("productName"));
            assertEquals(10, rs.getInt("quantity"));
            assertEquals(100.0, rs.getDouble("price"), 0.01);
        }
    }

    @Test
    public void testGetProductById() throws SQLException {
        Product testProduct = new Product("P123", "Supplier A", "Category A", "Brand A", "Product A", 10, 100.0);
        productDAO.addProduct(testProduct);

        Product product = productDAO.getProductById("P123");
        assertNotNull(product);
        assertEquals("P123", product.getProductId());
        assertEquals("Product A", product.getProductName());
        assertEquals(10, product.getQuantity());
    }

    @Test
    public void testUpdateProductQuantity() throws SQLException {
        Product testProduct = new Product("P123", "Supplier A", "Category A", "Brand A", "Product A", 10, 100.0);
        productDAO.addProduct(testProduct);

        boolean result = productDAO.updateProductQuantity("P123", 20);
        assertTrue(result);

        try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery("SELECT quantity FROM Product WHERE productId = 'P123'")) {
            assertTrue(rs.next());
            assertEquals(20, rs.getInt("quantity"));
        }
    }

    @Test
    public void testUpdateProductPrice() throws SQLException {
        Product testProduct = new Product("P123", "Supplier A", "Category A", "Brand A", "Product A", 10, 100.0);
        productDAO.addProduct(testProduct);

        boolean result = productDAO.updateProductPrice("P123", 150.0);
        assertTrue(result);

        try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery("SELECT price FROM Product WHERE productId = 'P123'")) {
            assertTrue(rs.next());
            assertEquals(150.0, rs.getDouble("price"), 0.01);
        }
    }

    @Test
    public void testRemoveProduct() throws SQLException {
        Product testProduct = new Product("P123", "Supplier A", "Category A", "Brand A", "Product A", 10, 100.0);
        productDAO.addProduct(testProduct);

        boolean result = productDAO.removeProduct("P123");
        assertTrue(result);

        try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery("SELECT * FROM Product WHERE productId = 'P123'")) {
            assertFalse(rs.next());
        }
    }

    @Test
    public void testGetAllProducts() throws SQLException {
        productDAO.addProduct(new Product("P123", "Supplier A", "Category A", "Brand A", "Product A", 10, 100.0));
        productDAO.addProduct(new Product("P124", "Supplier B", "Category B", "Brand B", "Product B", 5, 150.0));

        List<Product> products = productDAO.getAllProducts();
        assertEquals(2, products.size());
    }

    @Test
    public void testGetAllProductIds() throws SQLException {
        productDAO.addProduct(new Product("P123", "Supplier A", "Category A", "Brand A", "Product A", 10, 100.0));
        productDAO.addProduct(new Product("P124", "Supplier B", "Category B", "Brand B", "Product B", 5, 150.0));

        List<String> ids = productDAO.getAllProductIds();
        assertEquals(2, ids.size());
        assertTrue(ids.contains("P123"));
        assertTrue(ids.contains("P124"));
    }
}
