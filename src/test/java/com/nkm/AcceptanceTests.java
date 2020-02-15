package com.nkm;

import com.nkm.config.ApplicationConfig;
import com.nkm.config.StockRepository;
import com.nkm.stock.Item;
import org.assertj.core.data.Offset;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.time.LocalDate;

import static java.time.LocalDate.now;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class AcceptanceTests {

    private static final Offset<Double> ACCEPTABLE_OFFSET = Offset.offset(0.001);

    private final Application application = new ApplicationConfig().getApplication();
    private final StockRepository stockRepository = new StockRepository();
    private final Item soup = stockRepository.getItemByKey("Soup");
    private final Item bread = stockRepository.getItemByKey("Bread");
    private final Item apple = stockRepository.getItemByKey("Apple");
    private final Item milk = stockRepository.getItemByKey("Milk");

    @ParameterizedTest
    @ValueSource(ints = {-1, 0, +1, +2, +3, +4, +5})
    void multiSoupOfferWhenBoughtInDiscountWindow(int offsetPurchaseDate) {
        application.addBasketItem(3, soup);
        application.addBasketItem(2, bread);

        assertThat(application.priceUp(now().plusDays(offsetPurchaseDate))).isEqualTo(3.15, ACCEPTABLE_OFFSET);
    }

    @ParameterizedTest
    @ValueSource(ints = {-2, +6})
    void multiSoupOfferNotAppliedWhenBoughtOutsideDiscountWindow(int offsetPurchaseDate) {
        application.addBasketItem(3, soup);
        application.addBasketItem(2, bread);

        assertThat(application.priceUp(now().plusDays(offsetPurchaseDate))).isEqualTo(3.55, ACCEPTABLE_OFFSET);
    }

    @ParameterizedTest
    @ValueSource(ints = {0, +1, +2})
    void appleDiscountNotAppliedWhenBoughtBeforeDiscountWindow(int offsetPurchaseDate) {
        application.addBasketItem(6, apple);
        application.addBasketItem(1, milk);

        assertThat(application.priceUp(now().plusDays(offsetPurchaseDate))).isEqualTo(1.9, ACCEPTABLE_OFFSET);
    }

    @ParameterizedTest
    @ValueSource(ints = {+3, +5, +20})
    void appleDiscountIsAppliedDuringDiscountWindow(int offsetPurchaseDate) {
        application.addBasketItem(6, apple);
        application.addBasketItem(1, milk);

        assertThat(application.priceUp(now().plusDays(offsetPurchaseDate))).isEqualTo(1.84, ACCEPTABLE_OFFSET);
    }

    @Test
    void appleDiscountIsAppliedOnLastDayNextMonth() {
        application.addBasketItem(6, apple);
        application.addBasketItem(1, milk);

        assertThat(application.priceUp(lastDayOfMonth(nextMonth()))).isEqualTo(1.84, ACCEPTABLE_OFFSET);
    }

    @Test
    void appleDiscountIsNotAppliedAfterLastDayNextMonth() {
        application.addBasketItem(6, apple);
        application.addBasketItem(1, milk);

        assertThat(application.priceUp(lastDayOfMonth(nextMonth()).plusDays(1))).isEqualTo(1.9, ACCEPTABLE_OFFSET);
    }

    @Test
    void multiDiscountsApplied() {
        application.addBasketItem(3, apple);
        application.addBasketItem(2, soup);
        application.addBasketItem(1, bread);

        assertThat(application.priceUp(now().plusDays(5))).isEqualTo(1.97, ACCEPTABLE_OFFSET);
    }

    @Test
    void rejectNegativeQuantitiesWhenAddingToBasket() {
        assertThrows(NegativeQuantitiesNotSupportedException.class, () ->
                application.addBasketItem(-1, apple)
        );
    }

    private LocalDate nextMonth() {
        return now().plusMonths(1);
    }

    private LocalDate lastDayOfMonth(LocalDate date) {
        int lengthOfMonth = date.getMonth().length(date.isLeapYear());
        return date.withDayOfMonth(lengthOfMonth);
    }

}
