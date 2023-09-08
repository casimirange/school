package com.example.gestionscolaire.etudiant.controller;

import com.example.gestionscolaire.configuration.globalCoonfig.globalConfiguration.ApplicationConstant;
import com.example.gestionscolaire.etudiant.dto.EtudiantReqDto;
import com.example.gestionscolaire.etudiant.dto.EtudiantResDto;
import com.example.gestionscolaire.etudiant.service.IEtudiantService;
import com.google.zxing.WriterException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/etudiant")
@RequiredArgsConstructor
@Tag( name = "Etudiant")
public class EtudiantController {
    private final IEtudiantService iEtudiantService;

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
                                              @RequestParam(required = false, defaultValue = "desc") String order) {
        return ResponseEntity.ok().body(iEtudiantService.getEtudiants(Integer.parseInt(pageParam), Integer.parseInt(sizeParam), sort, order));
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
}
