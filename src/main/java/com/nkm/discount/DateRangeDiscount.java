package com.nkm.discount;

import com.nkm.Basket;

import java.time.LocalDate;
import java.time.Period;

public class DateRangeDiscount implements Discount, DateSensitive {

    private final LocalDate startDate;
    private final LocalDate endDate;
    private final Discount delegate;

    public DateRangeDiscount(LocalDate startDate, Period validPeriod, Discount delegate) {
        this.startDate = startDate;
        this.endDate = startDate.plus(validPeriod);
        this.delegate = delegate;
    }

    public DateRangeDiscount(LocalDate startDate, Discount delegate) {
        this(startDate, Period.ofDays(0), delegate);
    }

    @Override
    public double apply(Basket basket) {
        return delegate.apply(basket);
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
