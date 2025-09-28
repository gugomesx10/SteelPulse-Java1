package br.com.fiap.steelpulse.domain.exceptions;

public class EntidadeNaoLocalizada extends Exception{

    private static Throwable cause ;

    public EntidadeNaoLocalizada(String message) {
        super(message, cause);
    }
}
