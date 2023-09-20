package com.example.gestionscolaire.enseignant.service;

import com.example.gestionscolaire.enseignant.dto.EnseignantReqDto;
import com.example.gestionscolaire.enseignant.dto.EnseignantResDto;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface IEnseignantService {
    EnseignantResDto addProf(EnseignantReqDto enseignantReqDto);
    Page<EnseignantResDto> getProfs(int page, int size, String sort, String order);
    List<List<String>> importListProf(MultipartFile file) throws IOException;
    EnseignantResDto getProfbyId(Long id);
    EnseignantResDto getProfbyMatricule(String matricule);
    byte[] getQrCodeByProf(String matricule);
    EnseignantResDto updateEnseignant(EnseignantReqDto enseignantReqDto, String matricule);
    EnseignantResDto disableEtudiant(String matricule);
}
