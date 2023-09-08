package com.example.gestionscolaire.acces.dto;

import com.example.gestionscolaire.acces.model.TypePointage;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PointageProfReqDto {

//    private LocalDate date;
//    private LocalTime getTime;
    private String matricule;
//    private TypePointage type;
}
