package com.example.ProgettoBE_U2_W2_D5_GestioneViaggiAziendali.controller;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.example.ProgettoBE_U2_W2_D5_GestioneViaggiAziendali.dto.DipendenteDTO;
import com.example.ProgettoBE_U2_W2_D5_GestioneViaggiAziendali.dto.PrenotazioneDTO;
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
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping("/dipendente")
public class DipendenteController {
    @Autowired
    DipendenteService dipendenteService;
    @Autowired
    Cloudinary cloudinaryConfig;

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
        return new ResponseEntity<>("Dipendente inserito nel DB con id: " + idNuovoDipendente, HttpStatus.CREATED);
    }

    @PostMapping("/profilePic")
    public ResponseEntity<?> nuovoDipendenteConImmagine(@RequestPart("profilePic") MultipartFile profilePic, @RequestPart @Validated DipendenteDTO dipendenteDTO, BindingResult validation) {

        if (validation.hasErrors()) {
            String messaggioErrori = "ERRORE DI VALIDAZIONE \n";

            for (ObjectError errore : validation.getAllErrors()) {
                messaggioErrori += errore.getDefaultMessage() + "\n";
            }
            return new ResponseEntity<>(messaggioErrori, HttpStatus.BAD_REQUEST);
        }

        try {

            Map mappa = cloudinaryConfig.uploader().upload(profilePic.getBytes(), ObjectUtils.emptyMap());
            String urlImage = mappa.get("secure_url").toString();
            dipendenteDTO.setProfilePic(urlImage);

            Long idNuovoDipendente = dipendenteService.saveDipendente(dipendenteDTO);
            return new ResponseEntity<>("Dipendente inserito nel DB con id: " + idNuovoDipendente, HttpStatus.CREATED);
        } catch (IOException e) {
            throw new RuntimeException("Errore nel caricamento dell'immagine: " + e );
        }


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

    @PostMapping("/nuovaPrenotazione")
    public ResponseEntity<?> creaNuovaPrenotazione(@RequestBody @Validated PrenotazioneDTO prenotazioneDTO,BindingResult validation) {
        if (validation.hasErrors()) {
            String messaggioErrori = "ERRORE DI VALIDAZIONE \n";

            for (ObjectError errore : validation.getAllErrors()) {
                messaggioErrori += errore.getDefaultMessage() + "\n";
            }
            return new ResponseEntity<>(messaggioErrori, HttpStatus.BAD_REQUEST);
        } else {
            dipendenteService.creaPrenotazione(prenotazioneDTO);
            return new ResponseEntity<>("La prenotazione è stata creata con correttamente", HttpStatus.OK);
        }

    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteDipendente(@PathVariable long id) {
        return new ResponseEntity<>(dipendenteService.deleteDipendente(id), HttpStatus.OK);
    }
}
