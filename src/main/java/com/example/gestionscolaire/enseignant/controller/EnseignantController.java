package com.example.gestionscolaire.enseignant.controller;

import com.example.gestionscolaire.Document.entity.ETypeDocument;
import com.example.gestionscolaire.Document.entity.TypeDocument;
import com.example.gestionscolaire.Document.repository.ITypeDocumentRepo;
import com.example.gestionscolaire.Document.service.IDocumentStorageService;
import com.example.gestionscolaire.configuration.globalCoonfig.globalConfiguration.ApplicationConstant;
import com.example.gestionscolaire.enseignant.dto.EnseignantReqDto;
import com.example.gestionscolaire.enseignant.dto.EnseignantResDto;
import com.example.gestionscolaire.enseignant.model.Enseignants;
import com.example.gestionscolaire.enseignant.repository.IEnseignantRepo;
import com.example.gestionscolaire.enseignant.service.IEnseignantService;
import com.example.gestionscolaire.etudiant.dto.EtudiantReqDto;
import com.google.zxing.WriterException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/enseignants")
@RequiredArgsConstructor
@Tag( name = "Enseignants")
@Slf4j
public class EnseignantController {
    private final IEnseignantService iEnseignantService;
    private final IEnseignantRepo iEnseignantRepo;
    private final ITypeDocumentRepo iTypeDocumentRepo;
    private final IDocumentStorageService iDocumentStorageService;
    @Value("${app.api.base.url}")
    private String api_base_url;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "création des informations pour un enseignant", tags = "Enseignants", responses = {
            @ApiResponse(responseCode = "201", content = @Content(mediaType = "Application/Json", array = @ArraySchema(schema = @Schema(implementation = EtudiantReqDto.class)))),
            @ApiResponse(responseCode = "404", description = "Etudiant not found", content = @Content(mediaType = "Application/Json")),
            @ApiResponse(responseCode = "401", description = "Full authentication is required to access this resource", content = @Content(mediaType = "Application/Json")),
            @ApiResponse(responseCode = "403", description = "Forbidden : accès refusé", content = @Content(mediaType = "Application/Json")),
            @ApiResponse(responseCode = "500", description = "Internal Server Error ", content = @Content(mediaType = "Application/Json")),})
    public ResponseEntity<EnseignantResDto> createEnseignant(@RequestBody EnseignantReqDto enseignantRequest) {
        EnseignantResDto enseignantResDto = iEnseignantService.addProf(enseignantRequest);
        return ResponseEntity.ok().body(enseignantResDto);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "modification des informations pour un etudiant", tags = "Enseignants", responses = {
            @ApiResponse(responseCode = "201", content = @Content(mediaType = "Application/Json", array = @ArraySchema(schema = @Schema(implementation = EnseignantReqDto.class)))),
            @ApiResponse(responseCode = "404", description = "Enseignants not found", content = @Content(mediaType = "Application/Json")),
            @ApiResponse(responseCode = "401", description = "Full authentication is required to access this resource", content = @Content(mediaType = "Application/Json")),
            @ApiResponse(responseCode = "403", description = "Forbidden : accès refusé", content = @Content(mediaType = "Application/Json")),
            @ApiResponse(responseCode = "500", description = "Internal Server Error ", content = @Content(mediaType = "Application/Json")),})
//    @PreAuthorize("hasAnyRole('SUPERADMIN','ADMIN','AGENT','USER')")
    @PutMapping("/{matricule}")
    public ResponseEntity<EnseignantResDto> updateEnseignants(@RequestBody EnseignantReqDto enseignantReqDto, @PathVariable String matricule) {
        EnseignantResDto enseignantResDto = iEnseignantService.updateEnseignant(enseignantReqDto, matricule);
        return ResponseEntity.ok().body(enseignantResDto);
    }

    @PatchMapping("/statusChange/{matricule}")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "changer le statut d'un etudiant", tags = "Enseignants", responses = {
            @ApiResponse(responseCode = "201", content = @Content(mediaType = "Application/Json", array = @ArraySchema(schema = @Schema(implementation = EnseignantReqDto.class)))),
            @ApiResponse(responseCode = "404", description = "Enseignants not found", content = @Content(mediaType = "Application/Json")),
            @ApiResponse(responseCode = "401", description = "Full authentication is required to access this resource", content = @Content(mediaType = "Application/Json")),
            @ApiResponse(responseCode = "403", description = "Forbidden : accès refusé", content = @Content(mediaType = "Application/Json")),
            @ApiResponse(responseCode = "500", description = "Internal Server Error ", content = @Content(mediaType = "Application/Json")),})
