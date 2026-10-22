package com.formation.apirestdemo.dto;
/**
 * DTO pour la réponse : ce que le client reçoit.
 * On choisit explicitement les champs à exposer.
 */
public class ProduitResponse {
    private Long id;
    private String nom;
    private String description;
    private Double prix;
    private Integer stock;
    // Constructeur à partir de l'entité (méthode de conversion)
    public ProduitResponse(Long id, String nom, String description,
                           Double prix, Integer stock) {
        this.id = id;
        this.nom = nom;
        this.description = description;
        this.prix = prix;
        this.stock = stock;
    }
    // Getters (pas de setters : le DTO de réponse est en lecture seule)
    public Long getId() { return id; }
    public String getNom() { return nom; }
    public String getDescription() { return description; }
    public Double getPrix() { return prix; }
    public Integer getStock() { return stock; }
}