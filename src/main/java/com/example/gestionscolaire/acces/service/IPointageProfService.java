package com.example.gestionscolaire.acces.service;

import com.example.gestionscolaire.acces.dto.PointageProfReqDto;
import com.example.gestionscolaire.acces.dto.PointageProfResDto;
import com.example.gestionscolaire.acces.model.PointageProfesseur;

import java.util.List;

public interface IPointageProfService {
    PointageProfesseur enregistrerPointage(PointageProfReqDto pointageProfReqDto);
    List<PointageProfResDto> pointagesProfs();
}
