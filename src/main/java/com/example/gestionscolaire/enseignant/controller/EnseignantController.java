package com.example.gestionscolaire.enseignant.controller;

import com.example.gestionscolaire.configuration.globalCoonfig.globalConfiguration.ApplicationConstant;
import com.example.gestionscolaire.enseignant.dto.EnseignantReqDto;
import com.example.gestionscolaire.enseignant.dto.EnseignantResDto;
import com.example.gestionscolaire.enseignant.service.IEnseignantService;
import com.example.gestionscolaire.etudiant.dto.EtudiantReqDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/enseignants")
//@RequiredArgsConstructor
@Tag( name = "Enseignants")
public class EnseignantController {
    @Autowired
    IEnseignantService iEnseignantService;

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

    @PostMapping("/upload")
    @Operation(summary = "importer un fichier excel pour créer des enseignants", tags = "Enseignants", responses = {
            @ApiResponse(responseCode = "201", content = @Content(mediaType = "Application/Json", array = @ArraySchema(schema = @Schema(defaultValue = "champs (nom, prenom)", implementation = EnseignantReqDto.class)))),
            @ApiResponse(responseCode = "404", description = "Enseignants not found", content = @Content(mediaType = "Application/Json")),
            @ApiResponse(responseCode = "401", description = "Full authentication is required to access this resource", content = @Content(mediaType = "Application/Json")),
            @ApiResponse(responseCode = "403", description = "Forbidden : accès refusé", content = @Content(mediaType = "Application/Json")),
            @ApiResponse(responseCode = "500", description = "Internal Server Error ", content = @Content(mediaType = "Application/Json")),})
    public ResponseEntity<List<List<String>>> uploadExcelFile(@RequestParam("file") MultipartFile file) {
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
                                                   @RequestParam(required = false, defaultValue = "id") String sort,
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
    public ResponseEntity<?> getEtudiantByMatricule(@PathVariable String matricule) {
        EnseignantResDto enseignantResDto = iEnseignantService.getProfbyMatricule(matricule);
        return ResponseEntity.ok().body(enseignantResDto);
    }

    @GetMapping("/QrCode/{matricule}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "afficher le QrCode d'un enseignant par son matricule", tags = "Enseignants", responses = {
            @ApiResponse(responseCode = "201", content = @Content(mediaType = "Application/Json", array = @ArraySchema(schema = @Schema(implementation = EtudiantReqDto.class)))),
            @ApiResponse(responseCode = "404", description = "Enseignants not found", content = @Content(mediaType = "Application/Json")),
            @ApiResponse(responseCode = "401", description = "Full authentication is required to access this resource", content = @Content(mediaType = "Application/Json")),
            @ApiResponse(responseCode = "403", description = "Forbidden : accès refusé", content = @Content(mediaType = "Application/Json")),
            @ApiResponse(responseCode = "500", description = "Internal Server Error ", content = @Content(mediaType = "Application/Json")),})
    public ResponseEntity<?> getQrCodeByEtudiant(@PathVariable String matricule) {
        byte[] image = iEnseignantService.getQrCodeByProf(matricule);
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.CONTENT_DISPOSITION, "inline;filename=qrcode.pdf");
        return ResponseEntity.ok().headers(headers).contentType(MediaType.IMAGE_PNG).body(image);
    }
}
