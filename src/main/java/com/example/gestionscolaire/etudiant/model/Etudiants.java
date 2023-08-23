package com.example.gestionscolaire.etudiant.model;

import com.example.gestionscolaire.statut.model.Statut;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class Etudiants {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String firstName;
    private String lastName;
    @Column(unique = true, nullable = false)
    private String matricule;
    private String classe;
    @ManyToOne
    private Statut status;
    private String montantPay;
    private String totalPension;

}
