package com.example.gestionscolaire.etudiant.dto;

import com.example.gestionscolaire.statut.model.Statut;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
}
