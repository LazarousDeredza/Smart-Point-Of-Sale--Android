package com.example.inventorymanagement;

public class Modeltrans {
    private String gdate;
    private String gitems;
    private String gtotamt;
    private String gtranid;
    private String key;

    public Modeltrans() {
    }

    public Modeltrans(String gdate, String gitems, String gtotamt, String gtranid, String key) {
        this.gdate = gdate;
        this.gitems = gitems;
        this.gtotamt = gtotamt;
        this.gtranid = gtranid;
        this.key = key;
    }

    public String getGdate() {
        return gdate;
    }

    public void setGdate(String gdate) {
        this.gdate = gdate;
    }

    public String getGitems() {
        return gitems;
    }

    public void setGitems(String gitems) {
        this.gitems = gitems;
    }

    public String getGtotamt() {
        return gtotamt;
    }

    public void setGtotamt(String gtotamt) {
        this.gtotamt = gtotamt;
    }

    public String getGtranid() {
        return gtranid;
    }

    public void setGtranid(String gtranid) {
        this.gtranid = gtranid;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
