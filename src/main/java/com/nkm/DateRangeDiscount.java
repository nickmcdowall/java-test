package com.nkm;

import java.time.Period;
import java.util.function.Function;

public class DateRangeDiscount implements Discount {

    public DateRangeDiscount(Period validPeriod, Function<Basket, Double> calculation) {
    }

    @Override
    public double apply(Basket basket) {
        return 0;
    }
}
