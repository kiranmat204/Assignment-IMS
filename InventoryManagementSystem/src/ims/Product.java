/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ims;

/**
 *
 * @author ankur
 */
public class Product {

    private String productId;
    private String productName;
    private int quantity;
    private String productBrand;
    private double price;
    //private double retailPrice;
    private String category;
    private String supplier;
    private double sale;
    private double salePrice;

    public Product(String productId, String supplier, String category, String productBrand,
            String productName, int quantity, double price) {
        this.productId = productId;
        this.supplier = supplier;
        this.category = category;
        this.productBrand = productBrand;
        this.productName = productName;
        this.quantity = quantity;
        this.price = price;
        this.sale = sale;
        this.salePrice = salePrice;
    }

    public String getProductId() {
        return this.productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return this.productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getQuantity() {
        return this.quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getProductBrand() {
        return this.productBrand;
    }

    public void setProductBrand(String productBrand) {
        this.productBrand = productBrand;
    }

    public double getPrice() {
        return this.price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getSupplier() {
        return supplier;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }

    public double getSale() {
        return this.sale;
    }

    public void setSale(double sale) {
        this.sale = sale;
    }

    public double getSalePrice() {
        return this.salePrice;
    }

    public void setSalePrice(double salePrice) {
        this.salePrice = salePrice;
    }

//    public double getRetailPrice(){
//        return this.retailPrice;
//    }
//    public void setSellingPrice(double retailPrice){
//        this.retailPrice = retailPrice;
//    }
    @Override
    public String toString() {
        return "Product [ID=" + productId +
                ", Name=" + productName +
                ", Brand=" + productBrand +
                ", Category=" + category +
                ", Supplier=" + supplier +
                ", Quantity=" + quantity +
                ", Price=" + price +
                ", Sale=" + sale +
                ", Sale Price=" + salePrice + "]";
    }
}
