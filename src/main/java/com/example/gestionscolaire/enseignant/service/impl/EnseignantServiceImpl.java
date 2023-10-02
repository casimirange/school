package com.example.gestionscolaire.enseignant.service.impl;

import com.example.gestionscolaire.Document.entity.DocumentStorageProperties;
import com.example.gestionscolaire.Document.entity.ETypeDocument;
import com.example.gestionscolaire.Document.entity.TypeDocument;
import com.example.gestionscolaire.Document.repository.IDocumentStoragePropertiesRepo;
import com.example.gestionscolaire.Document.repository.ITypeDocumentRepo;
import com.example.gestionscolaire.Document.service.IDocumentStorageService;
import com.example.gestionscolaire.QrCode.QRCodeGenerator;
import com.example.gestionscolaire.enseignant.dto.EnseignantReqDto;
import com.example.gestionscolaire.enseignant.dto.EnseignantResDto;
import com.example.gestionscolaire.enseignant.model.Enseignants;
import com.example.gestionscolaire.enseignant.repository.IEnseignantRepo;
import com.example.gestionscolaire.enseignant.service.IEnseignantService;
import com.example.gestionscolaire.etudiant.model.Etudiants;
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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;
//import org.apache.commons.lang3.RandomStringUtils;

//import org.apache.poi.xssf.usermodel.XSSFWorkbook;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class EnseignantServiceImpl implements IEnseignantService {

    private final IEnseignantRepo iEnseignantRepo;
    private final IStatusRepo iStatusRepo;
    private final ITypeDocumentRepo iTypeDocumentRepo;
    private final IDocumentStorageService iDocumentStorageService;
    private final IDocumentStoragePropertiesRepo docStorageRepo;
    @Value("${app.api.base.url}")
    private String api_base_url;

    @Override
    public EnseignantResDto addProf(EnseignantReqDto enseignantReqDto) {
        Enseignants enseignants = mapToEnseignant(enseignantReqDto);
        enseignants.setMatricule(generateMatriculeProf());
        enseignants.setCreatedAt(LocalDateTime.now());
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
        List<DocumentStorageProperties> docList = new ArrayList<>();
        Workbook workbook = new XSSFWorkbook(inputStream);
        Sheet sheet = workbook.getSheetAt(0);
        Iterator<Row> rowIterator = sheet.iterator();
        // Sauter la première valeur (première itération)
        if (rowIterator.hasNext()) {
            rowIterator.next();
        }
        TypeDocument typeDocument = iTypeDocumentRepo.findByName(ETypeDocument.IMAGE).orElseThrow(() -> new ResourceNotFoundException("Statut :  " + ETypeDocument.IMAGE + "  not found"));

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
            if (iEnseignantRepo.findBySchoolMatricule(rowData.get(2)).isPresent()){
                Enseignants et = iEnseignantRepo.findBySchoolMatricule(rowData.get(10)).get();
                et.setFirstName(rowData.get(0));
                et.setLastName(rowData.get(1));
                et.setUpdatedAt(LocalDateTime.now());
                enseignantsList.add(et);
            }else {


            enseignantReqDto.setFirstName(rowData.get(0));
            enseignantReqDto.setLastName(rowData.get(1));
//            enseignantReqDto.setMatricule(generateMatriculeProf());
            log.info("voici l'enseignant " + enseignantReqDto);
            Enseignants enseignants = mapToEnseignant(enseignantReqDto);
            enseignants.setMatricule(generateMatriculeProf());
            enseignants.setSchoolMatricule(rowData.get(2));
            enseignants.setPhotoLink(api_base_url+"api/enseignant/file/"+rowData.get(2)+"/downloadFile?type=image&docType=jpeg");
            enseignants.setCreatedAt(LocalDateTime.now());
            enseignantsList.add(enseignants);
            }
            rows.add(rowData);
            DocumentStorageProperties doc = docStorageRepo.checkDocumentByOrderId(rowData.get(2), "png", typeDocument.getId());
            log.info("ce doc {}", doc);
            if(doc != null) {
                doc.setDocumentFormat("image/jpeg");
                doc.setFileName(rowData.get(2) + ".jpeg");
                doc.setType(typeDocument);
                log.info("ça existe déjà");
                docList.add(doc);
            } else {
                DocumentStorageProperties newdoc = new DocumentStorageProperties();
                newdoc.setMatricule(rowData.get(2));
                newdoc.setDocumentFormat("image/jpeg");
                newdoc.setType(typeDocument);
                newdoc.setDocumentType("png");
                newdoc.setFileName(rowData.get(2) + ".jpeg");
                docList.add(newdoc);
            }
//            iEnseignantRepo.save(enseignants);
        }
        iEnseignantRepo.saveAll(enseignantsList);
        docStorageRepo.saveAll(docList);
