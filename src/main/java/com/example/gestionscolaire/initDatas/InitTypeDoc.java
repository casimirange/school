/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.gestionscolaire.initDatas;

import com.example.gestionscolaire.Document.entity.ETypeDocument;
import com.example.gestionscolaire.Document.entity.TypeDocument;
import com.example.gestionscolaire.Document.repository.ITypeDocumentRepo;
import com.example.gestionscolaire.Users.entity.EStatusUser;
import com.example.gestionscolaire.Users.entity.StatusUser;
import com.example.gestionscolaire.Users.repository.IStatusUserRepo;
import lombok.AllArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Component;

/**
 *
 * @author Casimir
 */

@Component
@AllArgsConstructor
@Order(3)
public class InitTypeDoc implements ApplicationRunner{

    private IStatusUserRepo iTypeAccountRepository;
    private ITypeDocumentRepo iTypeDocumentRepo;

    @Override
    public void run(ApplicationArguments args) throws Exception {;
        System.out.println("initialisation du type de document");
        TypeDocument typeDocument = new TypeDocument(ETypeDocument.IMAGE);

        if (!iTypeDocumentRepo.existsByName(ETypeDocument.IMAGE)) {
            iTypeDocumentRepo.save(typeDocument);
        }
               
    }
    
}
