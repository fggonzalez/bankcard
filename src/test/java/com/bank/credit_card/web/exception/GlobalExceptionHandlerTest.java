package com.bank.credit_card.web.exception;


import com.bank.credit_card.web.GlobalExceptionHandler;
import com.bank.credit_card.web.exception.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class GlobalExceptionHandlerTest {

    @InjectMocks
    private GlobalExceptionHandler globalExceptionHandler;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(new DummyController())
                .setControllerAdvice(globalExceptionHandler)
                .build();
    }

    @Test
    void testHandleCustomException() throws Exception {
        mockMvc.perform(get("/test/custom-exception"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status", is(HttpStatus.BAD_REQUEST.value())))
                .andExpect(jsonPath("$.error", is(HttpStatus.BAD_REQUEST.getReasonPhrase())))
                .andExpect(jsonPath("$.message", is("Custom exception occurred")));
    }

    @Test
    void testHandleHttpMessageNotReadableException() throws Exception {
        mockMvc.perform(post("/test/http-message-not-readable")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("invalid json"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status", is(HttpStatus.BAD_REQUEST.value())))
                .andExpect(jsonPath("$.error", is(HttpStatus.BAD_REQUEST.getReasonPhrase())))
                .andExpect(jsonPath("$.message", is("El formato de la solicitud no es válido: Unrecognized token 'invalid': was expecting (JSON String, Number, Array, Object or token 'null', 'true' or 'false')")));
    }

    @Test
    void testHandleCardExpiredException() throws Exception {
        mockMvc.perform(get("/test/card-expired"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status", is(HttpStatus.BAD_REQUEST.value())))
                .andExpect(jsonPath("$.error", is(HttpStatus.BAD_REQUEST.getReasonPhrase())))
                .andExpect(jsonPath("$.message", is("La tarjeta ha expirado")));
    }

    @Test
    void testHandleCardNotActiveException() throws Exception {
        mockMvc.perform(get("/test/card-not-active"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status", is(HttpStatus.BAD_REQUEST.value())))
                .andExpect(jsonPath("$.error", is(HttpStatus.BAD_REQUEST.getReasonPhrase())))
                .andExpect(jsonPath("$.message", is("La tarjeta no está activada")));
    }

    @Test
    void testHandleCardBlockedException() throws Exception {
        mockMvc.perform(get("/test/card-blocked"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status", is(HttpStatus.BAD_REQUEST.value())))
                .andExpect(jsonPath("$.error", is(HttpStatus.BAD_REQUEST.getReasonPhrase())))
                .andExpect(jsonPath("$.message", is("La tarjeta está bloqueada")));
    }

    @Test
    void testHandleInsufficientFundsException() throws Exception {
        mockMvc.perform(get("/test/insufficient-funds"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status", is(HttpStatus.BAD_REQUEST.value())))
                .andExpect(jsonPath("$.error", is(HttpStatus.BAD_REQUEST.getReasonPhrase())))
                .andExpect(jsonPath("$.message", is("Fondos insuficientes")));
    }



    @Test
    void testHandleException() throws Exception {
        mockMvc.perform(get("/test/exception"))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.status", is(HttpStatus.INTERNAL_SERVER_ERROR.value())))
                .andExpect(jsonPath("$.error", is(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase())))
                .andExpect(jsonPath("$.message", is("An unexpected error occurred")));
    }

    @RestController
    @RequestMapping("/test")
    private static class DummyController {

        @GetMapping("/custom-exception")
        public void throwCustomException() {
            throw new CustomException("Custom exception occurred", HttpStatus.BAD_REQUEST);
        }

        @PostMapping("/http-message-not-readable")
        public void throwHttpMessageNotReadableException() {
            throw new HttpMessageNotReadableException("Unrecognized token 'invalid': was expecting (JSON String, Number, Array, Object or token 'null', 'true' or 'false')");
        }

        @GetMapping("/card-expired")
        public void throwCardExpiredException() {
            throw new CardExpiredException("La tarjeta ha expirado");
        }

        @GetMapping("/card-not-active")
        public void throwCardNotActiveException() {
            throw new CardNotActiveException("La tarjeta no está activada");
        }

        @GetMapping("/card-blocked")
        public void throwCardBlockedException() {
            throw new CardBlockedException("La tarjeta está bloqueada");
        }

        @GetMapping("/insufficient-funds")
        public void throwInsufficientFundsException() {
            throw new InsufficientFundsException("Fondos insuficientes");
        }

        @GetMapping("/invalid-card-id")
        public void throwInvalidCardIdException() {
            throw new InvalidCardIdException("El numero de tarjeta debe ser exactamente 16 dígitos y contener solo números");
        }

        @GetMapping("/exception")
        public void throwException() {
            throw new RuntimeException("An unexpected error occurred");
        }
    }
}
