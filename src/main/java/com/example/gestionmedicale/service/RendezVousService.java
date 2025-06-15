package com.example.gestionmedicale.service;

import com.example.gestionmedicale.model.Medecin;
import com.example.gestionmedicale.model.Patient;
import com.example.gestionmedicale.model.RendezVous;
import com.example.gestionmedicale.model.RendezVousStatus;
import com.example.gestionmedicale.repository.MedecinRepository;
import com.example.gestionmedicale.repository.PatientRepository;
import com.example.gestionmedicale.repository.RendezVousRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service // Indique à Spring que cette classe est un composant de service
public class RendezVousService {

    // Injection des dépendances nécessaires
    @Autowired
    private RendezVousRepository rendezVousRepository;
    @Autowired
    private PatientRepository patientRepository;
    @Autowired
    private MedecinRepository medecinRepository;

    /**
     * Crée un nouveau rendez-vous.
     * C'est une opération transactionnelle : si une étape échoue, tout est annulé.
     * @param usernamePatient Le nom d'utilisateur du patient qui demande le RDV.
     * @param medecinId L'ID du médecin souhaité.
     * @param dateHeure La date et l'heure du RDV.
     * @return Le RendezVous créé et sauvegardé.
     */
    @Transactional // Assure que l'opération est atomique
    public RendezVous creerRendezVous(String usernamePatient, Long medecinId, LocalDateTime dateHeure) {
        // 1. Trouver le patient à partir de son nom d'utilisateur.
        // S'il n'est pas trouvé, une exception est levée.
        Patient patient = patientRepository.findByUsername(usernamePatient)
                .orElseThrow(() -> new RuntimeException("Patient non trouvé avec le username: " + usernamePatient));

        // 2. Trouver le médecin à partir de son ID.
        Medecin medecin = medecinRepository.findById(medecinId)
                .orElseThrow(() -> new RuntimeException("Médecin non trouvé avec l'ID: " + medecinId));

        // 3. Créer une nouvelle instance de RendezVous
        RendezVous nouveauRendezVous = new RendezVous();
        nouveauRendezVous.setPatient(patient);
        nouveauRendezVous.setMedecin(medecin);
        nouveauRendezVous.setDateHeure(dateHeure);

        // 4. Définir le statut initial du rendez-vous
        nouveauRendezVous.setStatut(RendezVousStatus.PLANIFIE);

        // 5. Sauvegarder le nouveau rendez-vous dans la base de données
        return rendezVousRepository.save(nouveauRendezVous);
    }
}