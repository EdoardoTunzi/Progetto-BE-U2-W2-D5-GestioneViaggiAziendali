package com.example.ProgettoBE_U2_W2_D5_GestioneViaggiAziendali.dto;

import com.example.ProgettoBE_U2_W2_D5_GestioneViaggiAziendali.model.Dipendente;
import com.example.ProgettoBE_U2_W2_D5_GestioneViaggiAziendali.model.Viaggio;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDate;

@Data
public class PrenotazioneDTO {
    @NotNull(message = "Il campo viaggio è obbligatorio")
    @NotBlank(message = "Il campo viaggio non può essere vuoto")
    private Viaggio viaggio;
    @NotNull(message = "Il campo dipendente è obbligatorio")
    @NotBlank(message = "Il campo dipendente non può essere vuoto")
    private Dipendente dipendente;
    @NotNull(message = "Il campo data è obbligatorio")
    @NotBlank(message = "Il campo data non può essere vuoto")
    private LocalDate data;
    @Size(max = 200, message = "Le preferenze possono avere max 200 caratteri")
    private String preferenze;
}
