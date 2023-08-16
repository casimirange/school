package com.example.gestionscolaire.acces.dto;

import com.example.gestionscolaire.acces.model.TypePointage;
import com.example.gestionscolaire.etudiant.model.Etudiants;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PointageEtudiantsResDto {
    private Long id;
    private LocalDate date;
    private LocalTime getTimeIn1;
    private LocalTime getTimeIn2;
    private LocalTime getTimeIn3;
    private LocalTime getTimeIn4;
    private LocalTime getTimeIn5;
    private LocalTime getTimeOut1;
    private LocalTime getTimeOut2;
    private LocalTime getTimeOut3;
    private LocalTime getTimeOut4;
    private LocalTime getTimeOut5;
    private LocalTime getTime;
    private Etudiants etudiants;
    private TypePointage type;
}
