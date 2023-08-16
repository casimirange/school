package com.example.gestionscolaire.configuration.scolarite.service;

import com.example.gestionscolaire.configuration.scolarite.dto.ControlPointReqDto;
import com.example.gestionscolaire.configuration.scolarite.model.ControlCkeckPoint;

import java.util.List;

public interface IConrolPointService {
    ControlCkeckPoint addControlPoint(ControlPointReqDto controlPointReqDto);
    ControlCkeckPoint updateControlPoint(Long id, ControlPointReqDto controlReqDto);
    List<ControlCkeckPoint> allControlPoint();
}
