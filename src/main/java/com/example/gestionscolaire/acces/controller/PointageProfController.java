package com.example.gestionscolaire.acces.controller;

import com.example.gestionscolaire.acces.dto.MessageResponseDto;
import com.example.gestionscolaire.acces.dto.PointageProfReqDto;
import com.example.gestionscolaire.acces.dto.PointageProfResDto;
import com.example.gestionscolaire.acces.model.PointageProfesseur;
import com.example.gestionscolaire.acces.service.IPointageProfService;
import com.example.gestionscolaire.configuration.globalCoonfig.globalConfiguration.ApplicationConstant;
import com.example.gestionscolaire.enseignant.dto.EnseignantResDto;
import com.example.gestionscolaire.enseignant.service.IEnseignantService;
import com.example.gestionscolaire.etudiant.dto.EtudiantReqDto;
import com.example.gestionscolaire.etudiant.dto.EtudiantResDto;
import com.example.gestionscolaire.etudiant.service.IEtudiantService;
import com.example.gestionscolaire.statut.model.EStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/pointageProf")
@CrossOrigin("*")
public class PointageProfController {
    private final IPointageProfService iPointageProfService;
    private final IEnseignantService iEnseignantService;
    private final ResourceBundleMessageSource messageSource;

    @PutMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> savePointageProf(@RequestBody PointageProfReqDto pointageProfReqDto) {
        EnseignantResDto etudiants = iEnseignantService.getProfbyMatricule(pointageProfReqDto.getMatricule());
        if (etudiants.getStatus().getName().equals(EStatus.INACTIF)){
            return ResponseEntity.badRequest().body(new MessageResponseDto(HttpStatus.BAD_REQUEST,
                    messageSource.getMessage("messages.prof_inactif", null, LocaleContextHolder.getLocale())));
        }
        return ResponseEntity.ok().body(iPointageProfService.enregistrerPointage(pointageProfReqDto));
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> getAllPointageProfs(@RequestParam(required = false, value = "page", defaultValue = "0") String pageParam,
                                                        @RequestParam(required = false, value = "size", defaultValue = ApplicationConstant.DEFAULT_SIZE_PAGINATION) String sizeParam,
                                                        @RequestParam(required = false, defaultValue = "id") String sort,
                                                        @RequestParam(required = false, value = "schoolMatricule") String matricule,
                                                        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @RequestParam(required = false, value = "date" ) LocalDate date,
                                                        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @RequestParam(required = false, value = "dateRange" ) LocalDate dateRange,
                                                        @RequestParam(required = false, defaultValue = "desc") String order) {
        return ResponseEntity.ok().body(iPointageProfService.pointagesProfs(matricule, date, dateRange,
                Integer.parseInt(pageParam), Integer.parseInt(sizeParam), sort, order));
    }
}
