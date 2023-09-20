package com.example.gestionscolaire.Document.repository;


import com.example.gestionscolaire.Document.entity.ETypeDocument;
import com.example.gestionscolaire.Document.entity.TypeDocument;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.Optional;

@RepositoryRestResource(path = "typeDocument")
public interface ITypeDocumentRepo extends JpaRepository<TypeDocument, Long> {
	Optional<TypeDocument> findByName(ETypeDocument name);

	boolean existsByName(ETypeDocument name);

}
