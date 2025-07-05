package com.haiilo.checkout.service;

import com.haiilo.checkout.domainobject.Cart;
import com.haiilo.checkout.domainobject.CartItem;
import com.haiilo.checkout.domainobject.Item;
import com.haiilo.checkout.exception.CartNotFoundException;
import com.haiilo.checkout.exception.ItemNotFoundException;
import com.haiilo.checkout.repository.CartRepository;
import com.haiilo.checkout.repository.ItemRepository;
import com.haiilo.checkout.strategy.PricingStrategy;
import com.haiilo.checkout.strategy.PricingStrategyFactory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class CartService {
    private final CartRepository cartRepository;
    private final ItemRepository itemRepository;
    private final PricingStrategyFactory pricingStrategyFactory;

    public CartService(CartRepository cartRepository, ItemRepository itemRepository, PricingStrategyFactory pricingStrategyFactory) {
        this.cartRepository = cartRepository;
        this.itemRepository = itemRepository;
        this.pricingStrategyFactory = pricingStrategyFactory;
    }

    public Cart createCart() {
        Cart cart = Cart.builder().build();
        return cartRepository.save(cart);
    }

    public Cart addItem(long cartId, long itemId) {
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new CartNotFoundException(cartId));

        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new ItemNotFoundException(itemId));

        increaseItemQuantityInCart(itemId, cart, item);
        cart.setTotalPrice(calculateTotal(cart));

        return cartRepository.save(cart);
    }

    private void increaseItemQuantityInCart(long itemId, Cart cart, Item item) {
        CartItem existing = cart.getCartItems().stream()
                .filter(ci -> ci.getItem().getId().equals(itemId))
                .findFirst()
                .orElse(null);

        if (existing != null) {
            existing.setQuantity(existing.getQuantity() + 1);
        } else {
            CartItem newItem = CartItem.builder()
                    .cart(cart)
                    .item(item)
                    .quantity(1)
                    .build();
            cart.getCartItems().add(newItem);
        }
    }

    private BigDecimal calculateTotal(Cart cart) {
        return cart.getCartItems().stream()
                .map(ci -> {
                    PricingStrategy strategy = pricingStrategyFactory.getStrategy(ci.getItem());
                    return strategy.calculateTotalPrice(ci.getItem(), ci.getQuantity());
                })
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
