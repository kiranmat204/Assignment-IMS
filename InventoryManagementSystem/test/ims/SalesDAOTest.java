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
public class SalesDAOTest {

    {

    private static Connection conn;
    private SalesDAO salesDAO;

    @BeforeClass
    public static void setUpDatabase() throws SQLException {
        conn = DatabaseConnection.getConnection();

        // Create necessary tables for the test
        try (Statement stmt = conn.createStatement()) {
            String createProductTableSQL = "CREATE TABLE Product (" +
                    "productId VARCHAR(255) PRIMARY KEY, " +
                    "productName VARCHAR(255), " +
                    "quantity INT, " +
                    "price DOUBLE)";
            stmt.execute(createProductTableSQL);

            String createSalesTableSQL = "CREATE TABLE Sales (" +
                    "invoiceNumber VARCHAR(255), " +
                    "productId VARCHAR(255), " +
                    "quantity INT, " +
                    "salePrice DOUBLE, " +
                    "discount DOUBLE, " +
                    "PRIMARY KEY (invoiceNumber, productId))";
            stmt.execute(createSalesTableSQL);
        } catch (SQLException e) {
            // Ignore if the tables already exist
            if (!e.getSQLState().equals("X0Y32")) {
                throw e;
            }
        }
    }

    @Before
    public void setUp() {
        salesDAO = new SalesDAO();
    }

    @After
    public void cleanUp() throws SQLException {
        try (Statement stmt = conn.createStatement()) {
            stmt.execute("DELETE FROM Sales");
            stmt.execute("DELETE FROM Product");
        }
    }

    @AfterClass
    public static void tearDown() throws SQLException {
        try (Statement stmt = conn.createStatement()) {
            stmt.execute("DROP TABLE Sales");
            stmt.execute("DROP TABLE Product");
        }
        conn.close();
    }

    @Test
    public void testUpdateSale() throws SQLException {
        // Prepare sample data
        Product testProduct = new Product("P123", "Product A", 100, 150.0);
        SalesDAO salesDAO = new SalesDAO();
        SaleRecord sale = new SaleRecord("INV123", "P123", "Product A", 5, 200.0, 10.0);

        // Insert product into Product table
        String insertProductSQL = "INSERT INTO Product (productId, productName, quantity, price) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(insertProductSQL)) {
            stmt.setString(1, testProduct.getProductId());
            stmt.setString(2, testProduct.getProductName());
            stmt.setInt(3, testProduct.getQuantity());
            stmt.setDouble(4, testProduct.getPrice());
            stmt.executeUpdate();
        }

        // Insert sale into Sales table
        String insertSaleSQL = "INSERT INTO Sales (invoiceNumber, productId, quantity, salePrice, discount) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(insertSaleSQL)) {
            stmt.setString(1, sale.getInvoiceNumber());
            stmt.setString(2, sale.getProductId());
            stmt.setInt(3, sale.getQuantity());
            stmt.setDouble(4, sale.getSalePrice());
            stmt.setDouble(5, sale.getDiscount());
            stmt.executeUpdate();
        }

        // Now, update the sale
        sale.setQuantity(10);
        sale.setSalePrice(180.0);
        sale.setDiscount(5.0);

        salesDAO.updateSale(sale);

        // Verify that the sale was updated in the database
        String query = "SELECT * FROM Sales WHERE invoiceNumber = 'INV123' AND productId = 'P123'";
        try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
            assertTrue(rs.next());
            assertEquals(10, rs.getInt("quantity"));
            assertEquals(180.0, rs.getDouble("salePrice"), 0.01);
            assertEquals(5.0, rs.getDouble("discount"), 0.01);
        }
    }

    @Test
    public void testGetAllSales() throws SQLException {
        // Insert sample sales data
        Product testProduct1 = new Product("P123", "Product A", 100, 150.0);
        Product testProduct2 = new Product("P124", "Product B", 200, 250.0);

        String insertProductSQL = "INSERT INTO Product (productId, productName, quantity, price) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(insertProductSQL)) {
            stmt.setString(1, testProduct1.getProductId());
            stmt.setString(2, testProduct1.getProductName());
            stmt.setInt(3, testProduct1.getQuantity());
            stmt.setDouble(4, testProduct1.getPrice());
            stmt.executeUpdate();

            stmt.setString(1, testProduct2.getProductId());
            stmt.setString(2, testProduct2.getProductName());
            stmt.setInt(3, testProduct2.getQuantity());
            stmt.setDouble(4, testProduct2.getPrice());
            stmt.executeUpdate();
        }

        SaleRecord sale1 = new SaleRecord("INV123", "P123", "Product A", 10, 180.0, 5.0);
        SaleRecord sale2 = new SaleRecord("INV124", "P124", "Product B", 15, 230.0, 10.0);

        String insertSaleSQL = "INSERT INTO Sales (invoiceNumber, productId, quantity, salePrice, discount) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(insertSaleSQL)) {
            stmt.setString(1, sale1.getInvoiceNumber());
            stmt.setString(2, sale1.getProductId());
            stmt.setInt(3, sale1.getQuantity());
            stmt.setDouble(4, sale1.getSalePrice());
            stmt.setDouble(5, sale1.getDiscount());
            stmt.executeUpdate();

            stmt.setString(1, sale2.getInvoiceNumber());
            stmt.setString(2, sale2.getProductId());
            stmt.setInt(3, sale2.getQuantity());
            stmt.setDouble(4, sale2.getSalePrice());
            stmt.setDouble(5, sale2.getDiscount());
            stmt.executeUpdate();
        }

        // Act: Retrieve all sales
        List<SaleRecord> sales = salesDAO.getAllSales();

        // Assert: There should be two sales records
        assertNotNull(sales);
        assertEquals(2, sales.size());
        assertTrue(sales.stream().anyMatch(s -> s.getInvoiceNumber().equals("INV123")));
        assertTrue(sales.stream().anyMatch(s -> s.getInvoiceNumber().equals("INV124")));
    }

}
