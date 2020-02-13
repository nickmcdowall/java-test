package com.nkm;

import java.time.Period;
import java.util.function.Function;

public class DateRangeDiscount implements Discount {

    private final Function<Basket, Double> calculation;

    public DateRangeDiscount(Period validPeriod, Function<Basket, Double> calculation) {
        this.calculation = calculation;
    }

    @Override
    public double apply(Basket basket) {
        return calculation.apply(basket);
    }
}
