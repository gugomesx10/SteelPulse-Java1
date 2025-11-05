package br.com.fiap.steelpulse.infrastructure.exceptions;

public class ListagemDeUsuarioException extends RuntimeException {
    public ListagemDeUsuarioException(String message) {
        super(message);
    }
}