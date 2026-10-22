package com.formation.apirestdemo.repository;

import com.formation.apirestdemo.model.Produit;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository pour l'entité Produit.
 *
 * En héritant de JpaRepository, Spring génère automatiquement
 * les méthodes CRUD : findAll(), findById(), save(), deleteById(), etc.
 *
 * Pas besoin d'écrire l'implémentation : Spring Data le fait pour nous.
 */
@Repository
public interface ProduitRepository extends JpaRepository<Produit, Long> {

	/**
	 * Recherche des produits dont le nom contient la chaîne donnée
	 * (insensible à la casse).
	 */
	List<Produit> findByNomContainingIgnoreCase(String nom);

	List<Produit> findByStockGreaterThan(Double stock);

	/**
	 * Recherche des produits dont le prix est inférieur ou égal
	 * au montant donné.
	 */
	List<Produit> findByPrixLessThanEqual(Double prix);

	/**
	 * Recherche avancée avec filtres et pagination.
	 */
	@Query("SELECT p FROM Produit p WHERE " +
			"(:nom IS NULL OR LOWER(p.nom) LIKE LOWER(CONCAT('%', :nom, '%'))) AND " +
			"(:prixMin IS NULL OR p.prix >= :prixMin) AND " +
			"(:prixMax IS NULL OR p.prix <= :prixMax)")
	Page<Produit> filtrer(
			@Param("nom") String nom,
			@Param("prixMin") Double prixMin,
			@Param("prixMax") Double prixMax,
			Pageable pageable
	);
}