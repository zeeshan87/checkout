package com.haiilo.checkout.unit.service;

import com.haiilo.checkout.domainobject.Cart;
import com.haiilo.checkout.domainobject.CartItem;
import com.haiilo.checkout.exception.CartNotFoundException;
import com.haiilo.checkout.exception.ItemNotFoundException;
import com.haiilo.checkout.repository.CartRepository;
import com.haiilo.checkout.repository.ItemRepository;
import com.haiilo.checkout.service.CartService;
import com.haiilo.checkout.strategy.PricingStrategy;
import com.haiilo.checkout.strategy.PricingStrategyFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static com.haiilo.checkout.TestData.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CartServiceTest {

    @Mock
    private CartRepository cartRepository;

    @Mock
    private ItemRepository itemRepository;

    @Mock
    private PricingStrategyFactory pricingStrategyFactory;

    @Mock
    private PricingStrategy pricingStrategy;

    @InjectMocks
    private CartService cartService;

    @AfterEach
    void verifyNoExtraInteractions() {
        verifyNoMoreInteractions(cartRepository, itemRepository, pricingStrategyFactory, pricingStrategy);
    }

    @Test
    void testCreateCart() {
        Cart unsavedCart = Cart.builder().build();
        Cart savedCart = Cart.builder()
                .id(1L)
                .createdAt(NOW)
                .build();

        when(cartRepository.save(unsavedCart)).thenReturn(savedCart);

        Cart result = cartService.createCart();

        assertEquals(savedCart, result);
        verify(cartRepository).save(unsavedCart);
    }

    @Test
    void testAddItemToEmptyCartCreatesNewCartItem() {
        Cart cart = Cart.builder()
                .id(1L)
                .createdAt(NOW)
                .updatedAt(NOW)
                .build();
        CartItem cartItem = CartItem.builder()
                .item(APPLE_ITEM)
                .cart(cart)
                .quantity(1)
                .build();
        Cart updatedCart = Cart.builder()
                .id(1L)
                .cartItems(List.of(cartItem))
                .totalPrice(new BigDecimal("30"))
                .createdAt(NOW)
                .updatedAt(NOW)
                .build();

        when(cartRepository.findById(1L)).thenReturn(Optional.of(cart));
        when(itemRepository.findById(APPLE_ITEM.getId())).thenReturn(Optional.of(APPLE_ITEM));
        when(pricingStrategyFactory.getStrategy(APPLE_ITEM)).thenReturn(pricingStrategy);
        when(pricingStrategy.calculateTotalPrice(APPLE_ITEM, 1)).thenReturn(BigDecimal.valueOf(30));
        when(cartRepository.save(updatedCart)).thenReturn(updatedCart);

        Cart result = cartService.addItem(1L, 1L);

        assertEquals(updatedCart, result);
        verify(cartRepository).findById(1L);
        verify(itemRepository).findById(1L);
        verify(pricingStrategyFactory).getStrategy(APPLE_ITEM);
        verify(pricingStrategy).calculateTotalPrice(APPLE_ITEM, 1);
        verify(cartRepository).save(updatedCart);
    }

    @Test
    void testAddItemToCartIncreasesQuantity() {
        Cart cart = Cart.builder()
                .id(1L)
                .totalPrice(new BigDecimal("70.00"))
                .createdAt(NOW)
                .updatedAt(NOW)
                .build();
        CartItem existingAppleItem = CartItem.builder()
                .id(1L)
                .item(APPLE_ITEM)
                .cart(cart)
                .quantity(1)
                .createdAt(NOW)
                .updatedAt(NOW)
                .build();
        CartItem existingKiwiItem = CartItem.builder()
                .id(2L)
                .item(KIWI_ITEM)
                .cart(cart)
                .quantity(2)
                .createdAt(NOW)
                .updatedAt(NOW)
                .build();
        cart.setCartItems(List.of(existingAppleItem, existingKiwiItem));


        CartItem updatedAppleItem = CartItem.builder()
                .id(1L)
                .item(APPLE_ITEM)
                .cart(cart)
                .quantity(2)
                .createdAt(NOW)
                .updatedAt(NOW)
                .build();

        Cart updatedCart = Cart.builder()
                .id(1L)
                .cartItems(List.of(updatedAppleItem, existingKiwiItem))
                .totalPrice(new BigDecimal("85.00"))
                .createdAt(NOW)
                .updatedAt(NOW)
                .build();

        when(cartRepository.findById(1L)).thenReturn(Optional.of(cart));
        when(itemRepository.findById(APPLE_ITEM.getId())).thenReturn(Optional.of(APPLE_ITEM));
        when(pricingStrategyFactory.getStrategy(APPLE_ITEM)).thenReturn(pricingStrategy);
        when(pricingStrategyFactory.getStrategy(KIWI_ITEM)).thenReturn(pricingStrategy);
        when(pricingStrategy.calculateTotalPrice(APPLE_ITEM, 2)).thenReturn(new BigDecimal("45.00"));
        when(pricingStrategy.calculateTotalPrice(KIWI_ITEM, 2)).thenReturn(new BigDecimal("40.00"));
        when(cartRepository.save(updatedCart)).thenReturn(updatedCart);

        Cart result = cartService.addItem(1L, 1L);

        assertEquals(updatedCart, result);
        verify(cartRepository).findById(1L);
        verify(itemRepository).findById(1L);
        verify(pricingStrategyFactory).getStrategy(APPLE_ITEM);
        verify(pricingStrategyFactory).getStrategy(KIWI_ITEM);
        verify(pricingStrategy).calculateTotalPrice(APPLE_ITEM, 2);
        verify(pricingStrategy).calculateTotalPrice(KIWI_ITEM, 2);
        verify(cartRepository).save(updatedCart);
    }


    @Test
    void testAddItemToNonexistentCartThrowsException() {
        when(cartRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(CartNotFoundException.class, () -> cartService.addItem(99L, 100L));

        verify(cartRepository).findById(99L);
    }

    @Test
    void testAddNonexistentItemToCartThrowsException() {
        Cart cart = Cart.builder().id(1L).build();
        when(cartRepository.findById(1L)).thenReturn(Optional.of(cart));
        when(itemRepository.findById(999L)).thenReturn(Optional.empty());

        assertThrows(ItemNotFoundException.class, () -> cartService.addItem(1L, 999L));

        verify(cartRepository).findById(1L);
        verify(itemRepository).findById(999L);
    }
}

