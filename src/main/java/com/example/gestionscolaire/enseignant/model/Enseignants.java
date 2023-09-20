package com.example.gestionscolaire.enseignant.model;

import com.example.gestionscolaire.statut.model.Statut;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class Enseignants {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String firstName;
    private String lastName;
    private String photoLink;
    @Column(unique = true, nullable = false)
    private String matricule;
    @ManyToOne
    private Statut status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
