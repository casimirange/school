package com.example.gestionscolaire.acces.model;

import com.example.gestionscolaire.enseignant.model.Enseignants;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class PointageProfesseur {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
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
    @ManyToOne
    private Enseignants enseignant;
    private TypePointage type;

}
