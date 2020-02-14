package com.nkm.discount;

import com.nkm.Basket;
import com.nkm.item.Item;

import java.util.function.Function;

public class FixedPercentageItemDiscount implements Discount {

    public FixedPercentageItemDiscount(Class<? extends Item> itemClass, double percentageDiscount) {
    }

    @Override
    public double apply(Basket basket) {
        return 0;
    }
}
