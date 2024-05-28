package com.bank.credit_card.web.controller;

import com.bank.credit_card.domain.CreditCardTransaction;
import com.bank.credit_card.domain.dto.AnulateTransactionRequest;
import com.bank.credit_card.domain.dto.PurchaseRequest;
import com.bank.credit_card.domain.service.TransactionService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TransactionController.class)
public class TransactionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TransactionService transactionService;

    @Test
    public void testGetTransactionById() throws Exception {
        int transactionId = 123;
        CreditCardTransaction transaction = new CreditCardTransaction();
        transaction.setIdTransaction(transactionId);

        Mockito.when(transactionService.getTransaction(transactionId)).thenReturn(transaction);

        mockMvc.perform(get("/transactions/{transactionId}", String.valueOf(transactionId)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idTransaction").value(transactionId));
    }

    @Test
    public void testPurchase() throws Exception {
        String requestJson = "{\"cardId\":\"1234567890123456\", \"amount\":100.00}";

        mockMvc.perform(post("/transactions/purchase")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isOk());
    }

    @Test
    public void testAnulateTransaction() throws Exception {
        String requestJson = "{\"cardId\":\"1234567890123456\", \"transactionId\":123}";

        mockMvc.perform(post("/transactions/anulation")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isOk());
    }
}
