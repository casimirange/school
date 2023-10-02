package com.example.gestionscolaire.acces.service;

import com.example.gestionscolaire.acces.dto.PointageEtudiantsReqDto;
import com.example.gestionscolaire.acces.dto.PointageEtudiantsResDto;
import com.example.gestionscolaire.acces.model.PointageEtudiant;
import org.springframework.data.domain.Page;

import java.time.LocalDate;
import java.util.List;

public interface IPointageEtudiantService {
    PointageEtudiant enregistrerPointage(PointageEtudiantsReqDto pointageEtudiantsReqDto);
    Page<PointageEtudiantsResDto> pointageEtds(String matricule, LocalDate date1, LocalDate date2, int page, int size, String sort, String order);
}