//    @PreAuthorize("hasAnyRole('SUPERADMIN','ADMIN','AGENT','USER')")
    public ResponseEntity<EnseignantResDto> disableEnseignants(@PathVariable String matricule) throws IOException, WriterException {
        EnseignantResDto enseignantResDto = iEnseignantService.disableEtudiant(matricule);
        return ResponseEntity.ok().body(enseignantResDto);
    }
    @PostMapping("/upload")
    @Operation(summary = "importer un fichier excel pour créer des enseignants", tags = "Enseignants", responses = {
            @ApiResponse(responseCode = "201", content = @Content(mediaType = "Application/Json", array = @ArraySchema(schema = @Schema(defaultValue = "champs (nom, prenom)", implementation = EnseignantReqDto.class)))),
            @ApiResponse(responseCode = "404", description = "Enseignants not found", content = @Content(mediaType = "Application/Json")),
            @ApiResponse(responseCode = "401", description = "Full authentication is required to access this resource", content = @Content(mediaType = "Application/Json")),
            @ApiResponse(responseCode = "403", description = "Forbidden : accès refusé", content = @Content(mediaType = "Application/Json")),
            @ApiResponse(responseCode = "500", description = "Internal Server Error ", content = @Content(mediaType = "Application/Json")),})
    public ResponseEntity<?> uploadExcelFile(@RequestParam("file") MultipartFile file) {
        try {
            List<List<String>> excelData = iEnseignantService.importListProf(file);
            return ResponseEntity.ok(excelData);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "liste des enseignants", tags = "Enseignants", responses = {
            @ApiResponse(responseCode = "200", content = @Content(mediaType = "Application/Json", array = @ArraySchema(schema = @Schema(implementation = EnseignantResDto.class)))),
            @ApiResponse(responseCode = "404", description = "Enseignant not found", content = @Content(mediaType = "Application/Json")),
            @ApiResponse(responseCode = "403", description = "Forbidden : accès refusé", content = @Content(mediaType = "Application/Json")),
            @ApiResponse(responseCode = "401", description = "Full authentication is required to access this resource", content = @Content(mediaType = "Application/Json"))})
    public ResponseEntity<?> getAllEnseignant(@RequestParam(required = false, value = "page", defaultValue = "0") String pageParam,
                                                   @RequestParam(required = false, value = "size", defaultValue = ApplicationConstant.DEFAULT_SIZE_PAGINATION) String sizeParam,
                                                   @RequestParam(required = false, defaultValue = "firstName") String sort,
                                                   @RequestParam(required = false, defaultValue = "desc") String order) {
        return ResponseEntity.ok().body(iEnseignantService.getProfs(Integer.parseInt(pageParam), Integer.parseInt(sizeParam), sort, order));
    }

    @GetMapping("/{matricule}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "afficher les détails d'un enseignant par son matricule", tags = "Enseignants", responses = {
            @ApiResponse(responseCode = "201", content = @Content(mediaType = "Application/Json", array = @ArraySchema(schema = @Schema(implementation = EnseignantResDto.class)))),
            @ApiResponse(responseCode = "404", description = "Enseignants not found", content = @Content(mediaType = "Application/Json")),
            @ApiResponse(responseCode = "401", description = "Full authentication is required to access this resource", content = @Content(mediaType = "Application/Json")),
            @ApiResponse(responseCode = "403", description = "Forbidden : accès refusé", content = @Content(mediaType = "Application/Json")),
            @ApiResponse(responseCode = "500", description = "Internal Server Error ", content = @Content(mediaType = "Application/Json")),})
    public ResponseEntity<?> getEnseignantsByMatricule(@PathVariable String matricule, HttpServletRequest request) throws IOException {
        EnseignantResDto enseignantResDto = iEnseignantService.getProfbyMatricule(matricule);
        return ResponseEntity.ok().body(enseignantResDto);
    }

    @GetMapping("/QrCode/{matricule}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "afficher le QrCode d'un enseignant par son matricule", tags = "Enseignants", responses = {
            @ApiResponse(responseCode = "201", content = @Content(mediaType = "Application/Json", array = @ArraySchema(schema = @Schema(implementation = EnseignantReqDto.class)))),
            @ApiResponse(responseCode = "404", description = "Enseignants not found", content = @Content(mediaType = "Application/Json")),
            @ApiResponse(responseCode = "401", description = "Full authentication is required to access this resource", content = @Content(mediaType = "Application/Json")),
            @ApiResponse(responseCode = "403", description = "Forbidden : accès refusé", content = @Content(mediaType = "Application/Json")),
            @ApiResponse(responseCode = "500", description = "Internal Server Error ", content = @Content(mediaType = "Application/Json")),})
    public ResponseEntity<?> getQrCodeByEnseignants(@PathVariable String matricule) {
        byte[] image = iEnseignantService.getQrCodeByProf(matricule);
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.CONTENT_DISPOSITION, "inline;filename=qrcode.pdf");
        return ResponseEntity.ok().headers(headers).contentType(MediaType.IMAGE_PNG).body(image);
