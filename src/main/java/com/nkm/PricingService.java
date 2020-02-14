package com.nkm;

import com.nkm.discount.DateSensitive;
import com.nkm.discount.Discount;

import java.time.LocalDate;
import java.util.List;

import static java.util.Arrays.asList;

public class PricingService {

    private final List<Discount> discounts;

    public PricingService(Discount... discounts) {
        this.discounts = asList(discounts);
    }

    public double price(Basket basket, LocalDate date) {
        double totalDiscount = discounts.stream()
                .filter(discount -> validForGivenDate(date, discount))
                .mapToDouble(discount -> discount.apply(basket))
                .sum();

        return basket.totalCost() - totalDiscount;
    }

    private boolean validForGivenDate(LocalDate date, Discount discount) {
        if (discount instanceof DateSensitive) {
            return ((DateSensitive) discount).isValid(date);
        }
        return true;
    }
}
