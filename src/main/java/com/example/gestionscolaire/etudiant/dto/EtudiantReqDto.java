package com.example.gestionscolaire.etudiant.dto;

import com.example.gestionscolaire.statut.model.Statut;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EtudiantReqDto {
    @NotNull(message = "{firstName required}")
    @Schema(description = "Nom de l'étudiant")
    private String firstName;
    private String lastName;
    @NotNull(message = "{le matricule est obligatoire}")
    private String matricule;
    private String classe;
    private String montantPay;
    private String totalPension;
//    private Statut idStatus;
}
