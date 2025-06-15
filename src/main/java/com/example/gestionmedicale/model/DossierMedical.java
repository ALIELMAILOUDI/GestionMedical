package com.example.gestionmedicale.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.util.List;

@Entity
public class DossierMedical {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String historique;
    private String allergies;

    @OneToOne
    @JoinColumn(name = "patient_id")
    @JsonIgnore
    private Patient patient;

    @ManyToOne
    @JoinColumn(name = "service_medical_id")
    private ServiceMedical serviceMedical;

    @OneToMany(mappedBy = "dossierMedical")
    @JsonIgnore
    private List<Consultation> consultations;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getHistorique() {
        return historique;
    }

    public void setHistorique(String historique) {
        this.historique = historique;
    }

    public String getAllergies() {
        return allergies;
    }

    public void setAllergies(String allergies) {
        this.allergies = allergies;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public ServiceMedical getServiceMedical() {
        return serviceMedical;
    }

    public void setServiceMedical(ServiceMedical serviceMedical) {
        this.serviceMedical = serviceMedical;
    }

    public List<Consultation> getConsultations() {
        return consultations;
    }

    public void setConsultations(List<Consultation> consultations) {
        this.consultations = consultations;
    }

    // Getters et Setters...
}