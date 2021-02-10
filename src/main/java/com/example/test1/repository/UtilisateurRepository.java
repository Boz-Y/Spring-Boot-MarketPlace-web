package com.example.test1.repository;



import com.example.test1.models.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UtilisateurRepository extends JpaRepository<Utilisateur, Long> {
    Optional<Utilisateur>   findByusernameUtilis (String usernameUtilis);

    Boolean existsByusernameUtilis(String usernameUtilis);

    Boolean existsByemailUtilis(String emailUtilis);

}
