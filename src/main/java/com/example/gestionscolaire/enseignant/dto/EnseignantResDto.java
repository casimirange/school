package com.example.gestionscolaire.enseignant.dto;

import com.example.gestionscolaire.statut.model.Statut;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EnseignantResDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String matricule;
    private String photoLink;
    private Statut status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
