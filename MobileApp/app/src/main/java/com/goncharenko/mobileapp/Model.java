package com.goncharenko.mobileapp;

public class Model {
    int id;
    String from;
    String to;
    double amount;
    double result;

    public Model(int id, String from, String to, double amount, double result) {
        this.id = id;
        this.from = from;
        this.to = to;
        this.amount = amount;
        this.result = result;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public double getResult() {
        return result;
    }

    public void setResult(double result) {
        this.result = result;
    }
}
