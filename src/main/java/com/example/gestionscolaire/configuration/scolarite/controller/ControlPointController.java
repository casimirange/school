package com.example.gestionscolaire.configuration.scolarite.controller;

import com.example.gestionscolaire.configuration.scolarite.dto.ControlPointReqDto;
import com.example.gestionscolaire.configuration.scolarite.model.ControlCkeckPoint;
import com.example.gestionscolaire.configuration.scolarite.service.IConrolPointService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/controlPoint")
@CrossOrigin("*")
public class ControlPointController {
    private final IConrolPointService iConrolPointService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> saveControlPoint(@RequestBody ControlPointReqDto controlReqDto) {
        return ResponseEntity.ok().body(iConrolPointService.addControlPoint(controlReqDto));
    }

    @PutMapping("/{id:[0-9]+}")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> updateControl(@PathVariable Long id,@RequestBody ControlPointReqDto controlReqDto) {
        return ResponseEntity.ok().body(iConrolPointService.updateControlPoint(id, controlReqDto));
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ControlCkeckPoint> getAll() {
        return iConrolPointService.allControlPoint();
    }
}
