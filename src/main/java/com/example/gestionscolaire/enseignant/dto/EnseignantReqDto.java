package com.example.gestionscolaire.enseignant.dto;

import com.example.gestionscolaire.statut.model.Statut;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EnseignantReqDto {
    @NotNull(message = "{firstName required}")
    private String firstName;
    private String lastName;
    @NotNull(message = "{le matricule est obligatoire}")
    private String matricule;
    private Statut idStatus;
}
