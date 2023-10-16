package com.example.gestionscolaire;

//import com.example.gestionscolaire.personne.model.Enseignant;
//import com.example.gestionscolaire.personne.model.Etudiant;

import com.example.gestionscolaire.Document.entity.DocumentStorageProfProperties;
import com.example.gestionscolaire.Document.entity.DocumentStorageProperties;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.security.SecureRandom;
import java.util.Calendar;
import java.util.Random;

@SpringBootApplication
@Configuration
@EnableConfigurationProperties({DocumentStorageProperties.class, DocumentStorageProfProperties.class})
@EnableAsync
public class GestionScolaireApplication extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(GestionScolaireApplication.class);
    }

    public static void main(String[] args) {
        SpringApplication.run(GestionScolaireApplication.class, args);
        Calendar date = Calendar.getInstance();
        String year = date.get(Calendar.YEAR) + "";
        String matricule = year.substring(2, 4) + "ESG" + (1000 + new Random().nextInt(9000)) + RandomStringUtils.random(6, 40, 150, true, true);
        System.out.println(matricule);
    }

    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        return mapper;
    }

    @Bean
    public TaskExecutor threadPoolTaskExecutor() {

        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setQueueCapacity(2000);
        executor.setMaxPoolSize(6);
        executor.setCorePoolSize(6);
        executor.setThreadNamePrefix("poolThread-");
        executor.initialize();

        return executor;
    }
}
