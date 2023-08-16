package com.example.gestionscolaire.configuration.scolarite.service.impl;

import com.example.gestionscolaire.configuration.scolarite.dto.ControlReqDto;
import com.example.gestionscolaire.configuration.scolarite.model.Control;
import com.example.gestionscolaire.configuration.scolarite.repository.IControlPointRepo;
import com.example.gestionscolaire.configuration.scolarite.repository.IControlRepo;
import com.example.gestionscolaire.configuration.scolarite.service.IConrolService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@RequiredArgsConstructor
public class ControlService implements IConrolService {
    private final IControlRepo iContrilRopo;
    private final IControlPointRepo iContrilPointRopo;
    @Override
    public Control addControl(ControlReqDto controlReqDto) {
        Control control = new Control();
        control.setDateControl(controlReqDto.getDateControl());
        control.setControlPoint(iContrilPointRopo.findById(controlReqDto.getControlPoint()).get());
        control.setEtat(controlReqDto.isEtat());

        return iContrilRopo.save(control);
    }

    @Override
    public Control updateControl(Long id, ControlReqDto controlReqDto) {
        Control control = iContrilRopo.findById(id).get();
        control.setDateControl(controlReqDto.getDateControl());
        control.setControlPoint(iContrilPointRopo.findById(controlReqDto.getControlPoint()).get());
        control.setEtat(controlReqDto.isEtat());

        return iContrilRopo.save(control);
    }

    @Override
    public boolean existControlByEtat(boolean etat) {
        return iContrilRopo.existsControlByEtat(etat);
    }

    @Override
    public Control findControlByEtat(boolean etat) {
        return iContrilRopo.findControlByEtat(etat).get();
    }

    @Override
    public List<Control> allControl() {
        return iContrilRopo.findAll();
    }
}
