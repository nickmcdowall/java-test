package com.nkm;

import com.nkm.*;
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
        LocalDate yesterday = now().minusDays(1);
        Discount discount = new DateRangeDiscount(sevenDayFrom(yesterday), halfPriceBreadWithTWoSoupTins());

        pricingService = new PricingService(discount);

        Basket basket = new Basket();
        basket.add(TIN_SOUP);
        basket.add(TIN_SOUP);
        basket.add(TIN_SOUP);
        basket.add(BREAD_LOAF);
        basket.add(BREAD_LOAF);

        assertThat(pricingService.price(basket, now())).isEqualTo(3.15);
    }

    private Period sevenDayFrom(LocalDate startDate) {
        return between(startDate, startDate.plusDays(7));
    }

    private Function<Basket, Double> halfPriceBreadWithTWoSoupTins() {
        return null;
    }
}
