package com.example.gestionscolaire.acces.repository;

import com.example.gestionscolaire.acces.dto.PointageEtudiantsResDto;
import com.example.gestionscolaire.acces.model.PointageEtudiant;
import com.example.gestionscolaire.etudiant.model.Etudiants;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface IPointageEtudiantRepo extends JpaRepository<PointageEtudiant, Long> {
    boolean existsPointageEtudiantByDateAndEtudiant(LocalDate date, Etudiants etudiants);
    Optional<PointageEtudiant> findByDateAndEtudiant(LocalDate date, Etudiants etudiants);
    Page<PointageEtudiant> findAll(Specification<PointageEtudiant> specification, Pageable pageable);
}
