package com.formation.apirestdemo.dto;

import com.formation.apirestdemo.model.Utilisateur;

public class UtilisateurMapper {

    private UtilisateurMapper() {
    }

    public static Utilisateur versEntite(UtilisateurRequest request) {
        Utilisateur utilisateur = new Utilisateur();
        utilisateur.setNom(request.getNom());
        utilisateur.setPrenom(request.getPrenom());
        utilisateur.setEmail(request.getEmail());
        utilisateur.setAge(request.getAge());

        return utilisateur;
    }

    public static UtilisateurResponse versResponse(Utilisateur utilisateur) {
        return new UtilisateurResponse(
                utilisateur.getId(),
                utilisateur.getNom(),
                utilisateur.getPrenom(),
                utilisateur.getEmail()
        );
    }
}