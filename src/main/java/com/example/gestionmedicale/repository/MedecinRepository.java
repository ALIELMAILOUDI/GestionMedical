package com.example.gestionmedicale.repository;

import com.example.gestionmedicale.model.Medecin;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface MedecinRepository extends JpaRepository<Medecin, Long> {
    Optional<Medecin> findByUsername(String username);
}