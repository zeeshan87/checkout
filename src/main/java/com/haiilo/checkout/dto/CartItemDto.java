package com.haiilo.checkout.dto;

import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record CartItemDto(long itemId, String name, int quantity, BigDecimal unitPrice) {
}
