package com.haiilo.checkout.unit.mapper;

import com.haiilo.checkout.domainobject.Cart;
import com.haiilo.checkout.domainobject.CartItem;
import com.haiilo.checkout.dto.CartDto;
import com.haiilo.checkout.dto.CartItemDto;
import com.haiilo.checkout.mapper.CartMapper;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static com.haiilo.checkout.TestData.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class CartMapperTest {

    @Test
    void shouldMapCartItemToDto() {

        CartItem cartItem = CartItem.builder()
                .item(APPLE_ITEM)
                .quantity(2)
                .build();

        CartItemDto dto = CartMapper.mapToCartItemDto(cartItem);

        assertEquals(APPLE_ITEM.getId(),1L);
        assertEquals(APPLE_ITEM.getName(), dto.name());
        assertEquals(APPLE_ITEM.getUnitPrice(), dto.unitPrice());
        assertEquals(2, dto.quantity());
    }

    @Test
    void shouldMapCartItemListToDtoList() {
        CartItem cartItem = CartItem.builder()
                .item(APPLE_ITEM)
                .quantity(2)
                .build();

        List<CartItemDto> dtos = CartMapper.mapToCartItemDtoList(List.of(cartItem));

        assertEquals(1, dtos.size());
        CartItemDto dto = dtos.getFirst();
        assertEquals(APPLE_ITEM.getId(), dto.itemId());
        assertEquals(APPLE_ITEM.getName(), dto.name());
        assertEquals(APPLE_ITEM.getUnitPrice(), dto.unitPrice());
        assertEquals(2, dto.quantity());
    }

    @Test
    void shouldMapCartToDto() {
        CartItem cartItem = CartItem.builder()
                .item(KIWI_ITEM)
                .quantity(5)
                .build();

        Cart cart = Cart.builder()
                .id(10L)
                .cartItems(List.of(cartItem))
                .totalPrice(new BigDecimal("100.00"))
                .build();

        CartDto cartDto = CartMapper.mapToCartDto(cart);

        assertEquals(10L, cartDto.id());
        assertEquals(new BigDecimal("100.00"), cartDto.totalPrice());
        assertEquals(1, cartDto.items().size());

        CartItemDto dto = cartDto.items().getFirst();
        assertEquals(KIWI_ITEM.getId(), dto.itemId());
        assertEquals(KIWI_ITEM.getName(), dto.name());
        assertEquals(KIWI_ITEM.getUnitPrice(), dto.unitPrice());
        assertEquals(5, dto.quantity());
    }
}