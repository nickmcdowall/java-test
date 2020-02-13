package com.nkm;

import com.nkm.item.Item;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static java.util.stream.IntStream.range;

public class Basket {

    private List<Item> items = new ArrayList<>();

    public void add(int count, Item item) {
        range(0, count).forEach(i -> items.add(item));
    }

    public double totalCost() {
        return items.stream()
                .mapToDouble(item -> item.getPrice())
                .sum();
    }

    public Basket with(int count, Item item) {
        add(count, item);
        return this;
    }

    public Stream<Item> stream() {
        return items.stream();
    }
}
