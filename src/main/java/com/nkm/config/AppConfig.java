package com.nkm.config;

import com.nkm.Application;
import com.nkm.discount.DateRangeDiscount;
import com.nkm.discount.FixedPercentageItemDiscount;
import com.nkm.discount.MultiBuyGetItemDiscount;
import com.nkm.pricing.PricingService;

import java.time.LocalDate;
import java.time.Period;

import static java.time.LocalDate.now;

public class AppConfig {

    private final LocalDate threeDaysHence = now().plusDays(3);
    private final LocalDate yesterday = now().minusDays(1);
    private final Period untilEndOfFollowingMonth = Period.between(threeDaysHence, lastDayOfMonth(now().plusMonths(1)));
    private final FixedPercentageItemDiscount tenPercentOffApplesOffer = new FixedPercentageItemDiscount("Apple", 0.1);
    private final MultiBuyGetItemDiscount SoupMultiBuyOffer = new MultiBuyGetItemDiscount(2, "Soup", 0.5, "Bread");

    public Application getApplication() {
        return new Application(
                new PricingService(
                        new DateRangeDiscount(threeDaysHence, untilEndOfFollowingMonth, tenPercentOffApplesOffer),
                        new DateRangeDiscount(yesterday, Period.ofDays(6), SoupMultiBuyOffer)
                ));
    }

    private LocalDate lastDayOfMonth(LocalDate date) {
        int lengthOfMonth = date.getMonth().length(date.isLeapYear());
        return date.withDayOfMonth(lengthOfMonth);
    }

}
