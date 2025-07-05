package com.haiilo.checkout.service;

import com.haiilo.checkout.domainobject.Cart;
import com.haiilo.checkout.repository.CartRepository;
import org.springframework.stereotype.Service;

@Service
public class CartService {
    private final CartRepository cartRepository;
    public CartService(CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }

    public Cart createCart() {
        Cart cart = new Cart();
        return cartRepository.save(cart);
    }
}
