package com.nkm;

import com.nkm.config.Config;
import com.nkm.item.Item;
import org.assertj.core.data.Offset;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.time.LocalDate;

import static java.time.LocalDate.now;
import static org.assertj.core.api.Assertions.assertThat;

public class AcceptanceTests {

    private static final Offset<Double> ACCEPTABLE_OFFSET = Offset.offset(0.001);

    private final Config appConfig = new Config();
    private final Application application = appConfig.getApplication();
    private final Item tinSoup = appConfig.getTinSoup();
    private final Item breadLoaf = appConfig.getBreadLoaf();
    private final Item apple = appConfig.getApple();
    private final Item bottleMilk = appConfig.getBottleMilk();

    @ParameterizedTest
    @ValueSource(ints = {-1, 0, +1, +2, +3, +4, +5})
    void multiTinSoupOfferWhenBoughtInDiscountWindow(int offsetPurchaseDate) {
        application.addBasketItem(3, tinSoup);
        application.addBasketItem(2, breadLoaf);
        assertThat(application.priceUp(now().plusDays(offsetPurchaseDate))).isEqualTo(3.15, ACCEPTABLE_OFFSET);
    }

    @ParameterizedTest
    @ValueSource(ints = {-2, +6})
    void multiTinSoupOfferNotAppliedWhenBoughtOutsideDiscountWindow(int offsetPurchaseDate) {
        application.addBasketItem(3, tinSoup);
        application.addBasketItem(2, breadLoaf);
        assertThat(application.priceUp(now().plusDays(offsetPurchaseDate))).isEqualTo(3.55, ACCEPTABLE_OFFSET);
    }

    @ParameterizedTest
    @ValueSource(ints = {0, +1, +2})
    void appleDiscountNotAppliedWhenBoughtBeforeDiscountWindow(int offsetPurchaseDate) {
        application.addBasketItem(6, apple);
        application.addBasketItem(1, bottleMilk);
        assertThat(application.priceUp(now().plusDays(offsetPurchaseDate))).isEqualTo(1.9, ACCEPTABLE_OFFSET);
    }

    @ParameterizedTest
    @ValueSource(ints = {+3, +5, +20})
    void appleDiscountIsAppliedDuringDiscountWindow(int offsetPurchaseDate) {
        application.addBasketItem(6, apple);
        application.addBasketItem(1, bottleMilk);
        assertThat(application.priceUp(now().plusDays(offsetPurchaseDate))).isEqualTo(1.84, ACCEPTABLE_OFFSET);
    }

    @Test
    void appleDiscountIsAppliedOnLastDayNextMonth() {
        LocalDate nextMonth = now().plusMonths(1);

        application.addBasketItem(6, apple);
        application.addBasketItem(1, bottleMilk);
        assertThat(application.priceUp(lastDayOfMonth(nextMonth))).isEqualTo(1.84, ACCEPTABLE_OFFSET);
    }

    @Test
    void appleDiscountIsNotAppliedAfterLastDayNextMonth() {
        LocalDate nextMonth = now().plusMonths(1);

        application.addBasketItem(6, apple);
        application.addBasketItem(1, bottleMilk);
        assertThat(application.priceUp(lastDayOfMonth(nextMonth).plusDays(1))).isEqualTo(1.9, ACCEPTABLE_OFFSET);
    }

    private LocalDate lastDayOfMonth(LocalDate date) {
        int lengthOfMonth = date.getMonth().length(date.isLeapYear());
        return date.withDayOfMonth(lengthOfMonth);
    }

}
