/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ims;



import java.sql.*;


/**
 *
 * @author ankur
 */
public class DatabaseConnection {

    private static final String DB_URL = "jdbc:derby:inventoryDB;create=true";

    public static Connection getConnection() throws SQLException {
        try {
            // Load the Derby JDBC driver
            Class.forName("org.apache.derby.jdbc.EmbeddedDriver");

            Connection conn = DriverManager.getConnection(DB_URL);
            
            

            return conn;
        } catch (ClassNotFoundException e) {
            throw new SQLException("Derby JDBC Driver not found", e);
        }
    }
}
