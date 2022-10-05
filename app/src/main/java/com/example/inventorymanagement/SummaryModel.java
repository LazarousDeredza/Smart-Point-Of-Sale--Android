package com.example.inventorymanagement;

public class SummaryModel {


    private String date,count,amount,month,monthAmount,monthCount,trans;

    public SummaryModel() {
    }

    public SummaryModel(String date, String count, String amount, String month, String monthAmount, String monthCount, String trans) {
        this.date = date;
        this.count = count;
        this.amount = amount;
        this.month = month;
        this.monthAmount = monthAmount;
        this.monthCount = monthCount;
        this.trans = trans;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getMonthAmount() {
        return monthAmount;
    }

    public void setMonthAmount(String monthAmount) {
        this.monthAmount = monthAmount;
    }

    public String getMonthCount() {
        return monthCount;
    }

    public void setMonthCount(String monthCount) {
        this.monthCount = monthCount;
    }

    public String getTrans() {
        return trans;
    }

    public void setTrans(String trans) {
        this.trans = trans;
    }
}
