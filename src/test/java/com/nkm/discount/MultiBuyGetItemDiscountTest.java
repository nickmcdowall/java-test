package com.nkm.discount;

import com.nkm.Basket;
import com.nkm.item.Bread;
import com.nkm.item.Soup;
import org.assertj.core.data.Offset;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;

class MultiBuyGetItemDiscountTest {

    private final MultiBuyGetItemDiscount discount = new MultiBuyGetItemDiscount(2, Bread.class, 0.5, Soup.class);

    @ParameterizedTest(name = "{0} loaves, {1} soups with total discount: {2}")
    @CsvSource({
            "1, 1, 0",
            "2, 1, 50",
            "2, 2, 50",
            "3, 1, 50",
            "4, 1, 50",
            "4, 2, 100",
            "6, 0, 0",
            "6, 1, 50",
            "6, 2, 100",
            "6, 3, 150",
    })
    void discountOnEligibleBasket(int noOfLoaves, int noOfSoups, double expectedDiscount) {
        Double discountCalculated = discount.apply(new Basket()
                .with(noOfLoaves, new Bread(5))
                .with(noOfSoups, new Soup(100))
        );

        assertThat(discountCalculated).isEqualTo(expectedDiscount, Offset.offset(0.001));
    }

}