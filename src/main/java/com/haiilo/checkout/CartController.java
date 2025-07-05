package com.haiilo.checkout;

import com.haiilo.checkout.dto.CartDto;
import com.haiilo.checkout.service.CartService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

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
        long id = cartService.createCart().getId();
        return new CartDto(id);
    }

    @PostMapping("/{cartId}/items/{itemId}")
    public void scanItem(@PathVariable Long id) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @GetMapping("/{cartId}")
    public void total(@PathVariable Long id) {
        throw new UnsupportedOperationException("Not implemented yet");
    }
}