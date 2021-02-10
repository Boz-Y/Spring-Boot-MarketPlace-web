package com.example.test1.services;


import com.example.test1.metier.UtilisateurInterface;
import com.example.test1.models.ERole;
import com.example.test1.models.Privilege;
import com.example.test1.models.Utilisateur;
import com.example.test1.repository.PrivilegeRepository;
import com.example.test1.repository.UtilisateurRepository;
import com.example.test1.security.jwt.JwtResponse;
import com.example.test1.security.jwt.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
public class UtilisateurService implements UtilisateurInterface, UserDetailsService {
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    private UtilisateurRepository utilisateurRepository;
    @Autowired
    private PrivilegeRepository privilegeRepository;
    @Autowired
    PasswordEncoder encoder;
    @Autowired
    JwtUtils jwtUtils;


    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Utilisateur utilis = utilisateurRepository.findByusernameUtilis(username)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));

        return utilis;
    }


    @Override
    public List<Utilisateur> getAllUtilisateurs() {
        return utilisateurRepository.findAll();
    }

    @Override
    public ResponseEntity<Utilisateur> findByIdUtilisateur(Long id) {
        return ResponseEntity.ok(utilisateurRepository.findById(id).get());
    }

    @Override
    public void deleteByIdUtilisateur(Long id) {
        utilisateurRepository.deleteById(id);
    }

    @Override
    public void deleteAllUtilisateurs() {
        utilisateurRepository.deleteAll();
    }

    @Override
    public ResponseEntity<Utilisateur> AddUtilisateur(Utilisateur utilisateur) {
        if (utilisateurRepository.existsByusernameUtilis(utilisateur.getUsernameUtilis())) {
            return (ResponseEntity<Utilisateur>) ResponseEntity.badRequest();
        }
        if (utilisateurRepository.existsByemailUtilis(utilisateur.getEmailUtilis())) {
            return (ResponseEntity<Utilisateur>) ResponseEntity.badRequest();
        }
        utilisateur.setPwdUtilis(encoder.encode(utilisateur.getPwdUtilis()));
        Privilege userPrivilege = privilegeRepository.findByName(ERole.ROLE_USER)
                .orElseThrow(() -> new RuntimeException("Error: Role user is not found."));
        utilisateur.setPrivilege(userPrivilege);
        return ResponseEntity.ok(utilisateurRepository.save(utilisateur));
    }
    public ResponseEntity<Utilisateur> AddAdmin(Utilisateur utilisateur) {
        if (utilisateurRepository.existsByusernameUtilis(utilisateur.getUsernameUtilis())) {
            return (ResponseEntity<Utilisateur>) ResponseEntity.badRequest();
        }
        if (utilisateurRepository.existsByemailUtilis(utilisateur.getEmailUtilis())) {
            return (ResponseEntity<Utilisateur>) ResponseEntity.badRequest();
        }
        utilisateur.setPwdUtilis(encoder.encode(utilisateur.getPwdUtilis()));
        Privilege userPrivilege = privilegeRepository.findByName(ERole.ROLE_ADMIN)
                .orElseThrow(() -> new RuntimeException("Error: Role user is not found."));
        utilisateur.setPrivilege(userPrivilege);
        return ResponseEntity.ok(utilisateurRepository.save(utilisateur));
    }

    @Override
    public ResponseEntity<?> authenticateUser(Utilisateur utilisateur) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(utilisateur.getUsername(), utilisateur.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);
        Utilisateur utilis = (Utilisateur) authentication.getPrincipal();
        String privilege = utilis.getPrivilege().getName().name();

        return ResponseEntity.ok(new JwtResponse(jwt,
                utilis.getIdUtilis(),
                utilis.getUsername(),
                utilis.getEmailUtilis(),
                privilege));
    }

    @Override
    public ResponseEntity<Utilisateur> UpdateUtilisateur(Long id, Utilisateur utilisateur) {
        Utilisateur oldUtilisateur = utilisateurRepository.findById(id).get();
        utilisateurRepository.save(oldUtilisateur);
        return ResponseEntity.ok(oldUtilisateur);
    }
}
