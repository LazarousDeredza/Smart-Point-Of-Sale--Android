package com.example.inventorymanagement;

public class Log {

    private String id;
    private String log;
    private String date;


    public Log() {
    }

    public Log(String id, String log, String date) {
        this.id = id;
        this.log = log;
        this.date = date;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLog() {
        return log;
    }

    public void setLog(String log) {
        this.log = log;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
