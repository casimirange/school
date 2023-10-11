package com.example.gestionscolaire.statut.repository;

import com.example.gestionscolaire.Users.entity.EStatusUser;
import com.example.gestionscolaire.statut.model.EStatus;
import com.example.gestionscolaire.statut.model.Statut;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IStatusRepo extends JpaRepository<Statut, Long> {
    Optional<Statut> getStatutByName(EStatus eStatus);
    boolean existsByName(EStatus eStatus);
}
