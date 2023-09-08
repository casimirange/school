package com.example.gestionscolaire.authentication.dto;

import lombok.Value;

@Value
public class SignInResponse {
    private boolean using2FA;
    private String qrCodeImage;
    private String access_token;
    private boolean authenticated;
}
