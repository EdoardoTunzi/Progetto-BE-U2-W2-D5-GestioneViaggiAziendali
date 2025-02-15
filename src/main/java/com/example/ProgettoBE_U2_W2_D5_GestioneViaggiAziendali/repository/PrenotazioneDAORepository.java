package com.example.ProgettoBE_U2_W2_D5_GestioneViaggiAziendali.repository;

import com.example.ProgettoBE_U2_W2_D5_GestioneViaggiAziendali.model.Dipendente;
import com.example.ProgettoBE_U2_W2_D5_GestioneViaggiAziendali.model.Prenotazione;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface PrenotazioneDAORepository extends JpaRepository<Prenotazione,Long> {
    List<Prenotazione> findByDataAndDipendente(LocalDate data,Dipendente dipendente);
    List<Prenotazione> findByDipendente(Dipendente dipendente);
}

