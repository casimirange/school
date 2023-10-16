package com.example.gestionscolaire.etudiant.service.impl;

import com.example.gestionscolaire.Document.entity.DocumentStorageProperties;
import com.example.gestionscolaire.Document.entity.ETypeDocument;
import com.example.gestionscolaire.Document.entity.TypeDocument;
import com.example.gestionscolaire.Document.repository.IDocumentStoragePropertiesRepo;
import com.example.gestionscolaire.Document.repository.ITypeDocumentRepo;
import com.example.gestionscolaire.QrCode.QRCodeGenerator;
import com.example.gestionscolaire.configuration.scolarite.model.Control;
import com.example.gestionscolaire.configuration.scolarite.service.IConrolService;
import com.example.gestionscolaire.etudiant.dto.EtudiantReqDto;
import com.example.gestionscolaire.etudiant.dto.EtudiantResDto;
import com.example.gestionscolaire.etudiant.model.Etudiants;
import com.example.gestionscolaire.etudiant.repository.IEtudiantRepo;
import com.example.gestionscolaire.etudiant.service.IEtudiantService;
import com.example.gestionscolaire.statut.model.EStatus;
import com.example.gestionscolaire.statut.model.Statut;
import com.example.gestionscolaire.statut.repository.IStatusRepo;
import com.google.zxing.WriterException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFFormulaEvaluator;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.criteria.Predicate;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class EtudiantServiceImpl implements IEtudiantService {

    private final IEtudiantRepo iEtudiantRepo;
    private final IStatusRepo iStatusRepo;
    private final IDocumentStoragePropertiesRepo docStorageRepo;
    private final ITypeDocumentRepo iTypeDocumentRepo;
    private final IConrolService iConrolService;
    @Value("${app.api.base.url}")
    private String api_base_url;

    @Override
    public EtudiantResDto addEtudiant(EtudiantReqDto etudiantReqDto) {
        Etudiants etudiants = mapToEtudiant(etudiantReqDto);
        etudiants.setMatricule(generateMatriculeEtudiant());
        etudiants.setCreatedAt(LocalDateTime.now());
        log.info("voici {}", etudiants);
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
        findEtudiants.setSchoolMatricule(etudiants.getSchoolMatricule());
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
    public Page<EtudiantResDto> getEtudiants(String firstName, String lastName, String matricule, String classe, String statut, String sexe, int page, int size, String sort, String order) {
        Specification<Etudiants> specification = ((root, query, criteriaBuilder) -> {

            List<Predicate> predicates = new ArrayList<>();

            if (firstName != null && !firstName.isEmpty()){
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("firstName")),  '%'+firstName+'%'));
            }

            if (lastName != null && !lastName.isEmpty()){
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("lastName")),  '%'+lastName+'%'));
            }

            if (matricule != null && !matricule.isEmpty()){
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("schoolMatricule")),  '%'+matricule+'%'));
            }

            if (classe != null && !classe.isEmpty()){
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("classe")),  '%'+classe+'%'));
            }

            if (sexe != null && !sexe.isEmpty()){
                predicates.add(criteriaBuilder.equal(criteriaBuilder.lower(root.get("sex")),  sexe));
            }

            if (statut != null && !statut.isEmpty()){
                Optional<Statut> typeTransaction2 = iStatusRepo.getStatutByName(EStatus.valueOf(statut.toUpperCase()));
                predicates.add(criteriaBuilder.equal(criteriaBuilder.lower(root.get("status")), typeTransaction2.map(Statut::getId).orElse(null)));
            }

            return criteriaBuilder.and(predicates.toArray(new javax.persistence.criteria.Predicate[0]));
        });
        Page<Etudiants> products = iEtudiantRepo.findAll(specification, PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(order), sort)));
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
        String infoEtudiant = "" + etudiants.getMatricule();
        byte[] image = new byte[0];
        try {
            // Generate and Return Qr Code in Byte Array
            image = QRCodeGenerator.getQRCodeImage(infoEtudiant, 250, 250);
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
//        DocumentStorageProperties newDoc = new DocumentStorageProperties();

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
//        newDoc.setMatricule("matricule");
//        newDoc.setDocumentFormat("image/jpeg");
//        newDoc.setType(typeDocument);
//        newDoc.setDocumentType("png");
//        newDoc.setFileName("matricule" + ".jpeg");
//        docList.add(newDoc);
//        doc.setMatricule("matriculesssss");
//        doc.setDocumentFormat("image/jpeg");
//        doc.setType(typeDocument);
//        doc.setDocumentType("png");
//        doc.setFileName("matriculesssss" + ".jpeg");
//        docList.add(doc);
//        log.info("type {}", typeDocument.getName());
        while (rowIterator.hasNext()) {
            Row row = rowIterator.next();
            List<String> rowData = new ArrayList<>();
            Iterator<Cell> cellIterator = row.cellIterator();
            while (cellIterator.hasNext()) {
                Cell cell = cellIterator.next();
                rowData.add(cell.toString());
            }
//            String matricule = rowData.get(10);


//            log.info("sex {}", etudiantReqDto.getSex());
            int day = 0,year = 0 ;
            String[] caracteres = rowData.get(4).split("-");
            log.info("date {}", rowData.get(4));
            year =  Integer.parseInt(caracteres[2]);
            log.info("year {}", year);
            day = Integer.parseInt(caracteres[0]);
            log.info("day {}", day);
            String mois = caracteres[1];
            switch (mois) {
                case "janv.":
                    mois = "JANUARY";
                    break;
                case "févr.":
                    mois = "FEBRUARY";
                    break;
                case "mars":
                    mois = "MARCH";
                    break;
                case "avr.":
                    mois = "APRIL";
                    break;
                case "mai":
                    mois = "MAY";
                    break;
                case "juin":
                    mois = "JUNE";
                    break;
                case "juil.":
                    mois = "JULY";
                    break;
                case "août":
                    mois = "AUGUST";
                    break;
                case "sept.":
                    mois = "SEPTEMBER";
                    break;
                case "oct.":
                    mois = "OCTOBER";
                    break;
                case "nov.":
                    mois = "NOVEMBER";
                    break;
                case "déc.":
                    mois = "DECEMBER";
                    break;
            }
            log.info("month1 {}", caracteres[1]);
            log.info("month2 {}", LocalDate.of(year, Month.valueOf(mois), day));
            if (iEtudiantRepo.findBySchoolMatricule(rowData.get(10)).isPresent()){
                Etudiants et = iEtudiantRepo.findBySchoolMatricule(rowData.get(10)).get();
                et.setClasse(rowData.get(2));
                et.setTotalPension(rowData.get(8));
                et.setMontantPay(rowData.get(9));
                et.setUpdatedAt(LocalDateTime.now());
                etudiantsList.add(et);
            }else {
                etudiantReqDto.setFirstName(rowData.get(0));
                etudiantReqDto.setLastName(rowData.get(1));
                etudiantReqDto.setClasse(rowData.get(2));
                etudiantReqDto.setSex(rowData.get(3));
                etudiantReqDto.setDateOfBirth(LocalDate.of(year, Month.valueOf(mois), day));
    //            log.info("date {}", etudiantReqDto.getDateOfBirth());
                etudiantReqDto.setPlaceOfBirth(rowData.get(5));
                etudiantReqDto.setFatherName(!rowData.get(6).isEmpty() ? rowData.get(6) : "N/A");
                etudiantReqDto.setMotherName(!rowData.get(7).isEmpty() ? rowData.get(7) : "N/A");
                etudiantReqDto.setTotalPension(rowData.get(8));
                etudiantReqDto.setMontantPay(rowData.get(9));
                etudiantReqDto.setSchoolMatricule(rowData.get(10));
                Etudiants etudiants = mapToEtudiant(etudiantReqDto);
                etudiants.setMatricule(generateMatriculeEtudiant());
                etudiants.setCreatedAt(LocalDateTime.now());
                etudiants.setPhotoLink(api_base_url+"api/etudiant/file/"+rowData.get(10)+"/downloadFile?type=image&docType=jpeg");
                etudiantsList.add(etudiants);
            }
            rows.add(rowData);
            DocumentStorageProperties doc = docStorageRepo.checkDocumentByOrderId(rowData.get(10), "png", typeDocument.getId());
            log.info("ce doc {}", doc);
            if(doc != null) {
                doc.setDocumentFormat("image/jpeg");
                doc.setFileName(rowData.get(10) + ".jpeg");
                doc.setType(typeDocument);
                log.info("ça existe déjà");
                docList.add(doc);
            } else {
            DocumentStorageProperties newdoc = new DocumentStorageProperties();
            newdoc.setMatricule(rowData.get(10));
            newdoc.setDocumentFormat("image/jpeg");
            newdoc.setType(typeDocument);
            newdoc.setDocumentType("png");
            newdoc.setFileName(rowData.get(10) + ".jpeg");
            docList.add(newdoc);

            }
//            log.info("doc {}", newDoc);


            log.info("liste2 {}", etudiantsList);
        }

//        log.info("lisdoc {}", docList);
        //        log.info("liste3 {}", etudiantsList);
        iEtudiantRepo.saveAll(etudiantsList);
        docStorageRepo.saveAll(docList);
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
                .schoolMatricule(etudiants.getSchoolMatricule())
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
                .schoolMatricule(etudiantReqDto.getSchoolMatricule())
                .dateOfBirth(etudiantReqDto.getDateOfBirth())
                .sex(etudiantReqDto.getSex())
                .fatherName(etudiantReqDto.getFatherName())
                .motherName(etudiantReqDto.getMotherName())
                .build();
    }

    public String generateMatriculeEtudiant() {
//        String internalReference =  "ET" +Long.parseLong((1000 + new Random().nextInt(9000)) + RandomStringUtils.random(5, 40, 150, false, true, null, new SecureRandom()));
        Calendar date = Calendar.getInstance();
        String year = date.get(Calendar.YEAR)+"";
//        String matricule = year.substring(2,4) + "ET" + (1000 + new Random().nextInt(9000));
        String matricule = year.substring(2, 4) + "ET" + (1000 + new Random().nextInt(9000)) + RandomStringUtils.random(6, 40, 150, true, true);

//        String matricule = date.get(Calendar.YEAR) + "ET" + (1000 + new Random().nextInt(9000));
        return matricule;
    }

}
