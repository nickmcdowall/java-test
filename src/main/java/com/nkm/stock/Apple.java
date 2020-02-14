package com.nkm.stock;

public class Apple implements Item {

    private final double cost;

    public Apple(double cost) {
        this.cost = cost;
    }

    @Override
    public double getPrice() {
        return cost;
    }
}
