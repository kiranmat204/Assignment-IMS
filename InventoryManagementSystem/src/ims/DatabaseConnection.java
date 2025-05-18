/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ims;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author ankur
 */
public class DatabaseConnection {

    private static final String DB_URL = "jdbc:derby://localhost:1527/inventoryDB;create=true";

    public static Connection getConnection() throws SQLException {
        try {
            // Load the Derby JDBC driver
            Class.forName("org.apache.derby.jdbc.ClientDriver");
            System.out.println("Derby driver loaded successfully");

            Connection conn = DriverManager.getConnection(DB_URL);
            System.out.println("Connected to database successfully");
            return conn;
        } catch (ClassNotFoundException e) {
            throw new SQLException("Derby JDBC Driver not found", e);
        }
    }
}