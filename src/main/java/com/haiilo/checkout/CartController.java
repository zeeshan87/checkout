package com.haiilo.checkout;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cart")
public class DeviceController {
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CartDto createDevice() {
        throw new UnsupportedOperationException("Not implemented yet");
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