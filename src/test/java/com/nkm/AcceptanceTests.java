package com.nkm;

import com.nkm.discount.DateRangeDiscount;
import com.nkm.discount.FixedPercentageItemDiscount;
import com.nkm.discount.MultiBuyGetItemDiscount;
import com.nkm.item.Apple;
import com.nkm.item.BreadLoaf;
import com.nkm.item.TinSoup;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.Period;

import static java.time.LocalDate.now;
import static org.assertj.core.api.Assertions.assertThat;

public class AcceptanceTests {

    private final TinSoup tinSoup = new TinSoup(65);
    private final BreadLoaf breadLoaf = new BreadLoaf(80);
    private LocalDate threeDaysHence = now().plusDays(3);
    private LocalDate yesterday = now().minusDays(1);
    private Period untilEndOfFollowingMonth = Period.between(now(), lastDayOfMonth(now().plusMonths(1)));

    private LocalDate lastDayOfMonth(LocalDate date) {
        int lengthOfMonth = date.getMonth().length(date.isLeapYear());
        return date.withDayOfMonth(lengthOfMonth);
    }

    private FixedPercentageItemDiscount tenPercentOffApplesOffer = new FixedPercentageItemDiscount(Apple.class, 10.0);
    private MultiBuyGetItemDiscount tinSoupMultiBuyOffer = new MultiBuyGetItemDiscount(2, TinSoup.class, 0.5, BreadLoaf.class);
    private DateRangeDiscount appleOffer = new DateRangeDiscount(threeDaysHence, untilEndOfFollowingMonth, tenPercentOffApplesOffer);
    private DateRangeDiscount multiSoupOffer = new DateRangeDiscount(yesterday, Period.ofDays(7), tinSoupMultiBuyOffer);

    @Test
    void multiTinSoupOffer() {
        Application application = new Application(new PricingService(multiSoupOffer, appleOffer));
        application.addBasketItem(3, tinSoup);
        application.addBasketItem(2, breadLoaf);
        assertThat(application.priceUp(now())).isEqualTo(3.15);
    }

}
