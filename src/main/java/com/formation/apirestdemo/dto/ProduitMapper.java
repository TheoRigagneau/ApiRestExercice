package com.formation.apirestdemo.dto;
import com.formation.apirestdemo.dto.ProduitRequest;
import com.formation.apirestdemo.dto.ProduitResponse;
import com.formation.apirestdemo.model.Produit;
/**
 * Convertit les objets entre les couches DTO et Entity.
 * Centralise la logique de transformation.
 */
public class ProduitMapper {
    /**
     * Convertit un DTO de requête en entité JPA.
     */
    public static Produit versEntite(ProduitRequest request) {
        Produit produit = new Produit();
        produit.setNom(request.getNom());
        produit.setDescription(request.getDescription());
        produit.setPrix(request.getPrix());
        produit.setStock(request.getStock());
        return produit;
    }
    /**
     * Convertit une entité JPA en DTO de réponse.
     */
    public static ProduitResponse versResponse(Produit produit) {
        return new ProduitResponse(
                produit.getId(),
                produit.getNom(),
                produit.getDescription(),
                produit.getPrix(),
                produit.getStock()
        );
    }
}