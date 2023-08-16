package com.example.gestionscolaire.configuration.scolarite.controller;

import com.example.gestionscolaire.acces.dto.PointageProfReqDto;
import com.example.gestionscolaire.acces.dto.PointageProfResDto;
import com.example.gestionscolaire.acces.model.PointageProfesseur;
import com.example.gestionscolaire.acces.service.IPointageProfService;
import com.example.gestionscolaire.configuration.scolarite.dto.ControlReqDto;
import com.example.gestionscolaire.configuration.scolarite.model.Control;
import com.example.gestionscolaire.configuration.scolarite.service.IConrolService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/control")
@CrossOrigin("*")
public class ControlController {
    private final IConrolService iConrolService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> saveControl(@RequestBody ControlReqDto controlReqDto) {
        return ResponseEntity.ok().body(iConrolService.addControl(controlReqDto));
    }

    @PutMapping("/{id:[0-9]+}")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> updateControl(@PathVariable Long id,@RequestBody ControlReqDto controlReqDto) {
        return ResponseEntity.ok().body(iConrolService.updateControl(id, controlReqDto));
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Control> getAll() {
        return iConrolService.allControl();
    }
}
