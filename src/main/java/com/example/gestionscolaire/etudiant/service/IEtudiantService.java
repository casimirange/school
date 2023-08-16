package com.example.gestionscolaire.etudiant.service;

import com.example.gestionscolaire.etudiant.dto.EtudiantReqDto;
import com.example.gestionscolaire.etudiant.dto.EtudiantResDto;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface IEtudiantService {
    EtudiantResDto addEtudiant(EtudiantReqDto etudiantReqDto);
    List<EtudiantResDto> getEtudiants();
    EtudiantResDto getEtudiantbyId(Long id);
    List<List<String>> importListEtudiant(MultipartFile file) throws IOException;
}
