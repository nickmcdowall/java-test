package com.nkm;

import com.nkm.pricing.PricingService;
import com.nkm.stock.Basket;
import com.nkm.stock.Item;

import java.time.LocalDate;

public class Application {

    private PricingService pricingService;
    private Basket basket = new Basket();

    public Application(PricingService pricingService) {
        this.pricingService = pricingService;
    }

    public void addBasketItem(int count, Item item) {
        if (0 > count)
            throw new NegativeQuantitiesNotSupportedException();
        basket.add(count, item);
    }

    public double priceUp(LocalDate whenBought) {
        return pricingService.price(basket, whenBought);
    }
}
