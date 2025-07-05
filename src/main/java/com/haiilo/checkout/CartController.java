package com.haiilo.checkout;

import com.haiilo.checkout.domainobject.Cart;
import com.haiilo.checkout.dto.CartDto;
import com.haiilo.checkout.service.CartService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import static com.haiilo.checkout.mapper.CartMapper.mapToCartDto;

@RestController
@RequestMapping("/cart")
public class CartController {
    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CartDto createDevice() {
        Cart cart = cartService.createCart();
        return mapToCartDto(cart);
    }

    @PutMapping("/{cartId}/items/{itemId}")
    public CartDto scanItem(@PathVariable long cartId, @PathVariable long itemId) {
        Cart cart = cartService.addItem(cartId, itemId);
        return mapToCartDto(cart);
    }
}