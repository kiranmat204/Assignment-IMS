/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ims;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author kiranmat
 */
public class SaleProductForm extends JPanel {

    JComboBox<String> productIdBox;
    JTextField brandField, productNameField, categoryField, supplierField, currentPurchasePriceField;
    JTextField invoiceField, unitsSoldField, unitSalePriceField, discountField;
    JTextField stockQuantityField;
    JButton addToInvoiceBtn, submitButton;
    JTable invoiceTable;
    DefaultTableModel invoiceTableModel;

    // Holds all items before submitting invoice
    List<SaleItem> invoiceItems = new ArrayList<>();

    public SaleProductForm() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(BorderFactory.createEmptyBorder(10, 15, 15, 15));

        Font boldFont = new Font("Arial", Font.BOLD, 14);
        brandField = createReadOnlyField(boldFont);
        productNameField = createReadOnlyField(boldFont);
        categoryField = createReadOnlyField(boldFont);
        supplierField = createReadOnlyField(boldFont);
        currentPurchasePriceField = createReadOnlyField(boldFont);
        stockQuantityField = createReadOnlyField(boldFont);
        Font fieldFont = new Font("Arial", Font.PLAIN, 13);

        // === Product Selection Panel (Row 1) ===
        JPanel row1 = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        row1.add(createLabel("Product ID:", boldFont));

        productIdBox = new JComboBox<>();
        productIdBox.setPreferredSize(new Dimension(160, 25));
        row1.add(productIdBox);
        add(row1);

        // Populate product IDs
        IdDAO idDAO = new IdDAO();
        List<String> productIds = idDAO.getAllIds();
        productIdBox.addItem("Select product id");
        for (String id : productIds) {
            productIdBox.addItem(id);
        }
        productIdBox.setSelectedIndex(0);

        productIdBox.addActionListener(e -> {
            if (productIdBox.getSelectedIndex() > 0) {
                loadProductDetails();
            } else {
                clearProductFields();
            }
        });

        // === Product Details Panel (Grid 3x4) ===
        JPanel detailsPanel = new JPanel(new GridLayout(3, 4, 8, 8));

        brandField = createReadOnlyField(fieldFont);
        productNameField = createReadOnlyField(fieldFont);
        categoryField = createReadOnlyField(fieldFont);
        supplierField = createReadOnlyField(fieldFont);
        currentPurchasePriceField = createReadOnlyField(fieldFont);

        detailsPanel.add(createLabel("Brand:", boldFont));
        detailsPanel.add(brandField);
        detailsPanel.add(createLabel("Product Name:", boldFont));
        detailsPanel.add(productNameField);

        detailsPanel.add(createLabel("Category:", boldFont));
        detailsPanel.add(categoryField);
        detailsPanel.add(createLabel("Supplier:", boldFont));
        detailsPanel.add(supplierField);

        detailsPanel.add(createLabel("Purchase Price:", boldFont));
        detailsPanel.add(currentPurchasePriceField);

        detailsPanel.add(createLabel("Stock Quantity:", boldFont));
        detailsPanel.add(stockQuantityField);
        // empty cells to fill grid nicely
        detailsPanel.add(new JLabel());
        detailsPanel.add(new JLabel());

        add(detailsPanel);

        // === Sale Entry Panel (Grid 2x4) ===
        JPanel salePanel = new JPanel(new GridLayout(2, 4, 10, 5));

        invoiceField = createReadOnlyField(fieldFont);
        invoiceField.setText(generateInvoiceNumber());

        unitsSoldField = new JTextField();
        unitSalePriceField = new JTextField();
        discountField = new JTextField();

        salePanel.add(createLabel("Invoice No:", boldFont));
        salePanel.add(invoiceField);
        salePanel.add(createLabel("Units Sold:", boldFont));
        salePanel.add(unitsSoldField);

        salePanel.add(createLabel("Unit Sale Price:", boldFont));
        salePanel.add(unitSalePriceField);
        salePanel.add(createLabel("Discount %:", boldFont));
        salePanel.add(discountField);

        add(salePanel);

        // === Buttons ===
        addToInvoiceBtn = new JButton("Add to Invoice");
        styleButton(addToInvoiceBtn, boldFont);
        addToInvoiceBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(addToInvoiceBtn);

        addToInvoiceBtn.addActionListener(e -> addProductToInvoice());

        // === Invoice Items Table ===
        String[] columns = {"Product ID", "Quantity", "Sale Price", "Discount"};
        invoiceTableModel = new DefaultTableModel(columns, 0);
        invoiceTable = new JTable(invoiceTableModel);
        invoiceTable.setFillsViewportHeight(true);

        add(new JScrollPane(invoiceTable));

        // === Submit Button ===
        submitButton = new JButton("Submit Invoice");
        styleButton(submitButton, boldFont);
        submitButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(Box.createVerticalStrut(10));
        add(submitButton);

