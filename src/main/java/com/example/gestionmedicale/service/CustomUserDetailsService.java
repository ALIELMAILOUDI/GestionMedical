package com.example.gestionmedicale.service;

import com.example.gestionmedicale.repository.MedecinRepository;
import com.example.gestionmedicale.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.util.Arrays;
import java.util.stream.Collectors;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private PatientRepository patientRepository;
    @Autowired
    private MedecinRepository medecinRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Cherche d'abord dans les patients
        return patientRepository.findByUsername(username)
                .map(patient -> new User(
                        patient.getUsername(),
                        patient.getPassword(),
                        Arrays.stream(patient.getRoles().split(",")).map(SimpleGrantedAuthority::new).collect(Collectors.toList())
                ))
                // Sinon, cherche dans les médecins/admins
                .orElseGet(() -> medecinRepository.findByUsername(username)
                        .map(medecin -> new User(
                                medecin.getUsername(),
                                medecin.getPassword(),
                                Arrays.stream(medecin.getRoles().split(",")).map(SimpleGrantedAuthority::new).collect(Collectors.toList())
                        ))
                        .orElseThrow(() -> new UsernameNotFoundException("Utilisateur non trouvé : " + username))
                );
    }
}