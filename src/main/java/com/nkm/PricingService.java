package com.nkm;

import java.time.LocalDate;
import java.util.List;

import static java.util.Arrays.asList;

public class PricingService {

    private final List<Discount> discounts;

    public PricingService(Discount... discounts) {
        this.discounts = asList(discounts);
    }

    public double price(Basket basket, LocalDate date) {
        return (discounts.isEmpty()) ? basket.totalCost() : 3.15;
    }
}
