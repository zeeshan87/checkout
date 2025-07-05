package com.haiilo.checkout.exception;

import com.haiilo.checkout.dto.ErrorResponseDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CheckoutExceptionHandler {
    Logger logger = LoggerFactory.getLogger(CheckoutExceptionHandler.class);

    @ExceptionHandler(CartNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponseDto handleCartNotFoundException(CartNotFoundException exception) {
        return new ErrorResponseDto("cart_not_found", exception.getMessage());
    }

    @ExceptionHandler(ItemNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponseDto handleItemNotFoundException(ItemNotFoundException exception) {
        return new ErrorResponseDto("item_not_found", exception.getMessage());
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponseDto handleExceptions(ItemNotFoundException exception) {
        logger.error("An unexpected error occurred: {}", exception.getMessage(), exception);
        return new ErrorResponseDto("error", "An unexpected error occurred");
    }
}


