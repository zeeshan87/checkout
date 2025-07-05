package com.haiilo.checkout;

import com.haiilo.checkout.domainobject.Item;
import com.haiilo.checkout.domainobject.Offer;
import com.haiilo.checkout.strategy.GroupPricingStrategy;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class TestData {
    public static final LocalDateTime NOW = LocalDateTime.of(2024, 12, 25, 10, 30, 0);

    public static final Offer APPLE_OFFER = Offer.builder()
            .id(1L)
            .offerType(GroupPricingStrategy.STRATEGY_NAME)
            .quantity(2)
            .specialPrice(new BigDecimal("45.00"))
            .createdAt(NOW)
            .build();

    public static final Item APPLE_ITEM = Item.builder()
            .id(1L)
            .name("Apple")
            .unitPrice(new BigDecimal("30.00"))
            .offer(APPLE_OFFER)
            .createdAt(NOW)
            .updatedAt(NOW)
            .build();

    public static final Item KIWI_ITEM = Item.builder()
            .id(4L)
            .name("Kiwi")
            .unitPrice(new BigDecimal("20.00"))
            .createdAt(NOW)
            .updatedAt(NOW)
            .build();
}
