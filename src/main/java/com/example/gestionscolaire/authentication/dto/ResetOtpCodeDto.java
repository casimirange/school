package com.example.gestionscolaire.authentication.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class ResetOtpCodeDto {
    @NotNull(message = "${appProvider.required}")
    private String appProvider;
    @NotNull(message = "${email.required}")
    private String email;
}
