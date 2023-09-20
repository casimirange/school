package com.example.gestionscolaire.Document.repository;

import com.example.gestionscolaire.Document.entity.DocumentStorageProperties;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface IDocumentStoragePropertiesRepo extends JpaRepository<DocumentStorageProperties, Long> {

    @Query(value = "Select * from seance_documents where matricule = ?1 and document_type = ?2 and type_id = ?3", nativeQuery = true)
    DocumentStorageProperties checkDocumentByOrderId(String matricule, String docType, Long type);

    @Query(value = "Select file_name from seance_documents a where matricule = ?1 and document_type = ?2 and type_id = ?3", nativeQuery = true)
    String getUploadDocumnetPath(String matricule, String docType, Long type);
}
