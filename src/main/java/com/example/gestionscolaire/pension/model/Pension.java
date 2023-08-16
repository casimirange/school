package com.example.gestionscolaire.pension.model;

import com.example.gestionscolaire.classe.model.Classe;
import com.example.gestionscolaire.filiere.model.Filiere;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Pension {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String inscription;
    private String tranche1;
    private String tranche2;
    private String total;

    @OneToMany
    private List<Classe> classes;
}
