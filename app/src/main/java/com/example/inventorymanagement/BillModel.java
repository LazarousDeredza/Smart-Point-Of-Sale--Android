package com.example.inventorymanagement;

public class BillModel {
    private String date,items,totalAmount,id;

    public BillModel() {
    }

    public BillModel(String date, String items, String totalAmount, String id) {
        this.date = date;
        this.items = items;
        this.totalAmount = totalAmount;
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getItems() {
        return items;
    }

    public void setItems(String items) {
        this.items = items;
    }

    public String getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
