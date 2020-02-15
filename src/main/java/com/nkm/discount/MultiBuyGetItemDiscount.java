package com.nkm.discount;

import com.nkm.stock.Basket;
import com.nkm.stock.Item;

import java.util.List;

public class MultiBuyGetItemDiscount implements Discount {

    private final int requiredItemCount;
    private final String multiItemKey;
    private final double percentageDiscount;
    private final String discountItemKey;

    public MultiBuyGetItemDiscount(int requiredItemCount, String multiItemKey, double percentageDiscount, String discountItemKey) {
        this.requiredItemCount = requiredItemCount;
        this.multiItemKey = multiItemKey;
        this.percentageDiscount = percentageDiscount;
        this.discountItemKey = discountItemKey;
    }

    @Override
    public double apply(Basket basket) {
        List<Item> multiItems = basket.itemsWithKey(multiItemKey);
        int maxTimesDiscountCanBeApplied = multiItems.size() / requiredItemCount;
        List<Item> targetItems = basket.itemsWithKey(discountItemKey);

        return targetItems.stream()
                .limit(maxTimesDiscountCanBeApplied)
                .mapToDouble(Item::getPrice)
                .sum() * percentageDiscount;
    }

}
