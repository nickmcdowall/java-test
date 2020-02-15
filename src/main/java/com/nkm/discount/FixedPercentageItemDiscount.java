package com.nkm.discount;

import com.nkm.stock.Basket;
import com.nkm.stock.Item;

public class FixedPercentageItemDiscount implements Discount {

    private String itemKey;
    private double percentageDiscount;

    public FixedPercentageItemDiscount(String itemKey, double percentageDiscount) {
        this.itemKey = itemKey;
        this.percentageDiscount = percentageDiscount;
    }

    @Override
    public double apply(Basket basket) {
        return basket.itemsWithKey(itemKey).stream()
                .mapToDouble(Item::getPrice)
                .sum() * percentageDiscount;
    }
}
