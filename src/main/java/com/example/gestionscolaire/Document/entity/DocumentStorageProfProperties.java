package com.example.gestionscolaire.Document.entity;

import lombok.*;
import org.springframework.boot.context.properties.ConfigurationProperties;

import javax.persistence.*;


@Entity
@Getter
@Setter
@ConfigurationProperties(prefix = "files")
@NoArgsConstructor
@EqualsAndHashCode(of = { "documentId", "fileName", "documentType", "documentFormat", "type", "seance" })
@Table(name = "photos_enseignants")
public class DocumentStorageProfProperties {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private Long documentId;

    private String fileName;

    private String documentType;

    private String documentFormat;

    @ManyToOne
    private TypeDocument type;

    @Transient
    private String uploadDir;

    @Column(nullable = false)
    private String matricule;
}
