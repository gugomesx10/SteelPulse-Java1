package br.com.fiap.steelpulse.infrastructure.exceptions;

public class LoginException extends RuntimeException {
    public LoginException(String message) {
        super(message);
    }
}
