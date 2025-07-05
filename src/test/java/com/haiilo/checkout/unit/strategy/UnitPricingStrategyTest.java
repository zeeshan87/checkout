package com.haiilo.checkout.unit.strategy;

import com.haiilo.checkout.strategy.UnitPricingStrategy;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static com.haiilo.checkout.TestData.KIWI_ITEM;
import static org.junit.jupiter.api.Assertions.assertEquals;

class UnitPricingStrategyTest {

    private final UnitPricingStrategy strategy = new UnitPricingStrategy();

    @Test
    void shouldCalculateTotalUsingUnitPriceOnly() {
        BigDecimal total = strategy.calculateTotalPrice(KIWI_ITEM, 3);

        assertEquals(new BigDecimal("60.00"), total);
    }
}
