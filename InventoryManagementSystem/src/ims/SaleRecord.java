/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ims;

/**
 *
 * @author ankur
 */
public class SaleRecord {

    private String invoiceNumber;
    private String productId;
    private String productName;
    private int quantity;
    private double salePrice;
    private double discount;

    // Constructor, getters, setters
    public SaleRecord(String invoiceNumber, String productId, String productName, int quantity, double salePrice, double discount) {
        this.invoiceNumber = invoiceNumber;
        this.productId = productId;
        this.productName = productName;
        this.quantity = quantity;
        this.salePrice = salePrice;
        this.discount = discount;
    }

    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    public String getProductId() {
        return productId;
    }

    public String getProductName() {
        return productName;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getSalePrice() {
        return salePrice;
    }

    public double getDiscount() {
        return discount;
    }
}
