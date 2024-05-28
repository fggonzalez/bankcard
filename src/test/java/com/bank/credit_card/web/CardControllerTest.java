package com.bank.credit_card.web;

import com.bank.credit_card.domain.service.CardService;
import com.bank.credit_card.web.controller.CardController;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CardController.class)
public class CardControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CardService cardService;

    @Test
    public void testGenerateCardNumber() throws Exception {
        String productId = "123456";
        String cardNumber = "1234567890123456";

        Mockito.when(cardService.generateCardNumber(productId)).thenReturn(cardNumber);

        mockMvc.perform(get("/cards/{productId}/number", productId))
                .andExpect(status().isOk())
                .andExpect(content().string(cardNumber));
    }

    @Test
    public void testActivateCard() throws Exception {
        String cardId = "1234567890123456";
        mockMvc.perform(post("/cards/enroll")
                        .contentType("application/json")
                        .content("{\"cardId\":\"" + cardId + "\"}"))
                .andExpect(status().isOk());
    }

    @Test
    public void testBlockCard() throws Exception {
        String cardId = "1234567890123456";
        mockMvc.perform(delete("/cards/{cardId}", cardId))
                .andExpect(status().isOk());
    }

    @Test
    public void testRechargeBalance() throws Exception {
        String cardId = "1234567890123456";
        BigDecimal balance = new BigDecimal("1000.00");
        mockMvc.perform(post("/cards/balance")
                        .contentType("application/json")
                        .content("{\"cardId\":\"" + cardId + "\", \"balance\":\"" + balance.toString() + "\"}"))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetBalance() throws Exception {
        String cardId = "1234567890123456";
        BigDecimal balance = new BigDecimal("1000.00");

        Mockito.when(cardService.getBalance(cardId)).thenReturn(balance);

        mockMvc.perform(get("/cards/balance/{cardId}", cardId))
                .andExpect(status().isOk())
                .andExpect(content().string(balance.toString()));
    }
}
