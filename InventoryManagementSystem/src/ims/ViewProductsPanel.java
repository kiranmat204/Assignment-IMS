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

        // Add "Total Amount" column
        String[] columnNames = {"Product ID", "Product", "Brand", "Category", "Quantity", "Price", "Supplier", "Total Amount"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // make table non-editable
            }
        };

        JTable table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);

        // Table styles
        Font headerFont = new Font("Arial", Font.BOLD, 20);
        table.getTableHeader().setFont(headerFont);

        Font cellFont = new Font("Arial", Font.PLAIN, 16);
        table.setFont(cellFont);
        table.setRowHeight(25);

        // Fetch products and compute total
        ProductDAO productDAO = new ProductDAO();
        List<Product> products = productDAO.getAllProducts();
        double grandTotal = 0;

        for (Product product : products) {
            double totalAmount = product.getQuantity() * product.getPrice();
            grandTotal += totalAmount;

            Object[] row = {
                product.getProductId(),
                product.getProductName(),
                product.getProductBrand(),
                product.getCategory(),
                product.getQuantity(),
                product.getPrice(),
                product.getSupplier(),
                String.format("%.2f", totalAmount)
            };
            model.addRow(row);
        }

        // Title label
        JLabel titleLabel = new JLabel("Stock Report", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        add(titleLabel, BorderLayout.NORTH);

        add(scrollPane, BorderLayout.CENTER);

        // Total amount label
        JLabel totalLabel = new JLabel("Total Inventory Value: $" + String.format("%.2f", grandTotal), SwingConstants.RIGHT);
        totalLabel.setFont(new Font("Arial", Font.BOLD, 18));
        totalLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 10));
        add(totalLabel, BorderLayout.SOUTH);
    }
}