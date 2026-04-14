/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dataLayer;

/**
 *
 * @author aleja
 */
public class E_SaleDetail {
    private int id;
    private double price;
    private int quantity;
    private int isv;
    private int toyId;
    private int saleId;
    
    public E_SaleDetail() {
    }

    public E_SaleDetail(int id, double price, int quantity, int isv, int toyId, int saleId) {
        this.id = id;
        this.price = price;
        this.quantity = quantity;
        this.isv = isv;
        this.toyId = toyId;
        this.saleId = saleId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getToyId() {
        return toyId;
    }

    public void setToyId(int toyId) {
        this.toyId = toyId;
    }

    public int getSaleId() {
        return saleId;
    }

    public void setSaleId(int saleId) {
        this.saleId = saleId;
    }

    public int getIsv() {
        return isv;
    }

    public void setIsv(int isv) {
        this.isv = isv;
    }
}
