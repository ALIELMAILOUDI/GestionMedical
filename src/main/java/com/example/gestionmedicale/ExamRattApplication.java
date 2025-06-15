package com.example.gestionmedicale;
import com.example.gestionmedicale.repository.ServiceMedicalRepository;
import com.example.gestionmedicale.model.*;
import com.example.gestionmedicale.repository.*; // Importer tous les repositories
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;

@SpringBootApplication
public class ExamRattApplication {

    public static void main(String[] args) {
        SpringApplication.run(ExamRattApplication.class, args);
    }

    /**
     * Ce Bean s'exécute au démarrage de l'application pour initialiser la base de données
     * avec des données de test. C'est la méthode recommandée pour gérer la logique
     * comme l'encodage des mots de passe, qui ne peut pas être fait dans un simple fichier data.sql.
     */
    @Bean
    @Transactional // Recommandé pour les opérations complexes de base de données
    CommandLineRunner commandLineRunner(
            PatientRepository patientRepository,
            MedecinRepository medecinRepository,
            ServiceMedicalRepository serviceMedicalRepository,
            DossierMedicalRepository dossierMedicalRepository,
            RendezVousRepository rendezVousRepository,
            PasswordEncoder passwordEncoder
    ) {
        return args -> {
            System.out.println("---- Initialisation des données de test... ----");

            // 1. Créer les services médicaux
            ServiceMedical serviceCardio = new ServiceMedical();
            serviceCardio.setNom("Cardiologie");
            serviceCardio.setType("Service public");
            serviceMedicalRepository.save(serviceCardio);

            ServiceMedical servicePediatrie = new ServiceMedical();
            servicePediatrie.setNom("Pédiatrie");
            servicePediatrie.setType("Service privé");
            serviceMedicalRepository.save(servicePediatrie);

            // 2. Créer l'utilisateur Médecin
            Medecin medecin = new Medecin();
            medecin.setNom("Dr. House");
            medecin.setSpecialite("Diagnosticien");
            medecin.setUsername("docteur.house");
            medecin.setPassword(passwordEncoder.encode("password"));
            medecin.setRoles("ROLE_MEDECIN");
            medecin.setServiceMedical(serviceCardio);
            medecinRepository.save(medecin);

            // 3. Créer l'utilisateur Admin
            Medecin admin = new Medecin();
            admin.setNom("Admin User");
            admin.setSpecialite("Administration");
            admin.setUsername("admin");
            admin.setPassword(passwordEncoder.encode("password"));
            admin.setRoles("ROLE_ADMIN");
            // L'admin n'a pas de service médical associé
            medecinRepository.save(admin);

            // 4. Créer l'utilisateur Patient et son dossier médical
            Patient patientJohn = new Patient();
            patientJohn.setNom("John Doe");
            patientJohn.setDateNaissance(LocalDate.of(1990, 5, 15));
            patientJohn.setGroupeSanguin("O+");
            patientJohn.setUsername("john.doe");
            patientJohn.setPassword(passwordEncoder.encode("password"));
            patientJohn.setRoles("ROLE_PATIENT");
            patientRepository.save(patientJohn); // On sauvegarde d'abord le patient pour obtenir un ID

            DossierMedical dossierJohn = new DossierMedical();
            dossierJohn.setHistorique("Historique initial pour John Doe.");
            dossierJohn.setAllergies("Pollen");
            dossierJohn.setPatient(patientJohn);
            dossierMedicalRepository.save(dossierJohn);

            // 5. Créer un autre patient
            Patient patientJane = new Patient();
            patientJane.setNom("Jane Smith");
            patientJane.setDateNaissance(LocalDate.of(1985, 11, 20));
            patientJane.setGroupeSanguin("A-");
            patientJane.setUsername("jane.smith");
            patientJane.setPassword(passwordEncoder.encode("password"));
            patientJane.setRoles("ROLE_PATIENT");
            patientRepository.save(patientJane);

            DossierMedical dossierJane = new DossierMedical();
            dossierJane.setHistorique("Historique pour Jane Smith.");
            dossierJane.setAllergies("Aucune connue");
            dossierJane.setPatient(patientJane);
            dossierMedicalRepository.save(dossierJane);

            // 6. Créer un rendez-vous de test
            RendezVous rdv = new RendezVous();
            rdv.setPatient(patientJohn);
            rdv.setMedecin(medecin);
            rdv.setDateHeure(LocalDateTime.now().plusDays(5));
            rdv.setStatut(RendezVousStatus.PLANIFIE);
            rendezVousRepository.save(rdv);

            System.out.println("---- Données de test initialisées avec succès ! ----");
            System.out.println("Utilisateurs créés : admin, docteur.house, john.doe. Mot de passe pour tous : 'password'");
        };
    }
}