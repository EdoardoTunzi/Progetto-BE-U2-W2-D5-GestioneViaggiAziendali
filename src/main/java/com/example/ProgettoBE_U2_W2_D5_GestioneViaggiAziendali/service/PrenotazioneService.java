package com.example.ProgettoBE_U2_W2_D5_GestioneViaggiAziendali.service;


import com.example.ProgettoBE_U2_W2_D5_GestioneViaggiAziendali.dto.PrenotazioneDTO;
import com.example.ProgettoBE_U2_W2_D5_GestioneViaggiAziendali.model.Dipendente;
import com.example.ProgettoBE_U2_W2_D5_GestioneViaggiAziendali.model.Prenotazione;
import com.example.ProgettoBE_U2_W2_D5_GestioneViaggiAziendali.repository.PrenotazioneDAORepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PrenotazioneService {
    @Autowired
    PrenotazioneDAORepository prenotazioneRepo;

    public Page<PrenotazioneDTO> getAllPrenotazioni(Pageable page) {
        Page<Prenotazione> listaPrenotazioni = prenotazioneRepo.findAll(page);
        List<PrenotazioneDTO> listaDto = new ArrayList<>();

        for (Prenotazione prenotazione : listaPrenotazioni.getContent()) {
            PrenotazioneDTO dto = fromPrenotazioneToDTO(prenotazione);
            listaDto.add(dto);
        }

        Page<PrenotazioneDTO> listaPage = new PageImpl<>(listaDto);
        return listaPage;
    }

    public String deletePrenotazione(long id) {
        Optional<Prenotazione> prenotazioneTrovato = prenotazioneRepo.findById(id);
        if (prenotazioneTrovato.isPresent()){
            prenotazioneRepo.delete(prenotazioneTrovato.get());
            return "Prenotazione con id: " + id + " eliminato con successo!";
        } else {
            throw new RuntimeException("Errore nel delete! Nessuna prenotazione trovata con questo id");
        }

    }

    public PrenotazioneDTO fromPrenotazioneToDTO(Prenotazione prenotazione) {
        PrenotazioneDTO prenotazioneDTO = new PrenotazioneDTO();
        prenotazioneDTO.setDipendente_id(prenotazione.getDipendente().getId());
        prenotazioneDTO.setViaggio_id(prenotazione.getViaggio().getId());
        prenotazioneDTO.setData(prenotazione.getData());
        prenotazioneDTO.setNotePreferenze(prenotazione.getNotePreferenze());
        return prenotazioneDTO;
    }

}
