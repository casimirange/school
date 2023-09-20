package com.example.gestionscolaire.etudiant.dto;

import com.example.gestionscolaire.statut.model.Statut;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EtudiantResDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String matricule;
    private Statut status;
    private String classe;
    private String totalPension;
    private String montantPay;
    private String dateOfBirth;
    private String placeOfBirth;
    private String photoLink;
    private String sex;
    private String fatherName;
    private String motherName;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
