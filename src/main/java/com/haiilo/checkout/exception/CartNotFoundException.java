package com.haiilo.checkout.exception;

public class CartNotFoundException extends RuntimeException {
    public CartNotFoundException(long id) {
        super("Cart with id " + id + " not found");
    }
}
