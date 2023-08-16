package com.example.gestionscolaire.acces.service.impl;

import com.example.gestionscolaire.acces.dto.PointageEtudiantsReqDto;
import com.example.gestionscolaire.acces.dto.PointageEtudiantsResDto;
import com.example.gestionscolaire.acces.dto.PointageProfResDto;
import com.example.gestionscolaire.acces.model.PointageEtudiant;
import com.example.gestionscolaire.acces.model.TypePointage;
import com.example.gestionscolaire.acces.repository.IPointageEtudiantRepo;
import com.example.gestionscolaire.acces.service.IPointageEtudiantService;
import com.example.gestionscolaire.etudiant.model.Etudiants;
import com.example.gestionscolaire.etudiant.repository.IEtudiantRepo;
import com.example.gestionscolaire.etudiant.service.IEtudiantService;
import com.example.gestionscolaire.statut.model.EStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class PointageEtudiantServiceImpl implements IPointageEtudiantService {

    private final IPointageEtudiantRepo iPointageEtudiantRepo;
    private final IEtudiantRepo iEtudiantRepo;

    @Override
    public PointageEtudiant enregistrerPointage(PointageEtudiantsReqDto pointageEtudiantsReqDto) {
        Etudiants etudiants = iEtudiantRepo.findById(pointageEtudiantsReqDto.getEtdId()).get();
        log.info("oui");
//        if (etudiants.getStatus().equals(EStatus.ACTIF)) {
            if (!iPointageEtudiantRepo.existsPointageEtudiantByDateAndEtudiant(LocalDate.now(), etudiants)) {
                PointageEtudiant pointageEtudiant = new PointageEtudiant();
                pointageEtudiant.setDate(LocalDate.now());
                if (pointageEtudiantsReqDto.getType().equals(TypePointage.ENTREE)) {
                    pointageEtudiant.setGetTimeIn1(LocalTime.now());
                } else {
                    pointageEtudiant.setGetTimeOut1(LocalTime.now());
                }
                pointageEtudiant.setType(pointageEtudiantsReqDto.getType());
                pointageEtudiant.setEtudiant(etudiants);
                iPointageEtudiantRepo.save(pointageEtudiant);
            }
            else {
                PointageEtudiant getPointageEtudiant = iPointageEtudiantRepo.findByDateAndEtudiant(LocalDate.now(), etudiants).get();

                if (pointageEtudiantsReqDto.getType().equals(TypePointage.ENTREE)) {
                    if (getPointageEtudiant.getGetTimeOut1() != null && getPointageEtudiant.getGetTimeIn2() == null) {
                        getPointageEtudiant.setGetTimeIn2(LocalTime.now());
                    }
                    if (getPointageEtudiant.getGetTimeOut2() != null && getPointageEtudiant.getGetTimeIn3() == null) {
                        getPointageEtudiant.setGetTimeIn3(LocalTime.now());
                    }
                    if (getPointageEtudiant.getGetTimeOut3() != null && getPointageEtudiant.getGetTimeIn4() == null) {
                        getPointageEtudiant.setGetTimeIn4(LocalTime.now());
                    }
                    if (getPointageEtudiant.getGetTimeOut4() != null && getPointageEtudiant.getGetTimeIn5() == null) {
                        getPointageEtudiant.setGetTimeIn5(LocalTime.now());
                    }

                } else {
                    if (getPointageEtudiant.getGetTimeIn1() != null && getPointageEtudiant.getGetTimeOut1() == null) {
                        getPointageEtudiant.setGetTimeOut1(LocalTime.now());
                    }
                    if (getPointageEtudiant.getGetTimeIn2() != null && getPointageEtudiant.getGetTimeOut2() == null) {
                        getPointageEtudiant.setGetTimeOut2(LocalTime.now());
                    }
                    if (getPointageEtudiant.getGetTimeIn3() != null && getPointageEtudiant.getGetTimeOut3() == null) {
                        getPointageEtudiant.setGetTimeOut3(LocalTime.now());
                    }
                    if (getPointageEtudiant.getGetTimeIn4() != null && getPointageEtudiant.getGetTimeOut4() == null) {
                        getPointageEtudiant.setGetTimeOut4(LocalTime.now());
                    }
                    if (getPointageEtudiant.getGetTimeIn5() != null && getPointageEtudiant.getGetTimeOut5() == null) {
                        getPointageEtudiant.setGetTimeOut5(LocalTime.now());
                    }
                }
                iPointageEtudiantRepo.save(getPointageEtudiant);
            }
//        }
        return null;
    }

    @Override
    public List<PointageEtudiantsResDto> pointageEtds() {
        List<PointageEtudiant> pointageEtudiants = iPointageEtudiantRepo.findAll();
        log.info(String.valueOf(iPointageEtudiantRepo.findAll()));
        List<PointageEtudiantsResDto> pointageEtudiantsResDtos = new ArrayList<>();

        pointageEtudiants.forEach(x -> {
            PointageEtudiantsResDto pointageEtudiantResDto = new PointageEtudiantsResDto();
            pointageEtudiantResDto.setId(x.getId());
            pointageEtudiantResDto.setEtudiants(x.getEtudiant());
            pointageEtudiantResDto.setDate(x.getDate());
            pointageEtudiantResDto.setType(x.getType());
            pointageEtudiantResDto.setGetTimeIn1(x.getGetTimeIn1());
            pointageEtudiantResDto.setGetTimeIn2(x.getGetTimeIn2());
            pointageEtudiantResDto.setGetTimeIn3(x.getGetTimeIn3());
            pointageEtudiantResDto.setGetTimeIn4(x.getGetTimeIn4());
            pointageEtudiantResDto.setGetTimeIn5(x.getGetTimeIn5());
            pointageEtudiantResDto.setGetTimeOut1(x.getGetTimeOut1());
            pointageEtudiantResDto.setGetTimeOut2(x.getGetTimeOut2());
            pointageEtudiantResDto.setGetTimeOut3(x.getGetTimeOut3());
            pointageEtudiantResDto.setGetTimeOut4(x.getGetTimeOut4());
            pointageEtudiantResDto.setGetTimeOut5(x.getGetTimeOut5());
            long y = 0, v = 0, z = 0, w = 0, d = 0;

            if (x.getGetTimeOut2() != null && x.getGetTimeIn2() != null) {
                y = ChronoUnit.MINUTES.between(x.getGetTimeIn2(), x.getGetTimeOut2());
            }

            if (x.getGetTimeOut3() != null && x.getGetTimeIn3() != null) {
                v = ChronoUnit.MINUTES.between(x.getGetTimeIn3(), x.getGetTimeOut3());
            }

            if (x.getGetTimeOut4() != null && x.getGetTimeIn4() != null) {
                z = ChronoUnit.MINUTES.between(x.getGetTimeIn4(), x.getGetTimeOut4());
            }

            if (x.getGetTimeOut5() != null && x.getGetTimeIn5() != null) {
                w = ChronoUnit.MINUTES.between(x.getGetTimeIn5(), x.getGetTimeOut5());
            }

            if (x.getGetTimeOut1() != null && x.getGetTimeIn1() != null) {
                d = ChronoUnit.MINUTES.between(x.getGetTimeIn1(), x.getGetTimeOut1());
            }
            int time = (int) (d + y + v + z + w);
            log.info("la durée en minute de " + x.getEtudiant().getLastName() + " est :" + time + " min et en temps est de: " + LocalTime.of((time / 60), (time % 60)));
            pointageEtudiantResDto.setGetTime(LocalTime.of((time / 60), (time % 60)));
            pointageEtudiantsResDtos.add(pointageEtudiantResDto);
        });

        log.info(String.valueOf(pointageEtudiantsResDtos));

        return pointageEtudiantsResDtos;
    }

}
