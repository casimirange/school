package com.example.gestionscolaire.configuration.scolarite.repository;

import com.example.gestionscolaire.configuration.scolarite.model.Control;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IControlRepo extends JpaRepository<Control, Long> {
    Optional<Control> findControlByEtat(boolean etat);
    boolean existsControlByEtat(boolean etat);
}
