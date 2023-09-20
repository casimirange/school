package com.example.gestionscolaire.Document.service;

import com.example.gestionscolaire.Document.entity.TypeDocument;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface IDocumentStorageService {
    String storeFile(MultipartFile file, String matricule, String docType, TypeDocument typeDocument);
    String storeProfFile(MultipartFile file, String matricule, String docType, TypeDocument typeDocument);

    Resource loadFileAsResource(String fileName) throws Exception;

    String getDocumentName(String matricule, String docType, Long type);
}
