package com.example.gestionscolaire.enseignant.service;

import com.example.gestionscolaire.enseignant.dto.EnseignantReqDto;
import com.example.gestionscolaire.enseignant.dto.EnseignantResDto;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface IEnseignantService {
    EnseignantResDto addProf(EnseignantReqDto enseignantReqDto);
    List<EnseignantResDto> getProfs();
    List<List<String>> importListProf(MultipartFile file) throws IOException;
    EnseignantResDto getProfbyId(Long id);
}
