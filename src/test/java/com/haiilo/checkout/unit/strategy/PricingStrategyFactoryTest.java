package com.haiilo.checkout.unit.strategy;

import com.haiilo.checkout.domainobject.Item;
import com.haiilo.checkout.strategy.GroupPricingStrategy;
import com.haiilo.checkout.strategy.PricingStrategy;
import com.haiilo.checkout.strategy.PricingStrategyFactory;
import com.haiilo.checkout.strategy.UnitPricingStrategy;
import org.junit.jupiter.api.Test;

import static com.haiilo.checkout.TestData.APPLE_ITEM;
import static org.junit.jupiter.api.Assertions.assertEquals;

class PricingStrategyFactoryTest {

    private final UnitPricingStrategy unitPricing = new UnitPricingStrategy();
    private final GroupPricingStrategy groupPricing = new GroupPricingStrategy();
    private final PricingStrategyFactory factory = new PricingStrategyFactory(unitPricing, groupPricing);

    @Test
    void shouldReturnGroupStrategyWhenOfferTypeIsGroupPricing() {
        PricingStrategy strategy = factory.getStrategy(APPLE_ITEM);

        assertEquals(GroupPricingStrategy.class, strategy.getClass());
    }

    @Test
    void shouldReturnUnitStrategyWhenNoOfferExists() {
        Item item = Item.builder().offer(null).build();

        PricingStrategy strategy = factory.getStrategy(item);

        assertEquals(UnitPricingStrategy.class, strategy.getClass());
    }
}
