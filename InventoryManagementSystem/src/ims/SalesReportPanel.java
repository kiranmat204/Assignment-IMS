/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ims;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

/**
 *
 * @author ankur
 */
public class SalesReportPanel extends JPanel {

    private JTable table;
    private DefaultTableModel model;
    private JLabel totalLabel;

    public SalesReportPanel() {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Define table columns
        String[] columnNames = {
            "Invoice Number", "Product ID", "Product Name",
            "Quantity", "Sale Price", "Discount (%)", "Total Price"
        };

        model = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);

        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 16));
        table.setFont(new Font("Arial", Font.PLAIN, 14));
        table.setRowHeight(22);

        add(scrollPane, BorderLayout.CENTER);

        JLabel titleLabel = new JLabel("Sales Report", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        add(titleLabel, BorderLayout.NORTH);

        // Button Panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton editButton = new JButton("Edit Selected Invoice");
        editButton.setFont(new Font("Arial", Font.BOLD, 16));
        editButton.addActionListener(e -> editSelectedInvoice());
        buttonPanel.add(editButton);
        add(buttonPanel, BorderLayout.BEFORE_FIRST_LINE);

        // Total Label
        totalLabel = new JLabel("Total Sales Amount: $0.00", SwingConstants.RIGHT);
        totalLabel.setFont(new Font("Arial", Font.BOLD, 18));
        add(totalLabel, BorderLayout.SOUTH);

        loadSalesData();
    }

    private void loadSalesData() {
        model.setRowCount(0);  // Clear previous data
        SalesDAO salesDAO = new SalesDAO();
        List<SaleRecord> sales = salesDAO.getAllSales();

        double grandTotal = 0;
        for (SaleRecord sale : sales) {
            double totalPrice = (sale.getSalePrice() - sale.getDiscount()) * sale.getQuantity();
            grandTotal += totalPrice;
            
             //System.out.println("Product Name: " + sale.getProductName());


            Object[] row = {
                sale.getInvoiceNumber(),
                sale.getProductId(),
                sale.getProductName(),
                sale.getQuantity(),
                String.format("%.2f", sale.getSalePrice()),
                String.format("%.2f", sale.getDiscount()),
                String.format("%.2f", totalPrice)
            };
            model.addRow(row);
        }

        totalLabel.setText("Total Sales Amount: $" + String.format("%.2f", grandTotal));
    }

    private void editSelectedInvoice() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a row to edit.");
            return;
        }

        String invoiceNumber = table.getValueAt(selectedRow, 0).toString();
        String productId = table.getValueAt(selectedRow, 1).toString();
        String productName = table.getValueAt(selectedRow, 2).toString();
        int quantity = Integer.parseInt(table.getValueAt(selectedRow, 3).toString());
        double salePrice = Double.parseDouble(table.getValueAt(selectedRow, 4).toString());
        double discount = Double.parseDouble(table.getValueAt(selectedRow, 5).toString());

        SaleRecord currentRecord = new SaleRecord(invoiceNumber, productId, productName, quantity, salePrice, discount);
        EditSaleDialog dialog = new EditSaleDialog(currentRecord);
        dialog.setVisible(true);

        if (dialog.isUpdated()) {
            SaleRecord updatedRecord = dialog.getUpdatedSale();
            SalesDAO dao = new SalesDAO();
            dao.updateSale(updatedRecord);
            loadSalesData();
        }
    }

}
