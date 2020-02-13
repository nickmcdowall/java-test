package com.nkm.discount;

import com.nkm.Basket;
import com.nkm.item.Item;

import java.util.function.Function;

public class MultiBuyGetItemDiscount implements Function<Basket, Double> {

    public MultiBuyGetItemDiscount(int itemCount, Class<? extends Item> multiItem, double percentageDiscount, Class<? extends Item> discountItem) {
    }

    @Override
    public Double apply(Basket basket) {
        return null;
    }
}
