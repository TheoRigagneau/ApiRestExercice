package com.formation.apirestdemo.exception;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
@RestControllerAdvice
public class GlobalExceptionHandler {
	/**
	 * Structure standard pour les réponses d'erreur.
	 */
	private Map<String, Object> construireErreur(HttpStatus statut, String message) {
		Map<String, Object> erreur = new HashMap<>();
		erreur.put("timestamp", LocalDateTime.now().toString());
		erreur.put("statut", statut.value());
		erreur.put("erreur", statut.getReasonPhrase());
		erreur.put("message", message);
		return erreur;
	}
	@ExceptionHandler(RessourceIntrouvableException.class)
	public ResponseEntity<Map<String, Object>> handleIntrouvable(
			RessourceIntrouvableException ex) {
		return ResponseEntity
				.status(HttpStatus.NOT_FOUND)
				.body(construireErreur(HttpStatus.NOT_FOUND, ex.getMessage()));
	}
	@ExceptionHandler(ConflitException.class)
	public ResponseEntity<Map<String, Object>> handleConflit(ConflitException ex) {
		return ResponseEntity
				.status(HttpStatus.CONFLICT)
				.body(construireErreur(HttpStatus.CONFLICT, ex.getMessage()));
	}
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Map<String, Object>> handleValidation(
			MethodArgumentNotValidException ex) {
		Map<String, String> champsEnErreur = new HashMap<>();
		ex.getBindingResult().getFieldErrors().forEach(erreur ->
				champsEnErreur.put(erreur.getField(), erreur.getDefaultMessage())
		);
		Map<String, Object> reponse = construireErreur(
				HttpStatus.BAD_REQUEST, "Validation échouée");
		reponse.put("details", champsEnErreur);
		return ResponseEntity.badRequest().body(reponse);
	}
	@ExceptionHandler(Exception.class)
	public ResponseEntity<Map<String, Object>> handleGeneral(Exception ex) {
		return ResponseEntity
				.status(HttpStatus.INTERNAL_SERVER_ERROR)
				.body(construireErreur(
						HttpStatus.INTERNAL_SERVER_ERROR,
						"Une erreur interne est survenue"));
	}
}