package com.example.ProgettoBE_U2_W2_D5_GestioneViaggiAziendali.service;

import com.example.ProgettoBE_U2_W2_D5_GestioneViaggiAziendali.dto.ViaggioDTO;
import com.example.ProgettoBE_U2_W2_D5_GestioneViaggiAziendali.model.Viaggio;
import com.example.ProgettoBE_U2_W2_D5_GestioneViaggiAziendali.repository.ViaggioDAORepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ViaggioService {
    @Autowired
    ViaggioDAORepository viaggioRepo;

    //metodi dao

    //salvataggio
    public Long saveViaggio(ViaggioDTO viaggioDTO) {
        Viaggio viaggioInserito = fromViaggioDTOToViaggio(viaggioDTO);
        Viaggio viaggioSalvato = viaggioRepo.save(viaggioInserito);
        return viaggioSalvato.getId();
    }



    //metodi travaso DTO

    //Viaggio a Viaggio DTO
    public Viaggio fromViaggioDTOToViaggio(ViaggioDTO viaggioDTO) {
        Viaggio viaggio = new Viaggio();
        viaggio.setDestinazione(viaggioDTO.getDestinazione());
        viaggio.setData(viaggioDTO.getData());
        viaggio.setStato(viaggioDTO.getStato());
        return viaggio;
    }
    //ViaggioDTo a Viaggio
    public ViaggioDTO fromViaggioToViaggioDTO(Viaggio viaggio) {
        ViaggioDTO viaggioDTO = new ViaggioDTO();
        viaggioDTO.setDestinazione(viaggio.getDestinazione());
        viaggioDTO.setData(viaggio.getData());
        viaggioDTO.setStato(viaggio.getStato());
        return viaggioDTO;
    }
}
