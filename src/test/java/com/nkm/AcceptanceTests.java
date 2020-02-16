package com.nkm;

import com.nkm.config.ApplicationConfig;
import com.nkm.config.StockRepository;
import com.nkm.stock.Item;
import org.assertj.core.data.Offset;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static com.nkm.convenience.DescriptiveRelativeDates.lastDayOfMonth;
import static com.nkm.convenience.DescriptiveRelativeDates.nextMonth;
import static java.time.LocalDate.now;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class AcceptanceTests {

    private static final Offset<Double> ACCEPTABLE_ROUNDING_OFFSET = Offset.offset(0.0000001);

    private final Application application = new ApplicationConfig().getApplication();
    private final StockRepository stockRepository = new StockRepository();
    private final Item soup = stockRepository.getItemByKey("Soup");
    private final Item bread = stockRepository.getItemByKey("Bread");
    private final Item apple = stockRepository.getItemByKey("Apple");
    private final Item milk = stockRepository.getItemByKey("Milk");

    @ParameterizedTest
    @ValueSource(ints = {-1, 0, +1, +2, +3, +4, +5, +6})
    void multiSoupOfferWhenBoughtInDiscountWindow(int relativePurchaseDate) {
        application.addBasketItem(3, soup);
        application.addBasketItem(2, bread);

        assertThat(application.priceUp(now().plusDays(relativePurchaseDate))).isEqualTo(3.15, ACCEPTABLE_ROUNDING_OFFSET);
    }

    @ParameterizedTest
    @ValueSource(ints = {-2, +7})
    void multiSoupOfferNotAppliedWhenBoughtOutsideDiscountWindow(int relativePurchaseDate) {
        application.addBasketItem(3, soup);
        application.addBasketItem(2, bread);

        assertThat(application.priceUp(now().plusDays(relativePurchaseDate))).isEqualTo(3.55, ACCEPTABLE_ROUNDING_OFFSET);
    }

    @ParameterizedTest
    @ValueSource(ints = {0, +1, +2})
    void appleDiscountNotAppliedWhenBoughtBeforeDiscountWindow(int relativePurchaseDate) {
        application.addBasketItem(6, apple);
        application.addBasketItem(1, milk);

        assertThat(application.priceUp(now().plusDays(relativePurchaseDate))).isEqualTo(1.9, ACCEPTABLE_ROUNDING_OFFSET);
    }

    @ParameterizedTest
    @ValueSource(ints = {+3, +5, +20})
    void appleDiscountIsAppliedDuringDiscountWindow(int relativePurchaseDate) {
        application.addBasketItem(6, apple);
        application.addBasketItem(1, milk);

        assertThat(application.priceUp(now().plusDays(relativePurchaseDate))).isEqualTo(1.84, ACCEPTABLE_ROUNDING_OFFSET);
    }

    @Test
    void appleDiscountIsAppliedOnLastDayNextMonth() {
        application.addBasketItem(6, apple);
        application.addBasketItem(1, milk);

        assertThat(application.priceUp(lastDayOfMonth(nextMonth()))).isEqualTo(1.84, ACCEPTABLE_ROUNDING_OFFSET);
    }

    @Test
    void appleDiscountIsNotAppliedAfterLastDayNextMonth() {
        application.addBasketItem(6, apple);
        application.addBasketItem(1, milk);

        assertThat(application.priceUp(lastDayOfMonth(nextMonth()).plusDays(1))).isEqualTo(1.9, ACCEPTABLE_ROUNDING_OFFSET);
    }

    @Test
    void multiDiscountsApplied() {
        application.addBasketItem(3, apple);
        application.addBasketItem(2, soup);
        application.addBasketItem(1, bread);

        assertThat(application.priceUp(now().plusDays(5))).isEqualTo(1.97, ACCEPTABLE_ROUNDING_OFFSET);
    }

    @Test
    void rejectNegativeQuantitiesWhenAddingToBasket() {
        assertThrows(NegativeQuantitiesNotSupportedException.class, () ->
                application.addBasketItem(-1, apple)
        );
    }

}
