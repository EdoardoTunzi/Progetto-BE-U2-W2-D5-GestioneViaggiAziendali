package com.example.ProgettoBE_U2_W2_D5_GestioneViaggiAziendali.controller;

import com.example.ProgettoBE_U2_W2_D5_GestioneViaggiAziendali.dto.DipendenteDTO;
import com.example.ProgettoBE_U2_W2_D5_GestioneViaggiAziendali.service.DipendenteService;
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
@RequestMapping("/dipendente")
public class DipendenteController {
    @Autowired
    DipendenteService dipendenteService;

    @GetMapping(value = "", produces = "application/json")
    public ResponseEntity<Page<DipendenteDTO>> getAllDipendenti(Pageable page) {
        Page<DipendenteDTO> dipendenti = dipendenteService.getAllDipendenti(page);
        return new ResponseEntity<>(dipendenti, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public DipendenteDTO getDipendenteById(@PathVariable long id) {
        return dipendenteService.findDipendenteById(id);

    }

    @PostMapping("")
    public ResponseEntity<?> nuovoDipendente(@RequestBody @Validated DipendenteDTO dipendenteDTO, BindingResult validation) {

        if (validation.hasErrors()) {
            String messaggioErrori = "ERRORE DI VALIDAZIONE \n";

            for (ObjectError errore : validation.getAllErrors()) {
                messaggioErrori += errore.getDefaultMessage() + "\n";
            }
            return new ResponseEntity<>(messaggioErrori, HttpStatus.BAD_REQUEST);
        }

        Long idNuovoDipendente = dipendenteService.saveDipendente(dipendenteDTO);
        return new ResponseEntity<>("Viaggio inserito nel DB con id: " + idNuovoDipendente, HttpStatus.CREATED);
    }

    @PutMapping("/modifica/{id}")
    public ResponseEntity<?> updateDipendente(@RequestBody @Validated DipendenteDTO dipendenteDTO,BindingResult validation, @PathVariable long id ) {
        if (validation.hasErrors()) {
            String messaggioErrori = "ERRORE DI VALIDAZIONE \n";

            for (ObjectError errore : validation.getAllErrors()) {
                messaggioErrori += errore.getDefaultMessage() + "\n";
            }
            return new ResponseEntity<>(messaggioErrori, HttpStatus.BAD_REQUEST);
        } else {
            dipendenteService.updateDipendente(dipendenteDTO, id);
            return new ResponseEntity<>("Il dipendente è stato modificato correttamente", HttpStatus.OK);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteDipendente(@PathVariable long id) {
        return new ResponseEntity<>(dipendenteService.deleteDipendente(id), HttpStatus.OK);
    }
}
