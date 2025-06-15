package com.example.gestionmedicale.repository;

import com.example.gestionmedicale.model.Consultation;
import com.example.gestionmedicale.model.Prescription;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PrescriptionRepository extends JpaRepository<Prescription, Long> {
}
