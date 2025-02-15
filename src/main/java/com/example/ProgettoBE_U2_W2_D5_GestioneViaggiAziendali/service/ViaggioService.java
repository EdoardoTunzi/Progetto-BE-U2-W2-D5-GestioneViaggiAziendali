package com.example.ProgettoBE_U2_W2_D5_GestioneViaggiAziendali.service;

import com.example.ProgettoBE_U2_W2_D5_GestioneViaggiAziendali.dto.ViaggioDTO;
import com.example.ProgettoBE_U2_W2_D5_GestioneViaggiAziendali.exceptions.NotFoundException;
import com.example.ProgettoBE_U2_W2_D5_GestioneViaggiAziendali.model.Dipendente;
import com.example.ProgettoBE_U2_W2_D5_GestioneViaggiAziendali.model.StatoViaggio;
import com.example.ProgettoBE_U2_W2_D5_GestioneViaggiAziendali.model.Viaggio;
import com.example.ProgettoBE_U2_W2_D5_GestioneViaggiAziendali.repository.DipendenteDAORepository;
import com.example.ProgettoBE_U2_W2_D5_GestioneViaggiAziendali.repository.ViaggioDAORepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ViaggioService {
    @Autowired
    ViaggioDAORepository viaggioRepo;
    @Autowired
    DipendenteDAORepository dipendenteRepo;


    //salvataggio
    public Long saveViaggio(ViaggioDTO viaggioDTO) {
        Viaggio viaggioInserito = fromViaggioDTOToViaggio(viaggioDTO);
        Viaggio viaggioSalvato = viaggioRepo.save(viaggioInserito);
        return viaggioSalvato.getId();
    }

    //getAll
    public Page<ViaggioDTO> getAllViaggio(Pageable page) {
        Page<Viaggio> listaViaggi = viaggioRepo.findAll(page);
        List<ViaggioDTO> listaDto = new ArrayList<>();

        for (Viaggio viaggio : listaViaggi.getContent()) {
            ViaggioDTO dto = fromViaggioToViaggioDTO(viaggio);
            listaDto.add(dto);
        }

        Page<ViaggioDTO> listaPage = new PageImpl<>(listaDto);
        return listaPage;
    }

    //getById
    public ViaggioDTO findViaggioById(long id) {
        Optional<Viaggio> viaggio = viaggioRepo.findById(id);

        if (viaggio.isPresent()) {
            ViaggioDTO viaggiDto = fromViaggioToViaggioDTO(viaggio.get());
            return viaggiDto;
        } else {
            throw new NotFoundException("Nessun viaggio trovato con l'id: " + id);
        }
    }

    //put- sostituisciViaggioById
    public void updateViaggio(ViaggioDTO viaggioDTO, long id) {
        Optional<Viaggio> viaggioTrovato = viaggioRepo.findById(id);

        if (viaggioTrovato.isPresent()) {
            Viaggio viaggio = viaggioTrovato.get();
            viaggio.setDestinazione(viaggioDTO.getDestinazione());
            viaggio.setData(viaggioDTO.getData());
            viaggio.setStato(viaggioDTO.getStato());
            viaggioRepo.save(viaggio);
        } else {
            throw new NotFoundException("Errore nella modifica del viaggio inserito. Nessun viaggio trovato con l'id: " + id);
        }
    }

    //deleteByID
    public String deleteViaggio(long id) {
        Optional<Viaggio> viaggioTrovato = viaggioRepo.findById(id);
        if (viaggioTrovato.isPresent()) {
            viaggioRepo.delete(viaggioTrovato.get());
            return "Viaggio con id: " + id + " eliminato con successo!";
        } else {
            throw new NotFoundException("Errore nel delete! Nessun viaggio trovato con id: " + id);
        }
    }

    //Assegnazione dipendente a viaggio
    public void addDipendente(long viaggioId, long dipendenteId) {
        Optional<Viaggio> viaggio = viaggioRepo.findById(viaggioId);
        Optional<Dipendente> dipendente = dipendenteRepo.findById(dipendenteId);
        if (viaggio.isPresent() && dipendente.isPresent()) {
            Viaggio viaggiodaSalvare = viaggio.get();
            viaggiodaSalvare.setDipendente(dipendente.get());
            viaggioRepo.save(viaggiodaSalvare);
        } else {
            throw new NotFoundException("Viaggio o Dipendente non trovato con id: " + dipendenteId);
        }
    }

    //Modifica stato viaggio
    public void modificaStatoViaggio(long viaggioId, StatoViaggio statoViaggio) {
        Optional<Viaggio> viaggio = viaggioRepo.findById(viaggioId);
        if (viaggio.isPresent()) {
            Viaggio viaggioDaSalvare = viaggio.get();
            viaggioDaSalvare.setStato(statoViaggio);
            viaggioRepo.save(viaggioDaSalvare);
        } else {
            throw new NotFoundException("Viaggio non trovato con id: " + viaggioId);
        }
    }


    //-----------------------------metodi travaso DTO-----------------------------

    //Viaggio a Viaggio DTO
    public Viaggio fromViaggioDTOToViaggio(ViaggioDTO viaggioDTO) {
        Viaggio viaggio = new Viaggio();
        viaggio.setDestinazione(viaggioDTO.getDestinazione());
        viaggio.setData(viaggioDTO.getData());
        viaggio.setStato(viaggioDTO.getStato());
        viaggio.setDipendente(viaggioDTO.getDipendente());
        return viaggio;
    }

    //ViaggioDTo a Viaggio
    public ViaggioDTO fromViaggioToViaggioDTO(Viaggio viaggio) {
        ViaggioDTO viaggioDTO = new ViaggioDTO();
        viaggioDTO.setDestinazione(viaggio.getDestinazione());
        viaggioDTO.setData(viaggio.getData());
        viaggioDTO.setStato(viaggio.getStato());
        viaggioDTO.setDipendente(viaggio.getDipendente());
        return viaggioDTO;
    }
}
