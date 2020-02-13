package com.nkm.discount;

import com.nkm.Basket;
import com.nkm.item.BreadLoaf;
import com.nkm.item.TinSoup;
import org.assertj.core.data.Offset;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class MultiBuyGetItemDiscountTest {

    @Test
    void discountOnAdditionalItemOfSameType() {
        MultiBuyGetItemDiscount discount = new MultiBuyGetItemDiscount(2, BreadLoaf.class, 0.5, TinSoup.class);

        Double discountCalculated = discount.apply(new Basket()
                .with(3, new BreadLoaf(5))
                .with(1, new TinSoup(100))
        );

        assertThat(discountCalculated).isEqualTo(50.00, Offset.offset(0.001));
    }
}