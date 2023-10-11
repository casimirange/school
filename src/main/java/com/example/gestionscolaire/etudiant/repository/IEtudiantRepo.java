package com.example.gestionscolaire.etudiant.repository;

import com.example.gestionscolaire.etudiant.model.Etudiants;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IEtudiantRepo extends JpaRepository<Etudiants, Long> {
    Optional<Etudiants> findByMatricule(String matricule);
    Optional<Etudiants> findBySchoolMatricule(String matricule);
    List<Etudiants> findBySchoolMatriculeLikeIgnoreCase(String matricule);
    Page<Etudiants> findAll(Specification<Etudiants> specification, Pageable pageable);
}
