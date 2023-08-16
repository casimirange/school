package com.example.gestionscolaire.configuration.scolarite.service;

import com.example.gestionscolaire.configuration.scolarite.dto.ControlReqDto;
import com.example.gestionscolaire.configuration.scolarite.model.Control;

import java.util.List;

public interface IConrolService {
    Control addControl(ControlReqDto controlReqDto);
    Control updateControl(Long id, ControlReqDto controlReqDto);
    boolean existControlByEtat(boolean etat);
    Control findControlByEtat(boolean etat);
    List<Control> allControl();
}
