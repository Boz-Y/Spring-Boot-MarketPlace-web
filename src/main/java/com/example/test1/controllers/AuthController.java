package com.example.test1.controllers;


import java.util.List;
import javax.validation.Valid;
import com.example.test1.metier.UtilisateurInterface;
import com.example.test1.models.Utilisateur;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;




@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private UtilisateurInterface UtilisateurService;


    @PostMapping(value = "/signupUSER")
    public ResponseEntity<Utilisateur> saveUtilisateur(@Valid @RequestBody Utilisateur utilisateur)
    {
        return UtilisateurService.AddUtilisateur(utilisateur);
    }
    @PostMapping(value = "/signupADMIN")
    public ResponseEntity<Utilisateur> saveUtilisateurAD(@Valid @RequestBody Utilisateur utilisateur)
    {
        return UtilisateurService.AddAdmin(utilisateur);
    }
    @PostMapping(value = "/signin")
    public ResponseEntity<?>authentication(@Valid @RequestBody Utilisateur utilisateur){
        return UtilisateurService.authenticateUser(utilisateur);
    }

    @GetMapping(value = "/allusers")
    public List<Utilisateur> getAllUtilisateurs ()
    {

        return UtilisateurService.getAllUtilisateurs();
    }
}
