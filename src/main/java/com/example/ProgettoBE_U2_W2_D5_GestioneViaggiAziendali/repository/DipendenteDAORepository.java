package com.example.ProgettoBE_U2_W2_D5_GestioneViaggiAziendali.repository;

import com.example.ProgettoBE_U2_W2_D5_GestioneViaggiAziendali.model.Dipendente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DipendenteDAORepository extends JpaRepository<Dipendente, Long> {
}
