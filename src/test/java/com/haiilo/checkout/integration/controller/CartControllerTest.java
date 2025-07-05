package com.haiilo.checkout.integration.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.haiilo.checkout.dto.CartDto;
import com.haiilo.checkout.dto.CartItemDto;
import com.haiilo.checkout.dto.ErrorResponseDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class CartControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testCreateCart() throws Exception {
        String responseAsString = mockMvc.perform(post("/cart")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();

        CartDto response = objectMapper.readValue(responseAsString, CartDto.class);

        assertNotNull(response.id());
        assertEquals(Collections.EMPTY_LIST, response.items());
        assertEquals(new BigDecimal("0.00"), response.totalPrice());
    }

    @Test
    public void testScanItems() throws Exception {
        // Create a new cart
        String responseAsString = mockMvc.perform(post("/cart")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();

        long cartId = objectMapper.readValue(responseAsString, CartDto.class).id();

        // Scan an apple
        String appleResponseAsString = mockMvc.perform(put("/cart/" + cartId + "/items/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        CartDto appleResponse = objectMapper.readValue(appleResponseAsString, CartDto.class);
        assertEquals(new BigDecimal("30.00"), appleResponse.totalPrice());

        // Scan a banana
        String withFirstBanaAsResponse = mockMvc.perform(put("/cart/" + cartId + "/items/2")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        CartDto withFirstBananaResponse = objectMapper.readValue(withFirstBanaAsResponse, CartDto.class);
        assertEquals(new BigDecimal("80.00"), withFirstBananaResponse.totalPrice());

        for (int i = 0; i < 3; i++) {
            mockMvc.perform(put("/cart/" + cartId + "/items/1")
                            .contentType(MediaType.APPLICATION_JSON)
                            .accept(MediaType.APPLICATION_JSON)
                    )
                    .andExpect(status().isOk());

            // Scan a banana
            mockMvc.perform(put("/cart/" + cartId + "/items/2")
                            .contentType(MediaType.APPLICATION_JSON)
                            .accept(MediaType.APPLICATION_JSON)
                    )
                    .andExpect(status().isOk());
        }

        // Scan a Peach
        String finalResponseAsString = mockMvc.perform(put("/cart/" + cartId + "/items/3")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        CartDto finalResponse = objectMapper.readValue(finalResponseAsString, CartDto.class);
        assertEquals(cartId, finalResponse.id());
        assertEquals(3, finalResponse.items().size());

        CartItemDto appleItem = finalResponse.items().stream()
                .filter(item -> item.itemId() == 1L)
                .findFirst().get();
        assertEquals("Apple", appleItem.name());
        assertEquals(4, appleItem.quantity());
        assertEquals(new BigDecimal("30.00"), appleItem.unitPrice());

        CartItemDto bananaItem = finalResponse.items().stream()
                .filter(item -> item.itemId() == 2L)
                .findFirst().get();
        assertEquals("Banana", bananaItem.name());
        assertEquals(4, bananaItem.quantity());
        assertEquals(new BigDecimal("50.00"), bananaItem.unitPrice());

        CartItemDto peachItem = finalResponse.items().stream()
                .filter(item -> item.itemId() == 3L)
                .findFirst().get();
        assertEquals("Peach", peachItem.name());
        assertEquals(1, peachItem.quantity());
        assertEquals(new BigDecimal("60.00"), peachItem.unitPrice());

        assertEquals(new BigDecimal("330.00"), finalResponse.totalPrice());
    }

    @Test
    public void testScanItemThrowsException() throws Exception {
        String responseAsString = mockMvc.perform(put("/cart/99999/items/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isNotFound())
                .andReturn()
                .getResponse()
                .getContentAsString();

        ErrorResponseDto response = objectMapper.readValue(responseAsString, ErrorResponseDto.class);

        assertEquals("cart_not_found", response.code());
        assertEquals("Cart with id 99999 not found", response.message());
    }
}