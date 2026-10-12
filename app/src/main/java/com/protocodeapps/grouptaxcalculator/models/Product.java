package com.protocodeapps.grouptaxcalculator.models;

import java.io.Serializable;
import java.math.BigDecimal;
public class Product implements Serializable {
    private String name;

    private  BigDecimal taxPercentage;
    private BigDecimal price;
    private String description;
    private  boolean isPrepared; //7% rather than 11.5%

    public Product(String name, BigDecimal tax ,BigDecimal price, boolean isPrepared) { //Constructor
        this.name=name;
        this.taxPercentage =tax;
        this.price=price;
        this.isPrepared=isPrepared;
    }


//
    public void setName(String name) { this.name=name; }
    public String getName() { return name;}
//
    public void setTaxPercentage(BigDecimal taxPercentage) { this.taxPercentage = taxPercentage; }
    public BigDecimal getTaxPercentage() { return taxPercentage; }
//
    public void setPrice(BigDecimal price) { this.price=price; }
    public BigDecimal getPrice() { return price; }
//
    public void setPrepared(boolean isPrepared) { this.isPrepared=isPrepared; }
    public boolean getPrepared() { return isPrepared; }



}
