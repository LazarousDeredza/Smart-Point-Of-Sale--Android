package com.example.inventorymanagement;

public class BillModel {
    private String id, dateBilled,items,totalAmount;

    public BillModel() {
    }

    public BillModel(String dateBilled, String items, String totalAmount, String id) {
        this.dateBilled = dateBilled;
        this.items = items;
        this.totalAmount = totalAmount;
        this.id = id;
    }

    public String getDateBilled() {
        return dateBilled;
    }

    public void setDateBilled(String dateBilled) {
        this.dateBilled = dateBilled;
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
