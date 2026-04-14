/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dataLayer;

import java.sql.Date;

/**
 *
 * @author aleja
 */
public class E_Sale {
    private int id;
    private Date saleDate;
    private String numberOfBill;
    private int userId;
    private int customerId;

    public E_Sale() {
    }

    public E_Sale(int id, Date saleDate, String numberOfBill, int userId, int customerId) {
        this.id = id;
        this.saleDate = saleDate;
        this.numberOfBill = numberOfBill;
        this.userId = userId;
        this.customerId = customerId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getSaleDate() {
        return saleDate;
    }

    public void setSaleDate(Date saleDate) {
        this.saleDate = saleDate;
    }

    public String getNumberOfBill() {
        return numberOfBill;
    }

    public void setNumberOfBill(String numberOfBill) {
        this.numberOfBill = numberOfBill;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }
    
    
}
