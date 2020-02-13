package com.nkm.discount;

import com.nkm.Basket;
import com.nkm.item.Item;

import java.util.function.Function;

public class FixedPercentageItemDiscount implements Function<Basket, Double> {

    public FixedPercentageItemDiscount(Class<? extends Item> itemClass, double percentageDiscount) {
    }

    @Override
    public Double apply(Basket basket) {
        return null;
    }
}
