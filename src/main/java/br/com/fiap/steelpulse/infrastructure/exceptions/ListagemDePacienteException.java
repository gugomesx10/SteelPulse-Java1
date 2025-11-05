package br.com.fiap.steelpulse.infrastructure.exceptions;

public class ListagemDePacienteException extends RuntimeException {
    public ListagemDePacienteException(String message) {
        super(message);
    }
}