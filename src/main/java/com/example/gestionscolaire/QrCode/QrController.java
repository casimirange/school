package com.example.gestionscolaire.QrCode;

import com.google.zxing.WriterException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Base64;

//@RestController
//@Slf4j
public class QrController {

//    private static final String QR_CODE_IMAGE_PATH = "./src/main/resources/static/img/QRCode.png";
//
//    @GetMapping("/qrcode")
//    public ResponseEntity<?> getQRCode(Model model){
//        String medium="https://rahul26021999.medium.com/";
//        String github="https://github.com/rahul26021999";
//        String nom = "ouandji noumome";
//        String prenom = "casimir ange";
//        LocalDate date = LocalDate.of(1994, 03, 04);
//        String matricule = "ISTDI14E007490";
//
//        byte[] image = new byte[0];
//        try {
//
//            // Generate and Return Qr Code in Byte Array
//            image = QRCodeGenerator.getQRCodeImage(medium,250,250);
//
//            // Generate and Save Qr Code Image in static/image folder
//            QRCodeGenerator.generateQRCodeImage(github,250,250,QR_CODE_IMAGE_PATH);
//
//        } catch (WriterException | IOException e) {
//            e.printStackTrace();
//        }
//        // Convert Byte Array into Base64 Encode String
//        String qrcode = Base64.getEncoder().encodeToString(image);
//
//
//        model.addAttribute("medium",medium);
//        model.addAttribute("github",github);
//        model.addAttribute("qrcode",qrcode);
//        log.info("model: {}", model);
//
//        HttpHeaders headers = new HttpHeaders();
//        headers.set(HttpHeaders.CONTENT_DISPOSITION, "inline;filename=qrcode.pdf");
//        return ResponseEntity.ok().headers(headers).contentType(MediaType.IMAGE_PNG).body(image);
//    }
}