//        } catch (IOException e){
//
//        }
        return rows;
    }

    @Override
    public Page<EnseignantResDto> getProfs(int page, int size, String sort, String order) {
        Page<Enseignants> enseignantList = iEnseignantRepo.findAll(PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(order), sort)));
        return new PageImpl<>(enseignantList.stream().map(this::mapToEnseignantResponse).collect(Collectors.toList()), PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(order), sort)), iEnseignantRepo.findAll().size());
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
        String infoEtudiant = ""+enseignants.getMatricule();
        byte[] image = new byte[0];
        try {
            // Generate and Return Qr Code in Byte Array
            image = QRCodeGenerator.getQRCodeImage(infoEtudiant,300,100);
        } catch (WriterException | IOException e) {
            e.printStackTrace();
        }
        return image;
    }

    @Override
    public EnseignantResDto updateEnseignant(EnseignantReqDto enseignantReqDto, String matricule) {
        Enseignants findEnseignant = iEnseignantRepo.findByMatricule(matricule).get();
        Enseignants enseignants = mapToEnseignant(enseignantReqDto);
        findEnseignant.setFirstName(enseignants.getFirstName());
        findEnseignant.setLastName(enseignants.getLastName());
        findEnseignant.setUpdatedAt(LocalDateTime.now());
        return mapToEnseignantResponse(iEnseignantRepo.save(findEnseignant));
    }

    @Override
    public EnseignantResDto disableEtudiant(String matricule) {
        Enseignants findEnseignant = iEnseignantRepo.findByMatricule(matricule).get();
        findEnseignant.setStatus(findEnseignant.getStatus().getName().equals(EStatus.ACTIF) ? iStatusRepo.getStatutByName(EStatus.INACTIF).get() : iStatusRepo.getStatutByName(EStatus.ACTIF).get());
        findEnseignant.setUpdatedAt(LocalDateTime.now());
        return mapToEnseignantResponse(iEnseignantRepo.save(findEnseignant));
    }

    private EnseignantResDto mapToEnseignantResponse(Enseignants enseignant) {
        return EnseignantResDto.builder()
                .id(enseignant.getId())
                .firstName(enseignant.getFirstName())
                .lastName(enseignant.getLastName())
                .matricule(enseignant.getMatricule())
                .schoolMatricule(enseignant.getSchoolMatricule())
                .status(enseignant.getStatus())
                .photoLink(enseignant.getPhotoLink())
                .createdAt(enseignant.getCreatedAt())
                .updatedAt(enseignant.getUpdatedAt())
                .build();
    }

    private Enseignants mapToEnseignant(EnseignantReqDto enseignant) {
        return Enseignants.builder()
                .firstName(enseignant.getFirstName())
                .lastName(enseignant.getLastName())
                .schoolMatricule(enseignant.getSchoolMatricule())
                .status(iStatusRepo.getStatutByName(EStatus.ACTIF).get())
                .build();
    }

    public String generateMatriculeProf() {
//        String internalReference =  "ET" +Long.parseLong((1000 + new Random().nextInt(9000)) + RandomStringUtils.random(5, 40, 150, false, true, null, new SecureRandom()));
        Calendar date = Calendar.getInstance();
        String matricule = date.get(Calendar.YEAR) + "ESG" + (1000 + new Random().nextInt(9000));
//        String matricule = "ESG" + (1000 + new Random().nextInt(9000));
        return matricule;
    }
}
