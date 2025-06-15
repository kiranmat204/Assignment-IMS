/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ims;

import javax.swing.*;
import java.awt.*;

/**
 *
 * @author ankur
 */
public class EditSaleDialog extends JDialog {

    private boolean updated = false;
    private JTextField quantityField, priceField, discountField;
    private SaleRecord updatedSale;
    private final SaleRecord original;

    /**
     * Constructor to initialize the Edit Sale Dialog with the original
     * SaleRecord.
     *
     * @param sale The original sale record to edit
     */
    public EditSaleDialog(SaleRecord sale) {
        this.original = sale;

        // Set dialog properties
        setTitle("Edit Invoice: " + sale.getInvoiceNumber());
        setModal(true);
        setSize(350, 300);
        setLocationRelativeTo(null);
        setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));  // Using FlowLayout for flexibility

        // Quantity input
        add(new JLabel("Quantity:"));
        quantityField = new JTextField(String.valueOf(sale.getQuantity()), 10);
        quantityField.setPreferredSize(new Dimension(100, 25));
        add(quantityField);

        // Sale Price input
        add(new JLabel("Sale Price:"));
        priceField = new JTextField(String.valueOf(sale.getSalePrice()), 10);
        priceField.setPreferredSize(new Dimension(100, 25));
        add(priceField);

        // Discount input
        add(new JLabel("Discount:"));
        discountField = new JTextField(String.valueOf(sale.getDiscount()), 10);
        discountField.setPreferredSize(new Dimension(100, 25));
        add(discountField);

        // Save Button - ActionListener to save the changes
        JButton saveButton = new JButton("Save");
        saveButton.addActionListener(e -> {
            try {
                // Parse inputs for quantity, price, and discount
                int qty = Integer.parseInt(quantityField.getText().trim());
                double price = Double.parseDouble(priceField.getText().trim());
                double discount = Double.parseDouble(discountField.getText().trim());

                // Validate input values
                if (qty <= 0 || price < 0 || discount < 0) {
                    JOptionPane.showMessageDialog(this, "Invalid input values.");
                    return;
                }

                // Create updated SaleRecord
                updatedSale = new SaleRecord(
                        original.getInvoiceNumber(),
                        original.getProductId(),
                        original.getProductName(),
                        qty,
                        price,
                        discount
                );

                updated = true;  // Set the updated flag to true
                dispose();  // Close the dialog after saving
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Please enter valid numbers.");
            }
        });
        add(saveButton);

    }

    public boolean isUpdated() {
        return updated;
    }

    public SaleRecord getUpdatedSale() {
        return updatedSale;
    }
}
