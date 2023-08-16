package com.example.gestionscolaire.enseignant.controller;

import com.example.gestionscolaire.enseignant.dto.EnseignantReqDto;
import com.example.gestionscolaire.enseignant.dto.EnseignantResDto;
import com.example.gestionscolaire.enseignant.service.IEnseignantService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/enseignants")
//@RequiredArgsConstructor
public class EnseignantController {
    @Autowired
    IEnseignantService enseignantService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<EnseignantResDto> createEnseignant(@RequestBody EnseignantReqDto enseignantRequest) {
        EnseignantResDto enseignantResDto = enseignantService.addProf(enseignantRequest);
        return ResponseEntity.ok().body(enseignantResDto);
    }

    @PostMapping("/upload")
    public ResponseEntity<List<List<String>>> uploadExcelFile(@RequestParam("file") MultipartFile file) {
        try {
            List<List<String>> excelData = enseignantService.importListProf(file);
            return ResponseEntity.ok(excelData);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<EnseignantResDto> getAllEnseignant() {
        return enseignantService.getProfs();
    }
}
