package com.nkm.stock;

public class Bread implements Item {

    private double price;

    public Bread(double price) {
        this.price = price;
    }

    @Override
    public double getPrice() {
        return price;
    }
}
