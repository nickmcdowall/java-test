package com.nkm;

import com.nkm.config.Config;
import com.nkm.item.Item;
import org.assertj.core.data.Offset;
import org.junit.jupiter.api.Test;

import static java.time.LocalDate.now;
import static org.assertj.core.api.Assertions.assertThat;

public class AcceptanceTests {

    private final Config appConfig = new Config();
    private final Application application = appConfig.getApplication();
    private final Item tinSoup = appConfig.getTinSoup();
    private final Item breadLoaf = appConfig.getBreadLoaf();

    @Test
    void multiTinSoupOffer() {
        application.addBasketItem(3, tinSoup);
        application.addBasketItem(2, breadLoaf);
        assertThat(application.priceUp(now())).isEqualTo(3.15, Offset.offset(0.001));
    }

}
