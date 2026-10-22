package com.formation.apirestdemo.exception;
/**
 * Exception levée quand une ressource demandée n'existe pas.
 */
public class RessourceIntrouvableException extends RuntimeException {
    public RessourceIntrouvableException(String typeRessource, Long id) {
        super(typeRessource + " avec l'id " + id + " introuvable");
    }
}
