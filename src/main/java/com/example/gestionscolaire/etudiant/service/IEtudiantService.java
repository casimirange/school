package com.example.gestionscolaire.etudiant.service;

import com.example.gestionscolaire.etudiant.dto.EtudiantReqDto;
import com.example.gestionscolaire.etudiant.dto.EtudiantResDto;
import com.google.zxing.WriterException;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface IEtudiantService {
    EtudiantResDto addEtudiant(EtudiantReqDto etudiantReqDto);
    EtudiantResDto updateEtudiant(EtudiantReqDto etudiantReqDto, String matricule);
    EtudiantResDto disableEtudiant(String matricule);
    Page<EtudiantResDto> getEtudiants(String firstName, String lastName, String matricule, String classe, String statut, String sexe, int page, int size, String sort, String order);
    EtudiantResDto getEtudiantbyId(Long id);
    EtudiantResDto getEtudiantbyMatricule(String matricule);
    byte[] getQrCodeByEtudiant(String matricule);
    List<List<String>> importListEtudiant(MultipartFile file) throws IOException, WriterException;
}
