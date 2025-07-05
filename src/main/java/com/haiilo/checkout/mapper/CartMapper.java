package com.haiilo.checkout.mapper;

import com.haiilo.checkout.domainobject.Cart;
import com.haiilo.checkout.domainobject.CartItem;
import com.haiilo.checkout.dto.CartDto;
import com.haiilo.checkout.dto.CartItemDto;

import java.util.List;
import java.util.stream.Collectors;

public class CartMapper {
    public static CartItemDto mapToCartItemDto(CartItem cartItem) {
        return CartItemDto.builder()
                .itemId(cartItem.getItem().getId())
                .name(cartItem.getItem().getName())
                .unitPrice(cartItem.getItem().getUnitPrice())
                .quantity(cartItem.getQuantity())
                .build();
    }

    public static CartDto mapToCartDto(Cart cart) {
        return CartDto.builder()
                .id(cart.getId())
                .items(mapToCartItemDtoList(cart.getCartItems()))
                .totalPrice(cart.getTotalPrice())
                .build();
    }

    public static List<CartItemDto> mapToCartItemDtoList(List<CartItem> cartItems)
    {
        return cartItems.stream()
                .map(CartMapper::mapToCartItemDto)
                .collect(Collectors.toList());
    }
}
