package com.formation.apirestdemo.dto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
/**
 * DTO pour la création et la mise à jour d'un produit.
 * Ne contient pas l'ID (géré côté serveur).
 */
public class ProduitRequest {
    @NotBlank(message = "Le nom est obligatoire")
    private String nom;
    private String description;
    @Positive(message = "Le prix doit être positif")
    private Double prix;
    private Integer stock;
    // Getters et setters
    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public Double getPrix() { return prix; }
    public void setPrix(Double prix) { this.prix = prix; }
    public Integer getStock() { return stock; }
    public void setStock(Integer stock) { this.stock = stock; }
}
