package com.nkm.discount;

import com.nkm.stock.Basket;

public interface Discount {

    double apply(Basket basket);

}
