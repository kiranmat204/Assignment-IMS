/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ims;

/**
 *
 * @author ankur
 */
public class SaleItem {

    String productId;
    int quantity;
    double salePrice;
    double discount;
    double totalPrice;

    public SaleItem(String productId, int quantity, double salePrice, double discount, double totalPrice) {
        this.productId = productId;
        this.quantity = quantity;
        this.salePrice = salePrice;
        this.discount = discount;
        this.totalPrice = totalPrice;
    }

    public Object[] toRow() {
        return new Object[]{
            productId, 
            quantity, 
            String.format("%.2f", salePrice),  
            String.format("%.2f", discount),   
            String.format("%.2f", totalPrice)  
        };
    }
}
