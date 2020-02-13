package com.nkm;

import org.assertj.core.data.Offset;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;

import java.time.LocalDate;
import java.time.Period;
import java.util.function.Function;
import java.util.stream.Stream;

import static java.time.LocalDate.now;
import static java.time.Period.between;
import static org.assertj.core.api.Assertions.assertThat;

public class PricingServiceTest {

    private static final TinSoup TIN_SOUP = new TinSoup(0.65);
    private static final BreadLoaf BREAD_LOAF = new BreadLoaf(0.80);

    private PricingService pricingService;

    @ParameterizedTest
    @CsvSource(value = {
            "0.4, 3.15",
            "0.2, 3.35",
            "0.1, 3.45",
            "3.55, 0.0",
    })
    void pricingServiceSubtractsDiscountForValidDate(double discountAmount, double priceToPay) {
        Discount discount = new DateRangeDiscount(periodOfDaysFrom(yesterday(), 7), aFixedDiscountOf(discountAmount));

        pricingService = new PricingService(discount);

        Basket basket = new Basket()
                .with(3, TIN_SOUP)
                .with(2, BREAD_LOAF);

        assertThat(pricingService.price(basket, now())).isEqualTo(priceToPay, Offset.offset(0.001));
    }

    @ParameterizedTest
    @MethodSource("basketsNoDiscount")
    void priceBasketWithoutDiscount(Basket basket, double expectedPrice) {
        pricingService = new PricingService();

        assertThat(pricingService.price(basket, now())).isEqualTo(expectedPrice, Offset.offset(0.001));
    }

    private static Stream<Arguments> basketsNoDiscount() {
        return Stream.of(
                Arguments.of(new Basket(), 0),
                Arguments.of(new Basket().with(1, BREAD_LOAF), 0.80),
                Arguments.of(new Basket().with(1, TIN_SOUP), 0.65),
                Arguments.of(new Basket().with(3, TIN_SOUP).with(2, BREAD_LOAF), 3.55)
        );
    }

    private LocalDate yesterday() {
        return now().minusDays(1);
    }

    private Period periodOfDaysFrom(LocalDate startDate, int daysToAdd) {
        return between(startDate, startDate.plusDays(daysToAdd));
    }

    private Function<Basket, Double> aFixedDiscountOf(double discount) {
        return basket -> discount;
    }
}
