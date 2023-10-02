package com.example.gestionscolaire.etudiant.model;

import com.example.gestionscolaire.statut.model.Statut;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

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
    @Column(unique = true, nullable = false)
    private String schoolMatricule;
    private String classe;
    private String photoLink;
    private LocalDate dateOfBirth;
    private String placeOfBirth;
    private String sex;
    @Column(nullable = true)
    private String fatherName;
    @Column(nullable = true)
    private String motherName;
    @ManyToOne
    private Statut status;
    private String montantPay;
    private String totalPension;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
