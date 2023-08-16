package com.example.gestionscolaire.versements.model;

import com.example.gestionscolaire.etudiant.model.Etudiants;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Versements {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    private Etudiants etudiants;

    private int montant;
    private String anneeAccademique;
}
