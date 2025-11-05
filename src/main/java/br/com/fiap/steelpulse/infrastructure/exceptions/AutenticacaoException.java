package br.com.fiap.steelpulse.infrastructure.exceptions;

public class AutenticacaoException extends RuntimeException {
    public AutenticacaoException(String message) {
        super(message);
    }
}
