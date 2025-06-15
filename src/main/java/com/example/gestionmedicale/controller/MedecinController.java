package com.example.gestionmedicale.controller;

import com.example.gestionmedicale.model.Medecin;
import com.example.gestionmedicale.repository.MedecinRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController  // <-- Point 1: Est-ce que @RestController est bien là ?
@RequestMapping("/api/medecins") // <-- Point 2: Est-ce que le chemin est correct ?
public class MedecinController {

    @Autowired
    private MedecinRepository medecinRepository;

    @GetMapping // <-- Point 3: Est-ce que @GetMapping est bien là (sans chemin additionnel) ?
    @PreAuthorize("hasAnyRole('ADMIN', 'MEDECIN')")
    public List<Medecin> getAllMedecins() {
        return medecinRepository.findAll();
    }

    // Vous pouvez ajouter d'autres endpoints ici, comme pour récupérer un médecin par son ID
    // @GetMapping("/{id}")
    // public Medecin getMedecinById(@PathVariable Long id) { ... }
}