package com.example.gestionscolaire.etudiant.service.impl;

import com.example.gestionscolaire.enseignant.dto.EnseignantReqDto;
import com.example.gestionscolaire.enseignant.model.Enseignants;
import com.example.gestionscolaire.etudiant.dto.EtudiantReqDto;
import com.example.gestionscolaire.etudiant.dto.EtudiantResDto;
import com.example.gestionscolaire.etudiant.model.Etudiants;
import com.example.gestionscolaire.etudiant.repository.IEtudiantRepo;
import com.example.gestionscolaire.etudiant.service.IEtudiantService;
import com.example.gestionscolaire.statut.model.EStatus;
import com.example.gestionscolaire.statut.repository.IStatusRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class EtudiantServiceImpl implements IEtudiantService {

    private final IEtudiantRepo iEtudiantRepo;
    private final IStatusRepo iStatusRepo;

    @Override
    public EtudiantResDto addEtudiant(EtudiantReqDto etudiantReqDto) {
        Etudiants etudiants = mapToEtudiant(etudiantReqDto);
//        enseignants.setStatus(iStatusRepo.findByEStatus(EStatus.ACTIF).get());
        return mapToEtudiantResponse(iEtudiantRepo.save(etudiants));
    }

    @Override
    public List<EtudiantResDto> getEtudiants() {
        List<Etudiants> products = iEtudiantRepo.findAll();
        return products.stream().map(this::mapToEtudiantResponse).collect(Collectors.toList());
    }

    @Override
    public EtudiantResDto getEtudiantbyId(Long id) {
        Etudiants etudiants = iEtudiantRepo.findById(id).get();
        return mapToEtudiantResponse(etudiants);
    }

    @Override
    @Async
    @Transactional
    public List<List<String>> importListEtudiant(MultipartFile file) throws IOException {
        List<List<String>> rows = new ArrayList<>();
        InputStream inputStream = file.getInputStream();
        EtudiantReqDto etudiantReqDto = new EtudiantReqDto();
        Workbook workbook = new XSSFWorkbook(inputStream);
        Sheet sheet = workbook.getSheetAt(0);
        Iterator<Row> rowIterator = sheet.iterator();
        // Sauter la première valeur (première itération)
        if (rowIterator.hasNext()) {
            rowIterator.next();
        }

        // Parcourir le reste des lignes
        while (rowIterator.hasNext()) {
            Row row = rowIterator.next();
            List<String> rowData = new ArrayList<>();

            Iterator<Cell> cellIterator = row.cellIterator();
            while (cellIterator.hasNext()) {
                Cell cell = cellIterator.next();
                rowData.add(cell.toString());
            }

            etudiantReqDto.setFirstName(rowData.get(0));
            etudiantReqDto.setLastName(rowData.get(1));
            etudiantReqDto.setClasse(rowData.get(2));
            etudiantReqDto.setTotalPension(rowData.get(3));
            etudiantReqDto.setMontantPay(rowData.get(4));
            etudiantReqDto.setMatricule(generateInternalReference());
            rows.add(rowData);
            log.info("voici l'etudiant " + etudiantReqDto);
            Etudiants etudiants = mapToEtudiant(etudiantReqDto);
            iEtudiantRepo.save(etudiants);
        }

        return rows;
    }

    private EtudiantResDto mapToEtudiantResponse(Etudiants etudiants) {
        return EtudiantResDto.builder()
                .id(etudiants.getId())
                .firstName(etudiants.getFirstName())
                .lastName(etudiants.getLastName())
                .matricule(etudiants.getMatricule())
                .classe(etudiants.getClasse())
                .totalPension(etudiants.getTotalPension())
                .montantPay(etudiants.getMontantPay())
                .status(etudiants.getStatus())
                .build();
    }

    private Etudiants mapToEtudiant(EtudiantReqDto etudiantReqDto) {
        return Etudiants.builder()
                .firstName(etudiantReqDto.getFirstName())
                .lastName(etudiantReqDto.getLastName())
                .matricule(etudiantReqDto.getMatricule())
                .classe(etudiantReqDto.getClasse())
                .totalPension(etudiantReqDto.getTotalPension())
                .montantPay(etudiantReqDto.getMontantPay())
                .status(iStatusRepo.getStatutByName(EStatus.ACTIF).get())
                .build();
    }

    public String generateInternalReference() {
//        String internalReference =  "ET" +Long.parseLong((1000 + new Random().nextInt(9000)) + RandomStringUtils.random(5, 40, 150, false, true, null, new SecureRandom()));
        String internalReference = "ET" + (1000 + new Random().nextInt(9000));
        return internalReference;
    }
}
