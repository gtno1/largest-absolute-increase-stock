package org.example;

import java.util.Date;

public class StockValue {


    private String name;

    private Date date;

    private String notes;

    private double value;

    private String change;


    public String getName() {
        return name;
    }

    public Date getDate() {
        return date;
    }

    public String getNotes() {
        return notes;
    }

    public double getValue() {
        return value;
    }

    public String getChange() {
        return change;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public void setChange(String change) {
        this.change = change;
    }
}
