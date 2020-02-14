package com.nkm.config;

import com.nkm.item.*;

import java.util.Map;

import static java.util.Optional.ofNullable;

public class StockConfig {
    private final Map<String, Item> stockItems = Map.of(
            "Apple", new Apple(0.10),
            "Milk", new Milk(1.3),
            "Bread", new Bread(0.80),
            "Soup", new Soup(0.65)
    );

    public Item getItemByKey(String key) {
        return ofNullable(stockItems.get(key))
                .orElseThrow(() -> new NoSuchStockItemException(key));
    }
}
