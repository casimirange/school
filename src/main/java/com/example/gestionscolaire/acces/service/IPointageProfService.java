package com.example.gestionscolaire.acces.service;

import com.example.gestionscolaire.acces.dto.PointageProfReqDto;
import com.example.gestionscolaire.acces.dto.PointageProfResDto;
import com.example.gestionscolaire.acces.model.PointageProfesseur;
import org.springframework.data.domain.Page;

import java.time.LocalDate;
import java.util.List;

public interface IPointageProfService {
    PointageProfesseur enregistrerPointage(PointageProfReqDto pointageProfReqDto);
    Page<PointageProfResDto> pointagesProfs(String matricule, LocalDate date1, LocalDate date2, int page, int size, String sort, String order);
}
