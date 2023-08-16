package com.example.gestionscolaire.acces.service.impl;

import com.example.gestionscolaire.acces.dto.PointageProfReqDto;
import com.example.gestionscolaire.acces.dto.PointageProfResDto;
import com.example.gestionscolaire.acces.model.PointageProfesseur;
import com.example.gestionscolaire.acces.model.TypePointage;
import com.example.gestionscolaire.acces.repository.IPointageProfesseurRepo;
import com.example.gestionscolaire.acces.service.IPointageProfService;
import com.example.gestionscolaire.enseignant.model.Enseignants;
import com.example.gestionscolaire.enseignant.repository.IEnseignantRepo;
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
public class PointageProfServiceImpl implements IPointageProfService {

    private final IPointageProfesseurRepo iPointageProfesseurRepo;
    private final IEnseignantRepo iEnseignantRepo;

    @Override
    public PointageProfesseur enregistrerPointage(PointageProfReqDto pointageProfReqDto) {
        Enseignants prof = iEnseignantRepo.findById(pointageProfReqDto.getProfId()).get();
        log.info("oui");
//        if (prof.getStatus().equals(EStatus.ACTIF)) {
            if (!iPointageProfesseurRepo.existsPointageProfesseurByDateAndEnseignant(LocalDate.now(), prof)) {
                log.info("non");
                PointageProfesseur pointageProf = new PointageProfesseur();
                pointageProf.setDate(LocalDate.now());
                if (pointageProfReqDto.getType().equals(TypePointage.ENTREE)) {
                    pointageProf.setGetTimeIn1(LocalTime.now());
                } else {
                    pointageProf.setGetTimeOut1(LocalTime.now());
                }
                pointageProf.setType(pointageProfReqDto.getType());
                pointageProf.setEnseignant(prof);
                iPointageProfesseurRepo.save(pointageProf);
            } else {
                PointageProfesseur getPointageProf = new PointageProfesseur();
                getPointageProf = iPointageProfesseurRepo.findByDateAndEnseignant(LocalDate.now(), prof).get();

                if (pointageProfReqDto.getType().equals(TypePointage.ENTREE)) {
                    if (getPointageProf.getGetTimeOut1() != null && getPointageProf.getGetTimeIn2() == null) {
                        getPointageProf.setGetTimeIn2(LocalTime.now());
                    }
                    if (getPointageProf.getGetTimeOut2() != null && getPointageProf.getGetTimeIn3() == null) {
                        getPointageProf.setGetTimeIn3(LocalTime.now());
                    }
                    if (getPointageProf.getGetTimeOut3() != null && getPointageProf.getGetTimeIn4() == null) {
                        getPointageProf.setGetTimeIn4(LocalTime.now());
                    }
                    if (getPointageProf.getGetTimeOut4() != null && getPointageProf.getGetTimeIn5() == null) {
                        getPointageProf.setGetTimeIn5(LocalTime.now());
                    }

                } else {
                    if (getPointageProf.getGetTimeIn1() != null && getPointageProf.getGetTimeOut1() == null) {
                        getPointageProf.setGetTimeOut1(LocalTime.now());
                    }
                    if (getPointageProf.getGetTimeIn2() != null && getPointageProf.getGetTimeOut2() == null) {
                        getPointageProf.setGetTimeOut2(LocalTime.now());
                    }
                    if (getPointageProf.getGetTimeIn3() != null && getPointageProf.getGetTimeOut3() == null) {
                        getPointageProf.setGetTimeOut3(LocalTime.now());
                    }
                    if (getPointageProf.getGetTimeIn4() != null && getPointageProf.getGetTimeOut4() == null) {
                        getPointageProf.setGetTimeOut4(LocalTime.now());
                    }
                    if (getPointageProf.getGetTimeIn5() != null && getPointageProf.getGetTimeOut5() == null) {
                        getPointageProf.setGetTimeOut5(LocalTime.now());
                    }
                }
                iPointageProfesseurRepo.save(getPointageProf);
            }
//        }
        return null;
    }

    @Override
    public List<PointageProfResDto> pointagesProfs() {
        List<PointageProfesseur> pointageProfesseurs = iPointageProfesseurRepo.findAll();
        log.info(String.valueOf(iPointageProfesseurRepo.findAll()));
        List<PointageProfResDto> pointageProfResDtos = new ArrayList<>();

        pointageProfesseurs.forEach(x -> {
            PointageProfResDto pointageProfResDto = new PointageProfResDto();
            pointageProfResDto.setId(x.getId());
            pointageProfResDto.setEnseignant(x.getEnseignant());
            pointageProfResDto.setDate(x.getDate());
            pointageProfResDto.setType(x.getType());
            pointageProfResDto.setGetTimeIn1(x.getGetTimeIn1());
            pointageProfResDto.setGetTimeIn2(x.getGetTimeIn2());
            pointageProfResDto.setGetTimeIn3(x.getGetTimeIn3());
            pointageProfResDto.setGetTimeIn4(x.getGetTimeIn4());
            pointageProfResDto.setGetTimeIn5(x.getGetTimeIn5());
            pointageProfResDto.setGetTimeOut1(x.getGetTimeOut1());
            pointageProfResDto.setGetTimeOut2(x.getGetTimeOut2());
            pointageProfResDto.setGetTimeOut3(x.getGetTimeOut3());
            pointageProfResDto.setGetTimeOut4(x.getGetTimeOut4());
            pointageProfResDto.setGetTimeOut5(x.getGetTimeOut5());
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
            log.info("la durée en minute de " + x.getEnseignant().getLastName() + " est :" + time + " min et en temps est de: " + LocalTime.of((time / 60), (time % 60)));
            pointageProfResDto.setGetTime(LocalTime.of((time / 60), (time % 60)));
            pointageProfResDtos.add(pointageProfResDto);
        });

        log.info(String.valueOf(pointageProfResDtos));

        return pointageProfResDtos;
    }
}
