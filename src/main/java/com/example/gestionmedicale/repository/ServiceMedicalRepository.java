package com.example.gestionmedicale.repository;

import com.example.gestionmedicale.model.Consultation;
import com.example.gestionmedicale.model.ServiceMedical;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServiceMedicalRepository extends JpaRepository<ServiceMedical, Long> {
}
