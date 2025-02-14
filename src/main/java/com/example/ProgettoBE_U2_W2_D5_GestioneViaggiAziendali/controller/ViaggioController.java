package com.example.ProgettoBE_U2_W2_D5_GestioneViaggiAziendali.controller;

import com.example.ProgettoBE_U2_W2_D5_GestioneViaggiAziendali.dto.ViaggioDTO;
import com.example.ProgettoBE_U2_W2_D5_GestioneViaggiAziendali.service.ViaggioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/viaggio")
public class ViaggioController {
    @Autowired
    ViaggioService viaggioService;

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public String nuovoViaggio(@RequestBody @Validated ViaggioDTO viaggioDTO) {
        Long idNuovoViaggio = viaggioService.saveViaggio(viaggioDTO);
        return "Viaggio inserito nel DB con id: " + idNuovoViaggio;
    }
}
