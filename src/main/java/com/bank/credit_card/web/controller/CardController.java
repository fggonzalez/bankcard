package com.bank.credit_card.web.controller;

import com.bank.credit_card.domain.dto.ActivateCardRequest;
import com.bank.credit_card.domain.dto.RechargeBalanceRequest;
import com.bank.credit_card.domain.service.CardService;
import com.bank.credit_card.web.exception.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/cards")
@Tag(name = "Card Controller", description = "Operaciones relacionadas para las tarjetas de crédito")
public class CardController {
    private static final Logger logger = LoggerFactory.getLogger(CardController.class);

    @Autowired
    private final CardService cardService;
    @Autowired
    public CardController(CardService cardService) {
        this.cardService = cardService;
    }



    /**
     * Genera un número de tarjeta basado en el ID del producto proporcionado y el nombre del cliente .
     *
     * @param productId El ID del producto para el cual generar un número de tarjeta.
     * @param fullName  El Nombre del Cliente para el cual generar un número de tarjeta.
     * @return ResponseEntity que contiene el número de tarjeta generado.
     */
    @Operation(summary = "Generar número de tarjeta", description = "Genera un nuevo número de tarjeta basado en el ID del producto proporcionado")
    @ApiResponse(responseCode = "200", description = "Número de tarjeta generado exitosamente")
    @ApiResponse(responseCode = "400", description = "Solicitud incorrecta por datos de entrada")
    @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    @GetMapping("/{productId}/number")
    public ResponseEntity<String> generateCardNumber(@PathVariable String productId, String fullName) {
        try {
        logger.info("Generando número de tarjeta para el ID del producto: {}", productId);
        String cardNumber = cardService.generateCardNumber(productId,fullName);
        return ResponseEntity.ok("El numero de la tarjeta es "+cardNumber);
        } catch (InvalidProductIdException | InvalidFullNameException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Un error inesperado ocurrio intente de nuevo ");
        }
    }

    /**
     * Activa una tarjeta basada en el ID de la tarjeta proporcionado.
     *
     * @param request Objeto ActivateCardRequest que contiene el ID de la tarjeta a activar.
     * @return ResponseEntity indicando el resultado de la operación.
     */
    @Operation(summary = "Activar tarjeta", description = "Activa una tarjeta basada en el ID de la tarjeta proporcionado")
    @ApiResponse(responseCode = "200", description = "Tarjeta activada exitosamente")
    @ApiResponse(responseCode = "400", description = "Solicitud incorrecta por datos de entrada")
    @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    @PostMapping("/enroll")
    public ResponseEntity<String> activateCard(@RequestBody ActivateCardRequest request) {
        String cardId = request.getCardId();

        try {
        cardService.activateCard(cardId);
        return ResponseEntity.ok("Tarjeta activada exitosamente");

        } catch (InvalidCardIdException | CardAlreadyActiveException e ) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Un error inesperado ocurrió. Intente de nuevo.");
        }
    }

    /**
     * Bloquea una tarjeta basada en el ID de la tarjeta proporcionado.
     *
     * @param cardId El ID de la tarjeta a bloquear.
     * @return ResponseEntity indicando el resultado de la operación.
     */
    @Operation(summary = "Bloquear tarjeta", description = "Bloquea una tarjeta basada en el ID de la tarjeta proporcionado por el usuario ")
    @ApiResponse(responseCode = "200", description = "Tarjeta bloqueada exitosamente")
    @ApiResponse(responseCode = "400", description = "Solicitud incorrecta por datos de entrada")
    @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    @DeleteMapping("/{cardId}")
    public ResponseEntity<String> blockCard(@PathVariable String cardId) {
        try {
        cardService.blockCard(cardId);
        return ResponseEntity.ok("Tarjeta Bloqueada exitosamente numero:"+cardId);
        } catch (InvalidCardIdException | CardAlreadyBlockedException e ) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Un error inesperado ocurrió. Intente de nuevo.");
        }
    }

    /**
     * Recarga el saldo de una tarjeta basada en el ID de la tarjeta y el saldo proporcionado para recargar.
     *
     * @param request Objeto RechargeBalanceRequest que contiene el ID de la tarjeta y el saldo a recargar.
     * @return ResponseEntity indicando el resultado de la operación.
     */
    @Operation(summary = "Recargar saldo", description = "Recarga el saldo de una tarjeta basada en el ID de la tarjeta y el saldo proporcionado a recargar ")
    @ApiResponse(responseCode = "200", description = "Saldo recargado exitosamente")
    @ApiResponse(responseCode = "400", description = "Solicitud incorrecta")
    @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    @PostMapping("/balance")
    public ResponseEntity<String> rechargeBalance(@Valid @RequestBody RechargeBalanceRequest request) {
        try {
            String cardId = request.getCardId();
            BigDecimal balance = request.getBalance();
            cardService.rechargeBalance(cardId, balance);
            return ResponseEntity.ok("Saldo Reacargado Exitosamente ");
        }catch (InvalidCardIdException | InvalidAmountException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }catch (Exception e) {
            return ResponseEntity.status(500).body("Un error inesperado ocurrió. Intente de nuevo.");
        }
    }

    /**
     * Obtiene el saldo de una tarjeta basada en el ID de la tarjeta proporcionado.
     *
     * @param cardId El ID de la tarjeta para obtener el saldo.
     * @return ResponseEntity que contiene el saldo de la tarjeta.
     */
    @Operation(summary = "Obtener saldo", description = "Obtiene el saldo de una tarjeta basada en el ID de la tarjeta proporcionado")
    @ApiResponse(responseCode = "200", description = "Saldo obtenido exitosamente")
    @ApiResponse(responseCode = "400", description = "Solicitud incorrecta por datos de entrada")
    @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    @GetMapping("/balance/{cardId}")
    public ResponseEntity<String> getBalance(@PathVariable String cardId) {
        try {
        BigDecimal balance = cardService.getBalance(cardId);
        return ResponseEntity.ok("El saldo de la tarjeta  es :" +balance + " USD");
        }catch (InvalidCardIdException  e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }catch (Exception e) {
            return ResponseEntity.status(500).body("Un error inesperado ocurrió. Intente de nuevo.");
        }
    }
}
