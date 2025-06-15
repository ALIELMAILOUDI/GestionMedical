package com.example.gestionmedicale.controller;

import com.example.gestionmedicale.model.Patient;
import com.example.gestionmedicale.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/patients")
public class PatientController {

    @Autowired
    private PatientRepository patientRepository;

    // A doctor or admin can see all patients
    @GetMapping
    @PreAuthorize("hasAnyRole('MEDECIN', 'ADMIN')")
    public List<Patient> getAllPatients() {
        return patientRepository.findAll();
    }

    // A user can only see their own data. A doctor/admin can see any patient's data.
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('MEDECIN') or hasRole('ADMIN') or #id == @patientRepository.findByUsername(authentication.name).orElse(new Patient()).getId()")
    public ResponseEntity<Patient> getPatientById(@PathVariable Long id, Authentication authentication) {
        Optional<Patient> patient = patientRepository.findById(id);
        return patient.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')") // Only admin can create patients
    public Patient createPatient(@RequestBody Patient patient) {
        return patientRepository.save(patient);
    }
}