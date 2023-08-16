package com.example.gestionscolaire.configuration.scolarite.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ControlReqDto {
    private LocalDateTime dateControl;
    private boolean etat;
    private Long controlPoint;
}
