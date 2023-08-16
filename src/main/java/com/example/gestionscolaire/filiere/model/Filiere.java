package com.example.gestionscolaire.filiere.model;

import com.example.gestionscolaire.classe.model.Classe;
import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Filiere {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(unique = true, nullable = false)
    @NotNull
    private String filiereName;

    @OneToMany
    private Set<Classe> classes;
}
