package com.nkm.item;

public class BottleMilk implements Item {
    private double cost;

    public BottleMilk(double cost) {
        this.cost = cost;
    }

    @Override
    public double getPrice() {
        return cost;
    }
}
