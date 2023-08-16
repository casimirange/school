package com.example.gestionscolaire.configuration.scolarite.service.impl;

import com.example.gestionscolaire.configuration.scolarite.dto.ControlPointReqDto;
import com.example.gestionscolaire.configuration.scolarite.model.ControlCkeckPoint;
import com.example.gestionscolaire.configuration.scolarite.repository.IControlPointRepo;
import com.example.gestionscolaire.configuration.scolarite.service.IConrolPointService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ControlPointService implements IConrolPointService {
    private final IControlPointRepo iContrilPointRopo;
    @Override
    public ControlCkeckPoint addControlPoint(ControlPointReqDto controlReqDto) {
        ControlCkeckPoint control = new ControlCkeckPoint();
        control.setRangeLevel(controlReqDto.getRange());
        control.setType(controlReqDto.getType());

        return iContrilPointRopo.save(control);
    }

    @Override
    public ControlCkeckPoint updateControlPoint(Long id, ControlPointReqDto controlReqDto) {
        ControlCkeckPoint control = iContrilPointRopo.findById(id).get();
        control.setRangeLevel(controlReqDto.getRange());
        control.setType(controlReqDto.getType());

        return iContrilPointRopo.save(control);
    }

    @Override
    public List<ControlCkeckPoint> allControlPoint() {
        return iContrilPointRopo.findAll();
    }
}
