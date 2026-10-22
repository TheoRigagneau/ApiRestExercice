package com.formation.apirestdemo.controller;

import com.formation.apirestdemo.dto.UtilisateurMapper;
import com.formation.apirestdemo.dto.UtilisateurRequest;
import com.formation.apirestdemo.dto.UtilisateurResponse;
import com.formation.apirestdemo.model.Utilisateur;
import com.formation.apirestdemo.service.UtilisateurService;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/utilisateurs")
public class UtilisateurController {

    private final UtilisateurService utilisateurService;

    public UtilisateurController(UtilisateurService utilisateurService) {
        this.utilisateurService = utilisateurService;
    }

    // ============================================================
    // GET /api/v1/utilisateurs — Lister tous les utilisateurs
    // ============================================================

    @GetMapping
    public ResponseEntity<List<UtilisateurResponse>> listerTous() {

        List<UtilisateurResponse> utilisateurs =
                utilisateurService.recupererTous()
                        .stream()
                        .map(UtilisateurMapper::versResponse)
                        .toList();

        return ResponseEntity.ok(utilisateurs);
    }

    // ============================================================
    // GET /api/v1/utilisateurs/{id} — Obtenir un utilisateur par ID
    // ============================================================

    @GetMapping("/{id}")
    public ResponseEntity<UtilisateurResponse> obtenirParId(
            @PathVariable Long id) {

        return utilisateurService.recupererParId(id)
                .map(UtilisateurMapper::versResponse)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // ============================================================
    // POST /api/v1/utilisateurs — Créer un utilisateur
    // ============================================================

    @PostMapping
    public ResponseEntity<UtilisateurResponse> creer(
            @Valid @RequestBody UtilisateurRequest request) {

        // Transformation du DTO Request en entité
        Utilisateur utilisateur =
                UtilisateurMapper.versEntite(request);

        // Création de l'utilisateur
        Utilisateur utilisateurCree =
                utilisateurService.creer(utilisateur);

        // Transformation de l'entité en DTO Response
        UtilisateurResponse response =
                UtilisateurMapper.versResponse(utilisateurCree);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    // ============================================================
    // PUT /api/v1/utilisateurs/{id} — Modifier un utilisateur
    // ============================================================

    @PutMapping("/{id}")
    public ResponseEntity<UtilisateurResponse> mettreAJour(
            @PathVariable Long id,
            @Valid @RequestBody UtilisateurRequest request) {

        // Transformation du DTO en entité
        Utilisateur utilisateur =
                UtilisateurMapper.versEntite(request);

        return utilisateurService.mettreAJour(id, utilisateur)
                .map(UtilisateurMapper::versResponse)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // ============================================================
    // DELETE /api/v1/utilisateurs/{id} — Supprimer un utilisateur
    // ============================================================

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> supprimer(
            @PathVariable Long id) {

        if (utilisateurService.supprimer(id)) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.notFound().build();
    }
}