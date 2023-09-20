package com.example.gestionscolaire.etudiant.service.impl;

import com.example.gestionscolaire.QrCode.QRCodeGenerator;
import com.example.gestionscolaire.etudiant.dto.EtudiantReqDto;
import com.example.gestionscolaire.etudiant.dto.EtudiantResDto;
import com.example.gestionscolaire.etudiant.model.Etudiants;
import com.example.gestionscolaire.etudiant.repository.IEtudiantRepo;
import com.example.gestionscolaire.etudiant.service.IEtudiantService;
import com.example.gestionscolaire.statut.model.EStatus;
import com.example.gestionscolaire.statut.repository.IStatusRepo;
import com.google.zxing.WriterException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.LocalDateTime;
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
        etudiants.setMatricule(generateMatriculeEtudiant());
        etudiants.setCreatedAt(LocalDateTime.now());
        log.info("voivi {}", etudiants);
        return mapToEtudiantResponse(iEtudiantRepo.save(etudiants));
    }

    @Override
    public EtudiantResDto updateEtudiant(EtudiantReqDto etudiantReqDto, String matricule) {
        Etudiants findEtudiants = iEtudiantRepo.findByMatricule(matricule).get();
        Etudiants etudiants = mapToEtudiant(etudiantReqDto);
        findEtudiants.setClasse(etudiants.getClasse());
        findEtudiants.setSex(etudiants.getSex());
        findEtudiants.setDateOfBirth(etudiants.getDateOfBirth());
        findEtudiants.setPlaceOfBirth(etudiants.getPlaceOfBirth());
        findEtudiants.setFatherName(etudiants.getFatherName());
        findEtudiants.setFirstName(etudiants.getFirstName());
        findEtudiants.setMotherName(etudiants.getMotherName());
        findEtudiants.setMontantPay(etudiants.getMontantPay());
        findEtudiants.setLastName(etudiants.getLastName());
        findEtudiants.setTotalPension(etudiants.getTotalPension());
        findEtudiants.setUpdatedAt(LocalDateTime.now());
        return mapToEtudiantResponse(iEtudiantRepo.save(findEtudiants));
    }

    @Override
    public EtudiantResDto disableEtudiant(String matricule) {
        Etudiants findEtudiants = iEtudiantRepo.findByMatricule(matricule).get();
        findEtudiants.setStatus(findEtudiants.getStatus().getName().equals(EStatus.ACTIF) ? iStatusRepo.getStatutByName(EStatus.INACTIF).get() : iStatusRepo.getStatutByName(EStatus.ACTIF).get());
        findEtudiants.setUpdatedAt(LocalDateTime.now());
        return mapToEtudiantResponse(iEtudiantRepo.save(findEtudiants));
    }

    @Override
    public Page<EtudiantResDto> getEtudiants(int page, int size, String sort, String order) {
        Page<Etudiants> products = iEtudiantRepo.findAll(PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(order), sort)));
        return new PageImpl<>(products.stream().map(this::mapToEtudiantResponse).collect(Collectors.toList()), PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(order), sort)), iEtudiantRepo.findAll().size());
    }

    @Override
    public EtudiantResDto getEtudiantbyId(Long id) {
        Etudiants etudiants = iEtudiantRepo.findById(id).get();
        return mapToEtudiantResponse(etudiants);
    }

    @Override
    public EtudiantResDto getEtudiantbyMatricule(String matricule) {
        Etudiants etudiants = iEtudiantRepo.findByMatricule(matricule).get();
        return mapToEtudiantResponse(etudiants);
    }

    @Override
    public byte[] getQrCodeByEtudiant(String matricule) {
        Etudiants etudiants = iEtudiantRepo.findByMatricule(matricule).get();
        String infoEtudiant = ""+etudiants.getMatricule();
        byte[] image = new byte[0];
        try {
            // Generate and Return Qr Code in Byte Array
            image = QRCodeGenerator.getQRCodeImage(infoEtudiant,250,250);
        } catch (WriterException | IOException e) {
            e.printStackTrace();
        }
        return image;
    }

    @Override
    @Async
    @Transactional
    public List<List<String>> importListEtudiant(MultipartFile file) throws IOException, WriterException {
        List<List<String>> rows = new ArrayList<>();
        InputStream inputStream = file.getInputStream();
        EtudiantReqDto etudiantReqDto = new EtudiantReqDto();
        List<Etudiants> etudiantsList = new ArrayList<>();
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
            etudiantReqDto.setSex(rowData.get(3));
            etudiantReqDto.setDateOfBirth(rowData.get(4));
            etudiantReqDto.setPlaceOfBirth(rowData.get(5));
            etudiantReqDto.setFatherName(rowData.get(6));
            etudiantReqDto.setMotherName(rowData.get(7));
            etudiantReqDto.setTotalPension(rowData.get(8));
            etudiantReqDto.setMontantPay(rowData.get(9));
            rows.add(rowData);
            Etudiants etudiants = mapToEtudiant(etudiantReqDto);
            etudiants.setMatricule(generateMatriculeEtudiant());
            etudiants.setCreatedAt(LocalDateTime.now());
            log.info("liste {}", etudiants);
            etudiantsList.add(etudiants);
            log.info("liste2 {}", etudiantsList);
        }

        log.info("liste3 {}", etudiantsList);
        iEtudiantRepo.saveAll(etudiantsList);
        log.info("fin");
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
                .photoLink(etudiants.getPhotoLink())
                .placeOfBirth(etudiants.getPlaceOfBirth())
                .dateOfBirth(etudiants.getDateOfBirth())
                .sex(etudiants.getSex())
                .fatherName(etudiants.getFatherName())
                .motherName(etudiants.getMotherName())
                .createdAt(etudiants.getCreatedAt())
                .updatedAt(etudiants.getUpdatedAt())
                .build();
    }

    private Etudiants mapToEtudiant(EtudiantReqDto etudiantReqDto) {
        return Etudiants.builder()
                .firstName(etudiantReqDto.getFirstName())
                .lastName(etudiantReqDto.getLastName())
                .classe(etudiantReqDto.getClasse())
                .totalPension(etudiantReqDto.getTotalPension())
                .montantPay(etudiantReqDto.getMontantPay())
                .status(iStatusRepo.getStatutByName(EStatus.ACTIF).get())
                .placeOfBirth(etudiantReqDto.getPlaceOfBirth())
                .dateOfBirth(etudiantReqDto.getDateOfBirth())
                .sex(etudiantReqDto.getSex())
                .fatherName(etudiantReqDto.getFatherName())
                .motherName(etudiantReqDto.getMotherName())
                .build();
    }

    public String generateMatriculeEtudiant() {
//        String internalReference =  "ET" +Long.parseLong((1000 + new Random().nextInt(9000)) + RandomStringUtils.random(5, 40, 150, false, true, null, new SecureRandom()));
        String matricule = "ET" + (1000 + new Random().nextInt(9000));
        return matricule;
    }

}
