package com.example.test1.metier;

import com.example.test1.models.Utilisateur;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

public interface UtilisateurInterface {
    List<Utilisateur> getAllUtilisateurs();

    ResponseEntity<Utilisateur> findByIdUtilisateur(Long id);

    void deleteByIdUtilisateur(Long id);

    void deleteAllUtilisateurs();

    ResponseEntity<Utilisateur> AddUtilisateur(Utilisateur utilisateur);

    ResponseEntity<Utilisateur> AddAdmin(Utilisateur utilisateur);

    ResponseEntity<Utilisateur> UpdateUtilisateur(Long id, Utilisateur utilisateur);


    ResponseEntity<?>authenticateUser(Utilisateur utilisateur);
}
