package com.example.gestionmedicale.repository;

import com.example.gestionmedicale.model.Consultation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConsultationRepository extends JpaRepository<Consultation, Long> {
}