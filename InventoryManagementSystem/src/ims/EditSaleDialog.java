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

    public EditSaleDialog(SaleRecord sale) {
        this.original = sale;

        setTitle("Edit Invoice: " + sale.getInvoiceNumber());
        setModal(true);
        setSize(300, 250);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(5, 2, 10, 10));

        add(new JLabel("Quantity:"));
        quantityField = new JTextField(String.valueOf(sale.getQuantity()));
        add(quantityField);

        add(new JLabel("Sale Price:"));
        priceField = new JTextField(String.valueOf(sale.getSalePrice()));
        add(priceField);

        add(new JLabel("Discount:"));
        discountField = new JTextField(String.valueOf(sale.getDiscount()));
        add(discountField);

        JButton saveButton = new JButton("Save");
        saveButton.addActionListener(e -> {
            try {
                int qty = Integer.parseInt(quantityField.getText().trim());
                double price = Double.parseDouble(priceField.getText().trim());
                double discount = Double.parseDouble(discountField.getText().trim());

                if (qty <= 0 || price < 0 || discount < 0) {
                    JOptionPane.showMessageDialog(this, "Invalid input values.");
                    return;
                }

                updatedSale = new SaleRecord(
                        original.getInvoiceNumber(),
                        original.getProductId(),
                        original.getProductName(),
                        qty,
                        price,
                        discount
                );

                updated = true;
                dispose();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Please enter valid numbers.");
            }
        });

        add(saveButton);
        add(new JLabel()); // spacer
    }

    public boolean isUpdated() {
        return updated;
    }

    public SaleRecord getUpdatedSale() {
        return updatedSale;
    }

}
