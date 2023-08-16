//package com.example.gestionscolaire.personne.controller;
//
//import com.example.gestionscolaire.personne.dto.EtudiantRequest;
//import com.example.gestionscolaire.personne.dto.EtudiantResponse;
//import com.example.gestionscolaire.personne.service.EtudiantService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.HttpStatus;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
////@RestController
////@RequestMapping("/api/etudiant")
//@RequiredArgsConstructor
//public class EtudiantController {
//
//    private final EtudiantService etudiantService;
//
//    @PostMapping
//    @ResponseStatus(HttpStatus.CREATED)
//    public void createEtudiant(@RequestBody EtudiantRequest productRequest) {
//        etudiantService.createEtudiant(productRequest);
//    }
//
//    @GetMapping
//    @ResponseStatus(HttpStatus.OK)
//    public List<EtudiantResponse> getAllEtudiants() {
//        return etudiantService.getAllEtudiant();
//    }
//}
