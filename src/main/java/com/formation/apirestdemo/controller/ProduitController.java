package com.formation.apirestdemo.controller;

import com.formation.apirestdemo.dto.ProduitMapper;
import com.formation.apirestdemo.dto.ProduitRequest;
import com.formation.apirestdemo.dto.ProduitResponse;
import com.formation.apirestdemo.model.Produit;
import com.formation.apirestdemo.service.ProduitService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import jakarta.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/produits")
@Tag(
		name = "Produits",
		description = "Gestion des produits du catalogue"
)
public class ProduitController {

	private final ProduitService produitService;

	public ProduitController(ProduitService produitService) {
		this.produitService = produitService;
	}

	// ============================================================
	// GET /api/v1/produits — Lister tous les produits
	// ============================================================

	@Operation(
			summary = "Lister tous les produits",
			description = "Retourne la liste paginée des produits. "
					+ "Paramètres : page (défaut 0), size (défaut 10), sort."
	)
	@ApiResponses({
			@ApiResponse(
					responseCode = "200",
					description = "Liste retournée avec succès"
			)
	})
	@GetMapping
	public ResponseEntity<Page<ProduitResponse>> listerTous(
			@PageableDefault(size = 10, sort = "nom") Pageable pageable) {

		Page<ProduitResponse> page = produitService
				.recupererTous(pageable)
				.map(ProduitMapper::versResponse);

		return ResponseEntity.ok(page);
	}

	// ============================================================
	// GET /api/v1/produits/{id} — Obtenir un produit par son ID
	// ============================================================

	@Operation(
			summary = "Obtenir un produit par son ID"
	)
	@ApiResponses({
			@ApiResponse(
					responseCode = "200",
					description = "Produit trouvé"
			),
			@ApiResponse(
					responseCode = "404",
					description = "Produit introuvable"
			)
	})
	@GetMapping("/{id}")
	public ResponseEntity<ProduitResponse> obtenirParId(

			@Parameter(
					description = "Identifiant du produit",
					example = "1"
			)
			@PathVariable Long id) {

		return produitService.recupererParId(id)
				.map(ProduitMapper::versResponse)
				.map(ResponseEntity::ok)
				.orElse(ResponseEntity.notFound().build());
	}

	// ============================================================
	// POST /api/v1/produits — Créer un nouveau produit
	// ============================================================

	@Operation(
			summary = "Créer un produit"
	)
	@ApiResponses({
			@ApiResponse(
					responseCode = "201",
					description = "Produit créé avec succès"
			),
			@ApiResponse(
					responseCode = "400",
					description = "Données invalides - voir le détail des champs en erreur"
			)
	})
	@PostMapping
	public ResponseEntity<ProduitResponse> creer(
			@Valid @RequestBody ProduitRequest request) {

		Produit entite = ProduitMapper.versEntite(request);

		Produit produitCree = produitService.creer(entite);

		ProduitResponse response = ProduitMapper.versResponse(produitCree);

		return ResponseEntity
				.status(HttpStatus.CREATED)
				.body(response);
	}

	// ============================================================
	// PUT /api/v1/produits/{id} — Mettre à jour un produit
	// ============================================================

	@Operation(
			summary = "Mettre à jour un produit"
	)
	@ApiResponses({
			@ApiResponse(
					responseCode = "200",
					description = "Produit mis à jour avec succès"
			),
			@ApiResponse(
					responseCode = "404",
					description = "Produit introuvable"
			),
			@ApiResponse(
					responseCode = "400",
					description = "Données invalides"
			)
	})
	@PutMapping("/{id}")
	public ResponseEntity<Produit> mettreAJour(
			@Parameter(
					description = "Identifiant du produit",
					example = "1"
			)
			@PathVariable Long id,

			@Valid @RequestBody Produit produit) {

		return produitService.mettreAJour(id, produit)
				.map(ResponseEntity::ok)
				.orElse(ResponseEntity.notFound().build());
	}

	// ============================================================
	// DELETE /api/v1/produits/{id} — Supprimer un produit
	// ============================================================

	@Operation(
			summary = "Supprimer un produit"
	)
	@ApiResponses({
			@ApiResponse(
					responseCode = "204",
					description = "Produit supprimé avec succès"
			),
			@ApiResponse(
					responseCode = "404",
					description = "Produit introuvable"
			)
	})
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> supprimer(
			@Parameter(
					description = "Identifiant du produit",
					example = "1"
			)
			@PathVariable Long id) {

		if (produitService.supprimer(id)) {
			return ResponseEntity.noContent().build();
		}

		return ResponseEntity.notFound().build();
	}

	// ============================================================
	// GET /api/v1/produits/recherche?nom=clavier
	// — Rechercher par nom
	// ============================================================

	@Operation(
			summary = "Rechercher des produits par nom",
			description = "Recherche partielle et insensible à la casse."
	)
	@ApiResponses({
			@ApiResponse(
					responseCode = "200",
					description = "Recherche effectuée avec succès"
			)
	})
	@GetMapping("/recherche")
	public ResponseEntity<Page<ProduitResponse>> rechercher(

			@Parameter(
					description = "Nom ou partie du nom du produit",
					example = "clavier"
			)
			@RequestParam(required = false) String nom,

			@Parameter(
					description = "Prix minimum",
					example = "10"
			)
			@RequestParam(required = false) Double prixMin,

			@Parameter(
					description = "Prix maximum",
					example = "100"
			)
			@RequestParam(required = false) Double prixMax,

			@PageableDefault(size = 10, sort = "nom")
			Pageable pageable) {

		Page<ProduitResponse> page = produitService
				.rechercherAvancee(nom, prixMin, prixMax, pageable)
				.map(ProduitMapper::versResponse);

		return ResponseEntity.ok(page);
	}


	@Operation(
			summary = "Rechercher les produits selon leur stock",
			description = "Retourne les produits dont le stock est supérieur à la quantité indiquée."
	)
	@ApiResponses({
			@ApiResponse(
					responseCode = "200",
					description = "Recherche effectuée avec succès"
			)
	})
	@GetMapping("/stock")
	public List<Produit> quantity(

			@Parameter(
					description = "Quantité minimale de stock",
					example = "10"
			)
			@RequestParam double quantity) {

		return produitService.quantity(quantity);
	}

}