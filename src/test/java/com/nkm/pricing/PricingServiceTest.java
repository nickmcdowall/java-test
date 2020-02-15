package com.nkm.pricing;

import com.nkm.discount.DateRangeDiscount;
import com.nkm.discount.Discount;
import com.nkm.pricing.PricingService;
import com.nkm.stock.Basket;
import com.nkm.stock.Bread;
import com.nkm.stock.Soup;
import org.assertj.core.data.Offset;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.time.LocalDate;
import java.util.stream.Stream;

import static java.time.LocalDate.now;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class PricingServiceTest {

    private static final Soup SOUP = new Soup(0.65);
    private static final Bread BREAD = new Bread(0.80);
    private static final Offset<Double> OFFSET = Offset.offset(0.0001);

    private PricingService pricingService;

    @ParameterizedTest
    @ValueSource(doubles = {
            0.8, 0.5, 0.4, 0.3, 0.0,
    })
    void discountAppliedWhenPaidOnValidDiscountDate(double expectedDiscount) {
        pricingService = new PricingService(
                new DateRangeDiscount(today(), aFixedDiscountOf(expectedDiscount)));

        Basket basket = new Basket().add(1, BREAD);

        assertThat(pricingService.price(basket, today()))
                .isEqualTo(basket.totalCost() - expectedDiscount, OFFSET);
    }

    @ParameterizedTest
    @ValueSource(ints = {
            -99, -1, +1, +99
    })
    void fullPriceWhenPaidBeforeOrAfterDiscountDate(int paymentDayOffset) {
        pricingService = new PricingService(
                new DateRangeDiscount(today(), aFixedDiscountOf(0.4)));

        Basket basket = new Basket().add(5, BREAD);

        assertThat(pricingService.price(basket, now().plusDays(paymentDayOffset)))
                .isEqualTo(basket.totalCost(), OFFSET);
    }

    @ParameterizedTest
    @MethodSource("variousBaskets")
    void fullPriceWhenNoDiscountsAvailable(Basket basket) {
        pricingService = new PricingService();

        assertThat(pricingService.price(basket, now())).isEqualTo(basket.totalCost(), OFFSET);
    }

    @Test
    void preventScenarioWherePriceIsNegative() {
        pricingService = new PricingService(aFixedDiscountOf(5.00));

        Basket basket = new Basket().add(1, BREAD);

        assertThat(pricingService.price(basket, now())).isGreaterThanOrEqualTo(0.0);
    }

    private LocalDate today() {
        return now();
    }

    private static Stream<Arguments> variousBaskets() {
        return Stream.of(
                arguments(new Basket()),
                arguments(new Basket().add(1, BREAD)),
                arguments(new Basket().add(1, SOUP)),
                arguments(new Basket().add(3, SOUP).add(2, BREAD))
        );
    }

    private Discount aFixedDiscountOf(double discount) {
        return basket -> discount;
    }
}
