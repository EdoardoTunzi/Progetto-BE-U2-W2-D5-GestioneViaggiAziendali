package com.example.ProgettoBE_U2_W2_D5_GestioneViaggiAziendali.service;

import com.example.ProgettoBE_U2_W2_D5_GestioneViaggiAziendali.dto.ViaggioDTO;
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

    //metodi dao

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
            throw new RuntimeException("Nessun viaggio trovato con l'id inserito");
        }
    }

    //put- sostituisciViaggioById
    public void updateViaggio(ViaggioDTO viaggioDTO, long id) {
        Optional<Viaggio> viaggioTrovato = viaggioRepo.findById(id);

        if(viaggioTrovato.isPresent()) {
            Viaggio viaggio = viaggioTrovato.get();
            viaggio.setDestinazione(viaggioDTO.getDestinazione());
            viaggio.setData(viaggioDTO.getData());
            viaggio.setStato(viaggioDTO.getStato());
            viaggioRepo.save(viaggio);
        } else {
            throw new RuntimeException("Errore nella modifica del viaggio inserito");
        }
    }

    //deleteByID
    public String deleteVIaggio(long id) {
        Optional<Viaggio> viaggioTrovato = viaggioRepo.findById(id);
        if (viaggioTrovato.isPresent()){
            viaggioRepo.delete(viaggioTrovato.get());
            return "Viaggio con id: " + id + " eliminato con successo!";
        } else {
            throw new RuntimeException("Errore nel delete! Nessun elemento trovato con questo id");
        }
    }

    //ASSEGNAZIONE DIPENDENTE A VIAGGIO
    public void addDipendente(long viaggioId, long dipendenteId) {
        Optional<Viaggio> viaggio = viaggioRepo.findById(viaggioId);
        Optional<Dipendente> dipendente = dipendenteRepo.findById(dipendenteId);
        if (viaggio.isPresent() && dipendente.isPresent()) {
            Viaggio viaggiodaSalvare = viaggio.get();
            viaggiodaSalvare.setDipendente(dipendente.get());
            viaggioRepo.save(viaggiodaSalvare);
        } else {
            throw new RuntimeException("Viaggio o Dipendente non trovato con questo id");
        }
    }
    //modifica stato viaggio
    public void modificaStatoViaggio(long viaggioId, StatoViaggio statoViaggio) {
        Optional<Viaggio> viaggio = viaggioRepo.findById(viaggioId);
        if (viaggio.isPresent()) {
            Viaggio viaggiodaSalvare = viaggio.get();
            viaggiodaSalvare.setStato(statoViaggio);
            viaggioRepo.save(viaggiodaSalvare);
        } else {
            throw new RuntimeException("Viaggio non trovato con questo id");
        }
    }



    //metodi travaso DTO

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
