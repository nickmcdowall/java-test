package com.nkm.discount;

import com.nkm.Basket;

import java.time.LocalDate;
import java.time.Period;
import java.util.function.Function;

public class DateRangeDiscount implements Discount {

    private final LocalDate startDate;
    private final LocalDate endDate;
    private final Function<Basket, Double> calculation;

    public DateRangeDiscount(LocalDate startDate, Period validPeriod, Function<Basket, Double> calculation) {
        this.startDate = startDate;
        this.endDate = startDate.plus(validPeriod);
        this.calculation = calculation;
    }

    public DateRangeDiscount(LocalDate startDate, Function<Basket, Double> calculation) {
        this(startDate, Period.ofDays(0), calculation);
    }

    @Override
    public double apply(Basket basket) {
        return calculation.apply(basket);
    }

    @Override
    public boolean isValid(LocalDate date) {
        return betweenRange(date) || onStartOrEnd(date);
    }

    private boolean onStartOrEnd(LocalDate date) {
        return date.isEqual(startDate) || date.isEqual(endDate);
    }

    private boolean betweenRange(LocalDate date) {
        return date.isAfter(startDate) && date.isBefore(endDate);
    }
}
