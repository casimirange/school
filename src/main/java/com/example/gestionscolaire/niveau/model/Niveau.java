package com.example.gestionscolaire.niveau.model;

import com.example.gestionscolaire.etudiant.model.Etudiants;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Niveau {
    @Id
    @Column(unique = true)
    private EType niveau;

}
