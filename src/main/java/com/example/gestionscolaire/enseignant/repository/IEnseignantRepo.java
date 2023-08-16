package com.example.gestionscolaire.enseignant.repository;

import com.example.gestionscolaire.enseignant.model.Enseignants;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IEnseignantRepo extends JpaRepository<Enseignants, Long> {
}
