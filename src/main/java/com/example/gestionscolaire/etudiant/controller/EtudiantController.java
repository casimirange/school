package com.example.gestionscolaire.etudiant.controller;

import com.example.gestionscolaire.Document.entity.ETypeDocument;
import com.example.gestionscolaire.Document.entity.TypeDocument;
import com.example.gestionscolaire.Document.repository.ITypeDocumentRepo;
import com.example.gestionscolaire.Document.service.IDocumentStorageService;
import com.example.gestionscolaire.configuration.globalCoonfig.globalConfiguration.ApplicationConstant;
import com.example.gestionscolaire.etudiant.dto.EtudiantReqDto;
import com.example.gestionscolaire.etudiant.dto.EtudiantResDto;
import com.example.gestionscolaire.etudiant.model.Etudiants;
import com.example.gestionscolaire.etudiant.repository.IEtudiantRepo;
import com.example.gestionscolaire.etudiant.service.IEtudiantService;
import com.google.zxing.WriterException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/etudiant")
@RequiredArgsConstructor
@Tag( name = "Etudiant")
@Slf4j
public class EtudiantController {
    private final IEtudiantRepo iEtudiantRepo;
    private final IEtudiantService iEtudiantService;
    private final ITypeDocumentRepo iTypeDocumentRepo;
    private final IDocumentStorageService iDocumentStorageService;
    @Value("${app.api.base.url}")
    private String api_base_url;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "création des informations pour un etudiant", tags = "Etudiant", responses = {
            @ApiResponse(responseCode = "201", content = @Content(mediaType = "Application/Json", array = @ArraySchema(schema = @Schema(implementation = EtudiantReqDto.class)))),
            @ApiResponse(responseCode = "404", description = "Etudiant not found", content = @Content(mediaType = "Application/Json")),
            @ApiResponse(responseCode = "401", description = "Full authentication is required to access this resource", content = @Content(mediaType = "Application/Json")),
            @ApiResponse(responseCode = "403", description = "Forbidden : accès refusé", content = @Content(mediaType = "Application/Json")),
            @ApiResponse(responseCode = "500", description = "Internal Server Error ", content = @Content(mediaType = "Application/Json")),})
//    @PreAuthorize("hasAnyRole('SUPERADMIN','ADMIN','AGENT','USER')")
    public ResponseEntity<EtudiantResDto> createEtudiant(@RequestBody EtudiantReqDto etudiantReqDto) throws IOException, WriterException {
        EtudiantResDto enseignantResDto = iEtudiantService.addEtudiant(etudiantReqDto);
        return ResponseEntity.ok().body(enseignantResDto);
    }


    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "modification des informations pour un etudiant", tags = "Etudiant", responses = {
            @ApiResponse(responseCode = "201", content = @Content(mediaType = "Application/Json", array = @ArraySchema(schema = @Schema(implementation = EtudiantReqDto.class)))),
            @ApiResponse(responseCode = "404", description = "Etudiant not found", content = @Content(mediaType = "Application/Json")),
            @ApiResponse(responseCode = "401", description = "Full authentication is required to access this resource", content = @Content(mediaType = "Application/Json")),
            @ApiResponse(responseCode = "403", description = "Forbidden : accès refusé", content = @Content(mediaType = "Application/Json")),
            @ApiResponse(responseCode = "500", description = "Internal Server Error ", content = @Content(mediaType = "Application/Json")),})
//    @PreAuthorize("hasAnyRole('SUPERADMIN','ADMIN','AGENT','USER')")
    @PutMapping("/{matricule}")
    public ResponseEntity<EtudiantResDto> updateEtudiant(@RequestBody EtudiantReqDto etudiantReqDto, @PathVariable String matricule) {
        EtudiantResDto enseignantResDto = iEtudiantService.updateEtudiant(etudiantReqDto, matricule);
        return ResponseEntity.ok().body(enseignantResDto);
    }

    @PatchMapping("/statusChange/{matricule}")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "changer le statut d'un etudiant", tags = "Etudiant", responses = {
            @ApiResponse(responseCode = "201", content = @Content(mediaType = "Application/Json", array = @ArraySchema(schema = @Schema(implementation = EtudiantReqDto.class)))),
            @ApiResponse(responseCode = "404", description = "Etudiant not found", content = @Content(mediaType = "Application/Json")),
            @ApiResponse(responseCode = "401", description = "Full authentication is required to access this resource", content = @Content(mediaType = "Application/Json")),
            @ApiResponse(responseCode = "403", description = "Forbidden : accès refusé", content = @Content(mediaType = "Application/Json")),
            @ApiResponse(responseCode = "500", description = "Internal Server Error ", content = @Content(mediaType = "Application/Json")),})
