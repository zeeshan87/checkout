package com.haiilo.checkout.strategy;

import com.haiilo.checkout.domainobject.Item;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component(UnitPricingStrategy.STRATEGY_NAME)
public class UnitPricingStrategy implements PricingStrategy {
    public static final String STRATEGY_NAME = "UNIT_PRICING";
    @Override
    public BigDecimal calculateTotalPrice(Item item, int quantity) {
        return item.getUnitPrice().multiply(BigDecimal.valueOf(quantity));
    }
}
