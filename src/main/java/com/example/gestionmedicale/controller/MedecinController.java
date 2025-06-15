package com.example.gestionmedicale.controller;

import com.example.gestionmedicale.model.Medecin;
import com.example.gestionmedicale.repository.MedecinRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/medecins") 
public class MedecinController {

    @Autowired
    private MedecinRepository medecinRepository;

    @GetMapping 
    @PreAuthorize("hasAnyRole('ADMIN', 'MEDECIN')")
    public List<Medecin> getAllMedecins() {
        return medecinRepository.findAll();
    }

}