//    @PreAuthorize("hasAnyRole('SUPERADMIN','ADMIN','AGENT','USER')")
    public ResponseEntity<EtudiantResDto> disableEtudiant(@PathVariable String matricule) throws IOException, WriterException {
        EtudiantResDto enseignantResDto = iEtudiantService.disableEtudiant(matricule);
        return ResponseEntity.ok().body(enseignantResDto);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "liste des etudiants", tags = "Etudiant", responses = {
            @ApiResponse(responseCode = "200", content = @Content(mediaType = "Application/Json", array = @ArraySchema(schema = @Schema(implementation = EtudiantResDto.class)))),
            @ApiResponse(responseCode = "404", description = "Enseignant not found", content = @Content(mediaType = "Application/Json")),
            @ApiResponse(responseCode = "403", description = "Forbidden : accès refusé", content = @Content(mediaType = "Application/Json")),
            @ApiResponse(responseCode = "401", description = "Full authentication is required to access this resource", content = @Content(mediaType = "Application/Json"))})
    public ResponseEntity<?> getAllEtudiants(@RequestParam(required = false, value = "page", defaultValue = "0") String pageParam,
                                             @RequestParam(required = false, value = "size", defaultValue = ApplicationConstant.DEFAULT_SIZE_PAGINATION) String sizeParam,
                                             @RequestParam(required = false, defaultValue = "id") String sort,
                                             @RequestParam(required = false, value = "firstName") String firstName,
                                             @RequestParam(required = false, value = "lastName") String lastName,
                                             @RequestParam(required = false, value = "schoolMatricule") String matricule,
                                             @RequestParam(required = false, value = "classe") String classe,
                                             @RequestParam(required = false, value = "status") String statut,
                                             @RequestParam(required = false, value = "sex") String sexe,
                                             @RequestParam(required = false, defaultValue = "desc") String order) {
        return ResponseEntity.ok().body(iEtudiantService.getEtudiants(firstName, lastName, matricule,
                classe, statut, sexe, Integer.parseInt(pageParam), Integer.parseInt(sizeParam), sort, order));
    }

    @GetMapping("/{matricule}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "afficher les détails d'un etudiant par son matricule", tags = "Etudiant", responses = {
            @ApiResponse(responseCode = "201", content = @Content(mediaType = "Application/Json", array = @ArraySchema(schema = @Schema(implementation = EtudiantReqDto.class)))),
            @ApiResponse(responseCode = "404", description = "Etudiant not found", content = @Content(mediaType = "Application/Json")),
            @ApiResponse(responseCode = "401", description = "Full authentication is required to access this resource", content = @Content(mediaType = "Application/Json")),
            @ApiResponse(responseCode = "403", description = "Forbidden : accès refusé", content = @Content(mediaType = "Application/Json")),
            @ApiResponse(responseCode = "500", description = "Internal Server Error ", content = @Content(mediaType = "Application/Json")),})
    public ResponseEntity<?> getEtudiantByMatricule(@PathVariable String matricule) {
        EtudiantResDto etudiantResDto = iEtudiantService.getEtudiantbyMatricule(matricule);
        return ResponseEntity.ok().body(etudiantResDto);
    }

    @GetMapping("/QrCode/{matricule}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "afficher le QrCode d'un etudiant par son matricule", tags = "Etudiant", responses = {
            @ApiResponse(responseCode = "201", content = @Content(mediaType = "Application/Json", array = @ArraySchema(schema = @Schema(implementation = EtudiantReqDto.class)))),
            @ApiResponse(responseCode = "404", description = "Etudiant not found", content = @Content(mediaType = "Application/Json")),
            @ApiResponse(responseCode = "401", description = "Full authentication is required to access this resource", content = @Content(mediaType = "Application/Json")),
            @ApiResponse(responseCode = "403", description = "Forbidden : accès refusé", content = @Content(mediaType = "Application/Json")),
            @ApiResponse(responseCode = "500", description = "Internal Server Error ", content = @Content(mediaType = "Application/Json")),})
    public ResponseEntity<?> getQrCodeByEtudiant(@PathVariable String matricule) {
        byte[] image = iEtudiantService.getQrCodeByEtudiant(matricule);
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.CONTENT_DISPOSITION, "inline;filename=qrcode.pdf");
        return ResponseEntity.ok().headers(headers).contentType(MediaType.IMAGE_PNG).body(image);
    }

    @PostMapping("/upload")
    @Operation(summary = "importer un fichier excel pour un etudiant", tags = "Etudiant", responses = {
            @ApiResponse(responseCode = "201", content = @Content(mediaType = "Application/Json", array = @ArraySchema(schema = @Schema(implementation = EtudiantReqDto.class)))),
            @ApiResponse(responseCode = "404", description = "Etudiant not found", content = @Content(mediaType = "Application/Json")),
            @ApiResponse(responseCode = "401", description = "Full authentication is required to access this resource", content = @Content(mediaType = "Application/Json")),
            @ApiResponse(responseCode = "403", description = "Forbidden : accès refusé", content = @Content(mediaType = "Application/Json")),
            @ApiResponse(responseCode = "500", description = "Internal Server Error ", content = @Content(mediaType = "Application/Json")),})
    public ResponseEntity<List<List<String>>> uploadExcelFile(@RequestParam("file") MultipartFile file) {
        try {
            List<List<String>> excelData = iEtudiantService.importListEtudiant(file);
            return ResponseEntity.ok(excelData);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @Operation(summary = "Importer la photo d'un étudiant", tags = "Etudiant", responses = {
            @ApiResponse(responseCode = "201", content = @Content(mediaType = "Application/Json", array = @ArraySchema(schema = @Schema(implementation = EtudiantReqDto.class)))),
            @ApiResponse(responseCode = "404", description = "Etudiant not found", content = @Content(mediaType = "Application/Json")),
            @ApiResponse(responseCode = "401", description = "Full authentication is required to access this resource", content = @Content(mediaType = "Application/Json")),
            @ApiResponse(responseCode = "403", description = "Forbidden : accès refusé", content = @Content(mediaType = "Application/Json")),})
    @PatchMapping("/photo/{matricule}")
    @PreAuthorize("hasAnyRole('SUPERADMIN','ADMIN','USER')")
    public ResponseEntity<?> importPhoto(@RequestBody MultipartFile file, @PathVariable String matricule) {

        Etudiants etudiant = iEtudiantRepo.findByMatricule(matricule).get();
        TypeDocument typeDocument = iTypeDocumentRepo.findByName(ETypeDocument.IMAGE).orElseThrow(()-> new ResourceNotFoundException("Statut :  "  +  ETypeDocument.IMAGE +  "  not found"));
        iDocumentStorageService.storeFile(file, etudiant.getSchoolMatricule(), "png", typeDocument);
        String fileDownloadUri = api_base_url+"api/etudiant/file/" + etudiant.getSchoolMatricule() + "/downloadFile?type=image&docType=png";
        etudiant.setPhotoLink(fileDownloadUri);
        etudiant.setUpdatedAt(LocalDateTime.now());
        iEtudiantRepo.save(etudiant);

        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.CONTENT_DISPOSITION, "inline;filename=" + etudiant.getMatricule() + ".png");

//        return ResponseEntity.ok().headers(headers).contentType(MediaType.APPLICATION_PDF).body(data);
        return ResponseEntity.ok(etudiant);
    }

    @Operation(summary = "Télécharger image de l'étudiant", tags = "Etudiant", responses = {
            @ApiResponse(responseCode = "200", content = @Content(mediaType = "Application/Json")),
            @ApiResponse(responseCode = "404", description = "File not found", content = @Content(mediaType = "Application/Json")),})
    @GetMapping("/file/{id}/downloadFile")
    public ResponseEntity<Object> downloadFile(@PathVariable("id") String matricule, HttpServletRequest request) {

        TypeDocument typeDocument = iTypeDocumentRepo.findByName(ETypeDocument.IMAGE).orElseThrow(()-> new ResourceNotFoundException("Type de document: IMAGE not found"));

        String fileName = iDocumentStorageService.getDocumentName(matricule, "png", typeDocument.getId());
        Resource resource = null;
        if (fileName != null && !fileName.isEmpty()) {
            try {
                resource = iDocumentStorageService.loadFileAsResource(fileName);
            } catch (Exception e) {
                log.info(e.getMessage());
            }
            // Try to determine file's content type
            String contentType = null;
            try {
                contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
            } catch (IOException e) {
                log.info("Could not determine file type.");
            }
            // Fallback to the default content type if type could not be determined
            if (contentType == null) {
                contentType = "application/octet-stream";
            }
            return ResponseEntity.ok().contentType(MediaType.parseMediaType(contentType))
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                    .body(resource);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("File not found");
        }
    }
}
