//package com.example.gestionscolaire.personne.controller;
//
//import com.example.gestionscolaire.personne.dto.EnseignantRequest;
//import com.example.gestionscolaire.personne.dto.EnseignantResponse;
//import com.example.gestionscolaire.personne.dto.EtudiantRequest;
//import com.example.gestionscolaire.personne.dto.EtudiantResponse;
//import com.example.gestionscolaire.personne.service.EnseignantService;
//import com.example.gestionscolaire.personne.service.EtudiantService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.HttpStatus;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
////@RestController
////@RequestMapping("/api/prof")
//@RequiredArgsConstructor
//public class EnseignantController {
//
//    private final EnseignantService enseignantService;
//
//    @PostMapping
//    @ResponseStatus(HttpStatus.CREATED)
//    public void createEtudiant(@RequestBody EnseignantRequest productRequest) {
//        enseignantService.createEnseignant(productRequest);
//    }
//
//    @GetMapping
//    @ResponseStatus(HttpStatus.OK)
//    public List<EnseignantResponse> getAllEtudiants() {
//        return enseignantService.getAllEnseignant();
//    }
//}
