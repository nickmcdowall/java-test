package com.nkm.stock;

public class StockItem implements Item {
    private final String key;
    private final double price;

    public StockItem(String key, double price) {
        this.key = key;
        this.price = price;
    }

    @Override
    public double getPrice() {
        return price;
    }

    @Override
    public String getKey() {
        return key;
    }
}
