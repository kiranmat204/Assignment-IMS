/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ims;

/**
 *
 * @author ankur
 */

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class ViewProductsPanel extends JPanel {

    public ViewProductsPanel() {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        String[] columnNames = {"Product ID", "Name", "Brand", "Category", "Quantity", "Price", "Supplier"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);
        JTable table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);

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

        add(new JLabel("Product List", SwingConstants.CENTER), BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
    }
}