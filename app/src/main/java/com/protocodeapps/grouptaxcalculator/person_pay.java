package com.protocodeapps.grouptaxcalculator;

public class person_pay {
    private double tip;
    private double pay;
    private int productQuantity;
    private double total;

    public void setTip(double tip) {
        this.tip=tip;
    }
    public double getTip() {
        return tip;
    }

    public void setPay(double pay) {
        this.pay=pay;
    }

    public double getPay() {
        return pay;
    }

    public void setProdQ(int productQuantity) {
        this.productQuantity=productQuantity;
    }
    public int getProdQ() {
        return productQuantity;
    }

    public void setTotal(double total) {
        this.total=total;
    }
    public double getTotal() {
        return total;
    }

    public void addPay(double add) {
        this.pay+=add;
    }
}
