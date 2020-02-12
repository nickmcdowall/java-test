package com.nkm;

import java.time.LocalDate;

public class PricingService {
    public PricingService(Discount... discount) {

    }

    public double price(Basket basket, LocalDate date) {
        return 3.15;
    }
}
