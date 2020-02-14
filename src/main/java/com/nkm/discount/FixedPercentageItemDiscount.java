package com.nkm.discount;

import com.nkm.Basket;
import com.nkm.item.Item;

public class FixedPercentageItemDiscount implements Discount {

    private Class<? extends Item> itemClass;
    private double percentageDiscount;

    public FixedPercentageItemDiscount(Class<? extends Item> itemClass, double percentageDiscount) {
        this.itemClass = itemClass;
        this.percentageDiscount = percentageDiscount;
    }

    @Override
    public double apply(Basket basket) {
        return basket.filterByType(itemClass).stream()
                .mapToDouble(Item::getPrice)
                .sum() * percentageDiscount;
    }
}
