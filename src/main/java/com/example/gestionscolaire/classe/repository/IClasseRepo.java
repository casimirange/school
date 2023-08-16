package com.example.gestionscolaire.classe.repository;

import com.example.gestionscolaire.classe.model.Classe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface IClasseRepo extends JpaRepository<Classe, Long> {
}
