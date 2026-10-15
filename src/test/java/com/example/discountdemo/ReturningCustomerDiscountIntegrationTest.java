package com.example.discountdemo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ReturningCustomerDiscountIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    void returningCustomerGetsDiscountAndPaysDiscountedTotal() throws Exception {
        mockMvc.perform(post("/api/checkout")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"customerId":1001,"items":[{"sku":"COFFEE","quantity":1}]}
                                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.subtotal").value(12.0))
                .andExpect(jsonPath("$.discountAmount").value(1.2))
                .andExpect(jsonPath("$.total").value(10.8));
    }
}
