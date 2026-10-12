package com.protocodeapps.grouptaxcalculator.models;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Person implements Serializable {

    public Person (String name, BigDecimal tip){ //Only take the name tip and the products since the rest will be calculated
        this.name = name;
        this.tipPercentage = tip;
        this.products = new ArrayList<Product>(); //List is more efficient in this case
    }

    private String name; //Each person has a name
    private  BigDecimal tipPercentage; //Each person has an individual tip
    private BigDecimal subTotal = BigDecimal.ZERO;
    private BigDecimal payTotal= BigDecimal.ZERO;
    private List<Product> products; //Each person has an array of products


    public void setName (){ this.name=name; }
    public String getName (){ return name; }


    //Returns the total pay with tax of the person
    public void calculateTotalPay(){
        payTotal = BigDecimal.ZERO;
        BigDecimal preparedTax = new BigDecimal("0.07"); //Prepared tax in PR is %7

        for (Product product:products) {
            if (product.getPrepared()) {
                payTotal = payTotal.add(product.getPrice().add(product.getPrice().multiply(preparedTax))); // price + (price * preparedTax)
            } else if (product.getPrice().compareTo(BigDecimal.ONE) <= 0) { //No tax if less than 1 dollar
                payTotal = payTotal.add(product.getPrice()); // price
            } else {
                payTotal = payTotal.add(product.getPrice().add(product.getPrice().multiply(product.getTaxPercentage()))); // price + (price * tax)
            }
            payTotal = payTotal.add(tipPercentage.multiply(product.getPrice())); // + tip (optional so may be 0)
        }

    }
    public BigDecimal getPayTotal() {
        return payTotal;
    }

    //Returns the subtotal of the person
    public void calculateSubTotal(){
        subTotal = BigDecimal.ZERO;
        for (Product product:products) {
            subTotal = subTotal.add(product.getPrice());
        }
    }
    public BigDecimal getSubTotal() {
        return subTotal;
    }

    public void addProduct(Product product){
        products.add(product);
    }

    public List<Product> getProducts(){
        return products;
    }





}
