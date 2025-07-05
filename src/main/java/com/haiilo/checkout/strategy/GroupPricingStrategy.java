package com.haiilo.checkout.strategy;

import com.haiilo.checkout.domainobject.Item;
import com.haiilo.checkout.domainobject.Offer;
import com.haiilo.checkout.exception.OfferNotValidException;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component()
public class GroupPricingStrategy implements PricingStrategy {
    public static final String STRATEGY_NAME = "GROUP_PRICING";
    @Override
    public BigDecimal calculateTotalPrice(Item item, int quantity) {
        Offer offer = item.getOffer();
        if (offer == null || offer.getQuantity() == null || offer.getSpecialPrice() == null) {
            throw new OfferNotValidException("No valid group pricing offer exist for item: " + item.getName());
        }

        int groups = quantity / offer.getQuantity();
        int remainingItems = quantity % offer.getQuantity();

        BigDecimal groupsSubTotal = BigDecimal.valueOf(groups).multiply(offer.getSpecialPrice());
        BigDecimal remainingItemsSubTotal = BigDecimal.valueOf(remainingItems).multiply(item.getUnitPrice());

        return groupsSubTotal.add(remainingItemsSubTotal) ;
    }
}
