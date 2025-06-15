package com.example.gestionmedicale.controller;

import com.example.gestionmedicale.model.Consultation;
import com.example.gestionmedicale.model.RendezVous;
import com.example.gestionmedicale.model.RendezVousStatus;
import com.example.gestionmedicale.repository.ConsultationRepository;
import com.example.gestionmedicale.repository.RendezVousRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/consultations")
public class ConsultationController {

    @Autowired
    private ConsultationRepository consultationRepository;
    @Autowired
    private RendezVousRepository rendezVousRepository;

    @PostMapping
    @PreAuthorize("hasRole('MEDECIN')")
    @Transactional 
    public ResponseEntity<Consultation> createConsultation(@RequestBody CreateConsultationRequest request) {

        // 1. Trouver le rendez-vous
        RendezVous rendezVous = rendezVousRepository.findById(request.rendezVousId())
                .orElse(null);

        if (rendezVous == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // RDV non trouvé
        }
        if (rendezVous.getStatut() == RendezVousStatus.TERMINE) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build(); // Déjà terminé
        }

        // 2. Créer la consultation
        Consultation consultation = new Consultation();
        consultation.setDate(LocalDateTime.now());
        consultation.setCompteRendu(request.compteRendu());
        consultation.setRendezVous(rendezVous);
        consultation.setDossierMedical(rendezVous.getPatient().getDossierMedical());

        // 3. Mettre à jour le statut du RDV
        rendezVous.setStatut(RendezVousStatus.TERMINE);

        // 4. Sauvegarder les deux entités
        rendezVousRepository.save(rendezVous);
        Consultation savedConsultation = consultationRepository.save(consultation);

        return ResponseEntity.status(HttpStatus.CREATED).body(savedConsultation);
    }

    public record CreateConsultationRequest(Long rendezVousId, String compteRendu) {}
}
