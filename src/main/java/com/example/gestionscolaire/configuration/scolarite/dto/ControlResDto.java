package com.example.gestionscolaire.configuration.scolarite.dto;

import com.example.gestionscolaire.configuration.scolarite.model.ControlCkeckPoint;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ControlResDto {
    private Long id;
    private LocalDateTime dateControl;
    private boolean etat;
    private ControlCkeckPoint controlPoint;
}
