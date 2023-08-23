package com.example.gestionscolaire.acces.dto;

import com.example.gestionscolaire.acces.model.TypePointage;
import com.example.gestionscolaire.etudiant.model.Etudiants;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.ManyToOne;
import java.time.LocalDate;
import java.time.LocalTime;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PointageEtudiantsReqDto {

//    private LocalDate date;
//    private LocalTime getTime;
    private Long etdId;
    private TypePointage type;
}
