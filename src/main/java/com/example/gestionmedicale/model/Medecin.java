package com.example.gestionmedicale.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.util.List;

@Entity
public class Medecin {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nom;
    private String specialite;

    // Pour la sécurité
    private String username;
    private String password;
    private String roles;

    @ManyToOne
    @JoinColumn(name = "service_medical_id")
    private ServiceMedical serviceMedical;

    @OneToMany(mappedBy = "medecin")
    @JsonIgnore
    private List<RendezVous> rendezVousList;

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

    public String getSpecialite() {
        return specialite;
    }

    public void setSpecialite(String specialite) {
        this.specialite = specialite;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRoles() {
        return roles;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }

    public ServiceMedical getServiceMedical() {
        return serviceMedical;
    }

    public void setServiceMedical(ServiceMedical serviceMedical) {
        this.serviceMedical = serviceMedical;
    }

    public List<RendezVous> getRendezVousList() {
        return rendezVousList;
    }

    public void setRendezVousList(List<RendezVous> rendezVousList) {
        this.rendezVousList = rendezVousList;
    }

    // Getters et Setters...
}