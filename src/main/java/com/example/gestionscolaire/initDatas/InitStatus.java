/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.gestionscolaire.initDatas;

import com.example.gestionscolaire.Users.entity.ERole;
import com.example.gestionscolaire.Users.entity.EStatusUser;
import com.example.gestionscolaire.Users.entity.RoleUser;
import com.example.gestionscolaire.Users.entity.StatusUser;
import com.example.gestionscolaire.Users.repository.IRoleUserRepo;
import com.example.gestionscolaire.statut.model.EStatus;
import com.example.gestionscolaire.statut.model.Statut;
import com.example.gestionscolaire.statut.repository.IStatusRepo;
import lombok.AllArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 *
 * @author Casimir
 */

@Component
@AllArgsConstructor
@Order(5)
public class InitStatus implements ApplicationRunner{

    private IStatusRepo iStatusRepo;
    
    @Override
    public void run(ApplicationArguments args) throws Exception {;
        System.out.println("initialisation des status utilisateurs");
        Statut userEnable = new Statut(EStatus.ACTIF);
        Statut userDisable = new Statut(EStatus.INACTIF);

        if (!iStatusRepo.existsByName(EStatus.ACTIF)) {
            iStatusRepo.save(userEnable);
        }
        if (!iStatusRepo.existsByName(EStatus.INACTIF)) {
            iStatusRepo.save(userDisable);
        }
               
    }
    
}
