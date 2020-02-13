package com.nkm;

import java.time.LocalDate;

public interface Discount {

    double apply(Basket basket);

    boolean isValid(LocalDate date);
}
