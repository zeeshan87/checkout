package com.haiilo.checkout.exception;

public class ItemNotFoundException extends RuntimeException {
    public ItemNotFoundException(long id) {
        super("Item with id " + id + " not found");
    }
}