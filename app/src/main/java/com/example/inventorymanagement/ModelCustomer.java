package com.example.inventorymanagement;

public class Modelcustomer {

    private String einame;
    private String ephoneNo;
    private String edate;
    private String emoney;
    private String eaddr;
    private String log;
    String key;

    public String getLog() {
        return log;
    }

    public void setLog(String log) {
        this.log = log;
    }





    public Modelcustomer() {

    }

    public Modelcustomer(String einame, String ephoneNo, String edate, String emoney, String eaddr, String key) {
        this.einame = einame;
        this.ephoneNo = ephoneNo;
        this.edate = edate;
        this.emoney = emoney;
        this.eaddr = eaddr;
        this.key = key;
        this.log = "";
    }

    public Modelcustomer(String einame, String ephoneNo, String edate, String emoney, String eaddr, String log, String key) {
        this.einame = einame;
        this.ephoneNo = ephoneNo;
        this.edate = edate;
        this.emoney = emoney;
        this.eaddr = eaddr;
        this.log = log;
        this.key = key;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getEphoneNo() {
        return ephoneNo;
    }

    public void setEphoneNo(String ephoneNo) {
        this.ephoneNo = ephoneNo;
    }

    public String getEdate() {
        return edate;
    }

    public void setEdate(String edate) {
        this.edate = edate;
    }

    public String getEmoney() {
        return emoney;
    }

    public void setEmoney(String emoney) {
        this.emoney = emoney;
    }

    public String getEaddr() {
        return eaddr;
    }

    public void setEaddr(String eaddr) {
        this.eaddr = eaddr;
    }



    // public Modelcustomer(String einame, String ebatchNo, String eqty, String
    // eexpdate, String emrp, String esp, String ecName, String ecode, String ecp,
    // String edesc)
    // {
    // this.ebatchNo=ebatchNo;
    // this.eexpdate=eexpdate;
    // this.einame=einame;
    // this.emrp=emrp;
    // this.eqty=eqty;
    // this.esp=esp;
    // this.ecName=ecName;
    // this.edesc=edesc;
    // }

    public String getEiname() {
        return einame;
    }

    public void setEiname(String einame) {
        this.einame = einame;
    }

}

