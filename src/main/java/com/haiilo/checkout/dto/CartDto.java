package com.haiilo.checkout.dto;

import lombok.Builder;

import java.math.BigDecimal;
import java.util.List;

@Builder
public record CartDto(long id, List<CartItemDto> items, BigDecimal totalPrice) {

}