        submitButton.addActionListener(e -> submitInvoice());
    }

    private JLabel createLabel(String text, Font font) {
        JLabel label = new JLabel(text);
        label.setFont(font);
        return label;
    }

    private JTextField createReadOnlyField(Font font) {
        JTextField field = new JTextField();
        field.setEditable(false);
        field.setFont(font);
        return field;
    }

    private void loadProductDetails() {
        String productId = (String) productIdBox.getSelectedItem();
        if (productId == null || productId.trim().isEmpty()) {
            clearProductFields();
            return;
        }

        String query = "SELECT productBrand, productName, category, supplier, price, quantity FROM PRODUCT WHERE productId = ?";
        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, productId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                brandField.setText(rs.getString("productBrand"));
                productNameField.setText(rs.getString("productName"));
                categoryField.setText(rs.getString("category"));
                supplierField.setText(rs.getString("supplier"));
                currentPurchasePriceField.setText(String.format("%.2f", rs.getDouble("price")));
                stockQuantityField.setText(String.valueOf(rs.getInt("quantity")));

                unitSalePriceField.setText("");
                discountField.setText("");
            } else {
                clearProductFields();
                JOptionPane.showMessageDialog(this, "Product not found.", "Error", JOptionPane.ERROR_MESSAGE);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void clearProductFields() {
        brandField.setText("");
        productNameField.setText("");
        categoryField.setText("");
        supplierField.setText("");
        currentPurchasePriceField.setText("");
        unitSalePriceField.setText("");
        discountField.setText("");
        stockQuantityField.setText("");
    }

    private void addProductToInvoice() {
        if (productIdBox.getSelectedIndex() <= 0) {
            JOptionPane.showMessageDialog(this, "Please select a valid Product ID.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            int quantity = Integer.parseInt(unitsSoldField.getText());
            double salePrice = Double.parseDouble(unitSalePriceField.getText());
            double discount = discountField.getText().trim().isEmpty() ? 0.0 : Double.parseDouble(discountField.getText());

            if (discount < 0 || discount > 100) {
                JOptionPane.showMessageDialog(this, "Discount must be between 0 and 100", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            SaleItem item = new SaleItem((String) productIdBox.getSelectedItem(), quantity, salePrice, discount);
            invoiceItems.add(item);
            invoiceTableModel.addRow(item.toRow());

            // Clear fields for next entry
            unitsSoldField.setText("");
            unitSalePriceField.setText("");
            discountField.setText("");

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Please enter valid numbers for Units Sold, Sale Price, and Discount.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void submitInvoice() {
        if (invoiceItems.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No items to submit.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String invoiceNo = invoiceField.getText();

        try (Connection conn = DatabaseConnection.getConnection()) {
            conn.setAutoCommit(false);  // Begin transaction

            // 1. Insert sales items
            String sql = "INSERT INTO SALES (invoiceNumber, productId, quantity, salePrice, discount) VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                for (SaleItem item : invoiceItems) {
                    stmt.setString(1, invoiceNo);
                    stmt.setString(2, item.productId);
                    stmt.setInt(3, item.quantity);
                    stmt.setDouble(4, item.salePrice);
                    stmt.setDouble(5, item.discount);
                    stmt.addBatch();
                }
                stmt.executeBatch();
            }

            // 2. Update product stock quantities
            String updateStockSql = "UPDATE PRODUCT SET quantity = quantity - ? WHERE productId = ?";
            try (PreparedStatement updateStmt = conn.prepareStatement(updateStockSql)) {
                for (SaleItem item : invoiceItems) {
                    updateStmt.setInt(1, item.quantity);
                    updateStmt.setString(2, item.productId);
                    updateStmt.addBatch();
                }
                updateStmt.executeBatch();
            }

            // 3. Commit transaction
            conn.commit();

            JOptionPane.showMessageDialog(this, "Invoice submitted successfully!");

            // 4. Reset form
            invoiceItems.clear();
            invoiceTableModel.setRowCount(0);
            invoiceField.setText(generateInvoiceNumber());

            clearProductFields();
            productIdBox.setSelectedIndex(0);

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error submitting invoice.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private String generateInvoiceNumber() {
        String query = "SELECT MAX(CAST(SUBSTR(invoiceNumber, 5) AS INT)) FROM SALES";
        try (Connection conn = DatabaseConnection.getConnection(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(query)) {

            int next = 1;
            if (rs.next()) {
                int maxNum = rs.getInt(1);
                if (!rs.wasNull()) {
                    next = maxNum + 1;
                }
            }
            return String.format("INV-%03d", next);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return "INV-001";
    }

    private void styleButton(JButton button, Font font) {
        button.setFont(font);
        button.setPreferredSize(new Dimension(140, 30));
        button.setBackground(new Color(0, 102, 204));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setOpaque(true);
        button.setBorderPainted(false);
    }
}
