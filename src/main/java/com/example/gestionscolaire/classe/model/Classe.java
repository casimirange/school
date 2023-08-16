package com.example.gestionscolaire.classe.model;

import com.example.gestionscolaire.etudiant.model.Etudiants;
import com.example.gestionscolaire.niveau.model.Niveau;
import com.example.gestionscolaire.pension.model.Pension;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Classe {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String className;

    @OneToMany
    private List<Etudiants> etudiants;
    @OneToOne
    private Niveau niveau;
//    @OneToOne
//    private Pension pension;
}
