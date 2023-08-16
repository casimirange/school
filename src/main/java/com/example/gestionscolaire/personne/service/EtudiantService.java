//package com.example.gestionscolaire.personne.service;
//
//import com.example.gestionscolaire.personne.dto.EtudiantRequest;
//import com.example.gestionscolaire.personne.dto.EtudiantResponse;
//import com.example.gestionscolaire.personne.model.Etudiant;
//import com.example.gestionscolaire.personne.repository.EtudiantRepo;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//import java.util.stream.Collectors;
//
////@Service
//@RequiredArgsConstructor
//@Slf4j
//public class EtudiantService {
//
//    private final EtudiantRepo etudiantRepo;
//
//    public void createEtudiant(EtudiantRequest etudiantRequest) {
////        Etudiant etudiant = Etudiant.builder()
////                .name(productRequest.getName())
////                .description(productRequest.getDescription())
////                .price(productRequest.getPrice())
////                .build();
//
//        Etudiant etudiant = new Etudiant();
//        etudiant.setClasse(etudiantRequest.getClasse());
//        etudiant.setFirstName(etudiantRequest.getFirstName());
//        etudiant.setLastName(etudiantRequest.getLastName());
//        etudiant.setEmail(etudiantRequest.getEmail());
//        etudiant.setFiliere(etudiantRequest.getFiliere());
//        etudiant.setEmail(etudiantRequest.getEmail());
//
//        etudiantRepo.save(etudiant);
//        log.info("Etudiant {} is saved", etudiant.getId());
//    }
//
//    public List<EtudiantResponse> getAllEtudiant() {
//        List<Etudiant> products = etudiantRepo.findAll();
//        return products.stream().map(this::mapToEtudiantResponse).collect(Collectors.toList());
//    }
//
//    private EtudiantResponse mapToEtudiantResponse(Etudiant etudiant) {
//        return EtudiantResponse.builder()
//                .id(etudiant.getId())
//                .firstName(etudiant.getFirstName())
//                .lastName(etudiant.getLastName())
//                .classe(etudiant.getClasse())
//                .dateNaissance(etudiant.getDateNaissance())
//                .email(etudiant.getEmail())
//                .tel(etudiant.getTel())
//                .matricule(etudiant.getMatricule())
//                .filiere(etudiant.getFiliere())
//                .sexe(etudiant.getSexe())
//                .niveau(etudiant.getNiveau())
//                .pension(etudiant.getPension())
//                .build();
//    }
//}
