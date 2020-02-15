package com.nkm.discount;

import com.nkm.Basket;
import com.nkm.stock.Apple;
import com.nkm.stock.Milk;
import com.nkm.stock.Item;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class FixedPercentageItemDiscountTest {

    private final Item apple = new Apple(100);
    private final Item Milk = new Milk(5);
    private final Discount percentageDiscount = new FixedPercentageItemDiscount(Apple.class, 0.1);
    private final Basket basket = new Basket();

    @Test
    void discountForSingleEligibleItemInBasket() {
        assertThat(percentageDiscount.apply(basket.add(1, apple))).isEqualTo(10);
    }

    @Test
    void discountForSingleEligibleItemInBasketWithOtherItems() {
        assertThat(percentageDiscount.apply(basket.add(1, apple).add(1, Milk))).isEqualTo(10);
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
        assertThat(percentageDiscount.apply(basket.add(1, Milk))).isEqualTo(0);
    }
}