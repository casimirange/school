package com.example.gestionscolaire;

//import com.example.gestionscolaire.personne.model.Enseignant;
//import com.example.gestionscolaire.personne.model.Etudiant;
import com.example.gestionscolaire.Document.entity.DocumentStorageProfProperties;
import com.example.gestionscolaire.Document.entity.DocumentStorageProperties;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Calendar;
import java.util.Random;

@SpringBootApplication
@Configuration
@EnableConfigurationProperties({DocumentStorageProperties.class, DocumentStorageProfProperties.class})
public class GestionScolaireApplication extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder){
        return builder.sources(GestionScolaireApplication.class);
    }

    public static void main(String[] args) {
        SpringApplication.run(GestionScolaireApplication.class, args);
        Calendar date = Calendar.getInstance();
        String matricule = date.get(Calendar.YEAR) + "ET" + (1000 + new Random().nextInt(9000));
        System.out.println(matricule);
    }

    @Bean
    public ObjectMapper objectMapper(){
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        return mapper;
    }
}
