package com.example.gestionscolaire.enseignant.service.impl;

import com.example.gestionscolaire.QrCode.QRCodeGenerator;
import com.example.gestionscolaire.enseignant.dto.EnseignantReqDto;
import com.example.gestionscolaire.enseignant.dto.EnseignantResDto;
import com.example.gestionscolaire.enseignant.model.Enseignants;
import com.example.gestionscolaire.enseignant.repository.IEnseignantRepo;
import com.example.gestionscolaire.enseignant.service.IEnseignantService;
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
import org.springframework.beans.factory.annotation.Autowired;
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
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
//import org.apache.commons.lang3.RandomStringUtils;

//import org.apache.poi.xssf.usermodel.XSSFWorkbook;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class EnseignantServiceImpl implements IEnseignantService {

    @Autowired
    IEnseignantRepo iEnseignantRepo;
    @Autowired
    IStatusRepo iStatusRepo;

    @Override
    public EnseignantResDto addProf(EnseignantReqDto enseignantReqDto) {
        Enseignants enseignants = mapToEnseignant(enseignantReqDto);
        enseignants.setMatricule(generateMatriculeProf());
//        enseignants.setStatus(iStatusRepo.findByEStatus(EStatus.ACTIF).get());
        return mapToEnseignantResponse(iEnseignantRepo.save(enseignants));
    }

    @Override
    @Async
    @Transactional
    public List<List<String>> importListProf(MultipartFile file) throws IOException {
        List<List<String>> rows = new ArrayList<>();
        InputStream inputStream = file.getInputStream();
        EnseignantReqDto enseignantReqDto = new EnseignantReqDto();
        List<Enseignants> enseignantsList = new ArrayList<>();
//        try (InputStream inputStream = file.getInputStream()) {
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
//                log.info("voici le contenu de la cellule " + cell.toString());
                rowData.add(cell.toString());

            }
//            log.info("voici le contenu de la cellule2 " + rowData.get(0));
            enseignantReqDto.setFirstName(rowData.get(0));
            enseignantReqDto.setLastName(rowData.get(1));
//            enseignantReqDto.setMatricule(generateMatriculeProf());
            rows.add(rowData);
            log.info("voici l'enseignant " + enseignantReqDto);
            Enseignants enseignants = mapToEnseignant(enseignantReqDto);
            enseignants.setMatricule(generateMatriculeProf());
            enseignantsList.add(enseignants);
//            iEnseignantRepo.save(enseignants);
        }
        iEnseignantRepo.saveAll(enseignantsList);
//        } catch (IOException e){
//
//        }
        return rows;
    }

    @Override
    public Page<EnseignantResDto> getProfs(int page, int size, String sort, String order) {
        Page<Enseignants> products = iEnseignantRepo.findAll(PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(order), sort)));

        EnseignantResDto enseignantResDto;
        List<EnseignantResDto> enseignantResDtos = new ArrayList<>();

        return new PageImpl<>(products.stream().map(this::mapToEnseignantResponse).collect(Collectors.toList()), PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(order), sort)), iEnseignantRepo.findAll().size());
    }

    @Override
    public EnseignantResDto getProfbyId(Long id) {
        Enseignants enseignants = iEnseignantRepo.findById(id).get();
        return mapToEnseignantResponse(enseignants);
    }

    @Override
    public EnseignantResDto getProfbyMatricule(String matricule) {
        Enseignants enseignants = iEnseignantRepo.findByMatricule(matricule).get();
        return mapToEnseignantResponse(enseignants);
    }

    @Override
    public byte[] getQrCodeByProf(String matricule) {
        Enseignants enseignants = iEnseignantRepo.findByMatricule(matricule).get();
        String infoEtudiant = "Nom: "+enseignants.getLastName()+ " \nPrenom: "+enseignants.getFirstName() +"\nMatricule: "+enseignants.getMatricule();
        byte[] image = new byte[0];
        try {
            // Generate and Return Qr Code in Byte Array
            image = QRCodeGenerator.getQRCodeImage(infoEtudiant,250,250);
        } catch (WriterException | IOException e) {
            e.printStackTrace();
        }
        return image;
    }

    private EnseignantResDto mapToEnseignantResponse(Enseignants enseignant) {
        return EnseignantResDto.builder()
                .id(enseignant.getId())
                .firstName(enseignant.getFirstName())
                .lastName(enseignant.getLastName())
                .matricule(enseignant.getMatricule())
                .status(enseignant.getStatus())
                .build();
    }

    private Enseignants mapToEnseignant(EnseignantReqDto enseignant) {
        return Enseignants.builder()
                .firstName(enseignant.getFirstName())
                .lastName(enseignant.getLastName())
//                .matricule(enseignant.getMatricule())
                .status(iStatusRepo.getStatutByName(EStatus.ACTIF).get())
                .build();
    }

    public String generateMatriculeProf() {
//        String internalReference =  "ET" +Long.parseLong((1000 + new Random().nextInt(9000)) + RandomStringUtils.random(5, 40, 150, false, true, null, new SecureRandom()));
        String matricule = "ESG" + (1000 + new Random().nextInt(9000));
        return matricule;
    }
}
