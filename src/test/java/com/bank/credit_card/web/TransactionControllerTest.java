package com.bank.credit_card.web;




import com.bank.credit_card.domain.CreditCardTransaction;
import com.bank.credit_card.domain.dto.AnulateTransactionRequest;
import com.bank.credit_card.domain.dto.PurchaseRequest;
import com.bank.credit_card.domain.service.TransactionService;
import com.bank.credit_card.web.controller.TransactionController;
import com.bank.credit_card.web.exception.InvalidCardIdException;
import com.bank.credit_card.web.exception.InvalidAmountException;
import com.bank.credit_card.web.exception.InvalidTransactionIdException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TransactionController.class)
public class TransactionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TransactionService transactionService;

    private CreditCardTransaction transaction;

    @BeforeEach
    void setUp() {
        transaction = new CreditCardTransaction();
        transaction.setIdTransaction(1);
        transaction.setIdCard("1234567890123456");
        transaction.setTotalAmount(BigDecimal.valueOf(100.00));
        transaction.setTimeTransaction(LocalDateTime.now().withNano(0));
        transaction.setAnulled(false);
    }

    @Test
    void testGetTransactionById_Success() throws Exception {
        when(transactionService.getTransaction(anyInt())).thenReturn(transaction);

        mockMvc.perform(get("/transactions/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idTransaction").value(1));
    }

    @Test
    void testGetTransactionById_InvalidId() throws Exception {
        when(transactionService.getTransaction(anyInt())).thenThrow(new InvalidTransactionIdException("Invalid Transaction ID"));

        mockMvc.perform(get("/transactions/invalid"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testGetTransactionById_RuntimeException() throws Exception {
        when(transactionService.getTransaction(anyInt())).thenThrow(new RuntimeException("Internal Server Error"));

        mockMvc.perform(get("/transactions/1"))
                .andExpect(status().isInternalServerError());
    }

    @Test
    void testPurchase_Success() throws Exception {
        when(transactionService.purchase(anyString(), any(BigDecimal.class))).thenReturn(transaction);

        String requestJson = "{\"cardId\":\"1234567890123456\",\"amount\":100.00}";

        mockMvc.perform(post("/transactions/purchase")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isOk())
                .andExpect(content().string("Compra realizada exitosamente, el número de transacción es 1"));
    }



    @Test
    void testPurchase_InvalidAmount() throws Exception {
        when(transactionService.purchase(anyString(), any(BigDecimal.class))).thenThrow(new InvalidAmountException("Invalid Amount"));

        String requestJson = "{\"cardId\":\"1234567890123456\",\"amount\":100.00}";

        mockMvc.perform(post("/transactions/purchase")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Invalid Amount"));
    }

    @Test
    void testPurchase_RuntimeException() throws Exception {
        when(transactionService.purchase(anyString(), any(BigDecimal.class))).thenThrow(new RuntimeException("Internal Server Error"));

        String requestJson = "{\"cardId\":\"1234567890123456\",\"amount\":100.00}";

        mockMvc.perform(post("/transactions/purchase")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isInternalServerError())
                .andExpect(content().string("Error interno del servidor: Internal Server Error"));
    }

    @Test
    void testAnulateTransaction_Success() throws Exception {
        String requestJson = "{\"cardId\":\"1234567890123456\",\"transactionId\":1}";

        mockMvc.perform(post("/transactions/anulation")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isOk())
                .andExpect(content().string("Transacción anulada exitosamente1"));
    }


    @Test
    void testAnulateTransaction_RuntimeException() throws Exception {
        Mockito.doThrow(new RuntimeException("Internal Server Error")).when(transactionService).anulateTransaction(anyString(), anyInt());

        String requestJson = "{\"cardId\":\"1234567890123456\",\"transactionId\":1}";

        mockMvc.perform(post("/transactions/anulation")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isInternalServerError())
                .andExpect(content().string("Error interno del servidor: Internal Server Error"));
    }
}