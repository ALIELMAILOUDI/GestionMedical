package com.example.gestionmedicale.repository;

import com.example.gestionmedicale.model.Consultation;
import com.example.gestionmedicale.model.DossierMedical;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DossierMedicalRepository extends JpaRepository<DossierMedical, Long> {
}
