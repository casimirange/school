package com.example.gestionscolaire.configuration.scolarite.dto;

import com.example.gestionscolaire.configuration.scolarite.model.ETypeControl;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ControlPointReqDto {
    private ETypeControl type;
    private Double range;
}
