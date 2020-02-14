package com.nkm.item;

public class Soup implements Item {
    private double price;

    public Soup(double price) {
        this.price = price;
    }

    @Override
    public double getPrice() {
        return price;
    }
}
