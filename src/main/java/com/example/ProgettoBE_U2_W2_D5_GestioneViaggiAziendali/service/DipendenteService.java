package com.example.ProgettoBE_U2_W2_D5_GestioneViaggiAziendali.service;

import com.example.ProgettoBE_U2_W2_D5_GestioneViaggiAziendali.dto.DipendenteDTO;
import com.example.ProgettoBE_U2_W2_D5_GestioneViaggiAziendali.dto.PrenotazioneDTO;
import com.example.ProgettoBE_U2_W2_D5_GestioneViaggiAziendali.model.Dipendente;
import com.example.ProgettoBE_U2_W2_D5_GestioneViaggiAziendali.model.Prenotazione;
import com.example.ProgettoBE_U2_W2_D5_GestioneViaggiAziendali.model.Viaggio;
import com.example.ProgettoBE_U2_W2_D5_GestioneViaggiAziendali.repository.DipendenteDAORepository;
import com.example.ProgettoBE_U2_W2_D5_GestioneViaggiAziendali.repository.PrenotazioneDAORepository;
import com.example.ProgettoBE_U2_W2_D5_GestioneViaggiAziendali.repository.ViaggioDAORepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class DipendenteService {
    @Autowired
    DipendenteDAORepository dipendenteRepo;
    @Autowired
    ViaggioDAORepository viaggioRepo;
    @Autowired
    PrenotazioneDAORepository prenotazioneRepo;


    //salvataggio
    public Long saveDipendente(DipendenteDTO dipendenteDTO) {
        Dipendente dipendenteInserito = fromDipendenteDTOToDipendente(dipendenteDTO);
        Dipendente dipendenteSalvato = dipendenteRepo.save(dipendenteInserito);
        return dipendenteSalvato.getId();
    }

    //getAll
    public Page<DipendenteDTO> getAllDipendenti(Pageable page) {
        Page<Dipendente> listaDipendenti = dipendenteRepo.findAll(page);
        List<DipendenteDTO> listaDto = new ArrayList<>();

        for (Dipendente dipendente : listaDipendenti.getContent()) {
            DipendenteDTO dto = fromDipendenteToDipendenteDTO(dipendente);
            listaDto.add(dto);
        }

        Page<DipendenteDTO> listaPage = new PageImpl<>(listaDto);
        return listaPage;
    }

    //getById
    public DipendenteDTO findDipendenteById(long id) {
        Optional<Dipendente> dipendente = dipendenteRepo.findById(id);

        if (dipendente.isPresent()) {
            DipendenteDTO dipendenteDto = fromDipendenteToDipendenteDTO(dipendente.get());
            return dipendenteDto;
        } else {
            throw new RuntimeException("Nessun dipendente trovato con l'id: " + id);
        }
    }

    //put- sostituisciDipendenteById
    public void updateDipendente(DipendenteDTO dipendenteDTO, long id) {
        Optional<Dipendente> dipendenteTrovato = dipendenteRepo.findById(id);

        if (dipendenteTrovato.isPresent()) {
            Dipendente dipendente = dipendenteTrovato.get();
            dipendente.setUsername(dipendenteDTO.getUsername());
            dipendente.setNome(dipendenteDTO.getNome());
            dipendente.setCognome(dipendenteDTO.getCognome());
            dipendente.setEmail(dipendenteDTO.getEmail());
            dipendente.setProfilePic(dipendenteDTO.getProfilePic());
            dipendenteRepo.save(dipendente);
        } else {
            throw new RuntimeException("Errore nella modifica del dipendente inserito");
        }
    }

    //deleteByID
    public String deleteDipendente(long id) {
        Optional<Dipendente> dipendenteTrovato = dipendenteRepo.findById(id);
        if (dipendenteTrovato.isPresent()) {
            dipendenteRepo.delete(dipendenteTrovato.get());
            return "Dipendente con id: " + id + " eliminato con successo!";
        } else {
            throw new RuntimeException("Errore nel delete! Nessun dipendente trovato con id: " + id);
        }

    }

    //creazione prenotazione
    public void creaPrenotazione(PrenotazioneDTO prenotazioneDTO) {
        Optional<Dipendente> dipendenteTrovato = dipendenteRepo.findById(prenotazioneDTO.getDipendente_id());
        Optional<Viaggio> viaggioTrovato = viaggioRepo.findById(prenotazioneDTO.getViaggio_id());
        if (dipendenteTrovato.isPresent() && viaggioTrovato.isPresent()) {
            Dipendente dipendente = dipendenteTrovato.get();
            Viaggio viaggio = viaggioTrovato.get();
            LocalDate dataPrenotazione = prenotazioneDTO.getData();

            List<Prenotazione> prenotazioniEsistentiConLaStessaData = prenotazioneRepo.findByDataAndDipendente(dataPrenotazione, dipendente);
            List<Prenotazione> prenotazioniDelDipendente = prenotazioneRepo.findByDipendente(dipendente);
            //prima di creare la prenotazione, controllo che il dipendente
            //non abbia gia una prenotazione nella stessa data e altre prenotazioni
            if (!prenotazioniEsistentiConLaStessaData.isEmpty()) {
                throw new RuntimeException("Il dipendente selezionato ha gia una prenotazione con la stessa data");
            } else if (!prenotazioniDelDipendente.isEmpty()) {
                throw new RuntimeException("Questo dipendente non può prenotare altri viaggi, perchè ha gia un viaggio prenotato!");
            } else {
                Prenotazione prenotazione = new Prenotazione();
                prenotazione.setDipendente(dipendente);
                prenotazione.setViaggio(viaggio);
                prenotazione.setData(prenotazioneDTO.getData());
                prenotazione.setNotePreferenze(prenotazioneDTO.getNotePreferenze());
                prenotazioneRepo.save(prenotazione);
            }
        } else {
            throw new RuntimeException(" L'id del viaggio o del dipendente non è stato trovato");
        }
    }


    //--------------------------metodi travaso DTO----------------------

    //Dipendente to DipendenteDTO
    public Dipendente fromDipendenteDTOToDipendente(DipendenteDTO dipendenteDTO) {
        Dipendente dipendente = new Dipendente();
        dipendente.setUsername(dipendenteDTO.getUsername());
        dipendente.setNome(dipendenteDTO.getNome());
        dipendente.setCognome(dipendenteDTO.getCognome());
        dipendente.setEmail(dipendenteDTO.getEmail());
        dipendente.setProfilePic(dipendenteDTO.getProfilePic());
        return dipendente;
    }

    //DipendenteDTO to Dipendente
    public DipendenteDTO fromDipendenteToDipendenteDTO(Dipendente dipendente) {
        DipendenteDTO dipendenteDTO = new DipendenteDTO();
        dipendenteDTO.setUsername(dipendente.getUsername());
        dipendenteDTO.setNome(dipendente.getNome());
        dipendenteDTO.setCognome(dipendente.getCognome());
        dipendenteDTO.setEmail(dipendente.getEmail());
        dipendenteDTO.setProfilePic(dipendente.getProfilePic());
        return dipendenteDTO;
    }
}