//        return ResponseEntity.ok().body(image);
    }

    @Operation(summary = "Importer la photo de l'enseigant", tags = "Enseignants", responses = {
            @ApiResponse(responseCode = "201", content = @Content(mediaType = "Application/Json", array = @ArraySchema(schema = @Schema(implementation = EnseignantReqDto.class)))),
            @ApiResponse(responseCode = "404", description = "Enseignants not found", content = @Content(mediaType = "Application/Json")),
            @ApiResponse(responseCode = "401", description = "Full authentication is required to access this resource", content = @Content(mediaType = "Application/Json")),
            @ApiResponse(responseCode = "403", description = "Forbidden : accès refusé", content = @Content(mediaType = "Application/Json")),})
    @PatchMapping("/photo/{matricule}")
    @PreAuthorize("hasAnyRole('SUPERADMIN','ADMIN','USER')")
    public ResponseEntity<?> importPhoto(@RequestBody MultipartFile file, @PathVariable String matricule) {

        Enseignants enseignants = iEnseignantRepo.findByMatricule(matricule).get();
        TypeDocument typeDocument = iTypeDocumentRepo.findByName(ETypeDocument.IMAGE).orElseThrow(()-> new ResourceNotFoundException("Statut :  "  +  ETypeDocument.IMAGE +  "  not found"));
        iDocumentStorageService.storeProfFile(file, enseignants.getSchoolMatricule(), "png", typeDocument);
        String fileDownloadUri = api_base_url+"api/enseignants/file/" + enseignants.getSchoolMatricule() + "/downloadFile?type=image&docType=png";
        enseignants.setPhotoLink(fileDownloadUri);
        enseignants.setUpdatedAt(LocalDateTime.now());
        iEnseignantRepo.save(enseignants);

        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.CONTENT_DISPOSITION, "inline;filename=" + enseignants.getMatricule() + ".png");

//        return ResponseEntity.ok().headers(headers).contentType(MediaType.APPLICATION_PDF).body(data);
        return ResponseEntity.ok(enseignants);
    }

    @Operation(summary = "Télécharger image de l'enseignant", tags = "Enseignants", responses = {
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
            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(contentType))
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                    .body(resource);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("File not found");
        }
    }
}
