/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dataLayer;

/**
 *
 * @author aleja
 */
public class E_Toy {
    private int id;
    private String description;
    private int recommendedAge;
    private String category;
    private String brand;
    private String supplier;
    private double salePrice;
    private double purchasePrice;
    private int stock;

    public E_Toy() {
    }

    public E_Toy(int id, String description, int recommendedAge, String category, String brand, String supplier, double salePrice, double purchasePrice, int stock) {
        this.id = id;
        this.description = description;
        this.recommendedAge = recommendedAge;
        this.category = category;
        this.brand = brand;
        this.supplier = supplier;
        this.salePrice = salePrice;
        this.purchasePrice = purchasePrice;
        this.stock = stock;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getRecommendedAge() {
        return recommendedAge;
    }

    public void setRecommendedAge(int recommendedAge) {
        this.recommendedAge = recommendedAge;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getSupplier() {
        return supplier;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }

    public double getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(double salePrice) {
        this.salePrice = salePrice;
    }

    public double getPurchasePrice() {
        return purchasePrice;
    }

    public void setPurchasePrice(double purchasePrice) {
        this.purchasePrice = purchasePrice;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }
    
    
    
}
