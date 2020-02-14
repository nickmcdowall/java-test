package com.nkm;

import com.nkm.discount.DateRangeDiscount;
import com.nkm.discount.Discount;
import com.nkm.item.Bread;
import com.nkm.item.TinSoup;
import org.assertj.core.data.Offset;
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

    private static final TinSoup TIN_SOUP = new TinSoup(0.65);
    private static final Bread BREAD_LOAF = new Bread(0.80);
    private static final Offset<Double> OFFSET = Offset.offset(0.0001);

    private PricingService pricingService;

    @ParameterizedTest
    @ValueSource(doubles = {
            0.8, 0.5, 0.4, 0.3, 0.0,
    })
    void discountAppliedWhenPaidOnValidDiscountDate(double discountAmount) {
        pricingService = new PricingService(
                new DateRangeDiscount(today(), aFixedDiscountOf(discountAmount)));

        Basket basket = new Basket().with(1, BREAD_LOAF);

        assertThat(pricingService.price(basket, today()))
                .isEqualTo(basket.totalCost() - discountAmount, OFFSET);
    }

    @ParameterizedTest
    @ValueSource(ints = {
            -99, -1, +1, +99
    })
    void fullPriceWhenPaidBeforeOrAfterDiscountDate(int paymentDayOffset) {
        pricingService = new PricingService(
                new DateRangeDiscount(today(), aFixedDiscountOf(0.4)));

        Basket basket = new Basket().with(5, BREAD_LOAF);

        assertThat(pricingService.price(basket, now().plusDays(paymentDayOffset)))
                .isEqualTo(basket.totalCost(), OFFSET);
    }

    @ParameterizedTest
    @MethodSource("variousBaskets")
    void fullPriceWhenNoDiscountsAvailable(Basket basket) {
        pricingService = new PricingService();

        assertThat(pricingService.price(basket, now())).isEqualTo(basket.totalCost(), OFFSET);
    }

    private LocalDate today() {
        return now();
    }

    private static Stream<Arguments> variousBaskets() {
        return Stream.of(
                arguments(new Basket()),
                arguments(new Basket().with(1, BREAD_LOAF)),
                arguments(new Basket().with(1, TIN_SOUP)),
                arguments(new Basket().with(3, TIN_SOUP).with(2, BREAD_LOAF))
        );
    }

    private Discount aFixedDiscountOf(double discount) {
        return basket -> discount;
    }
}
