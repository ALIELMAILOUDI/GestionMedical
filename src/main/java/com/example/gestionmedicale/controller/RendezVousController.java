package com.example.gestionmedicale.controller;

import com.example.gestionmedicale.model.RendezVous;
import com.example.gestionmedicale.repository.RendezVousRepository;
import com.example.gestionmedicale.service.RendezVousService; // Importer le service
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/rendezvous")
public class RendezVousController {

    @Autowired
    private RendezVousRepository rendezVousRepository;

    // Injection du nouveau service
    @Autowired
    private RendezVousService rendezVousService;

    // Endpoint pour obtenir tous les RDV (pour admin/médecin)
    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'MEDECIN')")
    public List<RendezVous> getAllRendezVous() {
        return rendezVousRepository.findAll();
    }

    // Endpoint pour qu'un patient puisse demander un rendez-vous.
    // Il utilise maintenant le RendezVousService.
    @PostMapping("/demander")
    @PreAuthorize("hasRole('PATIENT')")
    public ResponseEntity<RendezVous> demanderRendezVous(@RequestBody DemandeRendezVousRequest request, Authentication authentication) {
        try {
            // On récupère le nom d'utilisateur de la personne connectée
            String username = authentication.getName();

            // On délègue toute la logique de création au service
            RendezVous rdvCree = rendezVousService.creerRendezVous(
                    username,
                    request.medecinId(),
                    request.dateHeure()
            );
            return ResponseEntity.ok(rdvCree);
        } catch (RuntimeException e) {
            // Si le service lève une exception (ex: patient non trouvé), on retourne une erreur 400 Bad Request
            return ResponseEntity.badRequest().body(null);
        }
    }

    // Un DTO (Data Transfer Object) pour représenter la requête de création de RDV.
    // C'est une bonne pratique pour ne pas exposer directement les modèles.
    public record DemandeRendezVousRequest(Long medecinId, LocalDateTime dateHeure) {}
}