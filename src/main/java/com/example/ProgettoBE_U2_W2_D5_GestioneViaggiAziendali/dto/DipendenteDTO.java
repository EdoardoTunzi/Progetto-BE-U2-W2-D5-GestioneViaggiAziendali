package com.example.ProgettoBE_U2_W2_D5_GestioneViaggiAziendali.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.URL;
import org.hibernate.validator.constraints.UniqueElements;

@Data
public class DipendenteDTO {
    @NotNull(message = "Il campo username è obbligatorio")
    @NotBlank(message = "Il campo username non può essere vuoto")
    private String username;
    @NotNull(message = "Il campo nome è obbligatorio")
    @NotBlank(message = "Il campo nome non può essere vuoto")
    private String nome;
    @NotNull(message = "Il campo cognome è obbligatorio")
    @NotBlank(message = "Il campo cognome non può essere vuoto")
    private String cognome;
    @Email(message = "L'email inserita non è valida")
    private String email;
    @URL(message = "L'URL inserito non è valido")
    private String profilePic;
}
