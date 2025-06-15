package com.example.gestionmedicale.model;

import jakarta.persistence.*;

@Entity
public class Prescription {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String medicaments;
    private String instructions;

    @ManyToOne
    @JoinColumn(name = "consultation_id")
    private Consultation consultation;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMedicaments() {
        return medicaments;
    }

    public void setMedicaments(String medicaments) {
        this.medicaments = medicaments;
    }

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    public Consultation getConsultation() {
        return consultation;
    }

    public void setConsultation(Consultation consultation) {
        this.consultation = consultation;
    }

    // Getters et Setters...
}