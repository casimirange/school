package com.example.gestionscolaire.niveau.repository;

import com.example.gestionscolaire.niveau.model.EType;
import com.example.gestionscolaire.niveau.model.Niveau;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface INiveauRepo extends JpaRepository<Niveau, EType> {
}
