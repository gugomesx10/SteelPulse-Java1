package br.com.fiap.steelpulse.infrastructure.exceptions;

public class FormularioException extends RuntimeException {
    public FormularioException(String message) {
        super(message);
    }
}