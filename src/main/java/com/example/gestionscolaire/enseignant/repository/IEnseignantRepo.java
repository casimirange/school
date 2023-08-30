package com.example.gestionscolaire.enseignant.repository;

import com.example.gestionscolaire.enseignant.model.Enseignants;
import com.example.gestionscolaire.etudiant.model.Etudiants;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IEnseignantRepo extends JpaRepository<Enseignants, Long> {
    Optional<Enseignants> findByMatricule(String matricule);
}
