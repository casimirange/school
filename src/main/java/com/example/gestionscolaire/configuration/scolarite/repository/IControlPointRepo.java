package com.example.gestionscolaire.configuration.scolarite.repository;

import com.example.gestionscolaire.configuration.scolarite.model.ControlCkeckPoint;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IControlPointRepo extends JpaRepository<ControlCkeckPoint, Long> {
}
