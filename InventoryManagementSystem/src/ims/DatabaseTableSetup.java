/*
     * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
     * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ims;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.*;

/**
 *
 * @author kiranmat
 */
public class DatabaseTableSetup {

    public void initialiseTables() {
        //dropTables();              // Drop if exists
        createProductTable();
        createSupplierTable();     // Create fresh tables
        createBrandTable();
        createCategoryTable();
        createItemTable();
        createSalesTable();
        updateSalesTable();
        insertBaseSupplier();      // Insert default data
        insertBaseBrand();
        insertBaseCategory();
        insertBaseItems();

    }

//    private void dropTables() {
//        dropTableIfExists("ITEMS");
//        dropTableIfExists("CATEGORIES");
//        dropTableIfExists("BRANDS");
//        dropTableIfExists("SUPPLIER");
//    }
    private boolean tableExists(String tableName) {
        try (Connection conn = DatabaseConnection.getConnection(); ResultSet rs = conn.getMetaData().getTables(null, null, tableName.toUpperCase(), null)) {
            return rs.next();
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    private boolean tablePopulated(String tableName) {
        String query = "SELECT 1 FROM " + tableName + " FETCH FIRST 1 ROWS ONLY";
        try (Connection conn = DatabaseConnection.getConnection(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
            return rs.next();
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

//    private void dropTableIfExists(String tableName) {
//        if (tableExists(tableName)) {
//            try (Connection conn = DatabaseConnection.getConnection(); Statement stmt = conn.createStatement()) {
//                stmt.execute("DROP TABLE " + tableName);
//                System.out.println("Dropped table " + tableName);
//            } catch (Exception ex) {
//                System.out.println("Failed to drop table " + tableName);
//                ex.printStackTrace();
//            }
//        } else {
//            System.out.println("Table " + tableName + " does not exist, skipping drop.");
//        }
//    }
    private void createProductTable() {
        if (tableExists("PRODUCT")) {
            return;
        }
        String query = "CREATE TABLE PRODUCT (" +
                "productId VARCHAR(20) PRIMARY KEY," +
                "productName VARCHAR(50)," +
                "quantity INT NOT NULL," +
                "productBrand VARCHAR(50)," +
                "price DECIMAL(10,2)," +
                "category VARCHAR(50)," +
                "supplier VARCHAR(50)," +
                "retailPrice DECIMAL(10,2)," +
                "sale DECIMAL(5,2)," +
                "salePrice DECIMAL(10,2))";

        executeDB(query);
    }

    private void createSupplierTable() {
        if (tableExists("Supplier")) {
            return;
        }
        String query = "CREATE TABLE SUPPLIER (" +
                "SUPPLIERID INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY," +
                "SUPPLIERNAME VARCHAR(50))";
        executeDB(query);
    }

    private void createBrandTable() {
        if (tableExists("Brands")) {
            return;
        }
        String query = "CREATE TABLE BRANDS (" +
                "BRANDID VARCHAR(20) PRIMARY KEY," +
                "BRAND_NAME VARCHAR(50))";
        executeDB(query);
    }

    private void createCategoryTable() {
        if (tableExists("Categories")) {
            return;
        }
        String query = "CREATE TABLE CATEGORIES (" +
                "CATEGORYID VARCHAR(20) PRIMARY KEY," +
                "CATEGORY_NAME VARCHAR(50))";
        executeDB(query);
    }

    private void createItemTable() {
        if (tableExists("Items")) {
            return;
        }
        String query = "CREATE TABLE ITEMS (" +
                "ITEMID VARCHAR(20) PRIMARY KEY," +
                "ITEMNAME VARCHAR(100))";
        executeDB(query);
    }

    private void insertBaseSupplier() {
        if (tablePopulated("Supplier")) {
            return;
        }
        String query = "INSERT INTO SUPPLIER (SUPPLIERNAME) VALUES " +
                "('Apple America')," +
                "('Samsung South Korea')," +
                "('Xiaomi China')," +
                "('Third Party LTD')," +
                "('Computer Lover')," +
                "('StoneAge Tech')," +
                "('Iceburg Electronics')";
        executeDB(query);
    }

    private void insertBaseBrand() {
        if (tablePopulated("Brands")) {
            return;
        }
        String query = "INSERT INTO BRANDS VALUES " +
                "('1', 'Apple')," +
                "('2', 'Samsung')," +
                "('3', 'Xiaomi')," +
                "('4', 'LG')," +
                "('5', 'Nvdia')," +
                "('6', 'Panasonic')," +
                "('7', 'Dell')," +
                "('8', 'HP')," +
                "('9', 'Huawei')," +
                "('10', 'Sony')";
        executeDB(query);
    }

    private void insertBaseCategory() {
        if (tablePopulated("Categories")) {
            return;
        }
        String query = "INSERT INTO CATEGORIES VALUES " +
                "('1','Smartphone')," +
                "('2','Television')," +
                "('3','Smartwatch')," +
                "('4','Graphics Card')," +
                "('5','Tablet')," +
                "('6','Laptop')," +
                "('7','Smart TV')," +
                "('8','Console')";
        executeDB(query);
    }

    private void insertBaseItems() {
        if (tablePopulated("Items")) {
            return;
        }
        String query = "INSERT INTO ITEMS VALUES " +
                "('I1', 'Iphone')," +
                "('I2', 'Galaxy S23')," +
                "('I3', 'Mi Watch')," +
                "('I4', 'RTX 4090')," +
                "('I5', 'Sony Bravia')";
        executeDB(query);
    }

    private void executeDB(String query) {
        try (Connection conn = DatabaseConnection.getConnection(); Statement stmt = conn.createStatement()) {
            stmt.execute(query);
        } catch (Exception ex) {
            System.out.println("Error executing: " + query);
            ex.printStackTrace();
        }
    }

    private void createSalesTable() {
        if (tableExists("SALES")) {
            return;
        }

        String query = "CREATE TABLE SALES (" +
                "invoiceNumber VARCHAR(20)," +
                "productId VARCHAR(20)," +
                "quantity INT," +
                "salePrice DOUBLE," +
                "discount DOUBLE," +
                "PRIMARY KEY (invoiceNumber, productId))";

        executeDB(query);
    }

    private void updateSalesTable() {
        try (Connection conn = DatabaseConnection.getConnection(); Statement stmt = conn.createStatement()) {

            DatabaseMetaData metaData = conn.getMetaData();
            ResultSet rs = metaData.getColumns(null, null, "SALES", null);

            boolean salePriceExists = false;
            boolean discountExists = false;

            while (rs.next()) {
                String columnName = rs.getString("COLUMN_NAME");
                if ("SALEPRICE".equalsIgnoreCase(columnName)) {
                    salePriceExists = true;
                }
                if ("DISCOUNT".equalsIgnoreCase(columnName)) {
                    discountExists = true;
                }
            }
            rs.close();

            if (!salePriceExists) {
                String alterSalePrice = "ALTER TABLE SALES ADD COLUMN salePrice DOUBLE";
                stmt.executeUpdate(alterSalePrice);
                System.out.println("Added column 'salePrice' to SALES table.");
            }

            if (!discountExists) {
                String alterDiscount = "ALTER TABLE SALES ADD COLUMN discount DOUBLE";
                stmt.executeUpdate(alterDiscount);
                System.out.println("Added column 'discount' to SALES table.");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
