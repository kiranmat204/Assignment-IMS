/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ims;
import java.sql.Connection;
import java.sql.Statement;

/**
 *
 * @author kiranmat
 */
public class DatabaseTableSetup {
    public void initialiseTables(){
        createSupplierTable();
        createBrandTable();
        createCategoryTable();
        
        insertBaseSupplier();
        insertBaseBrand();
        insertBaseCategory();
    }
    
    private void createSupplierTable(){
        String createSupplierTable = "CREATE TABLE Supplier ("
                + "supplierId VARCHAR(20) PRIMARY KEY,"
                + "supplierName VARCHAR(50))";
        
        executeDB(createSupplierTable);
    }
    
    private void createBrandTable(){
        String createBrandTable = "CREATE TABLE Brand ("
                + "brandId VARCHAR(20) PRIMARY KEY,"
                + "brandName VARCHAR(50))";
        
        executeDB(createBrandTable);
    }
    
    private void createCategoryTable(){
        String createCategoryTable = "CREATE TABLE Category ("
                + "categoryId VARCHAR(20) PRIMARY KEY,"
                + "categoryName VARCHAR(50))";
        
        executeDB(createCategoryTable);
    }
    
    private void insertBaseSupplier(){
        String insertSupplierTable = "INSERT INTO Supplier VALUES ('1', 'Apple America'),"
                + "('2', 'Samsung South Korea'),"
                + "('3', 'Xiaomi China'),"
                + "('4', 'Third Party LTD'),"
                + "('5', 'Computer Lover'),"
                + "('6', 'StoneAge Tech'),"
                + "('7', 'Iceburg Electronics')";
        
        executeDB(insertSupplierTable);
    }
    
    private void insertBaseBrand(){
        String insertBrandTable = "INSERT INTO Brand VALUES ('1', 'Apple'),"
                + "('2','Samsung'),"
                + "('3','Xiaomi'),"
                + "('4','LG'),"
                + "('5','Nvdia'),"
                + "('6','Panasonic'),"
                + "('7','Dell'),"
                + "('8','HP'),"
                + "('9','Huawei'),"
                + "('10','Sony')";
        
        executeDB(insertBrandTable);
    }
    
    private void insertBaseCategory(){
        String insertCategoryTable = "INSERT INTO Category VALUES ('1','Smartphone'),"
                + "('2','Television'),"
                + "('3','Smartwatch'),"
                + "('4','Graphics Card'),"
                + "('5','Tablet'),"
                + "('6','Laptop'),"
                + "('7','Smart TV'),"
                + "('8','Console')";
        
        executeDB(insertCategoryTable);
    }
    
    private void executeDB(String query){
        try(Connection conn = DatabaseConnection.getConnection();
            Statement statement = conn.createStatement()){
            statement.execute(query);
        }
        catch(Exception ex){
            System.out.println(ex);
        }
    }
}
