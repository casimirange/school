package com.example.gestionscolaire.versements.repository;

import com.example.gestionscolaire.versements.model.Versements;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface IVersementRepo extends JpaRepository<Versements, Long> {
}
