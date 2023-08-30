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

    private static final String QR_CODE_IMAGE_PATH = "./src/main/resources/static/img";
//    public static void generateQRCodeImage(String text, int width, int height, String filePath)
//            throws WriterException, IOException {
//        QRCodeWriter qrCodeWriter = new QRCodeWriter();
//        BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, width, height);
//
//        Path path = FileSystems.getDefault().getPath(filePath);
//        MatrixToImageWriter.writeToPath(bitMatrix, "PNG", path);
//
//    }
    @Override
    public EtudiantResDto addEtudiant(EtudiantReqDto etudiantReqDto) throws WriterException, IOException {
        Etudiants etudiants = mapToEtudiant(etudiantReqDto);
        etudiants.setMatricule(generateMatriculeEtudiant());
//        String infoEtudiant = "Nom: "+etudiants.getLastName()+ " \nPrenom: "+etudiants.getFirstName() +"\nMatricule: "+etudiants.getMatricule();
//        generateQRCodeImage(infoEtudiant, 250, 250, QR_CODE_IMAGE_PATH+"/QRCode-"+etudiants.getMatricule()+".png");
//        enseignants.setStatus(iStatusRepo.findByEStatus(EStatus.ACTIF).get());
        return mapToEtudiantResponse(iEtudiantRepo.save(etudiants));
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
        String infoEtudiant = "Nom: "+etudiants.getLastName()+ " \nPrenom: "+etudiants.getFirstName() +"\nMatricule: "+etudiants.getMatricule();
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
            etudiantReqDto.setTotalPension(rowData.get(3));
            etudiantReqDto.setMontantPay(rowData.get(4));
//            etudiantReqDto.setMatricule(generateMatriculeEtudiant());
            rows.add(rowData);
            log.info("voici l'etudiant " + etudiantReqDto);
            Etudiants etudiants = mapToEtudiant(etudiantReqDto);
            etudiants.setMatricule(generateMatriculeEtudiant());
            etudiantsList.add(etudiants);
//            String infoEtudiant = "Nom: "+etudiants.getLastName()+ " \nPrenom: "+etudiants.getFirstName() +"\nMatricule: "+etudiants.getMatricule();
//            QRCodeGenerator.generateQRCodeImage(infoEtudiant, 250, 250, QR_CODE_IMAGE_PATH+"/QRCode-"+etudiants.getMatricule()+".png");
        }
        iEtudiantRepo.saveAll(etudiantsList);
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
//                .matricule(etudiantReqDto.getMatricule())
                .classe(etudiantReqDto.getClasse())
                .totalPension(etudiantReqDto.getTotalPension())
                .montantPay(etudiantReqDto.getMontantPay())
                .status(iStatusRepo.getStatutByName(EStatus.ACTIF).get())
                .build();
    }

    public String generateMatriculeEtudiant() {
//        String internalReference =  "ET" +Long.parseLong((1000 + new Random().nextInt(9000)) + RandomStringUtils.random(5, 40, 150, false, true, null, new SecureRandom()));
        String matricule = "ET" + (1000 + new Random().nextInt(9000));
        return matricule;
    }

//    private static void generateQRCodeImage(String text, int width, int height, String filePath)
//            throws WriterException, IOException {
//        QRCodeWriter qrCodeWriter = new QRCodeWriter();
//        BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, width, height);
//
//        Path path = FileSystems.getDefault().getPath(filePath);
//        MatrixToImageWriter.writeToPath(bitMatrix, "PNG", path);
//
//    }
//
//    private static byte[] getQRCodeImage(String text, int width, int height) throws WriterException, IOException {
//        QRCodeWriter qrCodeWriter = new QRCodeWriter();
//        BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, width, height);
//
//        ByteArrayOutputStream pngOutputStream = new ByteArrayOutputStream();
//        MatrixToImageConfig con = new MatrixToImageConfig( 0xFF000002 , 0xFFFFC041 ) ;
//
//        MatrixToImageWriter.writeToStream(bitMatrix, "PNG", pngOutputStream,con);
//        byte[] pngData = pngOutputStream.toByteArray();
//        return pngData;
//    }
}
