package com.nkm.config;

import com.nkm.Application;
import com.nkm.discount.DateRangeDiscount;
import com.nkm.discount.FixedPercentageDiscount;
import com.nkm.discount.MultiBuyGetItemDiscount;
import com.nkm.pricing.PricingService;

import static com.nkm.convenience.DescriptiveRelativeDates.*;

public class ApplicationConfig {

    private final FixedPercentageDiscount tenPercentOffApplesOffer = new FixedPercentageDiscount("Apple", 0.1);
    private final MultiBuyGetItemDiscount SoupMultiBuyOffer = new MultiBuyGetItemDiscount(2, "Soup", 0.5, "Bread");

    public Application getApplication() {
        return new Application(
                new PricingService(
                        new DateRangeDiscount(threeDaysHence(), lastDayOfNextMonth(), tenPercentOffApplesOffer),
                        new DateRangeDiscount(yesterday(), yesterday().plusDays(7), SoupMultiBuyOffer)
                ));
    }

}
