package com.nkm.stock;

import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.toList;
import static java.util.stream.IntStream.range;

public class Basket {

    private List<Item> items = new ArrayList<>();

    public Basket add(int count, Item item) {
        range(0, count).forEach(i -> items.add(item));
        return this;
    }

    public double totalCost() {
        return items.stream()
                .mapToDouble(Item::getPrice)
                .sum();
    }

    public List<Item> itemsOfType(Class<? extends Item> itemClass) {
        return items.stream()
                .filter(item -> item.getClass().equals(itemClass))
                .collect(toList());
    }
}
