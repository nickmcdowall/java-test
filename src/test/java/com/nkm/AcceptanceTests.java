package com.nkm;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.Period;
import java.util.function.Function;

import static java.time.LocalDate.now;
import static java.time.Period.between;
import static org.assertj.core.api.Assertions.assertThat;

public class AcceptanceTests {

    private static final TinSoup TIN_SOUP = new TinSoup(0.65);
    private static final BreadLoaf BREAD_LOAF = new BreadLoaf(0.80);

    private PricingService pricingService;

    @Test
    void priceThreeSoupTinsAndTwoBreadLoavesBoughtToday() {
        Discount discount = new DateRangeDiscount(periodOfDaysFrom(yesterday(), 7), halfPriceBreadWithTWoSoupTins());

        pricingService = new PricingService(discount);

        Basket basket = new Basket();
        basket.add(3, TIN_SOUP);
        basket.add(2, BREAD_LOAF);

        assertThat(pricingService.price(basket, now())).isEqualTo(3.15);
    }

    private LocalDate yesterday() {
        return now().minusDays(1);
    }

    private Period periodOfDaysFrom(LocalDate startDate, int daysToAdd) {
        return between(startDate, startDate.plusDays(daysToAdd));
    }

    private Function<Basket, Double> halfPriceBreadWithTWoSoupTins() {
        return null;
    }
}
