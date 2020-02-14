package com.nkm.discount;

import com.nkm.Basket;
import com.nkm.item.Item;

import java.util.List;

import static java.util.stream.Collectors.toList;

public class MultiBuyGetItemDiscount implements Discount {

    private final int itemCount;
    private final Class<? extends Item> multiItemClass;
    private final double percentageDiscount;
    private final Class<? extends Item> discountItemClass;

    public MultiBuyGetItemDiscount(int itemCount, Class<? extends Item> multiItemClass, double percentageDiscount, Class<? extends Item> discountItemClass) {
        this.itemCount = itemCount;
        this.multiItemClass = multiItemClass;
        this.percentageDiscount = percentageDiscount;
        this.discountItemClass = discountItemClass;
    }

    @Override
    public double apply(Basket basket) {
        List<Item> multiItems = filterByType(basket, multiItemClass);

        int hits = multiItems.size() / itemCount;

        List<Item> targetItem = filterByType(basket, discountItemClass);

        return targetItem.stream()
                .limit(hits)
                .mapToDouble(Item::getPrice)
                .sum() * percentageDiscount;
    }

    private List<Item> filterByType(Basket basket, Class<? extends Item> itemClass) {
        return basket.stream()
                .filter(item -> item.getClass().equals(itemClass))
                .collect(toList());
    }
}
