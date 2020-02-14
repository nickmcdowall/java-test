package com.nkm.discount;

import com.nkm.Basket;
import com.nkm.stock.Item;

import java.util.List;

public class MultiBuyGetItemDiscount implements Discount {

    private final int requiredItemCount;
    private final Class<? extends Item> multiItemClass;
    private final double percentageDiscount;
    private final Class<? extends Item> discountItemClass;

    public MultiBuyGetItemDiscount(int requiredItemCount, Class<? extends Item> multiItemClass, double percentageDiscount, Class<? extends Item> discountItemClass) {
        this.requiredItemCount = requiredItemCount;
        this.multiItemClass = multiItemClass;
        this.percentageDiscount = percentageDiscount;
        this.discountItemClass = discountItemClass;
    }

    @Override
    public double apply(Basket basket) {
        List<Item> multiItems = basket.filterByType(multiItemClass);
        int maxTimesDiscountCanBeApplied = multiItems.size() / requiredItemCount;
        List<Item> targetItems = basket.filterByType(discountItemClass);

        return targetItems.stream()
                .limit(maxTimesDiscountCanBeApplied)
                .mapToDouble(Item::getPrice)
                .sum() * percentageDiscount;
    }

}
