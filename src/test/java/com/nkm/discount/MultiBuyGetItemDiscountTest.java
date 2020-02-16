package com.nkm.discount;

import com.nkm.stock.Basket;
import com.nkm.stock.StockItem;
import org.assertj.core.data.Offset;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;

class MultiBuyGetItemDiscountTest {

    private final MultiBuyGetItemDiscount discount = new MultiBuyGetItemDiscount(2, "Bread", 0.5, "Soup");

    @ParameterizedTest(name = "{0} loaves, {1} soups with expected discount: {2}")
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
                .add(noOfLoaves, new StockItem("Bread", 5.00))
                .add(noOfSoups, new StockItem("Soup", 100.00))
        );

        assertThat(discountCalculated).isEqualTo(expectedDiscount);
    }

}