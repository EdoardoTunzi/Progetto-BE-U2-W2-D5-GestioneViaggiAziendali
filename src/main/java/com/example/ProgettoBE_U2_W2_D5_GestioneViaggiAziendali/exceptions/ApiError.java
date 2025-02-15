package com.example.ProgettoBE_U2_W2_D5_GestioneViaggiAziendali.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
@AllArgsConstructor
public class ApiError {
    private String message;
    private HttpStatus status;
}
