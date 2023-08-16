package com.example.gestionscolaire.acces.controller;

import com.example.gestionscolaire.acces.dto.PointageProfReqDto;
import com.example.gestionscolaire.acces.dto.PointageProfResDto;
import com.example.gestionscolaire.acces.model.PointageProfesseur;
import com.example.gestionscolaire.acces.service.IPointageProfService;
import com.example.gestionscolaire.etudiant.dto.EtudiantReqDto;
import com.example.gestionscolaire.etudiant.dto.EtudiantResDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/pointageProf")
@CrossOrigin("*")
public class PointageProfController {
    private final IPointageProfService iPointageProfService;

    @PutMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<PointageProfesseur> savePointageProf(@RequestBody PointageProfReqDto pointageProfReqDto) {
        return ResponseEntity.ok().body(iPointageProfService.enregistrerPointage(pointageProfReqDto));
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<PointageProfResDto> getAllPointageProfs() {
        return iPointageProfService.pointagesProfs();
    }
}
