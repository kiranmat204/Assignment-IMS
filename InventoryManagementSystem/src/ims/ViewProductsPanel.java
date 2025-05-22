/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ims;

/**
 *
 * @author ankur
 */


//import ims.core.Product;
//import ims.core.ProductDAO;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class ViewProductsPanel {
    private JFrame frame;

    public ViewProductsPanel() {
        frame = new JFrame("View Products");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(800, 400);
        frame.setLocationRelativeTo(null);

        String[] columnNames = {"Product ID", "Name", "Brand", "Category", "Quantity", "Price", "Supplier"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);
        JTable table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);
        frame.add(scrollPane, BorderLayout.CENTER);

        // Fetch products from database
        ProductDAO productDAO = new ProductDAO();
        List<Product> products = productDAO.getAllProducts();

        for (Product product : products) {
            Object[] row = {
                product.getProductId(),
                product.getProductName(),
                product.getProductBrand(),  
                product.getCategory(),
                product.getQuantity(),
                product.getPrice(),
                product.getSupplier()
            };
            model.addRow(row);
        }

        frame.setVisible(true);
    }
}
