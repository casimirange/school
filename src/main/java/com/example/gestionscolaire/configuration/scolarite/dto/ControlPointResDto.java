package com.example.gestionscolaire.configuration.scolarite.dto;

import com.example.gestionscolaire.configuration.scolarite.model.ETypeControl;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ControlPointResDto {
    private Long id;
    private ETypeControl type;
    private float range;
}
