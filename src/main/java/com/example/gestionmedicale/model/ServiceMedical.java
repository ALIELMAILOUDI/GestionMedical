package com.example.gestionmedicale.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.util.List;

@Entity
public class ServiceMedical {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nom;
    private String type;

    @OneToMany(mappedBy = "serviceMedical")
    @JsonIgnore
    private List<Medecin> medecins;

    @OneToMany(mappedBy = "serviceMedical")
    @JsonIgnore
    private List<DossierMedical> dossiersMedicaux;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<Medecin> getMedecins() {
        return medecins;
    }

    public void setMedecins(List<Medecin> medecins) {
        this.medecins = medecins;
    }

    public List<DossierMedical> getDossiersMedicaux() {
        return dossiersMedicaux;
    }

    public void setDossiersMedicaux(List<DossierMedical> dossiersMedicaux) {
        this.dossiersMedicaux = dossiersMedicaux;
    }

    // Getters et Setters...
}
