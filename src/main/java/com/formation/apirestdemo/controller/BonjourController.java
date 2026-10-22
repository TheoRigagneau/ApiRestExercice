package com.formation.apirestdemo.controller;

import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/bonjour")
public class BonjourController {

    // GET /api/v1/bonjour
    @GetMapping
    public Map<String, String> bonjour() {
        return Map.of("message", "Bonjour le monde !");
    }

    // GET /api/v1/bonjour/{prenom}
    @GetMapping("/{prenom}")
    public Map<String, String> bonjourPrenom(@PathVariable String prenom) {
        return Map.of("message", "Bonjour " + prenom + " !");
    }

    // POST /api/v1/bonjour
    @PostMapping
    public Map<String, String> bonjourPost(
            @RequestBody Map<String, String> body) {

        String prenom = body.get("prenom");

        return Map.of("message", "Bienvenue " + prenom + " !");
    }
}