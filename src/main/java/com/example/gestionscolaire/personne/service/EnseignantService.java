//package com.example.gestionscolaire.personne.service;
//
//import com.example.gestionscolaire.personne.dto.EnseignantRequest;
//import com.example.gestionscolaire.personne.dto.EnseignantResponse;
//import com.example.gestionscolaire.personne.dto.EtudiantRequest;
//import com.example.gestionscolaire.personne.dto.EtudiantResponse;
//import com.example.gestionscolaire.personne.model.Enseignant;
//import com.example.gestionscolaire.personne.model.Etudiant;
//import com.example.gestionscolaire.personne.repository.EnseignantRepo;
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
//public class EnseignantService {
//
//    private final EnseignantRepo enseignantRepo;
//
//    public void createEnseignant(EnseignantRequest enseignantRequest) {
////        Enseignant etudiant = Enseignant.builder()
////                .name(productRequest.getName())
////                .description(productRequest.getDescription())
////                .price(productRequest.getPrice())
////                .build();
//
//        Enseignant enseignant = new Enseignant();
//        enseignant.setFirstName(enseignantRequest.getFirstName());
//        enseignant.setLastName(enseignantRequest.getLastName());
//        enseignant.setEmail(enseignantRequest.getEmail());
//        enseignant.setEmail(enseignantRequest.getEmail());
//        enseignant.setDateNaissance(enseignantRequest.getDateNaissance());
//        enseignant.setSexe(enseignantRequest.getSexe());
//        enseignant.setTel(enseignantRequest.getTel());
//        enseignant.setMatricule(enseignantRequest.getMatricule());
////        enseignant.setPassword(enseignantRequest.getMatricule());
//
//        enseignantRepo.save(enseignant);
//        log.info("Enseignant {} is saved", enseignant.getId());
//    }
//
//    public List<EnseignantResponse> getAllEnseignant() {
//        List<Enseignant> products = enseignantRepo.findAll();
//        return products.stream().map(this::mapToEnseignantResponse).collect(Collectors.toList());
//    }
//
//    private EnseignantResponse mapToEnseignantResponse(Enseignant enseignant) {
//        return EnseignantResponse.builder()
//                .id(enseignant.getId())
//                .firstName(enseignant.getFirstName())
//                .lastName(enseignant.getLastName())
//                .dateNaissance(enseignant.getDateNaissance())
//                .email(enseignant.getEmail())
//                .tel(enseignant.getTel())
//                .matricule(enseignant.getMatricule())
//                .sexe(enseignant.getSexe())
//                .build();
//    }
//}
