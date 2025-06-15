package com.example.gestionmedicale.controller;

import com.example.gestionmedicale.model.Consultation;
import com.example.gestionmedicale.model.Prescription;
import com.example.gestionmedicale.repository.ConsultationRepository;
import com.example.gestionmedicale.repository.PrescriptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/prescriptions")
public class PrescriptionController {

    @Autowired
    private PrescriptionRepository prescriptionRepository;
    @Autowired
    private ConsultationRepository consultationRepository;

    @PostMapping
    @PreAuthorize("hasRole('MEDECIN')")
    public ResponseEntity<Prescription> addPrescriptionToConsultation(@RequestBody AddPrescriptionRequest request) {
        // 1. Trouver la consultation
        Consultation consultation = consultationRepository.findById(request.consultationId())
                .orElse(null);
        if (consultation == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        // 2. Créer et sauvegarder la prescription
        Prescription prescription = new Prescription();
        prescription.setMedicaments(request.medicaments());
        prescription.setInstructions(request.instructions());
        prescription.setConsultation(consultation);

        Prescription savedPrescription = prescriptionRepository.save(prescription);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedPrescription);
    }

    public record AddPrescriptionRequest(Long consultationId, String medicaments, String instructions) {}
}