package com.example.ProgettoBE_U2_W2_D5_GestioneViaggiAziendali.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


//La gestione degli errori personalizzati purtroppo non mi è chiara, perchè l'abbiamo vista molto velocemente
// e senza esempi pratici di live code a lezione.
//Ho voluto comunque tentare, un pò vedendo le slide e un pò cercando esempi online.
@RestControllerAdvice
public class CustomExceptionHandler {
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ApiError> handleNotFoundException(NotFoundException ex) {
        ApiError apiError = new ApiError(ex.getMessage(),HttpStatus.NOT_FOUND);
        return  new ResponseEntity<>(apiError, apiError.getStatus());
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ApiError> handleBadRequestException(BadRequestException ex) {
        ApiError apiError = new ApiError(ex.getMessage(),HttpStatus.BAD_REQUEST);
        return  new ResponseEntity<>(apiError, apiError.getStatus());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleGenericException(Exception ex) {
        ApiError apiError = new ApiError("Errore interno del server " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        return  new ResponseEntity<>(apiError, apiError.getStatus());
    }

}
