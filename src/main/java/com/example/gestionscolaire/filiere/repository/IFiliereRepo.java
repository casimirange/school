package com.example.gestionscolaire.filiere.repository;

import com.example.gestionscolaire.filiere.model.Filiere;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface IFiliereRepo extends JpaRepository<Filiere, Long> {
}
