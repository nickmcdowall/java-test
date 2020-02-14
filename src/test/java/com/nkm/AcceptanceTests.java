package com.nkm;

import com.nkm.config.Config;
import com.nkm.item.Item;
import org.assertj.core.data.Offset;
import org.junit.jupiter.api.Test;

import static java.time.LocalDate.now;
import static org.assertj.core.api.Assertions.assertThat;

public class AcceptanceTests {

    private static final Offset<Double> ACCEPTABLE_OFFSET = Offset.offset(0.001);

    private final Config appConfig = new Config();
    private final Application application = appConfig.getApplication();
    private final Item tinSoup = appConfig.getTinSoup();
    private final Item breadLoaf = appConfig.getBreadLoaf();

    @Test
    void multiTinSoupOfferBoughtNow() {
        application.addBasketItem(3, tinSoup);
        application.addBasketItem(2, breadLoaf);
        assertThat(application.priceUp(now())).isEqualTo(3.15, ACCEPTABLE_OFFSET);
    }

    @Test
    void multiTinSoupOfferBoughtBeforeDiscountApplies() {
        application.addBasketItem(3, tinSoup);
        application.addBasketItem(2, breadLoaf);
        assertThat(application.priceUp(now().minusDays(2))).isEqualTo(3.55, ACCEPTABLE_OFFSET);
    }

    @Test
    void multiTinSoupOfferBoughtAfterDiscountApplies() {
        application.addBasketItem(3, tinSoup);
        application.addBasketItem(2, breadLoaf);
        assertThat(application.priceUp(now().plusDays(6))).isEqualTo(3.55, ACCEPTABLE_OFFSET);
    }



}
