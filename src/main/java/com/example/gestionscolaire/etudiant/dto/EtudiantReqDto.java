package com.example.gestionscolaire.etudiant.dto;

import com.example.gestionscolaire.statut.model.Statut;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EtudiantReqDto {
    @NotNull(message = "{firstName required}")
    @Schema(description = "Prénom de l'étudiant")
    private String firstName;
    @Schema(description = "Nom de l'étudiant")
    private String lastName;
    @NotNull(message = "{classe required}")
    @Schema(description = "Classe de l'étudiant")
    private String classe;
    @NotNull(message = "{montantPay required}")
    @Schema(description = "montant de la pension payée par l'étudiant")
    private String montantPay;
    @NotNull(message = "{totalPension required}")
    @Schema(description = "montant total de la pension que l'étudiant doit payé")
    private String totalPension;
    @NotNull(message = "{dateOfBirth required}")
    @Schema(description = "date de naissance de l'étudiant")
    private LocalDate dateOfBirth;
    @NotNull(message = "{placeOfBirth required}")
    @Schema(description = "lieu de naissance de l'étudiant")
    private String placeOfBirth;
    @NotNull(message = "{sex required}")
    @Schema(description = "sexe de l'étudiant")
    private String sex;
    @Schema(description = "Nom du père")
    private String fatherName;
    @Schema(description = "Nom de la mère")
    private String motherName;
    @Schema(description = "Matricule de l'établissement")
    private String schoolMatricule;
}
