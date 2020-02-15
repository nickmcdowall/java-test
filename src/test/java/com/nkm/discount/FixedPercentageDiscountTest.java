package com.nkm.discount;

import com.nkm.stock.Basket;
import com.nkm.stock.Item;
import com.nkm.stock.StockItem;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class FixedPercentageDiscountTest {

    private final Item apple = new StockItem("Apple", 100);
    private final Item milk = new StockItem("Milk", 5);
    private final Discount percentageDiscount = new FixedPercentageDiscount("Apple", 0.1);
    private final Basket basket = new Basket();

    @Test
    void discountForSingleEligibleItemInBasket() {
        assertThat(percentageDiscount.apply(basket.add(1, apple))).isEqualTo(10);
    }

    @Test
    void discountForSingleEligibleItemInBasketWithOtherItems() {
        assertThat(percentageDiscount.apply(basket.add(1, apple).add(1, milk))).isEqualTo(10);
    }

    @Test
    void discountForMultiEligibleItemsInBasket() {
        assertThat(percentageDiscount.apply(basket.add(2, apple))).isEqualTo(20);
    }

    @Test
    void noDiscountForNoItemsInBasket() {
        assertThat(percentageDiscount.apply(new Basket())).isEqualTo(0);
    }

    @Test
    void noDiscountForNoMatchingItemsInBasket() {
        assertThat(percentageDiscount.apply(basket.add(1, milk))).isEqualTo(0);
    }
}