package com.haiilo.checkout.strategy;

import com.haiilo.checkout.domainobject.Item;
import org.springframework.stereotype.Component;

@Component
public class PricingStrategyFactory {

    private final UnitPricingStrategy unitPricingStrategy;
    private final GroupPricingStrategy groupPricingStrategy;

    public PricingStrategyFactory(UnitPricingStrategy unitPricingStrategy, GroupPricingStrategy groupPricingStrategy) {
        this.unitPricingStrategy = unitPricingStrategy;
        this.groupPricingStrategy = groupPricingStrategy;
    }

    public PricingStrategy getStrategy(Item item) {
        String offerType = item.getOffer() != null ? item.getOffer().getOfferType() : "_";

        return switch (offerType) {
            case GroupPricingStrategy.STRATEGY_NAME -> groupPricingStrategy;
            default -> unitPricingStrategy;
        };
    }
}
