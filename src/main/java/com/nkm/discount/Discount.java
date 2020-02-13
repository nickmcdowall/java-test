package com.nkm.discount;

import com.nkm.Basket;

import java.time.LocalDate;

public interface Discount {

    double apply(Basket basket);

    boolean isValid(LocalDate date);
}
