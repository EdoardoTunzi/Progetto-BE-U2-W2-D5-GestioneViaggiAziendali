package com.example.ProgettoBE_U2_W2_D5_GestioneViaggiAziendali.repository;

import com.example.ProgettoBE_U2_W2_D5_GestioneViaggiAziendali.model.Viaggio;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ViaggioDAORepository extends JpaRepository<Viaggio,Long> {
}
