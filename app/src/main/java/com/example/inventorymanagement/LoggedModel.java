package com.example.inventorymanagement;

public class LoggedModel {
    private String id,level,uuid;

    public LoggedModel() {
    }

    public LoggedModel(String id, String level, String uuid) {
        this.id = id;
        this.level = level;
        this.uuid = uuid;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }
}
