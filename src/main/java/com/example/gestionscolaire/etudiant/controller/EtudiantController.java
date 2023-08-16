package com.example.gestionscolaire.etudiant.controller;

import com.example.gestionscolaire.etudiant.dto.EtudiantReqDto;
import com.example.gestionscolaire.etudiant.dto.EtudiantResDto;
import com.example.gestionscolaire.etudiant.service.IEtudiantService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/etudiant")
@RequiredArgsConstructor
@Tag( name = "Etudiant")
public class EtudiantController {
    private final IEtudiantService iEtudiantService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "création des informations pour un enseignant", tags = "Etudiant", responses = {
            @ApiResponse(responseCode = "201", content = @Content(mediaType = "Application/Json", array = @ArraySchema(schema = @Schema(implementation = EtudiantReqDto.class)))),
            @ApiResponse(responseCode = "404", description = "Etudiant not found", content = @Content(mediaType = "Application/Json")),
            @ApiResponse(responseCode = "401", description = "Full authentication is required to access this resource", content = @Content(mediaType = "Application/Json")),
            @ApiResponse(responseCode = "403", description = "Forbidden : accès refusé", content = @Content(mediaType = "Application/Json")),
            @ApiResponse(responseCode = "500", description = "Internal Server Error ", content = @Content(mediaType = "Application/Json")),})
//    @PreAuthorize("hasAnyRole('SUPERADMIN','ADMIN','AGENT','USER')")
    public ResponseEntity<EtudiantResDto> createEnseignant(@RequestBody EtudiantReqDto etudiantReqDto) {
        EtudiantResDto enseignantResDto = iEtudiantService.addEtudiant(etudiantReqDto);
        return ResponseEntity.ok().body(enseignantResDto);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<EtudiantResDto> getAllEnseignant() {
        return iEtudiantService.getEtudiants();
    }

    @PostMapping("/upload")
    public ResponseEntity<List<List<String>>> uploadExcelFile(@RequestParam("file") MultipartFile file) {
        try {
            List<List<String>> excelData = iEtudiantService.importListEtudiant(file);
            return ResponseEntity.ok(excelData);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
