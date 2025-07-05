package com.haiilo.checkout.strategy;

import com.haiilo.checkout.domainobject.Item;

import java.math.BigDecimal;

public interface PricingStrategy {
    BigDecimal calculateTotalPrice(Item item, int quantity);
}
