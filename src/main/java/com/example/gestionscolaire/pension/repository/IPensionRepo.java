package com.example.gestionscolaire.pension.repository;

import com.example.gestionscolaire.pension.model.Pension;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface IPensionRepo extends JpaRepository<Pension, Long> {
}
