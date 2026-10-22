package com.formation.apirestdemo.service;

import com.formation.apirestdemo.model.Utilisateur;
import com.formation.apirestdemo.repository.UtilisateurRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Service contenant la logique métier pour les utilisateurs.
 */
@Service
public class UtilisateurService {

    private final UtilisateurRepository utilisateurRepository;

    /**
     * Injection de dépendance par constructeur.
     */
    public UtilisateurService(UtilisateurRepository utilisateurRepository) {
        this.utilisateurRepository = utilisateurRepository;
    }

    /**
     * Retourne tous les utilisateurs.
     */
    public List<Utilisateur> recupererTous() {
        return utilisateurRepository.findAll();
    }

    /**
     * Retourne un utilisateur par son ID.
     */
    public Optional<Utilisateur> recupererParId(Long id) {
        return utilisateurRepository.findById(id);
    }

    /**
     * Recherche un utilisateur par son email.
     */
    public Optional<Utilisateur> recupererParEmail(String email) {
        return utilisateurRepository.findByEmail(email);
    }

    /**
     * Crée un nouvel utilisateur.
     */
    public Utilisateur creer(Utilisateur utilisateur) {
        utilisateur.setId(null);
        return utilisateurRepository.save(utilisateur);
    }

    /**
     * Modifie un utilisateur existant.
     */
    public Optional<Utilisateur> mettreAJour(
            Long id,
            Utilisateur utilisateurModifie) {

        return utilisateurRepository.findById(id)
                .map(utilisateurExistant -> {

                    utilisateurExistant.setNom(
                            utilisateurModifie.getNom()
                    );

                    utilisateurExistant.setPrenom(
                            utilisateurModifie.getPrenom()
                    );

                    utilisateurExistant.setEmail(
                            utilisateurModifie.getEmail()
                    );

                    utilisateurExistant.setAge(
                            utilisateurModifie.getAge()
                    );

                    return utilisateurRepository.save(
                            utilisateurExistant
                    );
                });
    }

    /**
     * Supprime un utilisateur par son ID.
     *
     * @return true si l'utilisateur existait,
     *         false sinon.
     */
    public boolean supprimer(Long id) {

        if (utilisateurRepository.existsById(id)) {
            utilisateurRepository.deleteById(id);
            return true;
        }

        return false;
    }
}