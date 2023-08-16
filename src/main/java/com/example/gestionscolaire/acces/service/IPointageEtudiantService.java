package com.example.gestionscolaire.acces.service;

import com.example.gestionscolaire.acces.dto.PointageEtudiantsReqDto;
import com.example.gestionscolaire.acces.dto.PointageEtudiantsResDto;
import com.example.gestionscolaire.acces.model.PointageEtudiant;

import java.util.List;

public interface IPointageEtudiantService {
    PointageEtudiant enregistrerPointage(PointageEtudiantsReqDto pointageEtudiantsReqDto);
    List<PointageEtudiantsResDto> pointageEtds();
}
