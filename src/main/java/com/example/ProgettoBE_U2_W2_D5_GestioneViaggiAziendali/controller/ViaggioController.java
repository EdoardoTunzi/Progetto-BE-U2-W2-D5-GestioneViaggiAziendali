package com.example.ProgettoBE_U2_W2_D5_GestioneViaggiAziendali.controller;

import com.example.ProgettoBE_U2_W2_D5_GestioneViaggiAziendali.dto.ViaggioDTO;
import com.example.ProgettoBE_U2_W2_D5_GestioneViaggiAziendali.service.ViaggioService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/viaggio")
public class ViaggioController {
    @Autowired
    ViaggioService viaggioService;

    @PostMapping("")
    public ResponseEntity<?> nuovoViaggio(@RequestBody @Validated ViaggioDTO viaggioDTO, BindingResult validation) {

        if (validation.hasErrors()) {
            String messaggioErrori = "ERRORE DI VALIDAZIONE \n";

            for (ObjectError errore : validation.getAllErrors()) {
                messaggioErrori += errore.getDefaultMessage() + "\n";
            }
            return new ResponseEntity<>(messaggioErrori, HttpStatus.BAD_REQUEST);
        }

        Long idNuovoViaggio = viaggioService.saveViaggio(viaggioDTO);
        return new ResponseEntity<>("Viaggio inserito nel DB con id: " + idNuovoViaggio, HttpStatus.CREATED);
    }

    @GetMapping(value = "", produces = "application/json")
    public ResponseEntity<Page<ViaggioDTO>> getAllViaggi(Pageable page) {
        Page<ViaggioDTO> viaggi = viaggioService.getAllViaggio(page);
        return new ResponseEntity<>(viaggi, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ViaggioDTO getViaggioById(@PathVariable long id) {
        return viaggioService.findViaggioById(id);

    }

    @PutMapping("/modifica/{id}")
    public ResponseEntity<?> updateViaggio(@RequestBody @Validated ViaggioDTO viaggioDTO,BindingResult validation, @PathVariable long id ) {
        if (validation.hasErrors()) {
            String messaggioErrori = "ERRORE DI VALIDAZIONE \n";

            for (ObjectError errore : validation.getAllErrors()) {
                messaggioErrori += errore.getDefaultMessage() + "\n";
            }
            return new ResponseEntity<>(messaggioErrori, HttpStatus.BAD_REQUEST);
        } else {
            viaggioService.updateViaggio(viaggioDTO, id);
            return new ResponseEntity<>("Il viaggio è stato modificato correttamente", HttpStatus.OK);
        }
    }
}
