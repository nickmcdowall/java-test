package com.nkm.config;

import com.nkm.stock.Item;
import com.nkm.stock.NoSuchStockItemException;
import com.nkm.stock.StockItem;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import static java.util.Optional.ofNullable;
import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toMap;

public class StockRepository {

    private final Map<String, Item> stockItems = List.of(
            new StockItem("Apple", 0.10),
            new StockItem("Milk", 1.3),
            new StockItem("Bread", 0.80),
            new StockItem("Soup", 0.65)
    ).stream()
            .collect(toMap(StockItem::getKey, identity()));

    public Item getItemByKey(String key) {
        return ofNullable(stockItems.get(key))
                .orElseThrow(() -> new NoSuchStockItemException(key));
    }

    public Set<String> keys() {
        return new TreeSet(stockItems.keySet());
    }
}
