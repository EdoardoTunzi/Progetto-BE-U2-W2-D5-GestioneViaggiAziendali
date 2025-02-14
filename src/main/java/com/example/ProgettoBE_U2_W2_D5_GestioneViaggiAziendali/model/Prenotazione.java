package com.example.ProgettoBE_U2_W2_D5_GestioneViaggiAziendali.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "prenotazioni")
@Data
public class Prenotazione {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    private Viaggio viaggio;

    @ManyToOne
    private Dipendente dipendente;
    @Column(nullable = false)
    private LocalDate data;

    private String preferenze;
}