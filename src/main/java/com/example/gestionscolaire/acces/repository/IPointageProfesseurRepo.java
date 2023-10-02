package com.example.gestionscolaire.acces.repository;

import com.example.gestionscolaire.acces.model.PointageEtudiant;
import com.example.gestionscolaire.acces.model.PointageProfesseur;
import com.example.gestionscolaire.enseignant.model.Enseignants;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface IPointageProfesseurRepo extends JpaRepository<PointageProfesseur, Long> {
    boolean existsPointageProfesseurByDateAndEnseignant(LocalDate date, Enseignants enseignants);
    Optional<PointageProfesseur> findByDateAndEnseignant(LocalDate date, Enseignants enseignants);
    Page<PointageProfesseur> findAll(Specification<PointageProfesseur> specification, Pageable pageable);
}
