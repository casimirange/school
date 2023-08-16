package com.example.gestionscolaire.acces.controller;

import com.example.gestionscolaire.acces.dto.MessageResponseDto;
import com.example.gestionscolaire.acces.dto.PointageEtudiantsReqDto;
import com.example.gestionscolaire.acces.dto.PointageEtudiantsResDto;
import com.example.gestionscolaire.acces.service.IPointageEtudiantService;
import com.example.gestionscolaire.configuration.scolarite.model.Control;
import com.example.gestionscolaire.configuration.scolarite.service.IConrolService;
import com.example.gestionscolaire.etudiant.dto.EtudiantResDto;
import com.example.gestionscolaire.etudiant.service.IEtudiantService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/pointageEtudiants")
@CrossOrigin("*")
public class PointageEtudiantsController {
    private final IPointageEtudiantService iPointageEtudiantService;
    private final IConrolService iConrolService;
    private final IEtudiantService iEtudiantService;

    @PutMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> savePointageEtd(@RequestBody PointageEtudiantsReqDto pointageEtudiantsReqDto) {
        EtudiantResDto etudiants = iEtudiantService.getEtudiantbyId(pointageEtudiantsReqDto.getEtdId());
        System.out.println("et :"+etudiants);
        if (iConrolService.existControlByEtat(true)){
            Control control = iConrolService.findControlByEtat(true);
            LocalDateTime now = LocalDateTime.now();
            LocalDateTime specificDate = control.getDateControl();
            if (now.isAfter(specificDate)) {
                // Effectuer des actions une fois que la date actuelle est après la date spécifique
                if (control.getControlPoint().getType().toString().equals("INSCRIPTION")){
                    double range = 100 * (Double.parseDouble(etudiants.getMontantPay()) / Double.parseDouble(etudiants.getTotalPension()));
                    if (range < control.getControlPoint().getRangeLevel()){
                        return ResponseEntity.badRequest().body(new MessageResponseDto(HttpStatus.BAD_REQUEST, "vous n'avez pas payé l'inscription"));
                    }
                    if (range >= control.getControlPoint().getRangeLevel()){
                        return ResponseEntity.ok().body(iPointageEtudiantService.enregistrerPointage(pointageEtudiantsReqDto));
                    }
                }
                if (control.getControlPoint().getType().toString().equals("TRANCHE1")){
                    double range = 100 * (Double.parseDouble(etudiants.getMontantPay()) / Double.parseDouble(etudiants.getTotalPension()));
                    if (range < control.getControlPoint().getRangeLevel()){
                        return ResponseEntity.badRequest().body(new MessageResponseDto(HttpStatus.BAD_REQUEST, "vous n'avez pas payé la premiere tranche"));
                    }
                    if (range >= control.getControlPoint().getRangeLevel()){
                        return ResponseEntity.ok().body(iPointageEtudiantService.enregistrerPointage(pointageEtudiantsReqDto));
                    }
                }
                if (control.getControlPoint().getType().toString().equals("TRANCHE2")){
                    double range = 100 * (Double.parseDouble(etudiants.getMontantPay()) / Double.parseDouble(etudiants.getTotalPension()));
                    if (range < control.getControlPoint().getRangeLevel()){
                        return ResponseEntity.badRequest().body(new MessageResponseDto(HttpStatus.BAD_REQUEST, "vous n'avez pas payé la deuxieme tranche"));
                    }
                    if (range >= control.getControlPoint().getRangeLevel()){
                        return ResponseEntity.ok().body(iPointageEtudiantService.enregistrerPointage(pointageEtudiantsReqDto));
                    }
                }
            } else if (now.isBefore(specificDate)) {
                // Effectuer des actions une fois que la date actuelle est avant la date spécifique
                return ResponseEntity.ok().body(iPointageEtudiantService.enregistrerPointage(pointageEtudiantsReqDto));
            } else if (now.isEqual(specificDate)) {
                // Effectuer des actions une fois que la date actuelle est égale à la date spécifique
                if (control.getControlPoint().getType().toString().equals("INSCRIPTION")){
                    double range = 100 * (Double.parseDouble(etudiants.getMontantPay()) / Double.parseDouble(etudiants.getTotalPension()));
                    if (range < control.getControlPoint().getRangeLevel()){
                        return ResponseEntity.badRequest().body(new MessageResponseDto(HttpStatus.BAD_REQUEST, "vous n'avez pas payé l'inscription"));
                    }
                    if (range >= control.getControlPoint().getRangeLevel()){
                        return ResponseEntity.ok().body(iPointageEtudiantService.enregistrerPointage(pointageEtudiantsReqDto));
                    }
                }
                if (control.getControlPoint().getType().toString().equals("TRANCHE1")){
                    double range = 100 * (Double.parseDouble(etudiants.getMontantPay()) / Double.parseDouble(etudiants.getTotalPension()));
                    if (range < control.getControlPoint().getRangeLevel()){
                        return ResponseEntity.badRequest().body(new MessageResponseDto(HttpStatus.BAD_REQUEST, "vous n'avez pas payé la premiere tranche"));
                    }
                    if (range >= control.getControlPoint().getRangeLevel()){
                        return ResponseEntity.ok().body(iPointageEtudiantService.enregistrerPointage(pointageEtudiantsReqDto));
                    }
                }
                if (control.getControlPoint().getType().toString().equals("TRANCHE2")){
                    double range = 100 * (Double.parseDouble(etudiants.getMontantPay()) / Double.parseDouble(etudiants.getTotalPension()));
                    if (range < control.getControlPoint().getRangeLevel()){
                        return ResponseEntity.badRequest().body(new MessageResponseDto(HttpStatus.BAD_REQUEST, "vous n'avez pas payé la deuxieme tranche"));
                    }
                    if (range >= control.getControlPoint().getRangeLevel()){
                        return ResponseEntity.ok().body(iPointageEtudiantService.enregistrerPointage(pointageEtudiantsReqDto));
                    }
                }


            }
        }else {
            return ResponseEntity.ok().body(iPointageEtudiantService.enregistrerPointage(pointageEtudiantsReqDto));
        }
        return null;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<PointageEtudiantsResDto> getAllPointageEtd() {
        return iPointageEtudiantService.pointageEtds();
    }
}
