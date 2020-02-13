package com.nkm.item;

import com.nkm.item.Item;

public class TinSoup implements Item {
    private double price;

    public TinSoup(double price) {
        this.price = price;
    }

    @Override
    public double getPrice() {
        return price;
    }
}
