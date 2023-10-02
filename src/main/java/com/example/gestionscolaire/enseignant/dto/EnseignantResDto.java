package com.example.gestionscolaire.enseignant.dto;

import com.example.gestionscolaire.statut.model.Statut;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.core.io.Resource;
import lombok.NoArgsConstructor;

import java.io.InputStream;
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
    private String schoolMatricule;
    private String photoLink;
    private Statut status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private byte[] image;
}
