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

    public SaleItem(String productId, int quantity, double salePrice, double discount) {
        this.productId = productId;
        this.quantity = quantity;
        this.salePrice = salePrice;
        this.discount = discount;
    }

    public Object[] toRow() {
        return new Object[]{productId, quantity, salePrice, discount};
    }
}
