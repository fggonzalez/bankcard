package com.bank.credit_card.web;

import com.bank.credit_card.domain.service.CardService;
import com.bank.credit_card.web.controller.CardController;
import com.bank.credit_card.web.exception.CardAlreadyActiveException;
import com.bank.credit_card.web.exception.InvalidCardIdException;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
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
        String fullName = "John Doe";
        String expectedCardNumber = "1234567890123456";

        when(cardService.generateCardNumber(productId, fullName)).thenReturn(expectedCardNumber);

        mockMvc.perform(get("/cards/{productId}/number", productId)
                        .param("fullName", fullName))
                .andExpect(status().isOk())
                .andExpect(content().string(expectedCardNumber));
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
        BigDecimal expectedBalance = new BigDecimal("1000.00");

        when(cardService.getBalance(anyString())).thenReturn(expectedBalance);

        mockMvc.perform(get("/cards/balance/{cardId}", cardId))
                .andExpect(status().isOk())
                .andExpect(content().string("El saldo de la tarjeta  es :1000.00 USD"));
    }

    @Test
    public void testActivateCard_InvalidCardId_Format() throws Exception {
        String invalidCardId = "12345678901234AB";

        when(cardService.activateCard(invalidCardId))
                .thenThrow(new InvalidCardIdException("Entrada invalida se deben ingresar solo numeros"));

        mockMvc.perform(post("/cards/enroll")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"cardId\": \"" + invalidCardId + "\"}"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("400 BAD_REQUEST \"Entrada invalida se deben ingresar solo numeros\""));
    }

    @Test
    public void testActivateCard_CardAlreadyActive() throws Exception {
        String cardId = "1234567890123456";

        when(cardService.activateCard(cardId))
                .thenThrow(new CardAlreadyActiveException("La tarjeta ya se encuentra activada activa"));

        mockMvc.perform(post("/cards/enroll")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"cardId\": \"" + cardId + "\"}"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("La tarjeta ya se encuentra activada activa"));
    }
}
