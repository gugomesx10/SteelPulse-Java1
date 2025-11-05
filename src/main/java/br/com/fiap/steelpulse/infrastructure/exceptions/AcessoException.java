package br.com.fiap.steelpulse.infrastructure.exceptions;

public class AcessoException extends RuntimeException {
    public AcessoException(String message) {
        super(message);
    }
}
