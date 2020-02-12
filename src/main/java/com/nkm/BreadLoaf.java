package com.nkm;

public class BreadLoaf implements Item {
    
    private double price;

    public BreadLoaf(double price) {
        this.price = price;
    }

    @Override
    public double getPrice() {
        return price;
    }
}
