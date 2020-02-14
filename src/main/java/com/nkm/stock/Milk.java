package com.nkm.stock;

public class Milk implements Item {
    private double cost;

    public Milk(double cost) {
        this.cost = cost;
    }

    @Override
    public double getPrice() {
        return cost;
    }
}
