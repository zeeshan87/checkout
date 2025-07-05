package com.haiilo.checkout.unit.strategy;

import com.haiilo.checkout.domainobject.Item;
import com.haiilo.checkout.domainobject.Offer;
import com.haiilo.checkout.exception.OfferNotValidException;
import com.haiilo.checkout.strategy.GroupPricingStrategy;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static com.haiilo.checkout.TestData.APPLE_ITEM;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class GroupPricingStrategyTest {

    private final GroupPricingStrategy strategy = new GroupPricingStrategy();

    @Test
    void shouldApplyGroupOfferCorrectly() {

        BigDecimal total = strategy.calculateTotalPrice(APPLE_ITEM, 3);

        assertEquals(new BigDecimal("75.00"), total); // 2 for 45, 1 for 30

    }

    @Test
    void shouldThrowWhenOfferIsInvalid() {
        Item item = Item.builder()
                .name("Apple")
                .unitPrice(new BigDecimal("30.00"))
                .offer(Offer.builder().offerType(GroupPricingStrategy.STRATEGY_NAME).build())
                .build();

        assertThrows(OfferNotValidException.class, ()-> strategy.calculateTotalPrice(item,5));

    }

    @Test
    void shouldThrowWhenOfferIsNull() {
        Item item = Item.builder()
                .name("Apple")
                .unitPrice(new BigDecimal("30.00"))
                .offer(null)
                .build();

        assertThrows(OfferNotValidException.class, ()-> strategy.calculateTotalPrice(item,5));
    }
}
