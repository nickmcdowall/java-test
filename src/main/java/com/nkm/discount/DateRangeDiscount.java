package com.nkm.discount;

import com.nkm.stock.Basket;

import java.time.LocalDate;

public class DateRangeDiscount implements Discount, DateSensitive {

    private final LocalDate startDate;
    private final LocalDate endDate;
    private final Discount delegate;

    public DateRangeDiscount(LocalDate startDate, LocalDate endDate, Discount delegate) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.delegate = delegate;
    }

    public DateRangeDiscount(LocalDate startDate, Discount delegate) {
        this(startDate, startDate, delegate);
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
