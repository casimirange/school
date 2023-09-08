package com.example.gestionscolaire.configuration.globalCoonfig.email.service;


import com.example.gestionscolaire.configuration.globalCoonfig.email.dto.EmailDto;

public interface IEmailService {

	void sendEmail(EmailDto emailDto);

}
